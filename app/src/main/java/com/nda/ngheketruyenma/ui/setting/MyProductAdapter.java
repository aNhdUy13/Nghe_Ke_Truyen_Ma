package com.nda.ngheketruyenma.ui.setting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.SkuDetails;
import com.nda.ngheketruyenma.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyViewHolder> {

    AppCompatActivity  appCompatActivity;
    List<SkuDetails> skuDetailsList;
    BillingClient billingClient;

    public MyProductAdapter(AppCompatActivity appCompatActivity, List<SkuDetails> skuDetailsList, BillingClient billingClient) {
        this.appCompatActivity = appCompatActivity;
        this.skuDetailsList = skuDetailsList;
        this.billingClient = billingClient;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(appCompatActivity.getBaseContext())
        .inflate(R.layout.item_billing,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        holder.txt_title.setText(skuDetailsList.get(position).getTitle());
        holder.txt_description.setText(skuDetailsList.get(position).getDescription());
        holder.txt_price.setText(skuDetailsList.get(position).getPrice());

        holder.setListener(new IRecycleClickListener() {
            @Override
            public void onClick(View view, int position) {
                BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetailsList.get(position))
                        .build();

                int respon = billingClient.launchBillingFlow(appCompatActivity,billingFlowParams)
                        .getResponseCode();

                switch (respon)
                {
                    case BillingClient.BillingResponseCode.BILLING_UNAVAILABLE:
                        Toast.makeText(appCompatActivity, "BILLING_UNAVAILABLE", Toast.LENGTH_SHORT).show();
                        break;

                    case BillingClient.BillingResponseCode.DEVELOPER_ERROR:
                        Toast.makeText(appCompatActivity, "DEVELOPER_ERROR", Toast.LENGTH_SHORT).show();
                        break;

                    case BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED:
                        Toast.makeText(appCompatActivity, "FEATURE_NOT_SUPPORTED", Toast.LENGTH_SHORT).show();
                        break;

                    case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
                        Toast.makeText(appCompatActivity, "ITEM_ALREADY_OWNED", Toast.LENGTH_SHORT).show();
                        break;

                    case BillingClient.BillingResponseCode.SERVICE_DISCONNECTED:
                        Toast.makeText(appCompatActivity, "SERVICE_DISCONNECTED", Toast.LENGTH_SHORT).show();
                        break;

                    case BillingClient.BillingResponseCode.SERVICE_TIMEOUT:
                        Toast.makeText(appCompatActivity, "SERVICE_TIMEOUT", Toast.LENGTH_SHORT).show();
                        break;

                    case BillingClient.BillingResponseCode.ITEM_UNAVAILABLE:
                        Toast.makeText(appCompatActivity, "ITEM_UNAVAILABLE", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return skuDetailsList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title,txt_price,txt_description;

        IRecycleClickListener listener;

        public void setListener(IRecycleClickListener listener) {
            this.listener = listener;
        }

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            txt_title   = (TextView) itemView.findViewById(R.id.txt_title);
            txt_price   = (TextView) itemView.findViewById(R.id.txt_price);
            txt_description   = (TextView) itemView.findViewById(R.id.txt_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,getAdapterPosition());
                }
            });
        }


    }


}
