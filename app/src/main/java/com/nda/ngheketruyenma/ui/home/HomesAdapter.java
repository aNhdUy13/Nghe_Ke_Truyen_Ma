package com.nda.ngheketruyenma.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.ngheketruyenma.MainActivity;
import com.nda.ngheketruyenma.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomesAdapter extends RecyclerView.Adapter<HomesAdapter.HomesViewHolder>{

    private HomeFragment context;
    private List<Homes> homesList;

    public HomesAdapter(HomeFragment context, List<Homes> homesList) {
        this.context = context;
        this.homesList = homesList;
    }

    @NotNull
    @Override
    public HomesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getContext()).inflate(R.layout.item_homes,parent,false);

        return new HomesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomesViewHolder holder, int position) {
        Homes homes = homesList.get(position);

        if (holder == null)
        {return;}

        holder.txt_ShowStoriesName.setText(homes.getName());

        String imgUrl = null;
        imgUrl = homes.getImage();
        Picasso.get().load(imgUrl).into(holder.img_itemHome);

        holder.ll_item_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.gotoDetail(homes.getAuthor(),homes.getName(),homes.getContent(),homes.getSource(),
                        homes.getImage());
            }
        });

    }

    @Override
    public int getItemCount() {
        return homesList.size();
    }

    public class HomesViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_item_home;
        ImageView img_itemHome;
        TextView txt_ShowStoriesName;
        public HomesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txt_ShowStoriesName = (TextView) itemView.findViewById(R.id.txt_ShowStoriesName);
            img_itemHome = (ImageView) itemView.findViewById(R.id.img_itemHome);
            ll_item_home = (LinearLayout) itemView.findViewById(R.id.ll_item_home);

        }
    }
}
