package com.codebele.aahara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.Models.Models.MarketModel;
import com.codebele.aahara.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MarketModel> marketModelArrayList = new ArrayList<>();

    public MarketAdapter(Context context, ArrayList<MarketModel> marketModelArrayList) {
        this.context = context;
        this.marketModelArrayList = marketModelArrayList;
    }

    @Override
    public MarketAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_market_card, parent, false);
        return new MarketAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketAdapter.MyViewHolder holder, int position) {
        MarketModel marketModel = marketModelArrayList.get(position);
        holder.tvShopName.setText(marketModel.getShop_name());
        holder.tvLocation.setText(marketModel.getLocation());
        holder.tvItems.setText(marketModel.getItems());
        holder.tvTime.setText(marketModel.getTime());

    }

    @Override
    public int getItemCount() {
        return marketModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /** ButterKnife Code **/
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tv_items)
        TextView tvItems;
        @BindView(R.id.tv_time)
        TextView tvTime;
        /** ButterKnife Code **/
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
