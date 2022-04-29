package com.codebele.aahara.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.HotelDetailsActivity;
import com.codebele.aahara.Models.Models.HomeModel;
import com.codebele.aahara.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codebele.aahara.utilities.Constants.URL;

public class FoodAdaptar extends RecyclerView.Adapter<FoodAdaptar.MyViewHolder> {
    private Context context;
    private List<HomeModel.Vendor> foodModelArrayList = new ArrayList<>();
    private List<HomeModel.Vendor> mFilteredList = new ArrayList<>();

    public FoodAdaptar(Context context, List<HomeModel.Vendor> foodModelArrayList) {
        this.context = context;
        this.foodModelArrayList = foodModelArrayList;
        mFilteredList = foodModelArrayList;

    }

    @Override
    public FoodAdaptar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rest, parent, false);
        return new FoodAdaptar.MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull FoodAdaptar.MyViewHolder holder, int position) {
        holder.malllistRestname.setText(mFilteredList.get(position).getRestaurantName());
        holder.tvVeg.setText(mFilteredList.get(position).getStoreType());
        holder.malllistRating.setText(mFilteredList.get(position).getRating());
        holder.malllistKwd.setText(context.getResources().getString(R.string.rupee) + mFilteredList.get(position).getApproxPersonCost() + " per person");
        holder.tvType.setText(foodModelArrayList.get(position).getStoreType());

        /* displaying hotel images */
        Picasso.get()
                .load(URL + mFilteredList.get(position).getLogo())
                .placeholder(R.drawable.white_image)
                .into(holder.malllistImage);

        /* Hide and show Restaurants according to restaurants timings*/

        final String open_time =   String.valueOf(foodModelArrayList.get(position).getOpen());
        final String close_time = String.valueOf(foodModelArrayList.get(position).getClose());
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj1 = sdf.parse(open_time);
            final Date dateObj2 = sdf.parse(close_time);
            String open = new SimpleDateFormat("h:mm a").format(dateObj1);
            String close = new SimpleDateFormat("h:mm a").format(dateObj2);
            holder.openTime.setText(open);
            holder.closeTime.setText(close);
        } catch (final ParseException e) {
            e.printStackTrace();
        }

        if(mFilteredList.get(position).getRestStatus().equals("Close"))
            {
                /*while reataurant closed*/
                holder.llFcard.setEnabled(true);
                holder.llFcard.setClickable(true);
                holder.llFcard.setForeground(context.getResources().getDrawable(R.drawable.transperant_bg));
                holder.currentleyclose.setText("Currentley closed");
                holder.currentleyclose.setTextColor(Color.RED);
                holder.openTime.setVisibility(View.GONE);
                holder.closeTime.setVisibility(View.GONE);
            }
            else
            {
                /*while reataurant opened*/
                holder.llFcard.setEnabled(false);
                holder.llFcard.setClickable(false);
            }

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    /* filter the hotels by search*/
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();

                if (charString.isEmpty()) {

                    mFilteredList = (ArrayList<HomeModel.Vendor>) foodModelArrayList;
                } else {

                    ArrayList<HomeModel.Vendor> filteredList = new ArrayList<>();

                    for (HomeModel.Vendor item : foodModelArrayList) {

                        if (item.getRestaurantName().toLowerCase().contains(charString)) {

                            filteredList.add(item);
                        }
                    }

                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList = (ArrayList<HomeModel.Vendor>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.fCard)
        androidx.cardview.widget.CardView fCard;
        @BindView(R.id.malllist_image)
        ImageView malllistImage;
        @BindView(R.id.malllist_restname)
        TextView malllistRestname;
        @BindView(R.id.malllist_rating)
        TextView malllistRating;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_veg)
        TextView tvVeg;
        @BindView(R.id.malllist_kwd)
        TextView malllistKwd;
        @BindView(R.id.ll_fcard)
        LinearLayout llFcard;
        @BindView(R.id.open_time)
        TextView openTime;
        @BindView(R.id.close_time)
        TextView closeTime;
        @BindView(R.id.currentley_close)
        TextView currentleyclose;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            /* click to view the items of particular selected hotel*/
            int pos = getAdapterPosition();
            Intent intent = new Intent(context, HotelDetailsActivity.class);
            intent.putExtra("sk_restaurant_id", mFilteredList.get(pos).getSkRestaurantId());
            intent.putExtra("restaurant_name", mFilteredList.get(pos).getRestaurantName());
            context.startActivity(intent);
        }
    }

}
