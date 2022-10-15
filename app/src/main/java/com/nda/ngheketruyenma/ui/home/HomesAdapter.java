package com.nda.ngheketruyenma.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.ngheketruyenma.MainActivity;
import com.nda.ngheketruyenma.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomesAdapter extends RecyclerView.Adapter<HomesAdapter.HomesViewHolder> implements Filterable {
    private static final int TYPE_NATIVE_AD = 0;
    private static final int TYPE_DATA = 1;

    private HomeFragment context;
    private List<Homes> homesList;

    private List<Homes> homesListToSearch;
    public HomesAdapter(HomeFragment context, List<Homes> homesList) {
        this.context = context;
        this.homesList = homesList;

        this.homesListToSearch = homesList;
    }

    /*
        Setup native ads
     */

//    @Nullable
//    private List<NativeAdDetails> nativeAd;
//
//    @Nullable
//    private List<String> data;
//
//    public void setNativeAd(@Nullable List<NativeAdDetails> nativeAd) {
//        this.nativeAd = nativeAd;
//
//        notifyDataSetChanged();
//    }
//    public void setData(@Nullable List<String> data) {
//        this.data = data;
//
//        notifyDataSetChanged();
//    }

    /*
            (END) Setup native ads
     */
    @Override
    public int getItemCount() {
//        int result = 0;
//
//        if (nativeAd != null) {
//            result += nativeAd.size();
//        }
//
//        if (data != null) {
//            result += data.size();
//        }


        return homesList.size();

//        return homesList.size();
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
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearach = constraint.toString();
                if (strSearach.isEmpty())
                {
                    homesList = homesListToSearch;
                }
                else
                {
                    List<Homes> homesList2 =new ArrayList<>();
                    for (Homes homes : homesListToSearch)
                    {
                        if (homes.getName().toLowerCase().contains(strSearach.toLowerCase()))
                        {
                            homesList2.add(homes);
                        }
                    }

                    homesList = homesList2;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = homesList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                homesList = (ArrayList<Homes>) results.values;

                notifyDataSetChanged();
            }
        };    }
}
