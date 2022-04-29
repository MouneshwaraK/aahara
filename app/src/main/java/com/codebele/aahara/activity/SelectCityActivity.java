package com.codebele.aahara.activity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codebele.aahara.MainActivity;
import com.codebele.aahara.Models.Models.CityNamePojo;
import com.codebele.aahara.Models.Models.DeliveryAddressModel;
import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.NetworkUtils.ServerResponse;
import com.codebele.aahara.R;
import com.codebele.aahara.adapter.SeclectCityAdapter;
import com.codebele.aahara.utilities.PermissionUtility;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCityActivity extends BaseActivity {
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DeliveryAddressModel> deliveryAddressModels = new ArrayList<>();
    private SeclectCityAdapter seclectCityAdapter;
    com.codebele.SessionManagers.LoginSessionManager sessionManager;
    private String access_token;
    /** ButterKnife Code **/
/*    @BindView(R.id.btn_back)
    ImageView btnBack;*/
    @BindView(R.id.tv_select_city)
    TextView tvSelectCity;
    @BindView(R.id.unit_recycler)
    androidx.recyclerview.widget.RecyclerView unitRecycler;
    @BindView(R.id.btn_save_changes)
    Button btnSaveChanges;
    /** ButterKnife Code **/
    private List<Address> addresses = new ArrayList<>();
    private double latitude;
    private double longitude;
    private String mCountryCode = "";
    private String shortAddress = "";
    private String fullAddress = "";

    private String State = "India";
    private int SelectedPos = 0;
    private FusedLocationProviderClient fusedLocationClient;
    ArrayList<CityNamePojo> cityNamePojos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        ButterKnife.bind(this);
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(this);

        //  updateCurrentLocation();
        callCityListAPI(State);

    }


    private void updateCurrentLocation() {
        if (latitude == 0 && longitude == 0) {
            if (PermissionUtility.checkLocationFineAndCoarsePermission(this))
                startLocationUpdates();
        }
    }

    private void getAddressForLocation() {
        setAddress(latitude, longitude);
    }

    private void setAddress(double latitude, double longitude) {

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);
            this.addresses = addresses;
        }catch (Exception e) {

        }
        State= addresses.get(0).getAdminArea();
        callCityListAPI(State);
    }


    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback(), Looper.getMainLooper());

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            getAddressForLocation();
                        }
                    }
                });
    }


    private void initRecycler(ArrayList<CityNamePojo> cityNamePojos) {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        unitRecycler.setLayoutManager(layoutManager);
        seclectCityAdapter = new SeclectCityAdapter(cityNamePojos, this,this);
        unitRecycler.setAdapter(seclectCityAdapter);
    }


    private void callCityListAPI(String state) {

        HashMap<String, String> user = sessionManager.getUserDetails();
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("country_name", state);
        Api apiService = ApiClient.getClient().create(Api.class);
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
      //  access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        showProgressDialog(this);
        Call<ServerResponse<ArrayList<CityNamePojo>>> call = apiService.getcityBYstatename(access_token, "application/json",jsonObject);
        call.enqueue(new Callback<ServerResponse<ArrayList<CityNamePojo>>>() {
            @Override
            public void onResponse(Call<ServerResponse<ArrayList<CityNamePojo>>> call, Response<ServerResponse<ArrayList<CityNamePojo>>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    cityNamePojos= response.body().getData();
                    initRecycler(cityNamePojos);
                     //   viewcartList.addAll(response.body().getItems());

                    }
                else{
                       /* if (response.body().getItems().isEmpty()) {*//*
                            rvCart.setVisibility(View.GONE);
                            llAmtDetails.setVisibility(View.GONE);
                            rlEmptyCart.setVisibility(View.VISIBLE);
                            tvManage.setText("Oops No Items found");*//*
                        } else {
                           // initRecycler(response.body().getItems());

                        }*/
//                        mParent.showToast(getContext(), response.body().getMessage());
                    }

                }


            @Override
            public void onFailure(Call<ServerResponse<ArrayList<CityNamePojo>>> call, Throwable t) {
                dismissProgressDialog();
//                mParent.showToast(getContext(), t.toString());


            }
        });
    }

    private void callLastLogin(String cityId) {

        HashMap<String, String> user = sessionManager.getUserDetails();
        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("last_login_city", cityId);
        Api apiService = ApiClient.getClient().create(Api.class);
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        showProgressDialog(this);
        Call<ServerResponse<Object>> call = apiService.userlastlogin(access_token, "application/json",jsonObject);
        call.enqueue(new Callback<ServerResponse<Object>>() {
            @Override
            public void onResponse(Call<ServerResponse<Object>> call, Response<ServerResponse<Object>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {

                    Intent intent = new Intent(SelectCityActivity.this, MainActivity.class);
                    startActivity(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();

                } else {

                }

            }


            @Override
            public void onFailure(Call<ServerResponse<Object>> call, Throwable t) {
                dismissProgressDialog();
//                mParent.showToast(getContext(), t.toString());


            }
        });
    }

    @SuppressLint("WrongConstant")
    @OnClick({ R.id.btn_save_changes})
    public void bottomMenuClick(View view) {
        Fragment currentBackStackFragment = getSupportFragmentManager().findFragmentByTag("FRAGMENT");
        switch (view.getId()) {
            /*case R.id.btn_back:
              //  finish();
                break;*/

            case R.id.btn_save_changes:
                if(!cityNamePojos.get(SelectedPos).getCityId().equals("")) {
                    sessionManager.setCity(cityNamePojos.get(SelectedPos).getCityId(), cityNamePojos.get(SelectedPos).getCityName(), true);

                    callLastLogin(cityNamePojos.get(SelectedPos).getCityId());
                }

                break;


        }
    }

    public void setCity(int pos) {
        SelectedPos = pos;
        btnSaveChanges.setBackground(getResources().getDrawable(R.drawable.button_bg));
    }
}