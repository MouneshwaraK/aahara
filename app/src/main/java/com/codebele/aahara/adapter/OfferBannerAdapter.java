package com.codebele.aahara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.Models.Models.OfferBannerModel;
import com.codebele.aahara.R;
import com.codebele.aahara.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferBannerAdapter extends RecyclerView.Adapter<OfferBannerAdapter.MyViewHolder> {
    private Context context;
    HomeFragment homeFragment;

    List<OfferBannerModel> offerBannerModels = new ArrayList<>();

    public OfferBannerAdapter(Context context, HomeFragment homeFragment, List<OfferBannerModel> offerBannerModels) {
        this.context = context;
        this.homeFragment = homeFragment;
        this.offerBannerModels = offerBannerModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_offer_bannner,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageBanner.setImageResource(offerBannerModels.get(position).getImage());
    }


    @Override
    public int getItemCount() {
        return offerBannerModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_banner)
        ImageView imageBanner;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //  List<CourseViewPojo.BannerList> all_folder = new ArrayList<>();


}