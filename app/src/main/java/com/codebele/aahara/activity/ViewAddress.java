package com.codebele.aahara.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.Models.Models.AddAddressPojo;
import com.codebele.aahara.Models.Models.DeliveryAddressModel;
import com.codebele.aahara.Models.Models.MyAddressListModel;
import com.codebele.aahara.Models.Models.ViewAddressModel;
import com.codebele.aahara.Models.Models.ViewCartPojo;
import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.NetworkUtils.ServerResponse;
import com.codebele.aahara.R;
import com.codebele.aahara.ResponsePojo.DetAddressPojo;
import com.codebele.aahara.ResponsePojo.UpDateAddressPojo;
import com.codebele.aahara.adapter.DeliveryAddressAdapter;
import com.codebele.aahara.adapter.MyAddressAdapter;
import com.codebele.aahara.utilities.Constants;
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

public class ViewAddress  extends AppCompatActivity {
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.tv_myaddress)
    TextView tvMyaddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.img_add_address)
    ImageView imgAddAddress;
    @BindView(R.id.tv_add_new)
    TextView tvAddNew;
    @BindView(R.id.rv_address1)
    androidx.recyclerview.widget.RecyclerView rvAddress1;
    /**
     * ButterKnife Code
     **/

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;


    private List<MyAddressListModel> list;
    private ArrayList<DeliveryAddressModel> deliveryAddressModels = new ArrayList<>();
    private DeliveryAddressAdapter deliveryAddressAdapter;
    private static final int READ_REQUEST_CODE = 1;
    DeliveryAddressModel address;
    com.codebele.SessionManagers.LoginSessionManager sessionManager;
    private String access_token;
    Context context;
    ViewAddressModel viewAddressModel;
    UpDateAddressPojo upDateAddressPojo;
    ArrayList<ViewAddressModel> viewAddressModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewaddress);
        ButterKnife.bind(this);
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(this);
        list = new ArrayList<>();

        //loading list view item with this function
        //loadRecyclerViewItem();
        CallViewAddressAPI();

    }
    private void initRecycler(ArrayList<ViewAddressModel> viewAddressModels) {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAddress1.setLayoutManager(layoutManager);
        MyAddressAdapter myAddressAdapter= new MyAddressAdapter(viewAddressModels, this,this);
        rvAddress1.setAdapter(myAddressAdapter);
    }
    @OnClick({R.id.ll_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                Intent intent = new Intent(this, PlacePickerActivity.class);
                intent.putExtra("isUpdate",false);
                startActivityForResult(intent, READ_REQUEST_CODE);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
          /*      deliveryAddressModels = (ArrayList<DeliveryAddressModel>) data.getSerializableExtra(Constants.KEY_ADDRESS);
                for (int i = 0; i <= deliveryAddressModels.size(); i++) {
                    viewAddressModel = new ViewAddressModel(deliveryAddressModels.get(i).getAddress(),deliveryAddressModels.get(i).getAddress(),deliveryAddressModels.get(i).getAddress(),deliveryAddressModels.get(i).getAddress(),deliveryAddressModels.get(i).getAddress(),deliveryAddressModels.get(i).getAddress());
                    viewAddressModels.add(viewAddressModel);
                  //  initRecycler(deliveryAddressModels);
                    initRecycler(viewAddressModels);
                }*/

                CallViewAddressAPI();
            } else {

            }
        }
    }

/*    private void loadRecyclerViewItem() {
        for (int i = 1; i <= 5; i++) {
            MyAddressListModel myList = new MyAddressListModel();
            list.add(myList);
        }

        adapter = new MyAddressAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }*/
    private void CallViewAddressAPI() {

        Api apiService = ApiClient.getClient().create(Api.class);
        HashMap<String, String> user = sessionManager.getUserDetails();
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        Call<ServerResponse<ArrayList<ViewAddressModel>>>call = apiService.viewaddress(access_token, "application/json");
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

    public void CallDeleteAddressAPI(ViewAddressModel viewAddressModel) {
        Api apiService = ApiClient.getClient().create(Api.class);
        HashMap<String, String> user = sessionManager.getUserDetails();
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        JsonObject body = new JsonObject();
        body.addProperty("sk_address_id", viewAddressModel.getSkAddressId());
        Call<DetAddressPojo>call = apiService.deleteaddress(access_token, "application/json",body);
        call.enqueue(new Callback<DetAddressPojo>() {
            @Override
            public void onResponse(Call<DetAddressPojo> call, Response<DetAddressPojo> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;

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
            public void onFailure(Call<DetAddressPojo> call, Throwable t) {

            }
        });
        CallViewAddressAPI();
    }

//    public void CallUpdateApi(ViewAddressModel viewAddressModel) {
//        Api api = ApiClient.getClient().create(Api.class);
//        HashMap<String, String> user = sessionManager.getUserDetails();
//        Api apiService = ApiClient.getClient().create(Api.class);
//        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
//        JsonObject body = new JsonObject();
//        body.addProperty("sk_address_id",viewAddressModel.getSkAddressId());
//        body.addProperty("address_name",viewAddressModel.getAddressName());
//        body.addProperty("city",viewAddressModel.getCity());
//        body.addProperty("category_adress","home");
//        body.addProperty("landmark",viewAddressModel.getLandmark());
//        body.addProperty("pincode",viewAddressModel.getPincode());
//        body.addProperty("address_status","active");
//        Call<ServerResponse<UpDateAddressPojo>> call = api.updateaddress(access_token,body);
//        call.enqueue(new Callback<ServerResponse<UpDateAddressPojo>>() {
//            @Override
//            public void onResponse(Call<ServerResponse<UpDateAddressPojo>> call, Response<ServerResponse<UpDateAddressPojo>> response) {
//
//                if (response.isSuccessful()) {
//                    assert response.body() != null;
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse<UpDateAddressPojo>> call, Throwable t) {
//
//            }
//        });
//
//
//    }

    public void goToplacepicker(boolean b,ViewAddressModel viewAddressModel) {
        Intent intent = new Intent(this, PlacePickerActivity.class);
        intent.putExtra("isUpdate",b);
        intent.putExtra("addAddress",viewAddressModel);
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
}