package com.codebele.aahara.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.Models.Models.MarketModel;
import com.codebele.aahara.R;
import com.codebele.aahara.adapter.MarketAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketFragments extends Fragment {
    /** ButterKnife Code **/
    @BindView(R.id.rv_market)
    RecyclerView rvMarket;
    /** ButterKnife Code **/
    private View view;
    private Context context;
    private MarketAdapter marketAdapter;
    private ArrayList<MarketModel> marketModelArrayList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = container.getContext();
            this.view = inflater.inflate(R.layout.fragment_market, container, false);
          ButterKnife.bind(this,view);
            initializeRecycler();

        return view;
    }

    private void initializeRecycler() {
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvMarket.setLayoutManager(layoutManager);
        for (int i=0;i<=6;i++){
            marketModelArrayList.add(new MarketModel("Vishal Mega Mart","Ballari","Snacks,HouseHold Cares",
                    "55 mins"));
        }
        marketAdapter = new MarketAdapter(context,marketModelArrayList);
        rvMarket.setAdapter(marketAdapter);


    }

}
