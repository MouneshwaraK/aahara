package com.codebele.aahara.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.HotelDetailsActivity;
import com.codebele.aahara.Models.Models.ItemsPojo;
import com.codebele.aahara.R;
import com.codebele.aahara.activity.ViewAddress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private List<ItemsPojo> foodModelArrayList = new ArrayList<>();
    private Context context;
    HotelDetailsActivity hotelDetailsActivity;

    public MenuAdapter(Context context, ArrayList<ItemsPojo> itemsPojos, HotelDetailsActivity hotelDetailsActivity) {
        this.context = context;
        this.foodModelArrayList = itemsPojos;
        this.hotelDetailsActivity= hotelDetailsActivity;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.MyViewHolder holder, int position) {
//        MyList myList = list.get(position);
        holder.textViewHead.setText(foodModelArrayList.get(position).getItemsCategoryname());
        holder.tvNo.setText(String.valueOf(foodModelArrayList.get(position).getItems().size()));


        holder.textViewHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    holder.textViewHead.setTypeface(holder.textViewHead.getTypeface(), Typeface.BOLD_ITALIC);

                hotelDetailsActivity.scrollToMethod(position);

            }
        });
    }
//    private int sumMyIntValues(ListView myList) {
//        int sum=0;
//        for (int i = 0; i < myList.getCount(); i++) {
//            View v = myList.getChildAt(i);
//            TextView myView = (TextView) v.findViewById(R.id.);
//            sum = sum + Integer.parseInt( myView.getText().toString());
//        }
//        return sum;
//    }

    @Override
    public int getItemCount() {
        return foodModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        /** ButterKnife Code **/
        @BindView(R.id.textViewHead)
        TextView textViewHead;
        @BindView(R.id.tv_no)
        TextView tvNo;

        /** ButterKnife Code **/
        public MyViewHolder( View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        }
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
//
//        }
    }

