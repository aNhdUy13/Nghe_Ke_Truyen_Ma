package com.nda.ngheketruyenma.ui.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.ConsumeResponseListener;
import com.nda.ngheketruyenma.R;
import com.nda.ngheketruyenma.databinding.FragmentHomeBinding;
import com.nda.ngheketruyenma.databinding.FragmentSettingBinding;
import com.nda.ngheketruyenma.ui.home.HomeViewModel;


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
                startActivity(new Intent(getContext(),GetCoin.class));
            }
        });
    }
    private void shareApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String shareBody = "";
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}