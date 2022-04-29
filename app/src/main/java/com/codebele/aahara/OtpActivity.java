package com.codebele.aahara;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.NetworkUtils.ServerResponse;
import com.codebele.aahara.ResponsePojo.LoginPojo;
import com.codebele.aahara.ResponsePojo.OtpVerifyPojo;
import com.codebele.aahara.ResponsePojo.ResendOtpPojo;
import com.codebele.aahara.MainActivity;
import com.codebele.aahara.activity.SelectCityActivity;
import com.codebele.aahara.activity.SignUpActivity;
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

public class OtpActivity extends BaseActivity implements View.OnClickListener {
    /** ButterKnife Code **/
    @BindView(R.id.toolbar)
    androidx.appcompat.widget.Toolbar toolbar;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.verification)
    TextView verification;
    @BindView(R.id.tv_send_otp)
    TextView tvSendOtp;
    @BindView(R.id.tv_send_number)
    TextView tvSendNumber;
    @BindView(R.id.lnrl_otp)
    LinearLayout lnrlOtp;
    @BindView(R.id.et_otp)
    EditText etOtp;
    @BindView(R.id.tv_invalid)
    TextView tvInvalid;
    @BindView(R.id.ll_timer)
    LinearLayout llTimer;
    @BindView(R.id.chronometer)
    Chronometer chronometer;
    @BindView(R.id.txt_otp)
    TextView txtOtp;
    @BindView(R.id.tv_resend)
    TextView tvResend;
    /** ButterKnife Code **/
    String etEmail = "";
    String otp;
    String message;
    String status_code="";
    LoginPojo loginPojo;
   ResendOtpPojo resendOtpPojo;
    public static final String OTP_REGEX = "[0-9]{1,4}";
    private long dayInMilli = 30 * 1 * 1 * 1000;
    com.codebele.SessionManagers.LoginSessionManager sessionManager;
    HashMap<String, String> user = new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getApplicationContext());
        setContentView(R.layout.activity_otp);
        if (sessionManager.isUserLoggedIn()) {
            user = sessionManager.getUserDetails();
        }
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            chronometer.setCountDown(true);
        }
        chronometer.setBase(SystemClock.elapsedRealtime() + dayInMilli);
        chronometer.start();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometerChanged) {
                chronometer = chronometerChanged;
                if (chronometerChanged.getText().toString().equalsIgnoreCase("00:00")) {

                    chronometer.stop();
                    tvResend.setEnabled(true);
                   // tvResend.setTextColor(getResources().getColor(R.color.red));
                    chronometer.setVisibility(View.GONE);
                }
            }
        });

        chronometer.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        if (intent.getExtras() != null)
            etEmail = intent.getExtras().getString("mobile");
        otp = intent.getStringExtra("otp");
        status_code = intent.getStringExtra("status_code");
        message = intent.getStringExtra("message");
        tvSendNumber.setText(etEmail);
        loginPojo= (LoginPojo) getIntent().getSerializableExtra("login_pojo") ;
        otpnumber();
    }
    private boolean validate() {
        if (etOtp.getText().length() < 4) {
            tvInvalid.setVisibility(View.VISIBLE);
            return false;
        } else {
            tvInvalid.setVisibility(View.GONE);
            return true;

        }
    }

    private void otpVerify() {
        Api api = ApiClient.getClient().create(Api.class);
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("otp", etOtp.getText().toString());
        jsonObject.addProperty("mobile", etEmail.toString());
        jsonObject.addProperty("login_pojo", loginPojo.toString());
        showProgressDialog(this);


        Call<ServerResponse<ArrayList<OtpVerifyPojo>>>call = api.otpverify( "e026295867b9d6587c38a11c70b29d6b6e31f3c2",jsonObject);
        call.enqueue(new Callback<ServerResponse<ArrayList<OtpVerifyPojo>>>() {


            @Override
            public void onResponse(Call<ServerResponse<ArrayList<OtpVerifyPojo>>> call, Response<ServerResponse<ArrayList<OtpVerifyPojo>>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    Log.d("response", response.body().toString());
                    assert response.body() != null;
                    if (response.body().getData().equals("0")) {
                        tvInvalid.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(OtpActivity.this, SignUpActivity.class);
                        startActivity(intent);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();

                    } else if (response.body().getStatus_code().equals("1")) {
                        if(loginPojo.getList().getCity_name()!=null) {
                            if (!loginPojo.getList().getCity_name().isEmpty()) {
                                Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                                tvInvalid.setVisibility(View.GONE);
                                startActivity(intent);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                finish();
                            } else {
                                Intent intent = new Intent(OtpActivity.this, SelectCityActivity.class);
                                tvInvalid.setVisibility(View.GONE);
                                startActivity(intent);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                finish();
                            }
                        }
                        else {
                            Intent intent = new Intent(OtpActivity.this, SelectCityActivity.class);
                            tvInvalid.setVisibility(View.GONE);
                            startActivity(intent);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            finish();
                        }
                    }else if(response.body().getStatus_code().equals("2")){
                        if(loginPojo.getList().getCity_name()!=null) {
                            if (!loginPojo.getList().getCity_name().isEmpty()) {
                                Intent intent = new Intent(OtpActivity.this, com.codebele.aahara.MainActivity.class);
                                tvInvalid.setVisibility(View.GONE);
                                startActivity(intent);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                finish();
                            } else {
                                Intent intent = new Intent(OtpActivity.this, SelectCityActivity.class);
                                tvInvalid.setVisibility(View.GONE);
                                startActivity(intent);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                finish();
                            }
                        }
                        else {
                            Intent intent = new Intent(OtpActivity.this, SelectCityActivity.class);
                            tvInvalid.setVisibility(View.GONE);
                            startActivity(intent);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            finish();
                        }

                    } else {
                        try {
                            String error_message = response.errorBody().string();
                            JSONObject jObjError = new JSONObject(error_message);
                            showToast(OtpActivity.this, jObjError.getString("message"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToast(OtpActivity.this, getResources().getString(R.string.Invalid_Otp));
                        } catch (IOException e) {
                            e.printStackTrace();

                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<ArrayList<OtpVerifyPojo>>> call, Throwable t){
                    dismissProgressDialog();
                    showToast(getApplicationContext(), t.toString());
//                enterMail.setVisibility(View.GONE);
//                wrongMail.setVisibility(View.GONE);

            }
        });
    }
    private void otpnumber() {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etOtp.getText().length() >= 4) {
                    if (validate()) {
                       // otpVerify();
//                    String otp = otp1;
                        if (otp.equals(etOtp.getText().toString())){

                            if (message.equalsIgnoreCase("user exits")) {
                                if(loginPojo.getList().getCity_name()!=null) {
                                    if (!loginPojo.getList().getCity_name().isEmpty()) {
                                        Intent intent = new Intent(OtpActivity.this, com.codebele.aahara.MainActivity.class);
                                        tvInvalid.setVisibility(View.GONE);
                                        startActivity(intent);
                                        chronometer.stop();
                                        intent.putExtra("email", etEmail);
                                        intent.putExtra("status_code", status_code);
                                        sessionManager.createUserLoginSession(loginPojo.getList().getAccessToken(),
                                                loginPojo.getList().getFullName(), loginPojo.getList().getMobile(), loginPojo.getList().getEmail(), loginPojo.getList().getCityId(), loginPojo.getList().getCity_name());
                                        sessionManager.setisCity(true);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(OtpActivity.this, SelectCityActivity.class);
                                        tvInvalid.setVisibility(View.GONE);
                                        chronometer.stop();
                                        intent.putExtra("email", etEmail);
                                        intent.putExtra("status_code", status_code);
                                        sessionManager.createUserLoginSession(loginPojo.getList().getAccessToken(),
                                                loginPojo.getList().getFullName(), loginPojo.getList().getMobile(), loginPojo.getList().getEmail(), loginPojo.getList().getCityId(), loginPojo.getList().getCity_name());

                                        startActivity(intent);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        finish();
                                    }
                                }else{
                                    Intent intent = new Intent(OtpActivity.this, SelectCityActivity.class);
                                    tvInvalid.setVisibility(View.GONE);
                                    chronometer.stop();
                                    intent.putExtra("email", etEmail);
                                    intent.putExtra("status_code", status_code);
                                    sessionManager.createUserLoginSession(loginPojo.getList().getAccessToken(),
                                            loginPojo.getList().getFullName(), loginPojo.getList().getMobile(), loginPojo.getList().getEmail(), loginPojo.getList().getCityId(), loginPojo.getList().getCity_name());

                                    startActivity(intent);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    finish();
                                }
                             /*   Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                                chronometer.stop();
                                intent.putExtra("email", etEmail);
                                intent.putExtra("status_code",status_code);
                                sessionManager.createUserLoginSession(loginPojo.getList().getAccessToken(),
                                        loginPojo.getList().getFullName(),loginPojo.getList().getMobile(),loginPojo.getList().getEmail(),loginPojo.getList().getCityId(),loginPojo.getList().getCity_name());
                                startActivity(intent);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                finish();*/
                            }
                            else{
                                Intent intent = new Intent(OtpActivity.this, SignUpActivity.class);
                                intent.putExtra("mobile", etEmail);
                                chronometer.stop();
                                startActivity(intent);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                finish();
                            }

                        } else {
                            tvInvalid.setVisibility(View.VISIBLE);
//                            chronometer.stop();
                        }

                    }
                }

            }
        };
        etOtp.addTextChangedListener(tw);
//        chronometer.stop();
    }

    @OnClick({R.id.img_back, R.id.tv_resend})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tv_resend:
                callResendOtp();
                break;

        }
    }

    private void callResendOtp() {
        Api api = ApiClient.getClient().create(Api.class);
        JsonObject body = new JsonObject();
        body.addProperty("mobile", etEmail);
        body.addProperty("otp", otp);
        showProgressDialog(this);
        Call<ServerResponse<ResendOtpPojo>> call = api.resendotp("", body);
        call.enqueue(new Callback<ServerResponse<ResendOtpPojo>>() {
            @Override
            public void onResponse(Call<ServerResponse<ResendOtpPojo>> call, Response<ServerResponse<ResendOtpPojo>> response) {
                dismissProgressDialog();

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        resendOtpPojo =response.body().getData();
                        showToast(getApplicationContext(), response.body().getStatusMessage());
                        otp = response.body().getOtp();
                       // registrationPojo.setOtp(response.body().getData().getOtp());
                        chronometer.setBase(SystemClock.elapsedRealtime() + dayInMilli);
                        chronometer.start();
                        tvResend.setEnabled(false);
                        chronometer.setVisibility(View.VISIBLE);
                    } else {
                        showToast(getApplicationContext(), response.body().getStatusMessage());
                    }

                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
                        showToast(OtpActivity.this, jObjError.getString("message"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        showToast(OtpActivity.this, getResources().getString(R.string.something_wrong));

                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }
            }


            @Override
            public void onFailure(Call<ServerResponse<ResendOtpPojo>> call, Throwable t) {
                dismissProgressDialog();
                showToast(getApplicationContext(), t.toString());

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //chronometer.stop();
    }
}
