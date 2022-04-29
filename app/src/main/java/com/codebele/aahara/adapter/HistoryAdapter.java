package com.codebele.aahara.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.MainActivity;
import com.codebele.aahara.Models.Models.OrderHistoryPojo;
import com.codebele.aahara.R;
import com.codebele.aahara.activity.OrderSummaryActivity;
import com.codebele.aahara.fragments.HistoryFragments;
import com.codebele.aahara.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.codebele.aahara.utilities.Constants.URL;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private Context context;
    private List<OrderHistoryPojo.Order> orderModelArrayList = new ArrayList<>();
    String items = "";
    HistoryFragments historyFragments;

    public HistoryAdapter(Context context, List<OrderHistoryPojo.Order> orderModelArrayList, HistoryFragments historyFragments) {
        this.context = context;
        this.orderModelArrayList = orderModelArrayList;
        this.historyFragments = historyFragments;
    }

    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_card, parent, false);
        return new HistoryAdapter.MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {
//        HistoryModel historyModel = historyModelArrayList.get(position);
        OrderHistoryPojo.Order orderHistoryPojo = orderModelArrayList.get(position);
        holder.tvShopName.setText(orderHistoryPojo.getRestaurantName());
        holder.tvLocation.setText(orderHistoryPojo.getCity_name());
        for (int i = 0; i < orderHistoryPojo.getItem().size(); i++) {
            if (i == 0) {
                items = orderHistoryPojo.getItem().get(i).getItemName();
            } else {
                items = new StringBuilder().append(items).append(",").append(orderHistoryPojo.getItem().get(i).getItemName()).toString();
            }
        }
        holder.tvItems.setText(items);
        holder.tvOrderedOn.setText(orderHistoryPojo.getCreatedDate());
        holder.tvTotalAmount.setText(orderHistoryPojo.getTotalOrderValue());
        holder.tvRepeatOrder.setText(orderHistoryPojo.getPayment_status());
        String order_state = orderHistoryPojo.getOrder_status().substring(0, 1).toUpperCase() + orderHistoryPojo.getOrder_status().substring(1).toLowerCase();

        holder.tvOrderStatus.setText(order_state);
        try {
            Picasso.get()
                    .load(URL + orderHistoryPojo.getLogo())
                    .placeholder(R.drawable.white_image)
                    .into(holder.ivLogo);
        } catch (Exception e) {

        }
        if (orderHistoryPojo.getPayment_status().equalsIgnoreCase(Constants.KEY_PENDING)) {
            holder.tvRepeatOrder.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
            return orderModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tv_details)
        TextView tvDetails;
        @BindView(R.id.tv_items)
        TextView tvItems;
        @BindView(R.id.tv_ordered_on)
        TextView tvOrderedOn;
        @BindView(R.id.tv_total_amount)
        TextView tvTotalAmount;
        @BindView(R.id.tv_closed)
        TextView tvClosed;
        @BindView(R.id.tv_cancel_order)
        TextView tvCancelOrder;
        @BindView(R.id.tv_delivered_and_rated)
        TextView tvDeliveredAndRated;
        @BindView(R.id.tv_rating)
        TextView tvRating;
        @BindView(R.id.tv_cancel_ord)
        TextView tvCancelOrd;
        @BindView(R.id.tv_repeat_order)
        TextView tvRepeatOrder;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;

        /**
         * ButterKnife Code
         **/
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvCancelOrder.setAlpha(.5f);
        }

        @OnClick({R.id.tv_details, R.id.tv_repeat_order, R.id.tv_cancel_ord})
        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            OrderHistoryPojo.Order historyPojo = orderModelArrayList.get(pos);
            switch (v.getId()) {
                case R.id.tv_details:
                    Intent intent = new Intent(context, OrderSummaryActivity.class);
                    intent.putExtra(Constants.KEY_ORDER_ID, historyPojo.getSkOrderId());
                    intent.putExtra(Constants.KEY_DELIVERY_STATUS, historyPojo.getPayment_status());
                    context.startActivity(intent);
                    break;
                case R.id.tv_repeat_order:
                    historyFragments.callRepeat(pos);
                    Intent intent1 = new Intent(context, MainActivity.class);
                    context.startActivity(intent1);
                    ((Activity) context).finish();
                    break;
                case R.id.tv_cancel_ord:
                    historyFragments.callCancel(pos);
                    Intent intent2 = new Intent(context, MainActivity.class);
                    context.startActivity(intent2);
                    ((Activity) context).finish();
                    break;

            }

        }
    }
}
