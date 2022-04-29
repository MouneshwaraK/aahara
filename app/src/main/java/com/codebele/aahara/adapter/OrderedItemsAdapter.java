package com.codebele.aahara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.Models.Models.OrderedItemsModel;
import com.codebele.aahara.R;
import com.codebele.aahara.ResponsePojo.OrderSummaryPojo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderedItemsAdapter extends RecyclerView.Adapter<OrderedItemsAdapter.MyViewHolder> {
    private ArrayList<OrderSummaryPojo.Item> orderedItemsModelArrayList = new ArrayList<>();
    private Context contex;
    public OrderedItemsAdapter(ArrayList<OrderSummaryPojo.Item> orderedItemsModelArrayList, Context contex) {
        this.orderedItemsModelArrayList = orderedItemsModelArrayList;
        this.contex = contex;
    }

    @Override
    public OrderedItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ordered_items_card, parent, false);
        return new OrderedItemsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderedItemsAdapter.MyViewHolder holder, int position) {
        OrderSummaryPojo.Item orderedItemsModel = orderedItemsModelArrayList.get(position);
        try {
            holder.tvItemName.setText(orderedItemsModel.getItemName());
            holder.tvNoItems.setText(orderedItemsModel.getCartCount());
            holder.tvCostPerEachItem.setText(" ×  " + orderedItemsModel.getNewPrice());
            holder.tvTotalAmt.setText(String.valueOf(" ₹ " + orderedItemsModel.getCprice()));
        } catch (Exception e) {

        }


    }

    @Override
    public int getItemCount() {
        return orderedItemsModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.tv_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_no_items)
        TextView tvNoItems;
        @BindView(R.id.tv_cost_per_each_item)
        TextView tvCostPerEachItem;
        @BindView(R.id.tv_total_amt)
        TextView tvTotalAmt;

        /**
         * ButterKnife Code
         **/
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
