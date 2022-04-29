package com.codebele.aahara.activity;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.NetworkUtils.ServerResponse;
import com.codebele.aahara.R;
import com.codebele.aahara.ResponsePojo.SignupPojo;


import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity implements View.OnClickListener{
    /** ButterKnife Code **/
    @BindView(R.id.personal_toolbar)
    androidx.appcompat.widget.Toolbar personalToolbar;
    @BindView(R.id.layout_input_name)
    com.google.android.material.textfield.TextInputLayout layoutInputName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_name_error)
    TextView tvNameError;
    @BindView(R.id.layout_input_email)
    com.google.android.material.textfield.TextInputLayout layoutInputEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.tv_email_error)
    TextView tvEmailError;
    @BindView(R.id.btn_continue)
    Button btnContinue;
    /** ButterKnife Code **/
    String mobile = "";
    private com.codebele.SessionManagers.LoginSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_personal_details );
        ButterKnife.bind(this);
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(this);
        btnContinue.setAlpha(.5f);
        btnContinue.setClickable(false);
        textwatcher();
        Intent intent = getIntent();
        if (intent.getExtras() != null)
            mobile = intent.getExtras().getString("mobile");
//        if (getIntent().get)
    }

    @OnClick({R.id.btn_continue})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continue:
                if (validate()){
                    callSignUpApi();
                    break;
                }
        }
    }

    private void callSignUpApi() {
        Api api = ApiClient.getClient().create(Api.class);
        JsonObject body = new JsonObject();
        body.addProperty("full_name",etName.getText().toString());
        body.addProperty("email",etEmail.getText().toString());
        body.addProperty("mobile",mobile);
        Call<ServerResponse<SignupPojo>> call = api.postSignup("application/json",body);
        showProgressDialog(SignUpActivity.this);
        call.enqueue(new Callback<ServerResponse<SignupPojo>>() {
            @Override
            public void onResponse(Call<ServerResponse<SignupPojo>> call, Response<ServerResponse<SignupPojo>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isStatus()) {
                        SignupPojo signupPojo = response.body().getData();
                        sessionManager.createSession(signupPojo);
                        sessionManager.createUserLoginSession(signupPojo.getAccessToken(),
                                signupPojo.getFullName(), signupPojo.getMobile(), signupPojo.getEmail(), "","");
                        Intent intent = new Intent(SignUpActivity.this, SelectCityActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
//                        showToast(getApplicationContext(), response.body().getStatusMessage());
                    }


                    } else {
                        try {
                            String error_message = response.errorBody().string();
                            JSONObject jObjError = new JSONObject(error_message);
//                            showToast(SignUpActivity.this, jObjError.getString("message"));

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            showToast(SignUpActivity.this, getResources().getString(R.string.something_wrong));

                        } catch (IOException e) {
                            e.printStackTrace();

                        }

                    }
                }


            @Override
            public void onFailure(Call<ServerResponse<SignupPojo>> call, Throwable t) {
                dismissProgressDialog();
//                showToast(getApplicationContext(),t.toString());

            }
        });
    }

    private boolean validate() {
        if (etName.getText().toString().isEmpty()) {
            tvNameError.setVisibility(View.VISIBLE);
            etName.requestFocus();
            return false;
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            tvEmailError.setVisibility(View.VISIBLE);
            etEmail.requestFocus();
            return false;
        }else {
            return true;
        }

    }


    private void textwatcher() {
    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvNameError.setVisibility(View.GONE);
            tvEmailError.setVisibility(View.GONE);
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches() && etName.getText().length() >= 1) {
                btnContinue.setEnabled(true);
                btnContinue.setAlpha(1);
                btnContinue.setClickable(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    etEmail.addTextChangedListener(tw);
    etName.addTextChangedListener(tw);
}
}