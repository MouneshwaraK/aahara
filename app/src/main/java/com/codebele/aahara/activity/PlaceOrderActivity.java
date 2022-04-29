package com.codebele.aahara.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codebele.aahara.Interface.AddressInterface;
import com.codebele.aahara.Models.Models.DeliveryAddressModel;
import com.codebele.aahara.Models.Models.ViewAddressModel;
import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.NetworkUtils.ServerResponse;
import com.codebele.aahara.R;
import com.codebele.aahara.ResponsePojo.PlaceOrderPojo;
import com.codebele.aahara.adapter.DeliveryAddressAdapter;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.codebele.SessionManagers.LoginSessionManager.KEY_Fullname;

public class PlaceOrderActivity extends BaseActivity implements AddressInterface {
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.radio_btn__home)
    RadioButton radioBtnHome;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.rv_address)
    androidx.recyclerview.widget.RecyclerView rvAddress;
    @BindView(R.id.tv_add_new)
    TextView tvAddNew;
    @BindView(R.id.ll_cod)
    LinearLayout llCod;
//    @BindView(R.id.radio_btn_cod)
//    RadioButton radioBtnCod;
    @BindView(R.id.payment_group)
    RadioGroup paymentGroup;
    @BindView(R.id.radioBtn_cashOnDelivary)
    RadioButton radioBtnCashOnDelivary;
    @BindView(R.id.radioBtn_upipay)
    RadioButton radioBtnUpiPay;
    @BindView(R.id.et_instructions)
    EditText etInstructions;
    @BindView(R.id.btn_place_order)
    Button btnPlaceOrder;
    @BindView(R.id.btn_proceed_to_pay)
    Button btnProceedToPay;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    /**
     * ButterKnife Code
     **/
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DeliveryAddressModel> deliveryAddressModels = new ArrayList<>();
    private DeliveryAddressAdapter deliveryAddressAdapter;
    private static final int READ_REQUEST_CODE = 1;
    DeliveryAddressModel address;
    com.codebele.SessionManagers.LoginSessionManager sessionManager;
    HashMap<String, String> user;

    private String access_token, address_id;
    private boolean checked = false;
    String totalAmt="";
    final int UPI_PAYMENT = 0;
    String transaction_id="",payment_mode="cash";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getApplicationContext());
        user = sessionManager.getUserDetails();
        ButterKnife.bind(this);
        radioBtnHome.setChecked(true);
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(this);

        if (getIntent().getExtras() != null){
            totalAmt = getIntent().getStringExtra("total_payment_amt");
        }
        CallViewAddressAPI();
        checkPaymentMethod();
    }

    private void initRecycler(ArrayList<ViewAddressModel> deliveryAddressModels) {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAddress.setLayoutManager(layoutManager);
        deliveryAddressAdapter = new DeliveryAddressAdapter(deliveryAddressModels, this, this);
        rvAddress.setAdapter(deliveryAddressAdapter);
    }

    @OnClick({R.id.ll_home, R.id.ll_cod, R.id.btn_place_order, R.id.tv_add_new, R.id.iv_back,R.id.btn_proceed_to_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                break;
            case R.id.btn_proceed_to_pay:
                if (validate()) {
                    payUsingUpi(totalAmt,"Aahara food order",user.get(KEY_Fullname),"Q333681182@ybl");
                    //payUsingUpi("10","Aahara food order",user.get(KEY_Fullname),"Q333681182@ybl");

                }

                break;
            case R.id.btn_place_order:
                if (validate()) {
                    CallplaceorderApi();
                }
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_add_new:
                Intent intent1 = new Intent(this, PlacePickerActivity.class);
                startActivityForResult(intent1, READ_REQUEST_CODE);
                break;
        }
    }

    private boolean validate() {
        if (!checked) {
            return false;
        } else
            return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                CallViewAddressAPI();
            }
        }else if (requestCode== UPI_PAYMENT){

                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        String[] words = trxt.split("&");
                        String txid = words[0];
                        transaction_id = txid.substring(txid.lastIndexOf("=") + 1);
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }

        }
    }

    private void CallViewAddressAPI() {

        Api apiService = ApiClient.getClient().create(Api.class);
        HashMap<String, String> user = sessionManager.getUserDetails();

        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);

        Call<ServerResponse<ArrayList<ViewAddressModel>>> call = apiService.viewaddress(access_token, "application/json");
        call.enqueue(new Callback<ServerResponse<ArrayList<ViewAddressModel>>>() {
            @Override
            public void onResponse(Call<ServerResponse<ArrayList<ViewAddressModel>>> call, Response<ServerResponse<ArrayList<ViewAddressModel>>> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    ArrayList<ViewAddressModel> viewAddressModels = new ArrayList<>();
                    viewAddressModels.addAll(response.body().getData());
                    initRecycler(viewAddressModels);

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
            public void onFailure(Call<ServerResponse<ArrayList<ViewAddressModel>>> call, Throwable t) {

            }
        });
    }

    private void CallplaceorderApi() {
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        Api apiService = ApiClient.getClient().create(Api.class);
        JsonObject body = new JsonObject();
        body.addProperty("address_id", address_id);
        body.addProperty("payment_mode", payment_mode);
        body.addProperty("tran_id", transaction_id);
        body.addProperty("food_instruction",etInstructions.getText().toString());
        showProgressDialog(this);

        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);

        Call<PlaceOrderPojo> call = apiService.placeorder(access_token, "application/json", body);
        call.enqueue(new Callback<PlaceOrderPojo>() {
            @Override
            public void onResponse(Call<PlaceOrderPojo> call, Response<PlaceOrderPojo> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    Intent intent = new Intent(PlaceOrderActivity.this, Order_Successful.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<PlaceOrderPojo> call, Throwable t) {
                dismissProgressDialog();
                showToast(getApplicationContext(), t.toString());


            }
        });
    }

    @Override
    public void getAddressId(ViewAddressModel deliveryAddressModel) {
        address_id = deliveryAddressModel.getSkAddressId();
        checked = deliveryAddressModel.isSelected();
    }

    public void checkPaymentMethod(){
        paymentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioBtn_cashOnDelivary:
                        btnProceedToPay.setVisibility(View.GONE);
                        btnPlaceOrder.setVisibility(View.VISIBLE);

                        break;
                    case R.id.radioBtn_upipay:
                        btnPlaceOrder.setVisibility(View.GONE);
                        btnProceedToPay.setVisibility(View.VISIBLE);
                        // do operations specific to this selection

                        break;

                }
            }
        });
    }



    private void payUsingUpi(String amount, String note, String name, String upiId) {
        long tranasction_ref= System.currentTimeMillis();
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("mc", "")
                .appendQueryParameter("tr", String.valueOf(tranasction_ref))
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(PlaceOrderActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(PlaceOrderActivity.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(PlaceOrderActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
                //if (validate()) {
                    payment_mode = "upi";
                    CallplaceorderApi();
                //}

            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(PlaceOrderActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(PlaceOrderActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PlaceOrderActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}
