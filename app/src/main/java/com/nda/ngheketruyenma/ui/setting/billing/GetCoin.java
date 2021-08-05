package com.nda.ngheketruyenma.ui.setting.billing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.nda.ngheketruyenma.BuildConfig;
import com.nda.ngheketruyenma.R;
import com.nda.ngheketruyenma.ui.home.nativeAds.AdapterWithNativeAd;
import com.nda.ngheketruyenma.ui.setting.premium.PremiumFunction;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetCoin extends AppCompatActivity implements PurchasesUpdatedListener {
    private static final String LOG_TAG = "native Ads";
    /*
            Regarding native ads
         */
    @Nullable
    protected AdapterWithNativeAd adapter;
    RecyclerView rcx_nativeAds_getCoin;
    LinearLayout ll_nativeAdsGetCoin;
    /*
        ( END ) Regarding native ads
     */


    RecyclerView rcvGetCoin;
    MyProductAdapter myProductAdapter;

    ImageView img_Back, img_premium;

    public static int coin;
    @SuppressLint("StaticFieldLeak")
    public static TextView txtXu;
    //note add unique product ids
    //use same id for preference key
    private static List<String> skuList = new ArrayList() {{
        add("1_coin");
        add("2_coin");
        add("3_coin");
        add("4_coin");
        add("5_coin");

    }};

    private BillingClient billingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_coin);

        ll_nativeAdsGetCoin = (LinearLayout) findViewById(R.id.ll_nativeAdsGetCoin);
        txtXu   = (TextView) findViewById(R.id.txtXu);
        rcvGetCoin  = (RecyclerView) findViewById(R.id.rcvGetCoin);
        img_premium = (ImageView) findViewById(R.id.img_premium);

        img_premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetCoin.this, PremiumFunction.class));
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GetCoin.this,RecyclerView.VERTICAL,false);
        rcvGetCoin.setLayoutManager(linearLayoutManager);
//      rcvGetCoin.setHasFixedSize(true);


        loadCoin();

        img_Back = (ImageView) findViewById(R.id.img_Back);
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setUpBillingClient();

        nativeAds();

    }

    private void setUpBillingClient() {
        // Establish connection to billing client
        //check purchase status from google play store cache on every app start
        billingClient = BillingClient.newBuilder(this)
                .enablePendingPurchases().setListener(this).build();
//        billingClient = BillingClientSetup.getInstance(this,this);
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull @NotNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
                {
//                    Toast.makeText(GetCoin.this, "Success connect to billing !", Toast.LENGTH_SHORT).show();
                    initiate();
                    Purchase.PurchasesResult queryPurchase = billingClient.queryPurchases(BillingClient.SkuType.INAPP);

                    List<Purchase> queryPurchases = queryPurchase.getPurchasesList();
                    if(queryPurchases!=null && queryPurchases.size()>0){
                        handleItemAlreadyPurchase(queryPurchases);
                    }
//                    //Query
//                    List<Purchase> purchases = billingClient.queryPurchases(BillingClient.SkuType.INAPP)
//                            .getPurchasesList();
//                    handleItemAlreadyPurchase(purchases);
                }
                else
                {
                    Toast.makeText(GetCoin.this, "Error Code:" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(GetCoin.this, "You're disconnected !", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void handleItemAlreadyPurchase(List<Purchase> purchases) {

        for(Purchase purchase:purchases) {

            final int index=skuList.indexOf(purchase.getSku());
            //purchase found
            if(index>-1) {

                //if item is purchased
                if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED)
                {
                    if (!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature())) {
                        // Invalid purchase
                        // show error to user
                        Toast.makeText(getApplicationContext(), "Error : Invalid Purchase", Toast.LENGTH_SHORT).show();
                        continue;//skip current iteration only because other items in purchase list must be checked if present
                    }
                    // else purchase is valid
                    //if item is purchased and not consumed
                    if (!purchase.isAcknowledged()) {
                        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();

                        billingClient.consumeAsync(consumeParams, new ConsumeResponseListener() {
                            @Override
                            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                                    Toast.makeText(getApplicationContext(), "After : " + index , Toast.LENGTH_SHORT).show();

                                    if (index == 0)
                                    {
                                        coin = coin + 1;
                                        txtXu.setText(String.valueOf(coin));
                                    }
                                    if (index == 1)
                                    {
                                        coin = coin + 2;
                                        txtXu.setText(String.valueOf(coin));
                                    }
                                    if (index == 2)
                                    {
                                        coin = coin + 3;
                                        txtXu.setText(String.valueOf(coin));
                                    }
                                    if (index == 3)
                                    {
                                        coin = coin + 4;
                                        txtXu.setText(String.valueOf(coin));
                                    }
                                    if (index == 4)
                                    {
                                        coin = coin + 5;
                                        txtXu.setText(String.valueOf(coin));
                                    }
//                                    int consumeCountValue=getPurchaseCountValueFromPref(skuList.get(index))+1;
//                                    savePurchaseCountValueToPref(purchaseItemIDs.get(index),consumeCountValue);
//                                    Toast.makeText(getApplicationContext(), "Item "+ skuList.get(index) +"Consumed", Toast.LENGTH_SHORT).show();
//                                    notifyList();
                                }
                            }
                        });
                    }
                }
                //if purchase is pending
                else if(  purchase.getPurchaseState() == Purchase.PurchaseState.PENDING)
                {
                    Toast.makeText(getApplicationContext(),
                            skuList.get(index)+" Purchase is Pending. Please complete Transaction", Toast.LENGTH_SHORT).show();
                }
                //if purchase is refunded or unknown
                else if( purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE)
                {
                    Toast.makeText(getApplicationContext(), skuList.get(index)+" Purchase Status Unknown", Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

    private void saveCoin()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("saveCoin",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("coinValue",coin);
        editor.apply();
    }

    private void loadCoin()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("saveCoin",MODE_PRIVATE);
        coin = sharedPreferences.getInt("coinValue",MODE_PRIVATE);

        txtXu.setText(String.valueOf(coin));
    }
    private void initiate() {
        //event
        if (billingClient.isReady())
        {
            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
            params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);

            billingClient.querySkuDetailsAsync(params.build(),
                    new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(@NotNull BillingResult billingResult,
                                                         List<SkuDetails> skuDetailsList) {
                            // Process the result.
                            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
                            {
                                if (skuDetailsList != null && skuDetailsList.size() > 0) {

                                    loadProductToRecycleView(skuDetailsList);

                                }
                            }
                            else
                            {
                                Toast.makeText(GetCoin.this, "Error : ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

    private void loadProductToRecycleView(List<SkuDetails> skuDetailsList) {
        myProductAdapter = new MyProductAdapter(GetCoin.this,skuDetailsList,billingClient);

        rcvGetCoin.setAdapter(myProductAdapter);
    }

    @Override
    public void onPurchasesUpdated(@NonNull @NotNull BillingResult billingResult,
                                   @Nullable @org.jetbrains.annotations.Nullable List<Purchase> list) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null)
        {
            handleItemAlreadyPurchase(list);
        }
        else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED)
        {
            Toast.makeText(this, "User Has Been Cancelled !", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Error : " + billingResult.getResponseCode(), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Verifies that the purchase was signed correctly for this developer's public key.
     * <p>Note: It's strongly recommended to perform such check on your backend since hackers can
     * replace this method with "constant true" if they decompile/rebuild your app.
     * </p>
     */
    private boolean verifyValidSignature(String signedData, String signature) {
        try {
            //for old playconsole
            // To get key go to Developer Console > Select your app > Development Tools > Services & APIs.
            //for new play console
            //To get key go to Developer Console > Select your app > Monetize > Monetization setup

            String base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhLDNARIFbYB5CmNvhd4uI7vKJXZgaOaN6/c5EFinJkE7S6cnjjhUFdIGHTHcO5C97ILY/xTKOxXW2P/5P4Yx4zcAOmvvljwotmGc3AMy360cVV+CwSTHkKnJt+9qsMamNd7Ks69Rx5oZO20ksTPf2DP09NI4VEz7xJ+UfymzS+m37/ZYzxKd0bP0uKBbBmKamDQMiKEFjsTDIUgtZ4itTeM1TSTPWL2DhibPzel2BVzciwpFdPIEC2xLMTnIgAeDqVl7ylcEk17d5NU4BlDJOrWaAEqv2O7rfGCoE7w7yStQ+4HxIohynmpDkA6vu/Hlf2BYjhAw9dbpK9LWEo6AyQIDAQAB";
            return SecurityBilling.verifyPurchase(base64Key, signedData, signature);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(billingClient!=null){
            billingClient.endConnection();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveCoin();
    }

    private void nativeAds() {
        // NOTE always use test ads during development and testing
        //StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);

//        setContentView(R.layout.recycler_view);

        rcx_nativeAds_getCoin = findViewById(R.id.rcx_nativeAds_getCoin);
        rcx_nativeAds_getCoin.setLayoutManager(new LinearLayoutManager(GetCoin.this, RecyclerView.VERTICAL, false));
        rcx_nativeAds_getCoin.setAdapter(adapter = new AdapterWithNativeAd(GetCoin.this));

        loadData();
        loadNativeAd();
    }

    private void loadNativeAd() {
        final StartAppNativeAd nativeAd = new StartAppNativeAd(GetCoin.this);

        nativeAd.loadAd(new NativeAdPreferences()
                .setAdsNumber(1)
                .setAutoBitmapDownload(true)
                .setPrimaryImageSize(2), new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                if (adapter != null) {
                    ll_nativeAdsGetCoin.setVisibility(View.VISIBLE);
                    adapter.setNativeAd(nativeAd.getNativeAds());
                }
            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
                if (BuildConfig.DEBUG) {
                    Log.v(LOG_TAG, "onFailedToReceiveAd: " + ad.getErrorMessage());
                }
            }
        });
    }

    // TODO example of loading JSON array, change this code according to your needs
    @UiThread
    private void loadData() {
        if (adapter != null) {
//            adapter.setData(Collections.singletonList("Loading..."));
        }

        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            @WorkerThread
            public void run() {
                String url = "https://raw.githubusercontent.com/StartApp-SDK/StartApp_InApp_SDK_Example/master/app/data.json";

                final List<String> data = new ArrayList<>();

                try (InputStream is = new URL(url).openStream()) {
                    if (is != null) {
                        JsonReader reader = new JsonReader(new InputStreamReader(is));
                        reader.beginArray();

                        while (reader.peek() == JsonToken.STRING) {
                            data.add(reader.nextString());
                        }

                        reader.endArray();
                    }
                } catch (RuntimeException | IOException ex) {
                    data.clear();
                    data.add(ex.toString());
                } finally {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (adapter != null) {
//                                adapter.setData(data);
//                            }
//                        }
//                    });
                }
            }
        });
    }
}