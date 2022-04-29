package com.codebele.aahara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.codebele.aahara.Models.Models.ViewAddressModel;
import com.codebele.aahara.R;
import com.codebele.aahara.activity.ViewAddress;

import java.util.List;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.ViewHolder> {
    private static final int READ_REQUEST_CODE = 1;
    ViewAddressModel viewAddressModel;
        private List<ViewAddressModel> list;
        private Context mCtx;
    private ViewAddress viewAddress;

        public MyAddressAdapter(List<ViewAddressModel> list, Context mCtx, ViewAddress viewAddress) {
            this.list = list;
            this.mCtx = mCtx;
            this.viewAddress =viewAddress;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.address_card, parent, false);
            return new ViewHolder(v);

        }



    @Override
    public void onBindViewHolder(final MyAddressAdapter.ViewHolder holder, int position) {
        ViewAddressModel myList = list.get(position);
        holder.textViewHead.setText(myList.getAddressName()+",");
        holder.textViewDesc.setText(myList.getCity()+","+myList.getLandmark());


        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(mCtx, holder.buttonViewOption);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.tv_edit:

                                viewAddress.goToplacepicker(true,(list.get(position)));
//                                if (mCtx instanceof ViewAddress) {
//                                    ((ViewAddress) mCtx).CallUpdateApi((list.get(position)));
//                                }

//                                Intent intent = new Intent(mCtx, PlacePickerActivity.class);
//                                mCtx.startActivity(intent);
                                break;
                            case R.id.tv_delete:
                                if (mCtx instanceof ViewAddress) {
                                    ((ViewAddress) mCtx).CallDeleteAddressAPI((list.get(position)));
                                }
                                return true;
//                                break;
//                            case R.id.menu3:
//                                //handle menu3 click
//                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView textViewHead;
            public TextView textViewDesc;
//            public TextView textViewDesc1;
//            public TextView textViewDesc2;
//            public TextView textViewDesc3;
            public TextView buttonViewOption;

            public ViewHolder(View itemView) {
                super(itemView);

                textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
                textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
//                textViewDesc1 = (TextView) itemView.findViewById(R.id.textViewDesc1);
//                textViewDesc2 = (TextView) itemView.findViewById(R.id.textViewDesc2);
//                textViewDesc3 = (TextView) itemView.findViewById(R.id.textViewDesc3);
                buttonViewOption = (TextView) itemView.findViewById(R.id.textViewOptions);
            }
        }
    }

