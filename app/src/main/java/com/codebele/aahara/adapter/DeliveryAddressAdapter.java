package com.codebele.aahara.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.Interface.AddressInterface;
import com.codebele.aahara.Models.Models.DeliveryAddressModel;
import com.codebele.aahara.Models.Models.ViewAddressModel;
import com.codebele.aahara.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.MyViewHolder> {
    private ArrayList<ViewAddressModel> deliveryAddressModels = new ArrayList<>();
    private Context context;
    private int mSelectedItem = -1;
    String address;
    private AddressInterface mCallBack;
    private final int limit = 5;


    public DeliveryAddressAdapter(ArrayList<ViewAddressModel> deliveryAddressModels, Context context, AddressInterface mCallBack) {
        this.deliveryAddressModels = deliveryAddressModels;
        this.context = context;
        this.mCallBack = mCallBack;
    }

    @Override
    public DeliveryAddressAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_delivery_address, parent, false);
        return new DeliveryAddressAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryAddressAdapter.MyViewHolder holder, int position) {
        ViewAddressModel deliveryAddressModel = deliveryAddressModels.get(position);
        try {
            holder.tvAddress.setText(deliveryAddressModel.getAddressName() + "," + deliveryAddressModel.getCity() + "," + deliveryAddressModel.getLandmark());
//            holder.tvAddress1.setText(deliveryAddressModel.getCity());
//            holder.tvAddress2.setText(deliveryAddressModel.getLandmark());
        } catch (Exception e) {

        }

        holder.radioBtnAddress.setChecked(position == mSelectedItem);

    }

    @Override
    public int getItemCount() {

        return deliveryAddressModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.ll_cod)
        LinearLayout llCod;
        @BindView(R.id.radio_btn_address)
        RadioButton radioBtnAddress;
        @BindView(R.id.tv_address)
        TextView tvAddress;
//        @BindView(R.id.tv_address1)
//        TextView tvAddress1;
//        @BindView(R.id.tv_address2)
//        TextView tvAddress2;

        /**
         * ButterKnife Code
         **/
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            View.OnClickListener l = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    ViewAddressModel deliveryAddressModel = deliveryAddressModels.get(pos);
                    if (!deliveryAddressModel.isSelected()) {
                        deliveryAddressModel.setSelected(true);
                        mSelectedItem = getAdapterPosition();
                        notifyItemRangeChanged(0, deliveryAddressModels.size());
                        mCallBack.getAddressId(deliveryAddressModel);
                    }
                }

            };

            itemView.setOnClickListener(l);
            radioBtnAddress.setOnClickListener(l);

        }

    }
}

