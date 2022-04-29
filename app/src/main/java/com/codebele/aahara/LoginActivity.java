package com.codebele.aahara;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.codebele.aahara.Models.Models.ImageSliderModel;
import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.ResponsePojo.LoginPojo;
import com.codebele.aahara.MainActivity;
import com.codebele.aahara.adapter.ImageSliderAdapter;

import com.codebele.aahara.app.Config;
import com.codebele.aahara.utilities.NotificationUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
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

import static com.codebele.aahara.SessionManagers.Utils.dismissProgressDialog;
import static com.codebele.aahara.SessionManagers.Utils.showProgressDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.tv_logo_name)
    TextView tvLogoName;
    //    @BindView(R.id.txt_login)
//    TextView txtLogin;
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    //    @BindView(R.id.txt_pin)
//    TextView txtPin;
    @BindView(R.id.number)
    LinearLayout number;
    //    @BindView(R.id.txt_code)
//    TextView txtCode;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.enter_mail)
    TextView enterMail;
    @BindView(R.id.wrong_mail)
    TextView wrongMail;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.terms_and_conditions)
    TextView terms_and_conditions;
    @BindView(R.id.privacy_policy)
    TextView privacy_policy;
    String deviceToken;

    @BindView(R.id.image_slide_smoothley)
    androidx.recyclerview.widget.RecyclerView recyclerView;
    /**
     * ButterKnife Code
     **/
    LoginPojo loginPojo;

    ArrayList<ImageSliderModel> model = new ArrayList<>();
    Context context;
    int scrollCount = 0;
    private ImageSliderAdapter adapter;
    Uri uri, uri1, uri2;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    com.codebele.SessionManagers.LoginSessionManager sessionManager;
    HashMap<String, String> user = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getApplicationContext());
        setContentView(R.layout.activity_login);

        if (sessionManager.isUserLoggedIn()) {
            user = sessionManager.getUserDetails();
            // GoogleCloudMessaging  gcm = GoogleCloudMessaging.getInstance(this);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        ButterKnife.bind(this);
        loginnumber();
        firebse_push_notification();
    }

    private void firebse_push_notification() {
        /////firebase setup

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                deviceToken = instanceIdResult.getToken();
                Log.e("deviceToken", deviceToken);
                // Saving reg id to shared preferences
                storeRegIdInPref(deviceToken);

                // sending reg id to your server
                sendRegistrationToServer(deviceToken);
            }
        });


        // txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        //  txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    // txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();

    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            // txtRegId.setText("Firebase Reg Id: " + regId);
        } else {
            // txtRegId.setText("Firebase Reg Id is not received yet!");
        }

    }

    private void loginnumber() {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etEmail.getText().length() >= 10) {
                    login.setVisibility(View.VISIBLE);
                    login.setEnabled(true);
                    // login.setBackgroundTintList(getResources().getColorStateList(R.color.red));
                    //login.setTextColor(getResources().getColor(R.color.white));

                } else if (etEmail.getText().length() <= 9) {
                    login.setEnabled(false);
                    //  login.setBackgroundTintList(getResources().getColorStateList(R.color.gray_text));
                    //  login.setTextColor(getResources().getColor(R.color.white));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etEmail.getText().length() >= 10) {
                    login.setVisibility(View.VISIBLE);
                    login.setEnabled(true);
                    //   login.setBackgroundColor(getResources().getColor(R.color.red));
                    //login.setTextColor(getResources().getColor(R.color.white));

                } else if (etEmail.getText().length() <= 9) {
                    login.setEnabled(false);
                    //  login.setBackgroundColor(getResources().getColor(R.color.gray_text));
                    //  login.setTextColor(getResources().getColor(R.color.white));
                }

            }
        };
        etEmail.addTextChangedListener(tw);
    }

    @OnClick({R.id.login, R.id.terms_and_conditions, R.id.privacy_policy})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (validate()) {
                    callLoginApi();
                }
//                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
//                intent.putExtra("etemail",etEmail.getText().toString());;
//                startActivity(intent);
                break;
            case R.id.terms_and_conditions:
                uri = Uri.parse("https://www.aahara.co.in/terms"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.privacy_policy:
                uri1 = Uri.parse("https://www.aahara.co.in/privacy-policy"); // missing 'http://' will cause crashed
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(intent1);
                break;


        }
    }


    private boolean validate() {
        if (etEmail.getText().length() < 0) {
            enterMail.setVisibility(View.VISIBLE);
            wrongMail.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            return false;
        } else if (etEmail.getText().length() < 10) {
            wrongMail.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
            enterMail.setVisibility(View.GONE);
            return false;
        } else {
            enterMail.setVisibility(View.GONE);
            wrongMail.setVisibility(View.GONE);
            return true;
        }
    }

    private void callLoginApi() {
        Api api = ApiClient.getClient().create(Api.class);
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobile", etEmail.getText().toString());
        jsonObject.addProperty("device_token", deviceToken);
        showProgressDialog(this);


        Call<LoginPojo> call = api.login(jsonObject);
        call.enqueue(new Callback<LoginPojo>() {


            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                dismissProgressDialog();

                if (response.isSuccessful()) {
                    Log.d("response", response.body().toString());
                    assert response.body() != null;
                    loginPojo = response.body();

                    Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                    intent.putExtra("mobile", etEmail.getText().toString());
                    intent.putExtra("otp", loginPojo.getOtp());
                    intent.putExtra("message", loginPojo.getMessage());
                    intent.putExtra("status_code", loginPojo.getStatus_code());
                    intent.putExtra("login_pojo", loginPojo);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
                        enterMail.setVisibility(View.GONE);
                        wrongMail.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }
            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t) {
                dismissProgressDialog();
                enterMail.setVisibility(View.GONE);
                wrongMail.setVisibility(View.GONE);
            }
        });
    }

    private void imageslider() {
        adapter = new ImageSliderAdapter(model, this);
        model.add(new ImageSliderModel(R.drawable.image1));
        model.add(new ImageSliderModel(R.drawable.image2));
        model.add(new ImageSliderModel(R.drawable.image03));
        model.add(new ImageSliderModel(R.drawable.image4));
        model.add(new ImageSliderModel(R.drawable.image5));
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageslider();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(LoginActivity.this) {
                    private static final float SPEED = 3500f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);

            }
        };
        autoScrollAnother();
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(0);
        recyclerView.setDrawingCacheEnabled(false);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                // true: consume touch event
                // false: dispatch touch event
                return true;
            }
        });


        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

    }

    public void autoScrollAnother() {

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition((scrollCount++));
                if (scrollCount == adapter.getItemCount() - 4) {
                    model.addAll(model);
                    adapter.notifyDataSetChanged();
                }
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }


    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
        Log.e(TAG, "leaving shared preference: " + token);
    }
}
