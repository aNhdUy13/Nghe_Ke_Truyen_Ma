package com.nda.ngheketruyenma.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nda.ngheketruyenma.R;

public class ConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (isConnected(context))
        {
            //Toast.makeText(context, "Đã Kết Nối Với Internet", Toast.LENGTH_SHORT).show();
        }
        else
        {
            dialogDisconnected(context);
        }
    }

    private void dialogDisconnected(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.check_internet_connection);
        Button txt_Continue = (Button) dialog.findViewById(R.id.btn_Continue);
        Button txt_CancelConnection = (Button) dialog.findViewById(R.id.btn_CancelConnection);

        txt_Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        txt_CancelConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public boolean isConnected(Context context)
    {
        try
        {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;

        }

    }
}
