package com.nda.ngheketruyenma.ui.setting.premium;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.ngheketruyenma.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterPremium extends RecyclerView.Adapter<AdapterPremium.HolderPremium>{

    private PremiumFunction context;
    private List<Premium> premiumList;

    public AdapterPremium(PremiumFunction context, List<Premium> premiumList) {
        this.context = context;
        this.premiumList = premiumList;
    }

    @NonNull
    @NotNull
    @Override
    public HolderPremium onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_back_ground,parent,false);

        return new HolderPremium(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HolderPremium holder, int position) {
        Premium premium = premiumList.get(position);

        if (holder == null)
        {return;}

        String imgUrl = null;
        imgUrl = premium.getImgBG();
        Picasso.get().load(imgUrl).into(holder.img_backGround);

        holder.img_backGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //context.changeBackground(premium.getImgBG());
            }
        });
    }

    @Override
    public int getItemCount() {
        return premiumList.size();
    }

    public class HolderPremium extends RecyclerView.ViewHolder {
        ImageView img_backGround;
        public HolderPremium(@NonNull @NotNull View itemView) {
            super(itemView);
            img_backGround  = (ImageView) itemView.findViewById(R.id.img_backGround);
        }
    }
}
