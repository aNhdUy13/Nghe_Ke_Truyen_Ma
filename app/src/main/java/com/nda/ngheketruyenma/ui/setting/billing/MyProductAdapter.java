package com.nda.ngheketruyenma.ui.setting.billing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    GetCoin  context;
    List<SkuDetails> skuDetailsList;
    BillingClient billingClient;

    public MyProductAdapter(GetCoin context, List<SkuDetails> skuDetailsList, BillingClient billingClient) {
        this.context = context;
        this.skuDetailsList = skuDetailsList;
        this.billingClient = billingClient;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_billing,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        holder.txt_title.setText(skuDetailsList.get(position).getTitle());
        holder.txt_description.setText(skuDetailsList.get(position).getDescription());
        holder.txt_price.setText(skuDetailsList.get(position).getPrice());

        holder.btn_buyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    buyProduct(position);

            }
        });


    }

    private void buyProduct(int position)
    {
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetailsList.get(position))
                .build();
//                billingClient.launchBillingFlow(appCompatActivity, billingFlowParams);

        int responseCode  = billingClient.launchBillingFlow(context,billingFlowParams)
                .getResponseCode();

        switch (responseCode )
        {

            case BillingClient.BillingResponseCode.BILLING_UNAVAILABLE:
                Toast.makeText(context, "BILLING_UNAVAILABLE", Toast.LENGTH_SHORT).show();
                break;

            case BillingClient.BillingResponseCode.DEVELOPER_ERROR:
                Toast.makeText(context, "DEVELOPER_ERROR", Toast.LENGTH_SHORT).show();
                break;

            case BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED:
                Toast.makeText(context, "FEATURE_NOT_SUPPORTED", Toast.LENGTH_SHORT).show();
                break;

            case BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED:
                Toast.makeText(context, "ITEM_ALREADY_OWNED", Toast.LENGTH_SHORT).show();
                break;

            case BillingClient.BillingResponseCode.SERVICE_DISCONNECTED:
                Toast.makeText(context, "SERVICE_DISCONNECTED", Toast.LENGTH_SHORT).show();
                break;

            case BillingClient.BillingResponseCode.SERVICE_TIMEOUT:
                Toast.makeText(context, "SERVICE_TIMEOUT", Toast.LENGTH_SHORT).show();
                break;

            case BillingClient.BillingResponseCode.ITEM_UNAVAILABLE:
                Toast.makeText(context, "ITEM_UNAVAILABLE", Toast.LENGTH_SHORT).show();
                break;

            default:


                break;

        }

    }

    @Override
    public int getItemCount() {
        return skuDetailsList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title,txt_price,txt_description;

        Button btn_buyProduct;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            txt_title   = (TextView) itemView.findViewById(R.id.txt_productName);
            txt_price   = (TextView) itemView.findViewById(R.id.txt_productPrice);
            txt_description   = (TextView) itemView.findViewById(R.id.txt_productDescription);
            btn_buyProduct    = (Button) itemView.findViewById(R.id.btn_buyProduct);

        }


    }


}
