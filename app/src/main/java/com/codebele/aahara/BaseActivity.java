package com.codebele.aahara;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity {
    public  static String TAG = BaseActivity.class.getName();
    LinearLayout ll_empty;
    private Dialog m_Dialog;

    public void showProgressDialog(Context context) {
        if(m_Dialog==null) {
            m_Dialog = new Dialog(context);
            View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_spinner_layout, null);
            m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            m_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            m_Dialog.setContentView(view);
            m_Dialog.setCancelable(false);
            m_Dialog.show();
        }
    }

    public void dismissProgressDialog() {
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

    public static void showToast(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }

    public void showShortToast(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();

    }

    public void initRecycler() {

    }

}
