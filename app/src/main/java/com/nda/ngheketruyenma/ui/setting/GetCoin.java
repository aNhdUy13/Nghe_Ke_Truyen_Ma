package com.nda.ngheketruyenma.ui.setting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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
import com.nda.ngheketruyenma.R;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class GetCoin extends AppCompatActivity implements PurchasesUpdatedListener {

    BillingClient billingClient;
    ConsumeResponseListener listener;

    TextView txtPremium;
    RecyclerView rcvGetCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_coin);

        setUpBillingClient();
        initiate();

    }

    private void initiate() {
        txtPremium  = (TextView) findViewById(R.id.txtPremium);
        rcvGetCoin  = (RecyclerView) findViewById(R.id.rcvGetCoin);
        rcvGetCoin.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvGetCoin.setLayoutManager(layoutManager);
        rcvGetCoin.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));

        //event
        if (billingClient.isReady())
        {
            SkuDetailsParams params = SkuDetailsParams.newBuilder()
                    .setSkusList(Arrays.asList("1_coin"))
                    .setType(BillingClient.SkuType.INAPP)
                    .build();
            billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {
                @Override
                public void onSkuDetailsResponse(@NonNull @NotNull BillingResult billingResult,
                                                 @Nullable @org.jetbrains.annotations.Nullable List<SkuDetails> list) {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
                    {
                        loadProductToRecycleView(list);
                    }
                    else
                    {
                        Toast.makeText(GetCoin.this, "Error : ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void loadProductToRecycleView(List<SkuDetails> list) {
        MyProductAdapter adapter = new MyProductAdapter(this,list,billingClient);
        rcvGetCoin.setAdapter(adapter);
    }

    private void setUpBillingClient() {
        listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull @NotNull BillingResult billingResult, @NonNull @NotNull String s) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
                {
                    Toast.makeText(GetCoin.this, "Comsume OKE", Toast.LENGTH_SHORT).show();
                }
            }
        };

        billingClient = BillingClientSetup.getInstance(this,this);
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull @NotNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
                {
                    Toast.makeText(GetCoin.this, "Success connect to billing !", Toast.LENGTH_SHORT).show();

                    //Query
                    List<Purchase> purchases = billingClient.queryPurchases(BillingClient.SkuType.INAPP)
                            .getPurchasesList();
                    handleItemAlreadyPurchase(purchases);
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
        StringBuilder purchaseItem = new StringBuilder(txtPremium.getText());
        for (Purchase purchase : purchases)
        {
            if (purchase.getSku().equals("1_coin"))
            {
                ConsumeParams consumeParams = ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();
                billingClient.consumeAsync(consumeParams,listener);
            }
            purchaseItem.append("\n" + purchase.getSku())
                    .append("\n");
        }
        txtPremium.setText(purchaseItem.toString());
        txtPremium.setVisibility(View.VISIBLE);
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
}