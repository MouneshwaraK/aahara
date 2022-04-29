package com.codebele.aahara.NetworkUtils;


import com.codebele.aahara.Models.Models.AddAddressPojo;
import com.codebele.aahara.Models.Models.CityNamePojo;
import com.codebele.aahara.Models.Models.CouponCardPojo;
import com.codebele.aahara.Models.Models.CouponCardsListPojo;
import com.codebele.aahara.Models.Models.HomeModel;
import com.codebele.aahara.Models.Models.ItemsPojo;
import com.codebele.aahara.Models.Models.OrderHistoryPojo;
import com.codebele.aahara.Models.Models.ViewAddressModel;
import com.codebele.aahara.Models.Models.ViewCartPojo;
import com.codebele.aahara.ResponsePojo.CartUpdatePojo;
import com.codebele.aahara.ResponsePojo.DetAddressPojo;
import com.codebele.aahara.ResponsePojo.EditProfilePojo;
import com.codebele.aahara.ResponsePojo.InsertCartPojo;
import com.codebele.aahara.ResponsePojo.LoginPojo;
import com.codebele.aahara.ResponsePojo.OrderSummaryPojo;
import com.codebele.aahara.ResponsePojo.OtpVerifyPojo;
import com.codebele.aahara.ResponsePojo.PlaceOrderPojo;
import com.codebele.aahara.ResponsePojo.ResendOtpPojo;
import com.codebele.aahara.ResponsePojo.SignupPojo;
import com.codebele.aahara.ResponsePojo.UpDateAddressPojo;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Codebele on 07-May-19.
 */
public interface Api {

    @POST("userSignin")
    Call<LoginPojo>login(@Body JsonObject userSignin);

    @POST("Signup")
    Call<ServerResponse<SignupPojo>>postSignup(@Header("Content-Type") String content_type, @Body JsonObject Signup);

    @POST("otpVerify")
    Call<ServerResponse<ArrayList<OtpVerifyPojo>>>otpverify(@Header("Accesstoken") String acces_token, @Body JsonObject otpVerify);

    @POST("resendOtp")
    Call<ServerResponse<ResendOtpPojo>>resendotp(@Header("Accesstoken") String acces_token, @Body JsonObject resendOtp);

    @POST("restaurant_view")
    Call<ServerResponse<ArrayList<HomeModel>>>restaurent_view(@Header("Accesstoken") String acces_token, @Body JsonObject restaurant_view);

    @POST("restaurant_Items")
    Call<ServerResponse<ArrayList<ItemsPojo>>>items(@Header("Accesstoken") String acces_token, @Body JsonObject restaurant_Items);

    @POST("cart_update")
    Call<ViewCartPojo>updatecart(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type, @Body JsonObject cart_update);

    @POST("insertIntocart")
    Call<ServerResponse<ArrayList<InsertCartPojo>>>insertcart(@Header("Content-Type") String content_type, @Header("Accesstoken") String acces_token, @Body JsonObject insert_into_cart);

    @POST("cart_view")
    Call<ViewCartPojo>viewcart(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type);

    @POST("placeorder")
    Call<PlaceOrderPojo>placeorder(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type,@Body JsonObject body);

    @POST("add_address")
    Call<ServerResponse<AddAddressPojo>>address(@Header("Accesstoken") String acces_token, @Body JsonObject add_address);

    @POST("address_view")
    Call<ServerResponse<ArrayList<ViewAddressModel>>>viewaddress(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type);

    @POST("delete_address")
    Call<DetAddressPojo>deleteaddress(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type,@Body JsonObject delete_address);

    @POST("update_address")
    Call<ServerResponse<UpDateAddressPojo>>updateaddress(@Header("Accesstoken") String acces_token, @Body JsonObject add_address);

    @POST("oderHistory")
    Call<ServerResponse<OrderHistoryPojo>>orderhistory(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type);
//getcityBYstatename
    @POST("getcityBYstatename")
    Call<ServerResponse<ArrayList<CityNamePojo>>>getcityBYstatename(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type, @Body JsonObject add_address);


    @POST("userlastlogin")
    Call<ServerResponse<Object>>userlastlogin(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type, @Body JsonObject add_address);


    @POST("cancelOrder")
    Call<ServerResponse<Object>>cancelOrder(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type, @Body JsonObject add_address);


    @POST("repeatOrder")
    Call<ServerResponse<Object>>repeatOrder(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type, @Body JsonObject add_address);

    @POST("edit_profile")
    Call<ServerResponse<Object>>editprofil(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type, @Body JsonObject edit_profile);

    @POST("oderSummary")
    Call<ServerResponse<OrderSummaryPojo>> orderSummary(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type, @Body JsonObject body);


    @POST("verifyCoupon")

    Call<ServerResponse<ArrayList<CouponCardPojo>>> verifyCoupon(@Header("Accesstoken")String access_token, @Header("Content-Type") String content_type, @Body JsonObject body);

    @GET("couponList")
    Call<ServerResponse<ArrayList<CouponCardsListPojo>>> viewcoupons(@Header("Accesstoken") String acces_token, @Header("Content-Type") String content_type);

}
