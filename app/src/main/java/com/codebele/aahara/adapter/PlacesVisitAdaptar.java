package com.codebele.aahara.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.codebele.aahara.HotelDetailsActivity;
import com.codebele.aahara.Models.Models.HomeModel;
import com.codebele.aahara.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codebele.aahara.NetworkUtils.ApiClient.BASE_URL;
import static com.codebele.aahara.utilities.Constants.URL;

public class PlacesVisitAdaptar extends RecyclerView.Adapter<PlacesVisitAdaptar.MyViewHolder>  implements Filterable {
    private Context context;
    private List<HomeModel.Vendor> foodModelArrayList = new ArrayList<>();
    private List<HomeModel.Vendor> mFilteredList = new ArrayList<>();
//    private List<HomeModel> HomeModel = new ArrayList<>();
    public PlacesVisitAdaptar(Context context, List<HomeModel.Vendor> foodModelArrayList) {
        this.context = context;
        this.foodModelArrayList = foodModelArrayList;
        mFilteredList = foodModelArrayList;
    }

    @Override
    public PlacesVisitAdaptar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_places_visit, parent, false);
        return new PlacesVisitAdaptar.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesVisitAdaptar.MyViewHolder holder, int position) {
        holder.tvName1.setText(mFilteredList.get(position).getRestaurantName());
        Picasso.get()
                .load(URL+mFilteredList.get(position).getLogo())
                .placeholder(R.drawable.white_image)
                .into(holder.malllistImage);

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        /** ButterKnife Code **/
        @BindView(R.id.fCard)
        androidx.cardview.widget.CardView fCard;
        @BindView(R.id.malllist_image)
        ImageView malllistImage;
        @BindView(R.id.tv_name1)
        TextView tvName1;
        /** ButterKnife Code **/
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Intent intent = new Intent(context,
                    HotelDetailsActivity.class);
            intent.putExtra("sk_restaurant_id", mFilteredList.get(pos).getSkRestaurantId());
            intent.putExtra("restaurant_name", mFilteredList.get(pos).getRestaurantName());
            context.startActivity(intent);
        }
    }
}
