package com.codebele.aahara.SessionManagers;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.codebele.aahara.R;

public class Utils {
    //Some KEYS
    public static final int STRING_REQUESTCODE = 1111;
    public static final int STRING_REQUESTCODE1 = 2222;
    public static final int STRING_REQUESTCODE2 = 3333;
    public static final int STRING_REQUESTCODE3 = 4444;
    public static final int STRING_LOCATIONCODE=100;
    public static final int STRING_OTPREQUEST = 200;
    public static Dialog m_Dialog;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    public static final String KEY_DATA ="data" ;
    private static final int REQUEST_PHONE_CALL=100;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                   /* AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                                            }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();*/
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Camera permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }
                return false;
            }

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Location permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPhonePermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CALL_PHONE)) {
                   /* AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                                            }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();*/
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PHONE_CALL);

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                }
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return true;
        }
    }

    public static String blog_desc="It has been eight years since I started my career in real estate. From a property advisor to now principal sales with one of the leading real estate developer in India, it has been an incredible journey so far...";
    public static String getRatingText(float val){
        if(val==1.0){
            return "Very Bad";
        }else if(val==2.0){
            return "Bad";
        }else if(val==3.0){
            return "Average";
        }else if(val==4.0){
            return "Good";
        }else {
            return "Excellent";
        }
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void showProgressDialog(Context context) {
        try{if(m_Dialog == null) {
            m_Dialog = new Dialog(context);
            View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_spinner_layout, null);
            m_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            m_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            m_Dialog.setContentView(view);
            m_Dialog.setCancelable(false);
        }
        m_Dialog.show();}
        catch (Exception e)
        {
            //Toast.makeText(context,"try a",Toast.LENGTH_SHORT).show();
        }
    }
    public static void dismissProgressDialog() {
        if (m_Dialog != null && m_Dialog.isShowing()) {
            m_Dialog.dismiss();
        }
    }

//    public static void showAlert(Context context, String message) {
//        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();
//        alertDialog.setTitle(context.getResources().getString(R.string.no_internet));
//        alertDialog.setIcon(context.getResources().getDrawable(R.drawable.no_internet));
//        alertDialog.setMessage(message);
//        alertDialog.setButton("OK",
//                new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog,
//                                        int which) {
//                        dialog.dismiss();
//
//                    }
//                });
//
//        // Showing Alert Message
//        alertDialog.show();
//    }

//    public static void showNoInternrt(Context context) {
//        ViewGroup viewGroup = ((Activity) context).findViewById(android.R.id.content);
//        final Context mcContext = context;
//        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_no_internet, viewGroup, false);
//        Button btn_done = (Button) dialogView.findViewById(R.id.btn_done);
//        /*  android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, R.style.MyAlertDialogStyle);*/
//
//        //finally creating the alert dialog and displaying it
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setView(dialogView);
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.setCanceledOnTouchOutside(false);
//        btn_done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//                ((Activity) mcContext).finish();
//                mcContext.startActivity(((Activity) mcContext).getIntent());
//            }
//        });
//        alertDialog.show();
//    }
}
