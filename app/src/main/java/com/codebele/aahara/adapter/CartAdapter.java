package com.codebele.aahara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.codebele.aahara.Interface.CartInterface;
import com.codebele.aahara.Models.Models.ViewCartPojo;
import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.R;
import com.codebele.aahara.fragments.CartFragment;
import com.codebele.aahara.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codebele.aahara.utilities.Constants.URL;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<ViewCartPojo.Items> cartModelArrayList = new ArrayList<>();
    private Context context;
    private CartInterface mCallback;

    public CartAdapter(List<ViewCartPojo.Items> cartModelArrayList, Context context, CartInterface cartInterface) {
        this.cartModelArrayList = cartModelArrayList;
        this.context = context;
        this.mCallback = cartInterface;
    }


    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cart_card, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        ViewCartPojo.Items viewCartPojo = cartModelArrayList.get(position);
        if (viewCartPojo != null) {
            holder.tvItemName.setText(viewCartPojo.getItemName());
            holder.tvCount.setText(viewCartPojo.getCartCount());

            if (!viewCartPojo.getFullPrice().isEmpty() && viewCartPojo.getFullPrice() != null) {
                holder.tvPrice.setText(" ₹ " + viewCartPojo.getFullPrice());
            } else {
                holder.tvPrice.setText(" ₹ " + viewCartPojo.getActualPrice());
                holder.tv_actual_price.setVisibility(View.GONE);
            }
            if (viewCartPojo.getActualPrice() != null && !viewCartPojo.getActualPrice().isEmpty()) {
                holder.tv_actual_price.setText("₹ " + viewCartPojo.getActualPrice());
//                holder.tv_oldPrice.setPaintFlags( holder.tv_oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }else {
                holder.tv_actual_price.setVisibility(View.GONE);
            }
            try {
                Picasso.get()
                        .load(URL + viewCartPojo.getImage())
                        .placeholder(R.drawable.white_image)
                        .into(holder.ivItem);
            } catch (Exception e) {

            }
        }


        holder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(holder.tvCount.getText().toString());
                if (value != 0) {
                    value--;
                    holder.tvCount.setText(String.valueOf(value));
                    String cartCount = String.valueOf(value);
                    mCallback.updateCart(position, viewCartPojo, cartCount);
                    if (value == 0)
                        removeAt(position);
                }
            }

        });

    }

    private void removeAt(int position) {
        cartModelArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cartModelArrayList.size());

        if (cartModelArrayList.size() == 0) {
            mCallback.removeTotal();
        }

    }

    @Override
    public int getItemCount() {
        return cartModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.iv_item)
        ImageView ivItem;
        @BindView(R.id.tv_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_actual_price)
        TextView tv_actual_price;
        @BindView(R.id.iv_minus)
        ImageButton ivMinus;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.iv_plus)
        ImageButton ivPlus;

        /**
         * ButterKnife Code
         **/
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivPlus.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            ViewCartPojo.Items viewcart = cartModelArrayList.get(pos);

            switch (v.getId()) {

                case R.id.iv_plus:
                    int count = Integer.parseInt(tvCount.getText().toString());
                    count++;
                    tvCount.setText(String.valueOf(count));
                    String cartCount = String.valueOf(count);
                    mCallback.updateCart(pos, viewcart, cartCount);
                    break;
            }

        }
    }
}
