package com.nda.ngheketruyenma.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.ngheketruyenma.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterHomesHorizontal extends RecyclerView.Adapter<AdapterHomesHorizontal.HomesHorizontalViewHolder>{

    private HomeFragment context;
    private List<HomesHorizontal> homesHorizontalList;

    public AdapterHomesHorizontal(HomeFragment context, List<HomesHorizontal> homesHorizontalList) {
        this.context = context;
        this.homesHorizontalList = homesHorizontalList;
    }

    @NotNull
    @Override
    public HomesHorizontalViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getContext()).inflate(R.layout.item_homes_horizontal,parent,false);

        return new AdapterHomesHorizontal.HomesHorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomesHorizontalViewHolder holder, int position) {

        HomesHorizontal homesHorizontal = homesHorizontalList.get(position);

        if (holder == null)
        {return;}

        holder.txt_ShowStoriesNameHorizontal.setText(homesHorizontal.getName());

        String imgUrl = null;
        imgUrl = homesHorizontal.getImage();
        Picasso.get().load(imgUrl).into(holder.img_itemHomeHorizontal);

        holder.ll_item_homeHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.gotoDetail(homesHorizontal.getAuthor(),homesHorizontal.getName(),homesHorizontal.getContent(),
                        homesHorizontal.getSource(),  homesHorizontal.getImage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return homesHorizontalList.size();
    }

    public class HomesHorizontalViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout ll_item_homeHorizontal;
        ImageView img_itemHomeHorizontal;
        TextView txt_ShowStoriesNameHorizontal;
        public HomesHorizontalViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txt_ShowStoriesNameHorizontal = (TextView) itemView.findViewById(R.id.txt_ShowStoriesNameHorizontal);
            img_itemHomeHorizontal = (ImageView) itemView.findViewById(R.id.img_itemHomeHorizontal);
            ll_item_homeHorizontal = (RelativeLayout) itemView.findViewById(R.id.ll_item_homeHorizontal);

        }
    }
}
