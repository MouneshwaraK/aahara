package com.codebele.aahara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.Models.Models.HomeModel;
import com.codebele.aahara.R;
import com.codebele.aahara.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    Context context;
    List<HomeModel> list = new ArrayList<>();
    HomeFragment homeFragment;
    private int mExpandedPosition= 0;

    public HomeAdapter(Context context, List<HomeModel> list, HomeFragment homeFragment) {
        this.context = context;
        this.list = list;
        this.homeFragment = homeFragment;
        this.list.get(0).setSelected(true);

    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_heading_home,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        final HomeModel homeModel = list.get(position);
        holder.tvFood.setText(homeModel.getSectionName());

        if(homeModel.isSelected())
        holder.tvFood.setTextColor(context.getResources().getColor(R.color.red));
        else
            holder.tvFood.setTextColor(context.getResources().getColor(R.color.gray_text));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /** ButterKnife Code **/
        @BindView(R.id.tv_food)
        TextView tvFood;
        /** ButterKnife Code **/


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();

            if (!list.get(pos).isSelected()) {
                for(int i =0;i<list.size();i++){
                    list.get(i).setSelected(false);
                }

                list.get(pos).setSelected(true);

                notifyDataSetChanged();

                homeFragment.initFragment(list.get(pos).getVendor());

//                        ll_leaves.setVisibility(View.VISIBLE);
//                        btn_select.setVisibility(View.GONE);
//                        btn_hide.setVisibility(View.VISIBLE);
            }


        }
    }
}


