package com.codebele.SessionManagers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.codebele.aahara.LoginActivity;
import com.codebele.aahara.ResponsePojo.SignupPojo;

import java.util.HashMap;

public class LoginSessionManager {
    SharedPreferences sharedPreferences;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor, editor1;

    //context
    Context context;

    //shared pref mode
    int PRIVATE_MODE = 0;

    //Shared Pref File name
    private static final String PREFER_NAME = "Aahara";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    // User name (make variable public to access from outside)
    public static final String KEY_Fullname = "Fullname";
    public static final String KEY_Mobile = "mobile";
    public static final String KEY_email = "email";
    public static final String KEY_Accesstoken = "getAccesstoken";
    public static final String KEY_user_type = "user_type";
    public static final String KEY_signupdate = "signupdate";
    public static final String KEY_address = "address";
    public static final String KEY_USER_id = "address";
//    public static final String KEY_sk_Business_id = "sk_business_id";
//    public static final String KEY_Firm_name = "firm_name";
    public static final String KEY_City = "city";
    public static final String KEY_City_NAME = "city_name";
    public static final String KEY_IS_CITY = "is_city";


//    public static final String KEY_State = "state";
//    public static final String KEY_Country = "country";
//    public static final String KEY_LandLine = "landline";
//    public static final String KEY_Business_status = "business_status";
//    public static final String KEY_sk_service_request_id = "sk_service_request_id";
//    public static final String KEY_service_type = "service_type";
//    public static final String KEY_service_request = "service_request";
//    public static final String KEY_vehicle = "vehicle";
//    public static final String KEY_pickup_point = "pickup_point";
//    public static final String KEY_drop_point = "drop_point";
//    public static final String KEY_pickup_date = "pickup_date";
//    public static final String KEY_pickup_time = "pickup_time";
//    public static final String KEY_booking_type = "booking_type";
//    public static final String KEY_seat = "seat";
//    public static final String KEY_rate = "rate";
//    public static final String KEY_user_name = "user_name";


    //constructor with context parameter
    public LoginSessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

//    public void createguest(boolean isguest) {
////        editor.putBoolean(KEY_GUEST, isguest);
//        editor.commit();
//    }

//    public boolean checkguest(){
//        return sharedPreferences.getBoolean(KEY_GUEST, false);
//    }


    //Create login session
    public void createUserLoginSession(String accessToken, String fullname, String mobile,
                                       String email,String cityId,String cityName){
//                                       String address,String city, String state,String rate, String user_name,
//                                       String seat, String booking_type,String drop_point, String pickup_time,String pickup_date,
//                                       String pickup_point,String vehicle, String service_request,String service_type,
//                                       String sk_service_request_id,String business_status,String landline, String email,
//                                       String sk_business_id,String country,String firm_name){
        // Storing login value as seat
        editor.putBoolean(IS_USER_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_Fullname,fullname);
        // Storing email in pref
        editor.putString(KEY_Accesstoken, accessToken);
        editor.putString(KEY_email, email);

        editor.putString(KEY_Mobile, mobile);
        editor.putString(KEY_City, cityId);
        editor.putString(KEY_City_NAME, cityName);

        // commit changes
        editor.commit();
    }

    public boolean checkLogin() {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);

            return true;
        }
        return false;
    }


    public HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_Fullname, sharedPreferences.getString(KEY_Fullname, null));

//        user.put(KEY_user_type, sharedPreferences.getString(KEY_user_type, null));
        //accessToken
        user.put(KEY_Accesstoken, sharedPreferences.getString(KEY_Accesstoken, null));
        //session_name
        user.put(KEY_email, sharedPreferences.getString(KEY_email, null));
        user.put(KEY_Mobile, sharedPreferences.getString(KEY_Mobile, null));
//        user.put(KEY_Password, sharedPreferences.getString(KEY_Password, null));
        user.put(KEY_signupdate, sharedPreferences.getString(KEY_signupdate, null));
        user.put(KEY_City, sharedPreferences.getString(KEY_City, null));
        user.put(KEY_City_NAME, sharedPreferences.getString(KEY_City_NAME, null));
        user.put(KEY_address, sharedPreferences.getString(KEY_address, null));



        return user;
    }
    public void setProfile(String email, String full_name) {
        editor.putString(KEY_Fullname, full_name);
        editor.putString(KEY_email, email);

        editor.commit();

    }
    public void logoutUser() {
        editor.putBoolean(IS_USER_LOGIN, false);
        editor.clear();
        editor.commit();

    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }

//    public void updateProfilePic(String image) {
////        editor.putString(KEY_ProfileImage, image);
////        editor.commit();
//    }


    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_USER_LOGIN,false);
    }

    public void createSession(SignupPojo signupPojo) {
    }

    public void setCity(String city, String city_name, boolean b) {
        editor.putString(KEY_City, city);
        editor.putString(KEY_City_NAME, city_name);
        editor.putBoolean(KEY_IS_CITY, b);
        editor.commit();

    }


    public void setisCity( boolean b) {
        editor.putBoolean(KEY_IS_CITY, b);
        editor.commit();

    }

    public boolean getIsCity() {
        boolean isCity = sharedPreferences.getBoolean(KEY_IS_CITY, false);
        return isCity;

    }
/*    public String g() {
        *//* editor.putString(KEY_academic_year, "");*//*
        String mobile = sharedPreferences.getString(KEY_Mobile, "");
        return mobile;

    }*/
//
//    public void setKEY_user_type(String Customer, String Coordinator,String Protocol,String) {
//        editor.putString(KEY_Firm_name, first_name);
//        editor.putString(KEY_email, email);
//        editor.commit();
//
//    }
//
//
//    public void createaddress(String First_name, String State, String Mobile, String City, String Address) {
//
//        editor.putString(KEY_Firm_name, First_name);
//        editor.putString(KEY_address, Address);
//        editor.putString(KEY_City, City);
//        editor.putString(KEY_Mobile, Mobile);
//        editor.putString(KEY_State, State);
//        editor.commit();
//    }
//
//
//    public void updateprofile(String Fname, String Password, String Amobile, String User_name, String Address) {
//        editor.putString(KEY_First_NAME, Fname);
//        editor.putString(KEY_address, Address);
//        editor.putString(KEY_Mobile, Amobile);
//        editor.putString(KEY_user_name, User_name);
//        editor.putString(KEY_Password, Password);
//        editor.commit();
//    }


//    public String getcartCount() {
//
//        String cart = sharedPreferences.getString(KEY_Cart_count, "");
//        return cart;
//
//    }
//
//    public void setcartCount(String cart_count) {
//        editor.putString(KEY_Cart_count, cart_count);
//        editor.commit();
//
//    }
//
//    public String getKEY_sk_Business_id() {
//        String id = sharedPreferences.getString(KEY_sk_Business_id, "");
//        return id;
//    }
//
//        public void setsk_service_request_id (String id){
//            editor.putString(KEY_sk_service_request_id, id);
//            editor.commit();
//        }
}

