package com.codebele.aahara.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codebele.aahara.Models.Models.DeliveryAddressModel;
import com.codebele.aahara.R;
import com.codebele.aahara.utilities.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnCameraChangeListener {
    private GoogleMap mMap;
    private GoogleApiClient mClient;
    private Location mLastLocation;
    private Marker mCurrentLocationMarker;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    public static final int REQUEST_LOCATION_CODE = 99;
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.btn_place_order)
    Button btnPlaceOrder;
    /**
     * ButterKnife Code
     **/
    public static final int READ_REQUEST_CODE = 101;
    private DeliveryAddressModel deliveryAddressModel;
    private ArrayList<DeliveryAddressModel> deliveryAddressModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        deliveryAddressModel = new DeliveryAddressModel();
        checkLocationPermission();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    Log.i("MainActivity", "Location: " + location.getLatitude()
                            + " " + location.getLongitude());
                    if (mLastLocation == null || mLastLocation.equals(location)) {
                        Log.i("MainActivity", "set: " + location.getLatitude()
                                + " " + location.getLongitude());
                        mLastLocation = location;
//                        etAddress.setText(+ location.getLatitude()
//                                + " " + location.getLongitude());
                        try {
                            Geocoder geo = new Geocoder(MapsActivity.this.getApplicationContext(), Locale.getDefault());
                            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (addresses.isEmpty()) {
                                etAddress.setText("Waiting for Location");
                            } else {
                                if (addresses.size() > 0) {
                                    etAddress.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName() + ", " + addresses.get(0).getPostalCode());
                                    //Toast.makeText(getApplicationContext(), "Address:                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 - " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                                    btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            deliveryAddressModel.setAddress(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName() + ", " + addresses.get(0).getPostalCode());
                                            deliveryAddressModels.add(deliveryAddressModel);
//                                           String address = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName()+ ", " + addresses.get(0).getPostalCode();
                                            Intent intent = new Intent();
                                            intent.putExtra(Constants.KEY_ADDRESS, deliveryAddressModels);
                                            setResult(READ_REQUEST_CODE, intent);
                                            finish();
                                        }
                                    });
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace(); // getFromLocation() may sometimes fail
                        }
                        if (mCurrentLocationMarker != null) {
                            mCurrentLocationMarker.remove();
                        }


                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title("current location");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_AZURE));
                        mCurrentLocationMarker = mMap.addMarker(markerOptions);

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                    }
                }
            }
        };

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_CODE);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();

    }

    protected void requestLocationUpdates() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                    Looper.myLooper());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mClient != null && mFusedLocationClient != null) {
            requestLocationUpdates();
        } else {
            buildGoogleApiClient();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission
                                    .ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(false);

                    }
                } else {
                    Toast.makeText(this, "Permission Denied!",
                            Toast.LENGTH_SHORT).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onCameraChange(CameraPosition position) {
        LatLng center = mMap.getCameraPosition().target;
        mCurrentLocationMarker.setPosition(center);
//        mMap.clear();
//        mMap.addMarker( new MarkerOptions()
//                .position( position.target )
//                .title( position.toString() )
//        );
    }
}
