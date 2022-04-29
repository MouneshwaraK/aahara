package com.codebele.aahara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.HotelDetailsActivity;
import com.codebele.aahara.Models.Models.ItemsPojo;
import com.codebele.aahara.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyViewHolder> {
    ArrayList<ItemsPojo> itemspojo = new ArrayList<ItemsPojo>();
    private List<ItemsPojo.Item> mFilteredList = new ArrayList<>();
    List<ItemsPojo.Item> list = new ArrayList<ItemsPojo.Item>();
    private int mExpandedPosition = -1;
    Context mContext;
    public int m_position;
   // public int position;
    public boolean isAdded = false;
    CharSequence searchString ="";
    Button button;
    String restuarants_id;
    private RecyclerView.LayoutManager layoutManager;
    SecondHotelAdapter secondHotelAdapter;
    HotelDetailsActivity hotelDetailsActivity;

    public void updateList(int m_position, int position,int count) {
        itemspojo.get(m_position).getItems().get(position).setItemCount(count);
    }

    //    CountInterface countInterface;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.img_downarrow)
        ImageView imgDownarrow;
        @BindView(R.id.img_uparrow)
        ImageView imgUparrow;
        @BindView(R.id.tv_bestseller)
        TextView tvBestseller;
        @BindView(R.id.second_recycler)
        RecyclerView secondRecycler;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            tvBestseller.setOnClickListener(this);

        }

        @OnClick({R.id.tv_bestseller})
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            switch (v.getId()) {
        /*        case R.id.tv_bestseller:
                    if (secondRecycler.getVisibility() == View.VISIBLE) {
                        mExpandedPosition = position;
                        notifyDataSetChanged();

                    } else {
                        mExpandedPosition = -1;
                        notifyDataSetChanged();

                    }
                    break;*/
            }
        }
    }

    public HotelAdapter(Context mContext, ArrayList<ItemsPojo> itemspojo, int position, HotelDetailsActivity hotelDetailsActivity) {
        this.itemspojo = itemspojo;
        this.list = list;
        this.mContext = mContext;
        this.m_position = position;
        this.hotelDetailsActivity = hotelDetailsActivity;
//        this.itemInterface=itemInterface;
    }

    @Override
    public HotelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotel_list, parent, false);

        return new HotelAdapter.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(HotelAdapter.MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        ItemsPojo itemspojolist = itemspojo.get(position);
        holder.tvBestseller.setText(itemspojolist.getItemsCategoryname());
        initializeRecyclerView(holder, position);


        holder.tvBestseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.secondRecycler.getVisibility() == View.VISIBLE) {
                    holder.secondRecycler.setVisibility(View.GONE);
                } else {
                    holder.secondRecycler.setVisibility(View.VISIBLE);
                }
            }

        });

    }


    public void searchString(CharSequence s){
        searchString = s;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return itemspojo.size();
    }

    private void initializeRecyclerView(MyViewHolder holder, int pos) {
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        holder.secondRecycler.setLayoutManager(layoutManager);

        holder.secondRecycler.getRecycledViewPool().setMaxRecycledViews(1, 0);

        secondHotelAdapter = new SecondHotelAdapter(mContext, itemspojo.get(pos).getItems()
                , pos, hotelDetailsActivity,this,searchString);
        holder.secondRecycler.setAdapter(secondHotelAdapter);


    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

}