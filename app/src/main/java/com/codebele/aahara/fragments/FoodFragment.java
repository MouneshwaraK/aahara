package com.codebele.aahara.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.Models.Models.FoodModel;
import com.codebele.aahara.Models.Models.HomeModel;
import com.codebele.aahara.R;
import com.codebele.aahara.adapter.FoodAdaptar;
import com.codebele.aahara.adapter.PlacesVisitAdaptar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FoodFragment extends Fragment {

    @BindView(R.id.tv_places)
    TextView tvPlaces;
    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;
    @BindView(R.id.rv_food)
    RecyclerView rvFood;
    private View view;

    private FoodAdaptar foodAdaptar;
    private PlacesVisitAdaptar placesVisitAdaptar;
    private ArrayList<FoodModel> foodModelArrayList= new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager_places;
    List<HomeModel.Vendor> vendor = new ArrayList<>();
    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_food, container, false);
        ButterKnife.bind(this, view);
        layoutManager_places = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        rvCategories.setPadding(15, 0, 50, 15);
        rvCategories.setLayoutManager(layoutManager_places);
        rvCategories.setClipToPadding(false);
        placesVisitAdaptar = new PlacesVisitAdaptar(getContext(), vendor);
        rvCategories.setAdapter(placesVisitAdaptar);
        return view;
    }


    @SuppressLint("WrongConstant")
    @OnClick({})
    public void bottomMenuClick(View view) {

        }

        public void doFilter(CharSequence s){
        if(foodAdaptar!=null)
         if(foodAdaptar.getItemCount()>0){
             foodAdaptar.getFilter().filter(s);
             foodAdaptar.notifyDataSetChanged();


         }else{
             foodAdaptar.getFilter().filter(s);
             foodAdaptar.notifyDataSetChanged();
         }
        }
    private void initializeRecycler(List<HomeModel.Vendor> new_vendor) {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvFood.setLayoutManager(layoutManager);
        foodAdaptar = new FoodAdaptar(getContext(), new_vendor);
        rvFood.setAdapter(foodAdaptar);
         vendor.clear();
         vendor.addAll(new_vendor);
        placesVisitAdaptar.notifyDataSetChanged();

    }

    public void setRecyler(List<HomeModel.Vendor> vendor) {

        initializeRecycler(vendor);
    }


}
