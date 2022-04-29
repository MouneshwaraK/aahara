package com.codebele.aahara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.Models.Models.CityNamePojo;
import com.codebele.aahara.Models.Models.ViewAddressModel;
import com.codebele.aahara.R;
import com.codebele.aahara.activity.SelectCityActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeclectCityAdapter extends RecyclerView.Adapter<SeclectCityAdapter.MyViewHolder> {
    private ArrayList<CityNamePojo> deliveryAddressModels = new ArrayList<>();
    private Context context;
    private int mSelectedItem = 0;//-1 initially
    private boolean isChecked = false;
    String address;
    SelectCityActivity selectCityActivity;

    public SeclectCityAdapter(ArrayList<CityNamePojo> deliveryAddressModels, Context context, SelectCityActivity selectCityActivity) {
        this.deliveryAddressModels = deliveryAddressModels;
        this.context = context;
        this.selectCityActivity = selectCityActivity;
    }

    @Override
    public SeclectCityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_delivery_address, parent, false);
        return new SeclectCityAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeclectCityAdapter.MyViewHolder holder, int position) {
        CityNamePojo deliveryAddressModel = deliveryAddressModels.get(position);
        try {
            holder.tvAddress.setText(deliveryAddressModel.getCityName());
//            holder.tvAddress1.setText(deliveryAddressModel.getCity());
//            holder.tvAddress2.setText(deliveryAddressModel.getLandmark());
        } catch (Exception e) {

        }

        holder.radioBtnAddress.setChecked(position == mSelectedItem/*0*/);

    }

    @Override
    public int getItemCount() {
        return deliveryAddressModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /** ButterKnife Code **/
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
        /** ButterKnife Code **/
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            View.OnClickListener l = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyItemRangeChanged(0, deliveryAddressModels.size());
                    selectCityActivity.setCity(mSelectedItem);

                }
            };

            itemView.setOnClickListener(l);
            radioBtnAddress.setOnClickListener(l);

        }

    }


}

