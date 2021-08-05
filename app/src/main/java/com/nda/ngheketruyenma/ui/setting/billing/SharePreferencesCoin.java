package com.nda.ngheketruyenma.ui.setting.billing;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class SharePreferencesCoin extends AppCompatActivity{

    public int coin;
    private static final String KEY = "coinValue";
    private static final String SAVE_COIN = "SAVE_COIN";
    private Context mContext;

    public SharePreferencesCoin(Context mContext) {
        this.mContext = mContext;
    }

    public void saveData()
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SAVE_COIN,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY,coin);
        editor.apply();
    }

    public void getData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SAVE_COIN,MODE_PRIVATE);
        coin = sharedPreferences.getInt(KEY,MODE_PRIVATE);
    }


}
