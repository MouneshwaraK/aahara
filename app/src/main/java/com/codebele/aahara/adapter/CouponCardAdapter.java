package com.codebele.aahara.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.Models.Models.CouponCardsListPojo;
import com.codebele.aahara.R;
import com.codebele.aahara.fragments.CartFragment;

import java.util.ArrayList;

public class CouponCardAdapter extends RecyclerView.Adapter<CouponCardAdapter.MyviewHolder> {
    CartFragment cartFragment;
    ArrayList<CouponCardsListPojo> coupons = new ArrayList<>();


    public CouponCardAdapter(ArrayList<CouponCardsListPojo> coupons, CartFragment cartFragment) {
        this.coupons = coupons;
        this.cartFragment =cartFragment;
    }

    @NonNull
    @Override
    public CouponCardAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_coupon_content,parent,false);

        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponCardAdapter.MyviewHolder holder, int position) {
        holder.couponName.setText(coupons.get(position).getCouponCode());
        holder.couponName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cartFragment.updateEt(holder.couponName.getText().toString());
               // cartFragment.updateEtbycartviewcoupon(holder.couponName.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView couponName;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            couponName = itemView.findViewById(R.id.coupon_name);
        }
    }
}
