package com.codebele.aahara.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.HotelDetailsActivity;
import com.codebele.aahara.Models.Models.HomeModel;
import com.codebele.aahara.Models.Models.ItemsPojo;
import com.codebele.aahara.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.codebele.aahara.utilities.Constants.URL;


public class SecondHotelAdapter extends RecyclerView.Adapter<SecondHotelAdapter.MyViewHolder> {
    List<ItemsPojo.Item> list = new ArrayList<ItemsPojo.Item>();
    private List<ItemsPojo.Item> mFilteredList = new ArrayList<>();
    //    private int mExpandedPosition= -1;
    Context mContext;
    public int m_position;
    int universalCount = 0;
    HotelDetailsActivity hotelDetailsActivity;
    //    ItemInterface itemInterface;
    HotelAdapter hotelAdapter;
    boolean isAdd = true;
    CharSequence searchString;

    public SecondHotelAdapter(Context mContext, List<ItemsPojo.Item> list, int position, HotelDetailsActivity hotelDetailsActivity, HotelAdapter hotelAdapter, CharSequence searchString) {
        this.list = list;
        this.mContext = mContext;
        this.m_position = position;
        this.hotelDetailsActivity = hotelDetailsActivity;
        this.hotelAdapter = hotelAdapter;
        this.searchString =searchString.toString().toLowerCase();
        mFilteredList = list;

    }






//    public void filterList(ArrayList<ItemsPojo.Item> filterdNames) {
//        mFilteredList = filterdNames;
//        notifyDataSetChanged();
//    }
//    private RecyclerView.LayoutManager layoutManager;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * ButterKnife Code
         **/
        @BindView(R.id.iv_empty_veg)
        ImageView ivEmptyVeg;
//        @BindView(R.id.card_image)
//        androidx.cardview.widget.CardView cardImage;
//        @BindView(R.id.ll_item_image)
//        RelativeLayout llItemImage;
//        @BindView(R.id.iv_item)
//        ImageView ivItem;
        @BindView(R.id.iv_veg)
        ImageView ivVeg;
//        @BindView(R.id.tv_spicy)
//        TextView tvSpicy;
        @BindView(R.id.tv_item_name)
        TextView tvItemName;
        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.foodrating)
        RatingBar foodrating;
        @BindView(R.id.tv_numb)
        TextView tvNumb;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_oldPrice)
        TextView tv_oldPrice;
        @BindView(R.id.ll_add)
        LinearLayout llAdd;
        @BindView(R.id.ll_add_quantity)
        RelativeLayout llAddQuantity;
        @BindView(R.id.tv_add)
        TextView tvAdd;
        @BindView(R.id.add_order)
        ImageView addOrder;
        @BindView(R.id.ll_count)
        LinearLayout llCount;
        @BindView(R.id.iv_minus)
        ImageButton ivMinus;
        @BindView(R.id.tv_quantity1)
        TextView tvQuantity1;
        @BindView(R.id.iv_plus)
        ImageButton ivPlus;

        @BindView(R.id.ll_hotel_items)
        LinearLayout llHotelItems;
        /**
         * ButterKnife Code
         **/

        private ArrayList<ItemsPojo> itemsPojos = new ArrayList<>();

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            // ivPlus.setOnClickListener(this);
            //Creating the LayoutInflater instance

        }
     /*   @Override
        @OnClick({R.id.ll_add, R.id.iv_plus})
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            switch (v.getId()) {
                case R.id.ll_add:
                    llCount.setVisibility(View.VISIBLE);
                    llAddQuantity.setVisibility(View.GONE);
                    universalCount++;
                    hotelDetailsActivity.popup(universalCount);
                    if(mContext instanceof HotelDetailsActivity) {
                        ((HotelDetailsActivity) mContext).callInserApi(list.get(position), 1);
                    }
                    break;
            }
        }*/
    }


    @Override
    public SecondHotelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_hotel_detail, parent, false);
        return new SecondHotelAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SecondHotelAdapter.MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        ItemsPojo.Item itemspojos = mFilteredList.get(position);
        if(itemspojos.getItemName().toLowerCase().contains(searchString)||searchString=="") {
            holder.tvItemName.setText(itemspojos.getItemName());
            holder.tvType.setText(itemspojos.getItemType());
            if (itemspojos.getItemRating() != null) {
                if (!itemspojos.getItemRating().isEmpty()) {
                    try {
                        holder.foodrating.setRating(Integer.parseInt(itemspojos.getItemRating()));
                    } catch (Exception e) {

                    }

                } else {
                    holder.foodrating.setRating(0);
                }
                if (itemspojos.getOfferPrice() != null && !itemspojos.getOfferPrice().isEmpty()) {
                    holder.tvMoney.setText("₹ " + itemspojos.getOfferPrice());
                } else {
                    holder.tvMoney.setText("₹ " + itemspojos.getActualPrice());
                    holder.tv_oldPrice.setVisibility(View.GONE);
                }
                if (itemspojos.getActualPrice() != null && !itemspojos.getActualPrice().isEmpty()) {
                    holder.tv_oldPrice.setText("₹ " + itemspojos.getActualPrice());
//                holder.tv_oldPrice.setPaintFlags( holder.tv_oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.tv_oldPrice.setVisibility(View.GONE);
                }
                /*if (itemspojos.getImage() != null)*/

                if (itemspojos.getItemCount() == 0) {
                    holder.llCount.setVisibility(View.GONE);
                    holder.llAddQuantity.setVisibility(View.VISIBLE);
                } else {
                    holder.llCount.setVisibility(View.VISIBLE);
                    holder.llAddQuantity.setVisibility(View.GONE);
                    holder.tvQuantity1.setText(String.valueOf(itemspojos.getItemCount()));

                }
                if (itemspojos.getImage() != null) {
                    if (itemspojos.getImage().isEmpty()) {
                        //   holder.cardImage.setVisibility(View.GONE);
//                    holder.tvSpicy.setVisibility(View.GONE);
//                    holder.ivVeg.setVisibility(View.VISIBLE);
//                    holder.llItemImage.setVisibility(View.GONE);
                        //   holder.ivEmptyVeg.setVisibility(View.VISIBLE);
                /*Picasso.get()
                        .load(URL + list.get(position).getImage())
                        .placeholder(R.drawable.images)
                        .into(holder.ivItem);*/
                    } else {
//                    Picasso.get()
//                            .load(URL + list.get(position).getImage())
//                            .placeholder(R.drawable.white_image)
//                            .into(holder.ivItem);
                    }
                } else {
          /*  Picasso.get()
                    .load(URL + list.get(position).getImage())
                    .placeholder(R.drawable.images)
                    .into(holder.ivItem);*/
                    // holder.cardImage.setVisibility(View.GONE);
                    //   holder.tvSpicy.setVisibility(View.GONE);
//                holder.llItemImage.setVisibility(View.GONE);
                    holder.ivVeg.setVisibility(View.VISIBLE);
                    //holder.ivEmptyVeg.setVisibility(View.VISIBLE);
                }
                if (itemspojos.getItemType() != null) {
                    if (!itemspojos.getItemType().isEmpty()) {
                        if (itemspojos.getItemType().equalsIgnoreCase("veg")) {
                            holder.ivVeg.setImageResource(R.drawable.veg);
                        } else {
                            holder.ivVeg.setImageResource(R.drawable.noveg);
                        }
                    }
                }

                holder.llAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        universalCount++;
                        holder.tvQuantity1.setText(String.valueOf(1));
                        holder.llCount.setVisibility(View.VISIBLE);
                        holder.llAddQuantity.setVisibility(View.GONE);
                        itemspojos.setItemCount(itemspojos.getItemCount() + 1);
                        //  list.get(position).setItemCount(1);
                        //   itemspojos.setPrice(itemspojos.getPrice()+Integer.parseInt(itemspojos.getNew_price()));

                        hotelAdapter.updateList(m_position, position, 1);
                        if (itemspojos.getItemCount() != -1)
                            hotelDetailsActivity.popup(1, itemspojos, true, true);
                        if (mContext instanceof HotelDetailsActivity) {
                            ((HotelDetailsActivity) mContext).callInserApi(mFilteredList.get(position), 1);
                        }

                    }

                });


                holder.ivMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //   int value = Integer.parseInt(holder.tvQuantity1.getText().toString());
                        if (itemspojos.getItemCount() != 0) {
                            itemspojos.setItemCount(itemspojos.getItemCount() - 1);

//                        itemspojos.setPrice(itemspojos.getPrice()-Integer.parseInt(itemspojos.getNew_price()));

                            universalCount--;
                            hotelAdapter.updateList(m_position, position, itemspojos.getItemCount());
                            if (mContext instanceof HotelDetailsActivity) {
                                ((HotelDetailsActivity) mContext).callInserApi(itemspojos, itemspojos.getItemCount());
                            }
                        }
                        holder.tvQuantity1.setText(String.valueOf(itemspojos.getItemCount()));
                        if (itemspojos.getItemCount() == 0) {
                            holder.llCount.setVisibility(View.GONE);
                            holder.llAddQuantity.setVisibility(View.VISIBLE);
                            hotelDetailsActivity.popup(0, itemspojos, false, false);
                        } else {
                            hotelDetailsActivity.popup(1, itemspojos, false, false);
                        }

                    }

                });

                holder.ivPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
              /*  int count = Integer.parseInt(holder.tvQuantity1.getText().toString());
                count++;*/
                        universalCount++;
                        itemspojos.setItemCount(itemspojos.getItemCount() + 1);


                        hotelAdapter.updateList(m_position, position, itemspojos.getItemCount());
                        if (mContext instanceof HotelDetailsActivity) {
                            ((HotelDetailsActivity) mContext).callInserApi(itemspojos, itemspojos.getItemCount());
                        }
                        holder.tvQuantity1.setText(String.valueOf(itemspojos.getItemCount()));
                        hotelDetailsActivity.popup(1, itemspojos, false, true);
                        //  tvQuantity.setText(String.valueOf(count));

                    }

                });
            }
        }else{
            holder.llHotelItems.setVisibility(View.GONE);
        }
//        if(position==getItemCount()-1)
//        {
//        }
    }


    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }
}