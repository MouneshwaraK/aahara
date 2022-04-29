package com.codebele.aahara.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.codebele.SessionManagers.LoginSessionManager;
import com.codebele.aahara.Models.Models.HomeModel;
import com.codebele.aahara.Models.Models.OfferBannerModel;
import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.NetworkUtils.ServerResponse;
import com.codebele.aahara.OtpActivity;
import com.codebele.aahara.R;
import com.codebele.aahara.adapter.HomeAdapter;
import com.codebele.aahara.adapter.OfferBannerAdapter;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.codebele.aahara.activity.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.codebele.aahara.BaseActivity.showToast;
import static com.codebele.aahara.SessionManagers.Utils.dismissProgressDialog;
import static com.codebele.aahara.SessionManagers.Utils.showProgressDialog;


public class HomeFragment extends Fragment {

    /**
     * ButterKnife Code
     **/
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.vp_offerBanner)
    ViewPager2 vpBanner;
    @BindView(R.id.ll_sliderDots)
    LinearLayout llSliderDots;

    //    @BindView(R.id.tv_food)
//    TextView tvFood;
//    @BindView(R.id.tv_market)
//    TextView tvMarket;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    /** ButterKnife Code **/
    @BindView(R.id.edt_search_text)
    EditText edtSearchText;
    /** ButterKnife Code **/
    /**
     * ButterKnife Code
     **/

    private int dotscount;
    private ImageView[] dots;
    com.codebele.SessionManagers.LoginSessionManager sessionManager;
    private String access_token;
    List<HomeModel> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HomeAdapter homeAdapter;
    private Context context;
    private View view;
    HashMap<String, String> user = new HashMap<String, String>();
    FoodFragment foodFragment;
    String cityid;
    List<OfferBannerModel> offerBannerModels = new ArrayList<>();
    OfferBannerAdapter offerBannerAdapter;

    public HomeFragment() {
        // Required empty public constructor
        this.context = context;
        this.view = view;
        this.list = list;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getContext());
        user = sessionManager.getUserDetails();
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        cityid = user.get(LoginSessionManager.KEY_City);
        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

               callRetrofitAPI();
                pullToRefresh.setRefreshing(false);
            }
        });
        foodFragment = new FoodFragment();

        getChildFragmentManager().beginTransaction().replace(R.id.frame_layout, foodFragment, "FRAGMENT").commit();

        edtSearchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                /*instituteAdapter.getFilter().filter(s);
                instituteAdapter.notifyDataSetChanged();*/
                foodFragment.doFilter(s);
            }
        });

        initViewPager();
        return view;
    }

    private void initRecycler2(ArrayList<HomeModel> homeModelArrayList) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        rvCategory.setLayoutManager(layoutManager);

        HomeAdapter adapter = new HomeAdapter(getContext(), homeModelArrayList, this);
        rvCategory.setAdapter(adapter);

        initFragment(homeModelArrayList.get(0).getVendor());
    }




    private void callRetrofitAPI() {
        Api apiService = ApiClient.getClient().create(Api.class);
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("city", cityid);
        showProgressDialog(getContext());
        Call<ServerResponse<ArrayList<HomeModel>>> call = apiService.restaurent_view(access_token, jsonObject);
        call.enqueue(new Callback<ServerResponse<ArrayList<HomeModel>>>() {
            @Override
            public void onResponse(Call<ServerResponse<ArrayList<HomeModel>>> call, Response<ServerResponse<ArrayList<HomeModel>>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {

                    assert response.body() != null;
//                    sessionManager.createUserLoginSession(response.body().getData().getAccesstoken(),response.body().getData().getEmail()
//                            ,response.body().getData().getAddress(),response.body().getData().getDoj(),response.body().getData().getMobile(),response.body().getData().getFullname());
                    ArrayList<HomeModel> homeModelArrayList = new ArrayList<>();
                    homeModelArrayList = response.body().getData();

//

                    if (response.body().getData().isEmpty()) {
                    } else {

                        initRecycler2(homeModelArrayList);
//                        initRecycler();
                    }

                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
                        showToast(getContext(), jObjError.getString("message"));

                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<ArrayList<HomeModel>>> call, Throwable t) {
                dismissProgressDialog();


            }
        });

    }

    public void initFragment(List<HomeModel.Vendor> vendor) {


        foodFragment.setRecyler(vendor);

    }

    public void initViewPager(){
        offerBannerModels.add(new OfferBannerModel(R.drawable.coupon_example));
        offerBannerModels.add(new OfferBannerModel(R.drawable.coupon_example4));
        offerBannerModels.add(new OfferBannerModel(R.drawable.coupon_example2));
        offerBannerModels.add(new OfferBannerModel(R.drawable.coupon_example3));


        offerBannerAdapter = new OfferBannerAdapter(getContext(), this, offerBannerModels);
        vpBanner.setAdapter(offerBannerAdapter);

        vpBanner.registerOnPageChangeCallback(pageChangeCallback);
        dotscount = offerBannerModels.size();
        dots = new ImageView[dotscount];
        if (dotscount != 0) {
            for (int i = 0; i < dotscount; i++) {

                dots[i] = new ImageView(getContext());
                dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(9, 0, 9, 0);
                llSliderDots.addView(dots[i], params);
            }
            dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
        } else {
            vpBanner.setVisibility(View.GONE);
        }


    }



    ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            for (int i = 0; i < dotscount; i++) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), R.drawable.non_active_dot));
            }
            dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));


        }
    };



    @Override
    public void onResume() {
        super.onResume();
        callRetrofitAPI();
    }
}
