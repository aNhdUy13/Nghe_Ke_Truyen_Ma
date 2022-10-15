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
import androidx.multidex.BuildConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nda.ngheketruyenma.R;
import com.nda.ngheketruyenma.databinding.FragmentSettingBinding;
import com.nda.ngheketruyenma.ui.setting.billing.GetCoin;
import com.nda.ngheketruyenma.ui.setting.premium.PremiumFunction;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SettingFragment extends Fragment {


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

//        cv_goToPremium.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), PremiumFunction.class));
//            }
//        });
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
//        cv_goToPremium          = (CardView) root.findViewById(R.id.cv_goToPremium);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}