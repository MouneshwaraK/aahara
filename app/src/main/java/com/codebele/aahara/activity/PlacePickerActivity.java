package com.codebele.aahara.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.codebele.aahara.LoginActivity;
import com.codebele.aahara.Models.Models.AddAddressPojo;
import com.codebele.aahara.Models.Models.AddressData;
import com.codebele.aahara.Models.Models.DeliveryAddressModel;
import com.codebele.aahara.Models.Models.ViewAddressModel;
import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.NetworkUtils.ServerResponse;
import com.codebele.aahara.R;
import com.codebele.aahara.ResponsePojo.SignupPojo;
import com.codebele.aahara.ResponsePojo.UpDateAddressPojo;
import com.codebele.aahara.utilities.Constants;
import com.codebele.aahara.utilities.CurrentPlaceSelectionBottomSheet;
import com.codebele.aahara.utilities.PermissionUtility;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacePickerActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private ImageView markerImage;
    private ImageView mImgBack;
    private ImageView markerShadowImage;
    private CurrentPlaceSelectionBottomSheet bottomSheet;
    private FloatingActionButton fab;
    private double latitude;
    private double longitude;
    private boolean showLatLong = true;
    private float zoom = 12.0F;
    private boolean addressRequired = true;
    private String shortAddress = "";
    private String fullAddress = "";
    private boolean hideMarkerShadow;
    private int markerDrawableRes = -1;
    private int markerColorRes = -1;
    private int fabColorRes = -1;
    private int primaryTextColorRes = -1;
    private int secondaryTextColorRes = -1;
    private List<Address> addresses = new ArrayList<>();
    private ImageView mBtnSearchLocation;
    private Button mBtnSelectAddress;
    private CardView mBtnSearchBar;
    private String mCountryCode = "";
    private FusedLocationProviderClient fusedLocationClient;
    private ImageView mBtnCurrentLocation;
    private boolean isSearchFromDropdown = false;
    private String googleMapApiKey;
    private DeliveryAddressModel deliveryAddressModel;
    private ArrayList<DeliveryAddressModel> deliveryAddressModels = new ArrayList<>();
    private com.codebele.SessionManagers.LoginSessionManager sessionManager;
    private String access_token;
    private View view;
    boolean isUpdate = false;
    ViewAddressModel viewAddressModel;
    private EditText et_landmark;
    private TextView tv_landmark_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ak_activity_place_picker);
        getIntentData();
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(this);
        initViews();
        deliveryAddressModel = new DeliveryAddressModel();
        updateCurrentLocation();
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
        viewAddressModel = (ViewAddressModel) getIntent().getSerializableExtra("addAddress");
    }

    private void updateCurrentLocation() {
        if (latitude == 0 && longitude == 0) {
            if (PermissionUtility.checkLocationFineAndCoarsePermission(this))
                startLocationUpdates();
        }
    }

    private void initViews() {

        initPlaces();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);

        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheet.showCoordinatesTextView(showLatLong);

        TextView mTvTitle = findViewById(R.id.tv_title);
//        mTvTitle.setText(R.string.search_address);
        markerImage = findViewById(R.id.marker_image_view);
        mImgBack = findViewById(R.id.img_back);
        markerShadowImage = findViewById(R.id.marker_shadow_image_view);
        fab = findViewById(R.id.place_chosen_button);
        mBtnSelectAddress = findViewById(R.id.btnSelectAddress);
        et_landmark = findViewById(R.id.et_landmark);

        mBtnSearchBar = findViewById(R.id.cvSearchAddress);
        tv_landmark_error = findViewById(R.id.tv_landmark_error);
        mBtnSearchLocation = findViewById(R.id.img_search);
        mBtnCurrentLocation = findViewById(R.id.ivCurrentLocation);
        mBtnSelectAddress.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (addresses != null) {

                    if (isUpdate) {
                        CallUpdateApi(viewAddressModel);
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(Constants.KEY_ADDRESS, deliveryAddressModels);
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    } else {
                        if (validate()){
                            CalladdressApi();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra(Constants.KEY_ADDRESS, deliveryAddressModels);
                            setResult(RESULT_OK, returnIntent);
                            finish();
                        }
                    }

                    deliveryAddressModel.setAddress(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getSubLocality() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName() + ", " + addresses.get(0).getPostalCode());
                    deliveryAddressModels.add(deliveryAddressModel);
                    AddressData addressData = new AddressData(latitude, longitude, shortAddress, addresses);

                } else {
                    if (!addressRequired) {
                        AddressData addressData = new AddressData(latitude, longitude, shortAddress, null);
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(Constants.KEY_ADDRESS, deliveryAddressModels);
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_address), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        textwatcher();


       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addresses != null) {
                    AddressData addressData =new AddressData(latitude, longitude, addresses);
                    Intent returnIntent =new Intent();
                    returnIntent.putExtra(Constants.ADDRESS_INTENT, addressData);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    if (!addressRequired) {
                        AddressData addressData =new AddressData(latitude, longitude, null);
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(Constants.ADDRESS_INTENT, addressData);
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_address), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });*/

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutocompletePicker();
                //  openAutocompletePicker(new LatLng(latitude,longitude),50000);
            }
        });

        mBtnSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutocompletePicker();
//                openAutocompletePicker(new LatLng(latitude,longitude),50000);
            }
        });

//        mBtnCurrentLocation.setVisibility(View.GONE);
        mBtnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionUtility.checkLocationFineAndCoarsePermission(PlacePickerActivity.this))
                    startLocationUpdates();
            }
        });
        setIntentCustomization();
    }

    private void textwatcher() {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_landmark.getText().toString())){
                    tv_landmark_error.setVisibility(View.VISIBLE);

                }else {
                    tv_landmark_error.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        et_landmark.addTextChangedListener(tw);
    }

    private boolean validate() {
        if (TextUtils.isEmpty(et_landmark.getText().toString())) {
            tv_landmark_error.setVisibility(View.VISIBLE);
            return false;
        } else {
            return true;
        }
    }
    private void CalladdressApi() {
        Api api = ApiClient.getClient().create(Api.class);
        HashMap<String, String> user = sessionManager.getUserDetails();
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        JsonObject body = new JsonObject();
        body.addProperty("address_name", addresses.get(0).getFeatureName());
        body.addProperty("city", addresses.get(0).getLocality());
        body.addProperty("pincode", addresses.get(0).getPostalCode());
        body.addProperty("address_latitude", addresses.get(0).getLatitude());
        body.addProperty("address_longitude", addresses.get(0).getLongitude());
        body.addProperty("landmark",et_landmark.getText().toString());
        body.addProperty("category_adress", "home");
        Call<ServerResponse<AddAddressPojo>> call = api.address(access_token, body);
        call.enqueue(new Callback<ServerResponse<AddAddressPojo>>() {
            @Override
            public void onResponse(Call<ServerResponse<AddAddressPojo>> call, Response<ServerResponse<AddAddressPojo>> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    showToast(getApplicationContext(),response.body().getStatusMessage());


                }

            }

            @Override
            public void onFailure(Call<ServerResponse<AddAddressPojo>> call, Throwable t) {


            }
        });

//        @Override
//    public void onResume() {
//        super.onResume();
//        CallUpdateApi();
//    }

    }

    private void initPlaces() {
        try {
            // Initialize Places.
//            Places.initialize(this, getResources().getString(R.string.google_maps_api_key));
            Places.initialize(this, googleMapApiKey);
            // Create a new Places client instance.
            PlacesClient placesClient = Places.createClient(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getIntentData() {
        if (getIntent() != null) {

            this.googleMapApiKey = this.getIntent().getStringExtra(Constants.GOOGLE_MAP_API_KEY);
            this.latitude = this.getIntent().getDoubleExtra(Constants.INITIAL_LATITUDE_INTENT, 0.0D);
            this.longitude = this.getIntent().getDoubleExtra(Constants.INITIAL_LONGITUDE_INTENT, 0.0D);
            this.showLatLong = this.getIntent().getBooleanExtra(Constants.SHOW_LAT_LONG_INTENT, false);
            this.addressRequired = this.getIntent().getBooleanExtra(Constants.ADDRESS_REQUIRED_INTENT, true);
            this.hideMarkerShadow = this.getIntent().getBooleanExtra(Constants.HIDE_MARKER_SHADOW_INTENT, false);
            this.zoom = this.getIntent().getFloatExtra(Constants.INITIAL_ZOOM_INTENT, 20);
            this.markerDrawableRes = this.getIntent().getIntExtra(Constants.MARKER_DRAWABLE_RES_INTENT, -1);
            this.markerColorRes = this.getIntent().getIntExtra(Constants.MARKER_COLOR_RES_INTENT, -1);
            this.fabColorRes = this.getIntent().getIntExtra(Constants.FAB_COLOR_RES_INTENT, -1);
            this.primaryTextColorRes = this.getIntent().getIntExtra(Constants.PRIMARY_TEXT_COLOR_RES_INTENT, -1);
            this.secondaryTextColorRes = this.getIntent().getIntExtra(Constants.SECONDARY_TEXT_COLOR_RES_INTENT, -1);
        }
    }

    private void setIntentCustomization() {
        if (hideMarkerShadow)
            markerShadowImage.setVisibility(View.GONE);
        else
            markerShadowImage.setVisibility(View.VISIBLE);

        if (markerColorRes != -1) {
            markerImage.setColorFilter(ContextCompat.getColor(this, markerColorRes));
        }
        if (markerDrawableRes != -1) {
            markerImage.setImageDrawable(ContextCompat.getDrawable(this, markerDrawableRes));
        }
        if (fabColorRes != -1) {
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, fabColorRes)));
        }
        if (primaryTextColorRes != -1) {
            bottomSheet.setPrimaryTextColor(ContextCompat.getColor(this, primaryTextColorRes));
        }
        if (secondaryTextColorRes != -1) {
            bottomSheet.setSecondaryTextColor(ContextCompat.getColor(this, secondaryTextColorRes));
        }

    }

    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        /*if (PermissionUtility.checkLocationFineAndCoarsePermission(this)) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        }*/

        map.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                if (markerImage.getTranslationY() == 0f) {
                    markerImage.animate()
                            .translationY(-5f)
                            .setInterpolator(new OvershootInterpolator())
                            .setDuration(250)
                            .start();
                    if (bottomSheet.isShowing()) {
                        bottomSheet.dismissPlaceDetails();
                    }
                }
            }
        });

        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                markerImage.animate()
                        .translationY(0f)
                        .setInterpolator(new OvershootInterpolator())
                        .setDuration(250)
                        .start();

                if (isSearchFromDropdown) {
                    if (bottomSheet != null)
                        bottomSheet.setPlaceDetails(latitude, longitude, shortAddress, fullAddress);
                    isSearchFromDropdown = false;
                } else {
                    bottomSheet.showLoadingBottomDetails();
                    LatLng latLng = map.getCameraPosition().target;
                    latitude = latLng.latitude;
                    longitude = latLng.longitude;
                    AsyncTask.execute(new Runnable() {
                        public final void run() {
                            getAddressForLocation();
                            runOnUiThread(new Runnable() {
                                public final void run() {
                                    bottomSheet.setPlaceDetails(latitude, longitude, shortAddress, fullAddress);
                                    Log.d("latitude ***", String.valueOf(latitude));
                                    Log.d("Longitue ***", String.valueOf(longitude));

                                }
                            });
                        }
                    });
                }

            }
        });

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom));

    }

    private void getAddressForLocation() {
        setAddress(latitude, longitude);
    }

    private void setAddress(double latitude, double longitude) {

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);
            this.addresses = addresses;
            if (addresses != null && addresses.size() != 0) {
                fullAddress = addresses.get(0).getAddressLine(0);
                shortAddress = generateFinalAddress(fullAddress).trim();
                mCountryCode = addresses.get(0).getCountryCode();
            } else {
                shortAddress = "";
                fullAddress = "";
            }
        } catch (Exception e) {
            shortAddress = "";
            fullAddress = "";
            addresses = null;
            e.printStackTrace();
        }
    }

    private String generateFinalAddress(String address) {
        String strRtr;
        String[] s = address.split(",");
        if (s.length >= 3)
            strRtr = s[1] + "," + s[2];
        else if (s.length == 2)
            strRtr = s[1];
        else
            strRtr = s[0];
        return strRtr;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.AUTOCOMPLETE_PLACE_PICKER_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data != null) {
                            Place place = Autocomplete.getPlaceFromIntent(data);
                            if (place.getLatLng() != null) {
                                //   bottomSheet.showLoadingBottomDetails();
                                isSearchFromDropdown = true;
                                latitude = place.getLatLng().latitude;
                                longitude = place.getLatLng().longitude;
                                shortAddress = place.getName();
                                fullAddress = place.getAddress();

                                Geocoder gcd = new Geocoder(this, Locale.getDefault());
                                List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
                                if (addresses != null && addresses.size() != 0) {
                                    fullAddress = addresses.get(0).getAddressLine(0);
                                    shortAddress = getPlaceName(place.getName(), generateFinalAddress(fullAddress).trim());
//                                    shortAddress += " " +generateFinalAddress(fullAddress).trim();
                                    mCountryCode = addresses.get(0).getCountryCode();
                                }

                                if (map != null)
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom));

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void openAutocompletePicker() {
        try {
            // Set the fields to specify which types of place data to return.
            List<Place.Field> fields = Arrays.asList(Place.Field.ID
                    , Place.Field.NAME
                    , Place.Field.ADDRESS
                    , Place.Field.ADDRESS_COMPONENTS
                    , Place.Field.LAT_LNG, Place.Field.RATING
                    , Place.Field.PHOTO_METADATAS, Place.Field.WEBSITE_URI
                    , Place.Field.VIEWPORT, Place.Field.PRICE_LEVEL
                    , Place.Field.TYPES, Place.Field.OPENING_HOURS
                    , Place.Field.PLUS_CODE, Place.Field.USER_RATINGS_TOTAL);
            // Start the autocomplete intent.

            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(this);

            startActivityForResult(intent, Constants.AUTOCOMPLETE_PLACE_PICKER_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAutocompletePicker(LatLng location, int mDistanceInMeters) {
        double latRadian = Math.toRadians(location.latitude);

        double degLatKm = 110.574235;
        double degLongKm = 110.572833 * Math.cos(latRadian);
        double deltaLat = mDistanceInMeters / 1000.0 / degLatKm;
        double deltaLong = mDistanceInMeters / 1000.0 / degLongKm;

        double minLat = location.latitude - deltaLat;
        double minLong = location.longitude - deltaLong;
        double maxLat = location.latitude + deltaLat;
        double maxLong = location.longitude + deltaLong;

        Log.d("Min lat long", "Min: " + Double.toString(minLat) + "," + Double.toString(minLong));
        Log.d("Max lat long", "Max: " + Double.toString(maxLat) + "," + Double.toString(maxLong));

        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(minLat, minLong),
                new LatLng(maxLat, maxLong));

        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID
                , Place.Field.NAME
                , Place.Field.ADDRESS
                , Place.Field.ADDRESS_COMPONENTS
                , Place.Field.LAT_LNG, Place.Field.RATING
                , Place.Field.PHOTO_METADATAS, Place.Field.WEBSITE_URI
                , Place.Field.VIEWPORT, Place.Field.PRICE_LEVEL
                , Place.Field.TYPES, Place.Field.OPENING_HOURS
                , Place.Field.PLUS_CODE, Place.Field.USER_RATINGS_TOTAL);
        // Start the autocomplete intent.

        Intent intent;

        if (TextUtils.isEmpty(mCountryCode)) {
            intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .setLocationRestriction(bounds)
                    .build(this);
        } else {
            intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .setCountry(mCountryCode)
                    .setLocationRestriction(bounds)

                    .build(this);
        }

        startActivityForResult(intent, Constants.AUTOCOMPLETE_PLACE_PICKER_REQUEST);

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

                            if (map != null)
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom));
                        }
                    }
                });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case Constants.MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();

                } else {
//                    CustomDialogUtil.showAlert(mContext, getString(R.string.permission_popup_title), getString(R.string.location_permission_manually));
                }
                break;
        }
    }

    private String getPlaceName(String shortName, String fullName) {

        String returnName = "";

        if (TextUtils.isEmpty(shortName)) {
            if (!TextUtils.isEmpty(fullName))
                returnName = fullName;
        } else {
            if (TextUtils.isEmpty(fullName))
                returnName = shortName;
            else
                returnName = shortName + ", " + fullName;

        }
        return returnName;

    }

    public void CallUpdateApi(ViewAddressModel viewAddressModel) {
        Api api = ApiClient.getClient().create(Api.class);
        HashMap<String, String> user = sessionManager.getUserDetails();
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        JsonObject body = new JsonObject();
        body.addProperty("sk_address_id", viewAddressModel.getSkAddressId());
        body.addProperty("address_name", addresses.get(0).getFeatureName());
        body.addProperty("city", addresses.get(0).getLocality());
        body.addProperty("pincode", addresses.get(0).getPostalCode());
        body.addProperty("address_latitude", addresses.get(0).getLatitude());
        body.addProperty("address_longitude", addresses.get(0).getLongitude());
        body.addProperty("landmark",et_landmark.getText().toString());
        body.addProperty("landmark", addresses.get(0).getSubLocality());
        body.addProperty("category_adress", "home");
//        body.addProperty("address_status","active");
        Call<ServerResponse<UpDateAddressPojo>> call = api.updateaddress(access_token, body);
        call.enqueue(new Callback<ServerResponse<UpDateAddressPojo>>() {
            @Override
            public void onResponse(Call<ServerResponse<UpDateAddressPojo>> call, Response<ServerResponse<UpDateAddressPojo>> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                }

            }

            @Override
            public void onFailure(Call<ServerResponse<UpDateAddressPojo>> call, Throwable t) {

            }
        });

    }
}
