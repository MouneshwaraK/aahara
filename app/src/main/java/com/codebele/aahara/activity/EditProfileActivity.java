package com.codebele.aahara.activity;

import androidx.annotation.Nullable;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codebele.aahara.MainActivity;
import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.NetworkUtils.ServerResponse;
import com.codebele.aahara.R;
import com.codebele.aahara.utilities.PermissionsManager;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

import static com.codebele.SessionManagers.LoginSessionManager.KEY_Fullname;
import static com.codebele.SessionManagers.LoginSessionManager.KEY_email;

public class EditProfileActivity extends BaseActivity {
    /** ButterKnife Code **/
    @BindView(R.id.toolbar)
    androidx.appcompat.widget.Toolbar toolbar;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_profile)
    RelativeLayout rlProfile;
    @BindView(R.id.iv_profile)
    com.mikhaellopez.circularimageview.CircularImageView ivProfile;
    @BindView(R.id.tv_add_photo)
    TextView tvAddPhoto;
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
    @BindView(R.id.input_location)
    com.google.android.material.textfield.TextInputLayout inputLocation;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.btn_save_changes)
    Button btnSaveChanges;
    /** ButterKnife Code **/
    private PermissionsManager permissionsManager;
    private Bitmap bitmap;
    private Uri fileUri;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int READ_REQUEST_CODE = 101;
    com.codebele.SessionManagers.LoginSessionManager sessionManager;
    private String access_token;

    HashMap<String, String> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getApplicationContext());
        user = sessionManager.getUserDetails();
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        etName.setText(user.get(KEY_Fullname));
        etEmail.setText(user.get(KEY_email));
    }

    private void selectImage(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_edit_pic_card, viewGroup, false);
        TextView chose_pic = (TextView) dialogView.findViewById(R.id.chose_pic);
        TextView take_pic = (TextView) dialogView.findViewById(R.id.take_pic);

        TextView cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
//        ButterKnife.bind(this,dialogView);
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        chose_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                galleryIntent();
                checkPermission(2);
                alertDialog.dismiss();

            }
        });
        take_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cameraIntent();
                checkPermission(1);
                alertDialog.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
    private void checkPermission(int flag) {
        final int type = flag;
        List<String> getPermissions = new ArrayList<>();
        getPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        getPermissions.add(Manifest.permission.CAMERA);
        permissionsManager = new PermissionsManager(this, new PermissionsManager.PermissionsResultCallback() {
            @Override
            public void onPermissionsGranted(List<String> permissions) {
                if (permissions.contains(Manifest.permission.READ_EXTERNAL_STORAGE)
                        && permissions.contains(Manifest.permission.CAMERA)) {
                    try {
                        switch (type) {
                            case 1:
                                captureImage();
                                break;
                            case 2:
                                filesearch();
                                break;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onPermissionsDenied(List<String> permissions) {
                showToast(EditProfileActivity.this, "App needs to be able to access camera");
            }

            @Override
            public void onPermissionsAvailable(List<String> permissions) {
                if (permissions.contains(Manifest.permission.READ_EXTERNAL_STORAGE)
                        && permissions.contains(Manifest.permission.CAMERA)) {
                    try {
                        switch (type) {
                            case 1:
                                captureImage();
                                break;
                            case 2:
                                filesearch();
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        permissionsManager.requestPermissions(getPermissions.toArray(new String[getPermissions.size()]));

    }
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                    onCaptureImageResult(data);
                    break;
                case READ_REQUEST_CODE:
                    onSelectFromGalleryResult(data);
                    break;
            }
        } else {

        }
    }

    private void onSelectFromGalleryResult(Intent data) {


        Bitmap bm = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            ivProfile.setImageBitmap(bitmap);
//            callProfilePicUpdateAPI(bitmap);
//            callAPi(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onCaptureImageResult(Intent data) {
        if (data.getExtras() != null) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            bitmap = thumbnail;
            ivProfile.setImageBitmap(bitmap);
//            callProfilePicUpdateAPI(bitmap);
//            callAPi(0);
        }
    }

    private void filesearch() {
     /*   Intent intent = new Intent();
        intent.setType("image/*");*/
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

//        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, READ_REQUEST_CODE);
//        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }
    private void calleditprofileApi() {

        JsonObject body = new JsonObject();
        body.addProperty("full_name",etName.getText().toString());
        body.addProperty("email",etEmail.getText().toString());
        Api apiService = ApiClient.getClient().create(Api.class);
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);

        Call<ServerResponse<Object>> call = apiService.editprofil(access_token,"application/json",body);

        call.enqueue(new Callback<ServerResponse<Object>>() {
            @Override
            public void onResponse(Call<ServerResponse<Object>> call, Response<ServerResponse<Object>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (response.body().isStatus()) {
                        Object object = response.body().getData();
                        sessionManager.setProfile(etEmail.getText().toString(),etName.getText().toString());
                        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                        intent.putExtra("tag", "profile");
                        startActivity(intent);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
            public void onFailure(Call<ServerResponse<Object>> call, Throwable t) {
                dismissProgressDialog();
//                showToast(getApplicationContext(),t.toString());

            }
        });
    }


    @OnClick({R.id.iv_back,R.id.tv_add_photo,R.id.btn_save_changes})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_add_photo:
                selectImage(view);
                break;
            case R.id.btn_save_changes:
                calleditprofileApi();
                break;
        }
    }

}
