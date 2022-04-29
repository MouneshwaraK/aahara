package com.codebele.aahara.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codebele.aahara.Interface.CartInterface;
import com.codebele.aahara.MainActivity;
import com.codebele.aahara.Models.Models.CouponCardPojo;
import com.codebele.aahara.Models.Models.CouponCardsListPojo;
import com.codebele.aahara.Models.Models.ViewCartPojo;
import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.NetworkUtils.ServerResponse;
import com.codebele.aahara.R;
import com.codebele.aahara.activity.PlaceOrderActivity;
import com.codebele.aahara.adapter.CartAdapter;
import com.codebele.aahara.adapter.CouponCardAdapter;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static com.codebele.aahara.SessionManagers.Utils.dismissProgressDialog;


public class CartFragment extends Fragment implements CartInterface, View.OnClickListener {

    private View view;
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.tv_manage)
    TextView tvManage;
    @BindView(R.id.rv_cart)
    androidx.recyclerview.widget.RecyclerView rvCart;
    @BindView(R.id.ll_amt_details)
    LinearLayout llAmtDetails;
    @BindView(R.id.tv_total_amount)
    TextView tvTotalAmount;
    @BindView(R.id.tv_tax)
    TextView tvTax;
    @BindView(R.id.tv_tax_amt)
    TextView tvTaxAmt;
    @BindView(R.id.tv_delivery_charges)
    TextView tvDeliveryCharges;
    @BindView(R.id.tv_discount_coupon)
    TextView tvDiscountCoupon;
    @BindView(R.id.tv_total_payble_amt)
    TextView tvTotalPaybleAmt;
    @BindView(R.id.btn_place_order)
    Button btnPlaceOrder;
    @BindView(R.id.rl_empty_cart)
    RelativeLayout rlEmptyCart;
    @BindView(R.id.iv_cart)
    ImageView ivCart;
    @BindView(R.id.btn_start_ordering)
    Button btnStartOrdering;
    @BindView(R.id.card_itemslist)
    CardView card_itemList;
    @BindView(R.id.btn_apply_coupon)
    Button btnApplyCoupon;
    @BindView(R.id.et_applycoupoun)
    EditText etApplyCoupon;
    @BindView(R.id.tv_coupon_verifier)
    TextView tvCouponVerifier;
    @BindView(R.id.tv_coupon_amount)
    TextView tvcouponamount;
    @BindView(R.id.coupouncard_recycler)
    RecyclerView rv_couponcards;
    @BindView(R.id.rl_view_coupons)
    RelativeLayout rvCouponcards;
    @BindView(R.id.ll_coupons_layout)
    LinearLayout llCouponslayout;
    @BindView(R.id.iv_remove_coupon)
    ImageView ivRemoveCoupon;
    @BindView(R.id.iv_plus)
    ImageView ivPlus;
    @BindView(R.id.ll_noOffer_layout)
    LinearLayout llNoCouponOffer;
    @BindView(R.id.tv_minimum_amount)
    TextView tvMinimumPaymentAmt;

    /**
     * ButterKnife Code
     **/
    private CartAdapter cartAdapter;
    private ArrayList<ViewCartPojo> cartModelArrayList = new ArrayList<>();
    private ArrayList<CouponCardsListPojo> coupons = new ArrayList<>();
    ArrayList<ViewCartPojo.Items> viewcartList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    com.codebele.SessionManagers.LoginSessionManager sessionManager;
    private String access_token;
    String restorant_id,coupon_name;

    String sk_restaurant_id;
    String sk_user_id;
    String sk_items_id;
    MainActivity mParent;
    ViewCartPojo viewCartPojo;
    int applyflag=0;
    String totalPaymentAmt,minimumPaymentAmt;


    public CartFragment() { }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            context = context;
            mParent = (MainActivity) getActivity();

        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.context = container.getContext();
        this.view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        if (getActivity().getIntent().getExtras() != null) {
            sk_restaurant_id = getActivity().getIntent().getExtras().getString("sk_restaurant_id");
            sk_user_id = getActivity().getIntent().getExtras().getString("sk_user_id");
        }
        viewcartList = new ArrayList<>();
        initializeRecycler(viewcartList);
        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        callViewCartAPI();
        //checkCoupon();
        couponcardsAPI();

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callViewCartAPI();

                pullToRefresh.setRefreshing(false);
            }
        });

        return view;
    }

    private void couponcardsAPI() {

        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getContext());
        HashMap<String, String> user = sessionManager.getUserDetails();

        Api apiService = ApiClient.getClient().create(Api.class);
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        Log.e("access_token", access_token);
        mParent.showProgressDialog(getContext());
        Call<ServerResponse<ArrayList<CouponCardsListPojo>>> call = apiService.viewcoupons(access_token, "application/json");
        call.enqueue(new Callback<ServerResponse<ArrayList<CouponCardsListPojo>>>() {
            @Override
            public void onResponse(Call<ServerResponse<ArrayList<CouponCardsListPojo>>> call, Response<ServerResponse<ArrayList<CouponCardsListPojo>>> response) {
                mParent.dismissProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                       coupons = response.body().getData();
                           initializeCouponRecycler(coupons);
                    } else {

                    }
                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);

                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<ArrayList<CouponCardsListPojo>>>call, Throwable t) {
                mParent.dismissProgressDialog();

            }
        });
    }

    private void initializeCouponRecycler(ArrayList<CouponCardsListPojo> coupons) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        rv_couponcards.setLayoutManager(layoutManager);
        CouponCardAdapter couponCardAdapter = new CouponCardAdapter(coupons,this);
        rv_couponcards.setAdapter(couponCardAdapter);


    }

    private void verifyCouponCard() {
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        Api apiService = ApiClient.getClient().create(Api.class);
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("coupon_code", etApplyCoupon.getText().toString());
        jsonObject.addProperty("restaurant_id", restorant_id);

        mParent.showProgressDialog(getContext());
        Call<ServerResponse<ArrayList<CouponCardPojo>>> call = apiService.verifyCoupon(access_token,"application/json", jsonObject);
        call.enqueue(new Callback<ServerResponse<ArrayList<CouponCardPojo>>>(){
            @Override
            public void onResponse(Call<ServerResponse<ArrayList<CouponCardPojo>>> call, Response<ServerResponse<ArrayList<CouponCardPojo>>> response) {
                mParent.dismissProgressDialog();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()){
                        coupon_name = response.body().getData().get(0).getCoupon_code();
                        applyflag = 1;
                        Log.e("Coupon name",coupon_name);
                        callViewCartAPI();
                    }else {

                    }

                }
                else
                {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
                        btnApplyCoupon.setText("Apply");
                        tvCouponVerifier.setVisibility(View.VISIBLE);
                        tvCouponVerifier.setText(jObjError.getString("message"));//"Please provide valid coupon code");
                        mParent.showToast(getContext(), jObjError.getString("message"));

                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<ArrayList<CouponCardPojo>>> call, Throwable t) {
                mParent.dismissProgressDialog();

                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });



    }
    
    private void callViewCartAPI() {
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getContext());
        HashMap<String, String> user = sessionManager.getUserDetails();

        Api apiService = ApiClient.getClient().create(Api.class);
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        Log.e("access_token", access_token);
        mParent.showProgressDialog(getContext());
        Call<ViewCartPojo> call = apiService.viewcart(access_token, "application/json");
        call.enqueue(new Callback<ViewCartPojo>() {
            @Override
            public void onResponse(Call<ViewCartPojo> call, Response<ViewCartPojo> response) {
                mParent.dismissProgressDialog();
                if (response.isSuccessful()) {
                    Log.d("TAG", "List:" + String.valueOf(response.body().getItems().size()));
                    if (response.body().getStatus()) {
                        assert response.body() != null;
                        viewcartList.clear();
                        card_itemList.setVisibility(View.VISIBLE);
                        tvManage.setVisibility(View.VISIBLE);
                        llAmtDetails.setVisibility(View.VISIBLE);
                        restorant_id = response.body().getRestaurant_id();
                        Log.e("rest id",restorant_id);
                        Log.d("TAG", "List:" + String.valueOf(response.body().getItems().size()));
                        viewcartList.addAll(response.body().getItems());
                        cartAdapter.notifyDataSetChanged();
                        setData(response.body());
                        updateEt(response.body().getCouponcode().toString());

                        //updateEtbycartviewcoupon(response.body().getCouponcode().toString());
                    } else {
                        if (response.body().getItems().isEmpty()) {
                            rvCart.setVisibility(GONE);
                            llAmtDetails.setVisibility(GONE);
                            rlEmptyCart.setVisibility(View.VISIBLE);
                           // tvManage.setText("Oops No Items found");
                        } else {
                            initializeRecycler(response.body().getItems());
                            setData(response.body());
                        }
//                        mParent.showToast(getContext(), response.body().getMessage());
                    }
                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
//                        mParent.showToast(getContext(), jObjError.getString("message"));


                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<ViewCartPojo> call, Throwable t) {
                mParent.dismissProgressDialog();
//                mParent.showToast(getContext(), t.toString());


            }
        });
    }

    private void initializeRecycler(List<ViewCartPojo.Items> viewcartList) {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvCart.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(viewcartList, getContext(), this);
        rvCart.setAdapter(cartAdapter);
    }




    private void setData(ViewCartPojo response) {
        if (response != null) {
            tvcouponamount.setText("- ₹" + response.getCoupon());
            tvTotalAmount.setText("₹" + response.getTotalCost());
            tvTaxAmt.setText("₹" + response.getTotalTaxAmount());
            tvTax.setText("("+ response.getTaxPercentage() +  "%" + ")");
            tvDeliveryCharges.setText("₹" + response.getTotalDeliveryCharges());
            tvTotalPaybleAmt.setText("₹" + response.getTotalPayableAmount());
            totalPaymentAmt = response.getTotalPayableAmount();
            minimumPaymentAmt  = response.getItems().get(0).getMinimumCartAmount();
            if (Integer.parseInt(totalPaymentAmt)>Integer.parseInt(minimumPaymentAmt)){
                tvMinimumPaymentAmt.setVisibility(View.GONE);
                btnPlaceOrder.setEnabled(true);
                btnPlaceOrder.setFocusable(true);
            }else{
                btnPlaceOrder.setEnabled(false);
                btnPlaceOrder.setFocusable(false);

                tvMinimumPaymentAmt.setVisibility(View.VISIBLE);
                tvMinimumPaymentAmt.setText("Please make sure, minimum order should be above ₹"+minimumPaymentAmt);
            }
            Log.e("payable", String.valueOf(response.getTotalPayableAmount()));
        }

    }

    private void updateData(ViewCartPojo response) {
        tvTotalAmount.setText(String.valueOf("₹" + response.getTotalCost()));
        tvTaxAmt.setText(String.valueOf("₹" + response.getTotalTaxAmount()));
        tvDeliveryCharges.setText(String.valueOf("₹" + response.getTotalDeliveryCharges()));
        tvTotalPaybleAmt.setText(String.valueOf("₹" + response.getTotalPayableAmount()));
        Log.e("payable", String.valueOf(response.getTotalPayableAmount()));

    }

    @OnClick({R.id.btn_start_ordering, R.id.btn_place_order, R.id.btn_apply_coupon,R.id.rl_view_coupons,R.id.iv_remove_coupon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_ordering:
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
                break;
            case R.id.btn_place_order:
                startActivity(new Intent(getContext(), PlaceOrderActivity.class).putExtra("total_payment_amt",totalPaymentAmt));
                getActivity();
                break;
            case R.id.btn_apply_coupon:
                ivPlus.setVisibility(GONE);
                verifyCouponCard();
                break;
            case R.id.rl_view_coupons:
                if (llCouponslayout.getVisibility()== GONE){
                    llCouponslayout.setVisibility(View.VISIBLE);
                    tvCouponVerifier.setVisibility(GONE);
                    ivPlus.setBackgroundResource(R.drawable.ic_remove_circle_outline_black_24dp);
                    if (coupons.size()>0){

                        llNoCouponOffer.setVisibility(GONE);
                    }
                   else {
                       llNoCouponOffer.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    llCouponslayout.setVisibility(GONE);
                    ivPlus.setBackgroundResource(R.drawable.ic_baseline_add_circle_24);

                }
                break;
            case R.id.iv_remove_coupon:
                removeCouponApi();
                callViewCartAPI();
                ivRemoveCoupon.setVisibility(GONE);
                etApplyCoupon.setText("");
                btnApplyCoupon.setVisibility(GONE);
                ivPlus.setVisibility(View.VISIBLE);
                }


        }

    private void removeCouponApi() {

        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        Api apiService = ApiClient.getClient().create(Api.class);
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("coupon_code", "");
        Log.e("access_token", access_token);
        mParent.showProgressDialog(getContext());
        Call<ServerResponse<ArrayList<CouponCardPojo>>> call = apiService.verifyCoupon(access_token,"application/json", jsonObject);
        call.enqueue(new Callback<ServerResponse<ArrayList<CouponCardPojo>>>(){
            @Override
            public void onResponse(Call<ServerResponse<ArrayList<CouponCardPojo>>> call, Response<ServerResponse<ArrayList<CouponCardPojo>>> response) {
                mParent.dismissProgressDialog();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()){
                        applyflag=0;
                        tvCouponVerifier.setVisibility(GONE);
                        btnApplyCoupon.setText("Applied");
                        etApplyCoupon.setFocusable(false);
                        etApplyCoupon.setTextColor(Color.parseColor("#a6a6a6"));
                        callViewCartAPI();
                    }else {

                    }

                }
                else
                {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
                        btnApplyCoupon.setText("Apply");
                        tvCouponVerifier.setVisibility(View.VISIBLE);
                        tvCouponVerifier.setText(jObjError.getString("message"));//"Please provide valid coupon code");
//                        mParent.showToast(getContext(), jObjError.getString("message"));

                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<ArrayList<CouponCardPojo>>> call, Throwable t) {
                mParent.dismissProgressDialog();

                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });



    }


    @Override
    public void removeTotal() {
        rlEmptyCart.setVisibility(View.VISIBLE);
        llAmtDetails.setVisibility(GONE);
        card_itemList.setVisibility(GONE);
        //tvManage.setText("Oops! Your Cart is Empty");
    }

    @Override
    public void updateCart(int pos, ViewCartPojo.Items viewcart, String cartCount) {
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        Api apiService = ApiClient.getClient().create(Api.class);
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        JsonObject body = new JsonObject();
        body.addProperty("cart_id", viewcart.getSkCartId());
        body.addProperty("cart_count", cartCount);

        mParent.showProgressDialog(getContext());
        Call<ViewCartPojo> call = apiService.updatecart(access_token, "application/json", body);
        call.enqueue(new Callback<ViewCartPojo>() {
            @Override
            public void onResponse(Call<ViewCartPojo> call, Response<ViewCartPojo> response) {
                mParent.dismissProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(true)) {
                        //  ArrayList<ViewCartPojo.Items> viewcartList = new ArrayList<>();
                        viewcartList.clear();
                        viewcartList.addAll(response.body().getItems());
                        cartAdapter.notifyDataSetChanged();
                        setData(response.body());
//                        mParent.showToast(getContext(), response.body().getMessage());

                    } else {
//                        mParent.showToast(getContext(), response.body().getMessage());
                    }
                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
//                        mParent.showToast(getContext(), jObjError.getString("message"));


                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<ViewCartPojo> call, Throwable t) {
                mParent.dismissProgressDialog();
            }
        });
    }

    public void applycoupon(){
        callViewCartAPI();
    }


    public void updateEt(String toString) {
        if(toString.equals("")){
            btnApplyCoupon.setVisibility(View.VISIBLE);

            // btnApplyCoupon.setText("Applied");
            etApplyCoupon.setFocusable(true);
            ivRemoveCoupon.setVisibility(GONE);
            etApplyCoupon.setTextColor(Color.parseColor("#000000"));
        }else {


            if (applyflag == 0) {
                btnApplyCoupon.setVisibility(View.VISIBLE);
            } else {
                etApplyCoupon.setText(toString);
                tvCouponVerifier.setVisibility(GONE);
                btnApplyCoupon.setVisibility(GONE);
                // btnApplyCoupon.setText("Applied");
                etApplyCoupon.setFocusable(false);
                ivRemoveCoupon.setVisibility(View.VISIBLE);
                etApplyCoupon.setTextColor(Color.parseColor("#a6a6a6"));
            }
        }

    }


    }



