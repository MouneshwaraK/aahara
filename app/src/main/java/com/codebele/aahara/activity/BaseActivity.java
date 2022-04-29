package com.codebele.aahara.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.codebele.aahara.R;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {
    public  static String TAG = BaseActivity.class.getName();
    private Dialog m_Dialog;
    LinearLayout ll_empty;
    public void showProgressDialog(Context context) {
        if(m_Dialog == null) {
            m_Dialog = new Dialog(context);
            View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_spinner_layout, null);
            m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            m_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            m_Dialog.setContentView(view);
            m_Dialog.setCancelable(false);
            m_Dialog.show();
        }


    }
//    public void showProgressDialog(Context context) {
//
//        m_Dialog = new Dialog(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_spinner_layout, null);
//       loadingView = view.findViewById(R.id.loader);
//        loadingView.setVisibility(View.VISIBLE);
//       /* m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        m_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        m_Dialog.setContentView(view);
//        m_Dialog.setCancelable(false);
//        m_Dialog.show();*/
//    }

    public void dismissProgressDialog() {
//        if(loadingView.isShown()){
//            loadingView.setVisibility(View.GONE);
//        }
        if (m_Dialog != null && m_Dialog.isShowing()) {
            m_Dialog.dismiss();
        }
    }

    public void showAlertDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }

    public void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public void showShortToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

    }
//   public void showCustomToast(String message,boolean v){
//        View toastView = getLayoutInflater().inflate(R.layout.layout_custom_toast, null);
//       /* TextView tv = toastView.findViewById(R.id.vt_error_message);
//        tv.setText(message);*/
//       LinearLayout ll = toastView.findViewById(R.id.ll_toast);
//       if(v)
//           ll.setBackground(getResources().getDrawable(R.drawable.toast_wright_bg));
//       else
//           ll.setBackground(getResources().getDrawable(R.drawable.toast_wrong_bg));
//       TextView tv = (TextView) toastView.findViewById(R.id.vt_error_message);
//       tv.setText(message);
//        Toast toast = new Toast(getApplicationContext());
//        // Set custom view in toast.
//        toast.setView(toastView);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.BOTTOM, 0,10);
//        toast.show();
//    }

    public void showAlertDialogWithTitle(Context context, String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }




//    public void showNoInternetDialog(Context context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.Theme_AppCompat_Light_Dialog_Alert );
//        builder.setTitle(getString(R.string.txt_title_no_internet));
//        builder.setMessage(getString(R.string.txt_no_internet));
//        builder.setPositiveButton(android.R.string.ok, null);
//        builder.show();
//    }
//    public void showNoContentDialog(Context context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle(getString(R.string.no_content));
//        builder.setMessage(getString(R.string.no_content));
//        builder.setPositiveButton(android.R.string.ok, null);
//        builder.show();
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        registerReceiver(unauthorizedReceiver, new IntentFilter("Unauthorized"));
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(unauthorizedReceiver);
//    }

    public int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    }
