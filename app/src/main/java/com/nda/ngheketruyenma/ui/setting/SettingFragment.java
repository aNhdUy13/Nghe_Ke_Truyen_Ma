package com.nda.ngheketruyenma.ui.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nda.ngheketruyenma.R;
import com.nda.ngheketruyenma.databinding.FragmentHomeBinding;
import com.nda.ngheketruyenma.databinding.FragmentSettingBinding;
import com.nda.ngheketruyenma.ui.home.HomeViewModel;


public class SettingFragment extends Fragment {


    private SettingViewModel settingViewModel;
    private FragmentSettingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}