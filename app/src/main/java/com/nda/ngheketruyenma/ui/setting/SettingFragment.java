package com.nda.ngheketruyenma.ui.setting;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nda.ngheketruyenma.BuildConfig;
import com.nda.ngheketruyenma.R;
import com.nda.ngheketruyenma.databinding.FragmentSettingBinding;
import com.nda.ngheketruyenma.ui.home.nativeAds.AdapterWithNativeAd;
import com.nda.ngheketruyenma.ui.setting.billing.GetCoin;
import com.nda.ngheketruyenma.ui.setting.premium.PremiumFunction;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SettingFragment extends Fragment {

    private static final String LOG_TAG = "native Ads";
    /*
            Regarding native ads
         */
    @Nullable
    protected AdapterWithNativeAd adapter;
    RecyclerView rcv_nativeAdsSetting;
    CardView cv_nativeAdsSetting, cv_goToPremium;
    /*
        ( END ) Regarding native ads
     */

    
    CardView cvShareApp, cvGetCoin;
    private SettingViewModel settingViewModel;
    private FragmentSettingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapting(root);
        initiate();
        nativeAds();

        return root;
    }


    private void initiate() {
        cvShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });

        cvGetCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GetCoin.class));
            }
        });

        cv_goToPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PremiumFunction.class));
            }
        });
    }
    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String shareBody = "https://play.google.com/store/apps/details?id=com.nda.ngheketruyenma";
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);


        startActivity(Intent.createChooser(intent, getString(R.string.share_using)));

    }

    private void mapting(View root) {
        cvShareApp  = (CardView) root.findViewById(R.id.cvShareApp);
        cvGetCoin  = (CardView) root.findViewById(R.id.cvGetCoin);

        rcv_nativeAdsSetting    = (RecyclerView) root.findViewById(R.id.rcv_nativeAdsSetting);
        cv_nativeAdsSetting     = (CardView) root.findViewById(R.id.cv_nativeAdsSetting);

        cv_goToPremium          = (CardView) root.findViewById(R.id.cv_goToPremium);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void nativeAds() {
        // NOTE always use test ads during development and testing
        //StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);

//        setContentView(R.layout.recycler_view);

        rcv_nativeAdsSetting.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rcv_nativeAdsSetting.setAdapter(adapter = new AdapterWithNativeAd(getContext()));

        loadData();
        loadNativeAd();
    }

    private void loadNativeAd() {
        final StartAppNativeAd nativeAd = new StartAppNativeAd(getContext());

        nativeAd.loadAd(new NativeAdPreferences()
                .setAdsNumber(1)
                .setAutoBitmapDownload(true)
                .setPrimaryImageSize(2), new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                if (adapter != null) {
                    cv_nativeAdsSetting.setVisibility(View.VISIBLE);
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