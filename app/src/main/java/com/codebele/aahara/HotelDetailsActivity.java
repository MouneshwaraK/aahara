package com.codebele.aahara;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.SessionManagers.LoginSessionManager;
import com.codebele.aahara.Models.Models.ItemsPojo;
import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.NetworkUtils.ServerResponse;
import com.codebele.aahara.ResponsePojo.InsertCartPojo;
import com.codebele.aahara.adapter.HotelAdapter;
import com.codebele.aahara.adapter.MenuAdapter;
import com.codebele.aahara.adapter.SecondHotelAdapter;
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


public class HotelDetailsActivity extends BaseActivity implements View.OnClickListener {
    /**
     * ButterKnife Code
     **/
    @BindView(R.id.ll_cart_hide)
    RelativeLayout llCartHide;
    @BindView(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_rollplanet)
    TextView tvRollplanet;
    @BindView(R.id.bestseller)
    RelativeLayout bestseller;
    @BindView(R.id.tv_cost)
    TextView tvCost;
    @BindView(R.id.unit_recycler)
    androidx.recyclerview.widget.RecyclerView unitRecycler;
    @BindView(R.id.menu_recycler)
    androidx.recyclerview.widget.RecyclerView menuRecycler;
    @BindView(R.id.rl_menu)
    RelativeLayout rlMenu;
    @BindView(R.id.img_menu)
    ImageView imgMenu;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
//    @BindView(R.id.ll_menu_layout)
    RelativeLayout llMenu;
    HotelDetailsActivity hd;
    /**
     * ButterKnife Code
     **/


    @BindView(R.id.custom_toast_layout)
    RelativeLayout customToastLayout;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
    @BindView(R.id.tv_food_item)
    TextView tvFoodItem;
    @BindView(R.id.rupee)
    TextView rupee;
    @BindView(R.id.tv_amount_food)
    TextView tvAmountFood;
    @BindView(R.id.tv_plustaxes)
    TextView tvPlustaxes;
    @BindView(R.id.tv_view_cart1)
    LinearLayout tvViewCart1;
    @BindView(R.id.tv_view_cart)
    TextView tvViewCart;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.dish_search_text)
    EditText dishSearch;

    /**
     * ButterKnife Code
     **/
    Context mContext;

    ArrayList<ItemsPojo> itemsPojos = new ArrayList<>();
    ArrayList<ItemsPojo.Item> list = new ArrayList<>();
    //    ArrayList<HotelModel.MenuItem> menuItems = new ArrayList<>();
    HotelAdapter adapter;
    com.codebele.SessionManagers.LoginSessionManager sessionManager;
    private String access_token;
    InsertCartPojo insertCartPojo;
    HashMap<String, String> user = new HashMap<String, String>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager1;
    private HotelAdapter hotelAdapter;
    private SecondHotelAdapter secondHotelAdapter;

    MenuAdapter menuAdapter;
    //    private ArrayList<HotelModel.MenuList> hotelModel = new ArrayList<>();
    boolean isimgSearch = false;
    boolean isimgclose = false;
    boolean isplay = true;
    boolean isMenuVsible = false;
    String sk_restaurant_id;
    //  String sk_user_id;
    String sk_items_id;
    String restaurant_name = "";
    int price = 0;
    int itemcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new LoginSessionManager(getApplicationContext());
        setContentView(R.layout.activity_hoteldetails_new);
       llMenu = findViewById(R.id.ll_menu_layout);
        if (sessionManager.isUserLoggedIn()) {
            user = sessionManager.getUserDetails();
            access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        }
        ButterKnife.bind(this);
        btnBack.setOnClickListener(this);



        dishSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                    doFilter(s);
            }


        });
        /* Getting data from food adapter file*/
        if (getIntent().getExtras() != null) {
            Intent intent = getIntent();
            sk_restaurant_id = intent.getExtras().getString("sk_restaurant_id");
            sk_items_id = intent.getExtras().getString("sk_items_id");
            restaurant_name = intent.getExtras().getString("restaurant_name");
        }


        tvRollplanet.setText(restaurant_name);
        callItemsApi();
    }


    private void callItemsApi() {
        Api apiService = ApiClient.getClient().create(Api.class);
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("restuarants_id", sk_restaurant_id);
        Log.e("restuarants_id", sk_restaurant_id);
        showProgressDialog(this);
        Call<ServerResponse<ArrayList<ItemsPojo>>> call = apiService.items(access_token, jsonObject);
        call.enqueue(new Callback<ServerResponse<ArrayList<ItemsPojo>>>() {
            @Override
            public void onResponse(Call<ServerResponse<ArrayList<ItemsPojo>>> call, Response<ServerResponse<ArrayList<ItemsPojo>>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isStatus()) {
                        itemsPojos = response.body().getData();
                        itemcount = 0;
                        price = 0;
                        for (int i = 0; i < itemsPojos.size(); i++) {
                            for (int k = 0; k < itemsPojos.get(i).getItems().size(); k++) {
                                itemcount = itemcount + itemsPojos.get(i).getItems().get(k).getItemCount();
                                if (itemsPojos.get(i).getItems().get(k).getItemCount() != 0)
                                    if (itemsPojos.get(i).getItems().get(k).getOfferPrice() != null && !itemsPojos.get(i).getItems().get(k).getOfferPrice().isEmpty()) {
                                        price = price + (Integer.parseInt(itemsPojos.get(i).getItems().get(k).getOfferPrice()) * itemsPojos.get(i).getItems().get(k).getItemCount());
                                    }
                            }
                        }
                        if (itemcount == 0) {
                            customToastLayout.setVisibility(View.GONE);
                        } else {
                            tvQuantity.setText(String.valueOf(itemcount));
                            tvAmountFood.setText(String.valueOf(price));
                            customToastLayout.setVisibility(View.VISIBLE);
                        }
                        initializeRecyclerView();

                    } else {
//                        showToast(getApplicationContext(), response.body().getStatusMessage());
                    }
                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
//                        showToast(getApplicationContext(), jObjError.getString("message"));


                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<ArrayList<ItemsPojo>>> call, Throwable t) {
                dismissProgressDialog();
//                showToast(getApplicationContext(), t.toString());


            }
        });
    }




    private void doFilter(CharSequence s) {
        if(hotelAdapter!=null)
            if(hotelAdapter.getItemCount()>0){
                hotelAdapter.searchString(s);


            }

//        ArrayList<ItemsPojo.Item> filterdNames = new ArrayList<>();
//
//        //looping through existing elements
//        for (ItemsPojo.Item s1 : list) {
//            //if the existing elements contains the search input
//            if (s1.getItemName() != null)
//                if (s1.getItemName().toLowerCase().contains(s.toLowerCase())) {
//                    //adding the element to filtered list
//                    filterdNames.add(s1);
//                }
//        }
//        try {
//            secondHotelAdapter.filterList(filterdNames);
//        } catch (Exception e) {
//
//        }

    }

    /*private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<ItemsPojo> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (ItemsPojo s : itemsPojos) {
            //if the existing elements contains the search input
            if (s.getItemsCategoryname() != null)
                if (s.getItemsCategoryname().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
        }
        try {
            hotelAdapter.filterList(filterdNames);
        } catch (Exception e) {

        }

    }
*/

//    public void Showtoast() {
//        //Creating the LayoutInflater instance
//        LayoutInflater li = getLayoutInflater();
//        //Getting the View object as defined in the customtoast.xml file
//        View layout = li.inflate(R.layout.customtoast,(ViewGroup) findViewById(R.id.custom_toast_layout));
//
//        //Creating the Toast object
//        Toast toast = new Toast(getApplicationContext());
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.BOTTOM, 0, 0);
//        toast.setView(layout);//setting the view of custom toast layout
//        toast.show();
//    }


    private int getAdapterPosition() {
        return 0;
    }

    public static int getFirstVisiblePosition(RecyclerView rv) {
        if (rv != null) {
            final RecyclerView.LayoutManager layoutManager = rv
                    .getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) layoutManager)
                        .findFirstVisibleItemPosition();
            }
        }
        return 0;
    }

    private void initializeRecyclerView() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        unitRecycler.setLayoutManager(layoutManager);
        layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        menuRecycler.setLayoutManager(layoutManager1);

        hotelAdapter = new HotelAdapter(this, itemsPojos, 0, this);
        unitRecycler.setAdapter(hotelAdapter);

//        unitRecycler.getRecycledViewPool().setMaxRecycledViews(1,0);
        unitRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i = 0; i <= 2; i++) {
                    try {
                        tvCost.setText(itemsPojos.get(getFirstVisiblePosition(unitRecycler)).getItemsCategoryname());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                hotelAdapter = new HotelAdapter(HotelModel.MenuItem,getContext(),this);
//                unitRecycler.setAdapter(hotelAdapter);
            }

        });
        menuAdapter = new MenuAdapter(this, itemsPojos, this);
//        unitRecycler.setAdapter(hotelAdapter);

        menuRecycler.setAdapter(menuAdapter);
    }

    /*
     * R.id.img_search, R.id.img_close,
     * */
    @Override
    @OnClick({R.id.btn_back, R.id.rl_menu, R.id.custom_toast_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
           /* case R.id.img_search:
                if (isimgSearch = true) {
                    llSearch.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.img_close:
                if (isimgclose = true) {
                    llSearch.setVisibility(View.GONE);
                    edtSearchText.getText().clear();
                }
                break;*/

            case R.id.rl_menu:
                if (!isMenuVsible) {
                    isMenuVsible = true;
                    menuRecycler.setVisibility(View.VISIBLE);
                } else {
                    isMenuVsible = false;
                    menuRecycler.setVisibility(View.GONE);
                }

                break;

            case R.id.custom_toast_layout:
                Intent intent = new Intent(HotelDetailsActivity.this, MainActivity.class);
                intent.putExtra("tag", "cart");
                startActivity(intent);
                finish();
                break;
        }
    }

    public void popup(int value, ItemsPojo.Item itemspojos, boolean isAdd, boolean isPlus) {
        try {
            if (isPlus) {
                itemcount = itemcount + 1;
                if (itemspojos.getOfferPrice() != null && !itemspojos.getOfferPrice().isEmpty()) {
                    price = price + (Integer.parseInt(itemspojos.getOfferPrice()));
                }else if(itemspojos.getActualPrice() != null && !itemspojos.getActualPrice().isEmpty()){
                    price = price + (Integer.parseInt(itemspojos.getActualPrice()));
                }
            } else {
                itemcount = itemcount - 1;
                if (itemspojos.getOfferPrice() != null && !itemspojos.getOfferPrice().isEmpty()) {
                    price = price - (Integer.parseInt(itemspojos.getOfferPrice()));
                }else if(itemspojos.getActualPrice() != null && !itemspojos.getActualPrice().isEmpty()){
                    price = price - (Integer.parseInt(itemspojos.getActualPrice()));
                }
            }
            tvQuantity.setText(String.valueOf(itemcount));
            tvAmountFood.setText(String.valueOf(price));
            if (itemcount == 0) {
                customToastLayout.setVisibility(View.GONE);
            } else {
                customToastLayout.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }
//            tvAmountFood.setText(itemspojos.getNew_price());


    }


    public void callInserApi(ItemsPojo.Item itemspojos, int universalCount) {
        Api api = ApiClient.getClient().create(Api.class);
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Crestuarants_id", sk_restaurant_id);
        jsonObject.addProperty("Citem_id", itemspojos.getSkItemsId());
        jsonObject.addProperty("cart_count", String.valueOf(universalCount));
//        jsonObject.addProperty("Cuser_id", user.get(KEY_USER_ID));

        showProgressDialog(this);


        Call<ServerResponse<ArrayList<InsertCartPojo>>> call = api.insertcart("application/json", access_token, jsonObject);
        call.enqueue(new Callback<ServerResponse<ArrayList<InsertCartPojo>>>() {
            @Override
            public void onResponse(Call<ServerResponse<ArrayList<InsertCartPojo>>> call, Response<ServerResponse<ArrayList<InsertCartPojo>>> response) {
                dismissProgressDialog();
                if (response.isSuccessful()) {
//                    ArrayList<InsertCartPojo> insertCartPojos = new ArrayList<>();
//                    insertCartPojos = response.body().getData();
                    if (response.body().isStatus()) {
//                        showToast(getApplicationContext(), response.body().getStatusMessage());
                    } else {
//                        showToast(getApplicationContext(), response.body().getStatusMessage());
                    }

                } else {
                    try {
                        String error_message = response.errorBody().string();
//                        JSONObject jObjError = new JSONObject(error_message);
//                        showToast(HotelDetailsActivity.this, error_message);

                    } catch (Exception e) {
                        e.printStackTrace();
//                        showToast(HotelDetailsActivity.this, getResources().getString(R.string.inserted_successfully));

                    }
                }
            }


            @Override
            public void onFailure(Call<ServerResponse<ArrayList<InsertCartPojo>>> call, Throwable t) {
                dismissProgressDialog();
//                showToast(getApplicationContext(), t.toString());

            }
        });


    }


    public void scrollToMethod(int position) {
//        if (position==unitRecycler.getBottom()-1)
//        {
//            llMenu.setVisibility(View.GONE);
//        }
        unitRecycler.getLayoutManager().scrollToPosition(position);

    }




}