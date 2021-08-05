package com.nda.ngheketruyenma.ui.setting.premium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nda.ngheketruyenma.R;
import com.nda.ngheketruyenma.ui.home.Homes;
import com.nda.ngheketruyenma.ui.setting.billing.GetCoin;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PremiumFunction extends AppCompatActivity {
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef  = database.getReference("Wallpaper");

    RelativeLayout rl_gallery, rl_home,rl_setting;
    ImageView img_Back;
//    RecyclerView rcv_premiumFunction;

//    List<Premium> premiumList = new ArrayList<>();
//    AdapterPremium adapterPremium;

    TextView txt_premium_xu;
    CardView cv_bd1, cv_bd2,cv_bd3,cv_bd4,cv_bd5;
    ImageView bd_1,bd_2,bd_3,bd_4,bd_5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_function);

        mapting();
//        setUpRecycleView();
        initiate();
    }

//    private void setUpRecycleView() {
//
//        adapterPremium = new AdapterPremium(this, premiumList);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PremiumFunction.this
//                ,RecyclerView.HORIZONTAL,false);
//        rcv_premiumFunction.setLayoutManager(linearLayoutManager);
//        rcv_premiumFunction.setAdapter(adapterPremium);
//
//    }



    public void changeBackground(ImageView imgToChange)
    {
        Dialog dialog = new Dialog(PremiumFunction.this);
        dialog.setContentView(R.layout.dialog_change_background);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_changeBackground = (Button) dialog.findViewById(R.id.btn_changeBackground);
        btn_changeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataChanged(imgToChange);
            }
        });


        dialog.show();
    }

    private void checkDataChanged(ImageView imgToChange) {


        Drawable drawable = imgToChange.getDrawable();


        if (GetCoin.coin >= 3)
        {
            GetCoin.coin = GetCoin.coin - 3;
            GetCoin.txtXu.setText(String.valueOf(GetCoin.coin));
            txt_premium_xu.setText(String.valueOf(GetCoin.coin));


            Toast.makeText(this, "Thay Đổi Thành Công", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Không Đủ Xu", Toast.LENGTH_SHORT).show();

        }
        //Toast.makeText(this, "After trừ : " + GetCoin.coin, Toast.LENGTH_SHORT).show();
    }

    private void initiate() {
        txt_premium_xu.setText(String.valueOf(GetCoin.coin));
//        getAllWallpaper();
        img_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cv_bd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(bd_1);
            }
        });

//        cv_bd2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeBackground(bd_2);
//            }
//        });

        cv_bd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(bd_3);
            }
        });

        cv_bd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(bd_4);
            }
        });

        cv_bd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBackground(bd_5);
            }
        });
    }

//    private void getAllWallpaper() {
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren())
//                {
//                    Premium premium =dataSnapshot.getValue(Premium.class);
//
//                    premiumList.add(premium);
//                }
//
//                adapterPremium.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void mapting() {
        img_Back    = (ImageView) findViewById(R.id.img_Back);
//        rcv_premiumFunction = (RecyclerView) findViewById(R.id.rcv_premiumFunction);

        cv_bd1      = (CardView) findViewById(R.id.cv_bd1);
//        cv_bd2      = (CardView) findViewById(R.id.cv_bd2);
        cv_bd3      = (CardView) findViewById(R.id.cv_bd3);
        cv_bd4      = (CardView) findViewById(R.id.cv_bd4);
        cv_bd5      = (CardView) findViewById(R.id.cv_bd5);

        bd_1        = (ImageView) findViewById(R.id.bd_1);
//        bd_2        = (ImageView) findViewById(R.id.bd_2);
        bd_3        = (ImageView) findViewById(R.id.bd_3);
        bd_4        = (ImageView) findViewById(R.id.bd_4);
        bd_5        = (ImageView) findViewById(R.id.bd_5);

        txt_premium_xu  = (TextView) findViewById(R.id.txt_premium_xu);

        rl_gallery      = (RelativeLayout) findViewById(R.id.rl_gallery);
        rl_home         = (RelativeLayout) findViewById(R.id.rl_home);
        rl_setting      = (RelativeLayout) findViewById(R.id.rl_setting);

    }
}