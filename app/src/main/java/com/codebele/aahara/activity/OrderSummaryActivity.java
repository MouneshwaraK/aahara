package com.codebele.aahara.activity;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codebele.aahara.Models.Models.OrderHistoryPojo;
import com.codebele.aahara.Models.Models.OrderedItemsModel;
import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.NetworkUtils.ServerResponse;
import com.codebele.aahara.R;
import com.codebele.aahara.ResponsePojo.OrderSummaryPojo;
import com.codebele.aahara.adapter.OrderedItemsAdapter;
import com.codebele.aahara.utilities.Constants;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryActivity extends BaseActivity {
    private static final int MY_PERMISSIONS = 1;
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.toolbar)
    androidx.appcompat.widget.Toolbar toolbar;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_restaurant_name)
    TextView tvRestaurantName;
    @BindView(R.id.tv_restaurant_address)
    TextView tvRestaurantAddress;
    @BindView(R.id.tv_payment_status)
    TextView tvPaymentStatus;
    @BindView(R.id.rv_your_orders)
    androidx.recyclerview.widget.RecyclerView rvYourOrders;
    @BindView(R.id.tv_item_total)
    TextView tvItemTotal;
    @BindView(R.id.tv_tax_amt)
    TextView tvTaxAmt;
    @BindView(R.id.tv_coupon_amt)
    TextView tvCouponAmt;
    @BindView(R.id.tv_total_charge)
    TextView tvTotalCharge;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_payment)
    TextView tvPayment;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.tv_delivered_address)
    TextView tvDeliveredAddress;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_city)
    TextView tvCity;
    /**
     * ButterKnife Code
     **/
    private String access_token, oreder_id, phn_number;
    private ArrayList<OrderedItemsModel> orderedItemsModelArrayList = new ArrayList<>();
    private OrderedItemsAdapter orderedItemsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private com.codebele.SessionManagers.LoginSessionManager sessionManager;
    private ArrayList<OrderSummaryPojo.Item> orderSummaryPojos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            oreder_id = intent.getExtras().getString(Constants.KEY_ORDER_ID);
        }
        orderSummaryPojos = new ArrayList<>();
        initRecycler(orderSummaryPojos);
        callOrderSummaryApi();
    }


    private void callOrderSummaryApi() {


        Api apiService = ApiClient.getClient().create(Api.class);
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("sk_order_id", oreder_id);
        showProgressDialog(this);
        access_token = sessionManager.getUserDetails().get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        Call<ServerResponse<OrderSummaryPojo>> call = apiService.orderSummary(access_token, "application/json", jsonObject);
        call.enqueue(new Callback<ServerResponse<OrderSummaryPojo>>() {
            @Override
            public void onResponse(Call<ServerResponse<OrderSummaryPojo>> call, Response<ServerResponse<OrderSummaryPojo>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isStatus()) {
                        if (response.body().getData() != null) {
                            orderSummaryPojos.addAll(response.body().getData().getItem());
                            orderedItemsAdapter.notifyDataSetChanged();
                            setData(response.body().getData());
                        }
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
            public void onFailure(Call<ServerResponse<OrderSummaryPojo>> call, Throwable t) {
                dismissProgressDialog();
            }
        });
    }

    private void setData(OrderSummaryPojo data) {
        try {
            String restaurantAddress = data.getRestaurantAddress().substring(0, 1).toUpperCase() + data.getRestaurantAddress().substring(1).toLowerCase();
            tvRestaurantName.setText(data.getRestaurantName());
            tvRestaurantAddress.setText(restaurantAddress);
            tvPaymentStatus.setText(data.getPaymentStatus());
            tvItemTotal.setText("₹" + data.getTotalCost());
            tvTaxAmt.setText("₹" + data.getTaxAmount());
            tvCouponAmt.setText("₹" + data.getTotalTaxAmount());
            tvTotalCharge.setText("₹" + data.getDeliveryCharges());
            tvTotal.setText("₹" + data.getTotalPayableAmount());

            tvOrderNum.setText(data.getSkOrderId());
            tvPayment.setText(data.getPaymentMode());
            tvDate.setText(data.getOrderedDate());
            tvPhoneNum.setText("");
            tvDeliveredAddress.setText(data.getAddressName());
            tvCity.setText(data.getCityName());
            tvNumber.setText("Call " + "(" + data.getRestaurantMobile() + ")");
            phn_number = data.getRestaurantMobile();
        } catch (Exception e) {


        }
    }

    private void initRecycler(ArrayList<OrderSummaryPojo.Item> orderSummaryPojos) {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvYourOrders.setLayoutManager(layoutManager);
        rvYourOrders.setHasFixedSize(true);
        orderedItemsAdapter = new OrderedItemsAdapter(orderSummaryPojos, this);

        rvYourOrders.setAdapter(orderedItemsAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.iv_back, R.id.tv_number})
    public void onClic(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_number:
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, MY_PERMISSIONS);
                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phn_number));
                        startActivity(intent);
                    } catch (Exception e) {

                    }
                }


                break;
        }
    }
}
