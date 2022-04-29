package com.codebele.aahara.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codebele.aahara.MainActivity;
import com.codebele.aahara.Models.Models.OrderHistoryPojo;
import com.codebele.aahara.NetworkUtils.Api;
import com.codebele.aahara.NetworkUtils.ApiClient;
import com.codebele.aahara.NetworkUtils.ServerResponse;
import com.codebele.aahara.R;
import com.codebele.aahara.adapter.HistoryAdapter;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragments extends Fragment {
    /** ButterKnife Code **/
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.rv_history)
    androidx.recyclerview.widget.RecyclerView rvHistory;
    /** ButterKnife Code **/
    private View view;
    private Context context;
    private HistoryAdapter historyAdaptar;
    //    private ArrayList<HistoryModel> historyModelArrayList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    com.codebele.SessionManagers.LoginSessionManager sessionManager;
    private String access_token;
    private ArrayList<OrderHistoryPojo.Order> orderModelArrayList = new ArrayList<>();
    private MainActivity mParent;
    HashMap<String, String> user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            context = context;
            mParent = (MainActivity) getActivity();
            /*dashboardInterface = (DashboardInterface) context;
            AndroidSupportInjection.inject(this);*/
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = getContext();
        this.view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);

        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getContext());
        user = sessionManager.getUserDetails();
        callOrderHistory();

        return view;

    }

    private void initializeRecycler(ArrayList<OrderHistoryPojo.Order> orderModelArrayList) {
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        historyAdaptar = new HistoryAdapter(context, this.orderModelArrayList, this);
        rvHistory.setLayoutManager(layoutManager);
        rvHistory.setAdapter(historyAdaptar);


    }

    private void callOrderHistory() {


        Api apiService = ApiClient.getClient().create(Api.class);
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);

        Call<ServerResponse<OrderHistoryPojo>> call = apiService.orderhistory(access_token, "application/json");
        call.enqueue(new Callback<ServerResponse<OrderHistoryPojo>>() {
            @Override
            public void onResponse(Call<ServerResponse<OrderHistoryPojo>> call, Response<ServerResponse<OrderHistoryPojo>> response) {

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        assert response.body() != null;
                        orderModelArrayList = (ArrayList<OrderHistoryPojo.Order>) response.body().getData().getOrder();

                        if (orderModelArrayList.size() > 0) {
                            initializeRecycler(orderModelArrayList);
                        } else {
                            rvHistory.setVisibility(View.GONE);
                            ivEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
//                        mParent.showToast(getContext(), jObjError.getString("message"));


                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<OrderHistoryPojo>> call, Throwable t) {
                ivEmpty.setVisibility(View.VISIBLE);

            }
        });
    }

    public void callRepeat(int pos) {


        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("order_id", orderModelArrayList.get(pos).getSkOrderId());
        Api apiService = ApiClient.getClient().create(Api.class);
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        Log.e("access_token", access_token);

        Call<ServerResponse<Object>> call = apiService.repeatOrder(access_token, "application/json", jsonObject);
        call.enqueue(new Callback<ServerResponse<Object>>() {
            @Override
            public void onResponse(Call<ServerResponse<Object>> call, Response<ServerResponse<Object>> response) {

                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        assert response.body() != null;
                        mParent.cartFragment();
                    } else {

                        showAlert(response.body().getStatusMessage());
                    }
                } else {
                    //  initializeRecycler();
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
//                        mParent.showToast(getContext(), jObjError.getString("message"));
                        showAlert(jObjError.getString("message"));

                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<Object>> call, Throwable t) {
                ivEmpty.setVisibility(View.VISIBLE);
                showAlert(t.toString());
            }
        });
    }

    public void callCancel(int pos) {


        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("order_id", orderModelArrayList.get(pos).getSkOrderId());
        jsonObject.addProperty("order_status", "cancelled");
        Api apiService = ApiClient.getClient().create(Api.class);
        access_token = user.get(com.codebele.SessionManagers.LoginSessionManager.KEY_Accesstoken);
        Log.e("access_token", access_token);

        Call<ServerResponse<Object>> call = apiService.cancelOrder(access_token, "application/json", jsonObject);
        call.enqueue(new Callback<ServerResponse<Object>>() {
            @Override
            public void onResponse(Call<ServerResponse<Object>> call, Response<ServerResponse<Object>> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    showAlert(response.body().getStatusMessage());
                } else {
                    //  initializeRecycler();
                    try {
                        String error_message = response.errorBody().string();
                        JSONObject jObjError = new JSONObject(error_message);
//                        mParent.showToast(getContext(), jObjError.getString("message"));

                        showAlert(jObjError.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<Object>> call, Throwable t) {
                ivEmpty.setVisibility(View.VISIBLE);
                showAlert(t.toString());
            }
        });
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_cancel_card, viewGroup, false);
        TextView chose_pic = (TextView) dialogView.findViewById(R.id.chose_pic);

        chose_pic.setText(message);
        TextView cancel = (TextView) dialogView.findViewById(R.id.tv_cancel);
//        ButterKnife.bind(this,dialogView);
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                callOrderHistory();
            }
        });
    }

}
