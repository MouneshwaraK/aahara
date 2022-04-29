package com.codebele.aahara;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codebele.SessionManagers.LoginSessionManager;
import com.codebele.aahara.activity.SelectCityActivity;
import com.codebele.aahara.fragments.CartFragment;
import com.codebele.aahara.fragments.HistoryFragments;
import com.codebele.aahara.fragments.HomeFragment;
import com.codebele.aahara.fragments.ProfileFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    /** ButterKnife Code **/
    @BindView(R.id.appbar_layout_activity)
    androidx.coordinatorlayout.widget.CoordinatorLayout appbarLayoutActivity;
    @BindView(R.id.appbar)
    com.google.android.material.appbar.AppBarLayout appbar;
    @BindView(R.id.toolbar_layout)
    com.google.android.material.appbar.CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.bottomNavigation)
    LinearLayout bottomNavigation;
    @BindView(R.id.line_home)
    View lineHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.line_history)
    View lineHistory;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.line_cart)
    View lineCart;
    @BindView(R.id.tv_cart)
    TextView tvCart;
    @BindView(R.id.line_profile)
    View lineProfile;
    @BindView(R.id.tv_profile)
    TextView tvProfile;
    /** ButterKnife Code **/
    Fragment currentBackStackFragment = getSupportFragmentManager().findFragmentByTag("FRAGMENT");
    String tvquantity;
    com.codebele.SessionManagers.LoginSessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       // final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        if (getIntent().getExtras() != null) {

            try {
                if (getIntent().getExtras().getString("tag").equals("cart")) {
                    cartFragment();

                }else if (getIntent().getExtras().getString("tag").equals("history")) {
                    HistoryFragment();

                }else if (getIntent().getExtras().getString("tag").equals("profile")) {
                    ProfileFragment();
                } else {
                    initFragment();
                }
            } catch (Exception e) {
                e.printStackTrace();
                initFragment();
            }
        }else{
            initFragment();
        }
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                if (getIntent().getExtras() != null) {
//
//                    try {
//                        if (getIntent().getExtras().getString("tag").equals("cart")) {
//                            cartFragment();
//
//                        }else if (getIntent().getExtras().getString("tag").equals("history")) {
//                            HistoryFragment();
//
//                        }else if (getIntent().getExtras().getString("tag").equals("profile")) {
//                            ProfileFragment();
//                        } else {
//                            initFragment();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        initFragment();
//                    }
//                }else{
//                    initFragment();
//                }
//
//                pullToRefresh.setRefreshing(false);
//            }
//        });


        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        tvLocation.setText(user.get(LoginSessionManager.KEY_City_NAME));
        if(!sessionManager.getIsCity()){
            Intent intent = new Intent(com.codebele.aahara.MainActivity.this, SelectCityActivity.class);

            startActivity(intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
        }
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment currentBackStackFragment = getSupportFragmentManager().findFragmentByTag("FRAGMENT");
                if (currentBackStackFragment instanceof HomeFragment) {

                    resetBottomClickUI();
                    tvHome.setSelected(true);
                    lineHome.setVisibility(View.VISIBLE);
                    //    tvHome.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_active, 0, R.drawable.ic_arc);

                } else if (currentBackStackFragment instanceof HistoryFragments) {
                    resetBottomClickUI();
                    tvHistory.setSelected(true);
                    lineHistory.setVisibility(View.VISIBLE);
                    //  tvHistory.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_about_active, 0, R.drawable.ic_arc);

                } else if (currentBackStackFragment instanceof CartFragment) {
                    resetBottomClickUI();
                    tvCart.setSelected(true);
                    lineCart.setVisibility(View.VISIBLE);
                    // tvCart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_precaution_active, 0, R.drawable.ic_arc);


                } else if (currentBackStackFragment instanceof ProfileFragment) {
                    resetBottomClickUIProfile();
                    tvProfile.setSelected(true);
                    lineProfile.setVisibility(View.VISIBLE);
                    // tvProfile.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_symptoms_active, 0, R.drawable.ic_arc);

                }

            }
        });

    }

    private void resetBottomClickUIProfile() {
        tvHome.setSelected(false);
        tvHistory.setSelected(false);
        tvProfile.setSelected(false);
        tvCart.setSelected(false);
        lineHome.setVisibility(View.GONE);
        lineHistory.setVisibility(View.GONE);
        lineCart.setVisibility(View.GONE);
        lineProfile.setVisibility(View.GONE);
        toolbarLayout.setVisibility(View.GONE);
    }

    private void resetBottomClickUI() {
      /*  tvHome.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_disable, 0, 0);
        tvHistory.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_about_disable, 0, 0);
        tvProfile.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_symptoms_disable, 0, 0);
        tvCart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_precaution_disable, 0, 0);*/

        tvHome.setSelected(false);
        tvHistory.setSelected(false);
        tvProfile.setSelected(false);
        tvCart.setSelected(false);
        lineHome.setVisibility(View.GONE);
        lineHistory.setVisibility(View.GONE);
        lineCart.setVisibility(View.GONE);
        lineProfile.setVisibility(View.GONE);
        toolbarLayout.setVisibility(View.VISIBLE);


    }

    @SuppressLint("WrongConstant")
    @OnClick({R.id.tv_home, R.id.tv_cart, R.id.tv_history, R.id.tv_profile,R.id.tv_location})
    public void bottomMenuClick(View view) {
        Fragment currentBackStackFragment = getSupportFragmentManager().findFragmentByTag("FRAGMENT");
        switch (view.getId()) {
            case R.id.tv_home:
                tvHome.setSelected(true);
                lineHome.setVisibility(View.VISIBLE);
                HomeFragment homeFragmet = new HomeFragment();
                if (!(currentBackStackFragment instanceof HomeFragment)) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout, homeFragmet, "FRAGMENT").
                            addToBackStack(null).
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                            commit();
                }
                break;
            case R.id.tv_history:
                tvHistory.setSelected(true);
                lineHistory.setVisibility(View.VISIBLE);
                HistoryFragments historyFragment = new HistoryFragments();
                if (!(currentBackStackFragment instanceof HistoryFragments)) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout, historyFragment, "FRAGMENT").
                            addToBackStack(null).
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                            commit();
                }
                break;
            case R.id.tv_cart:
                tvCart.setSelected(true);
                lineCart.setVisibility(View.VISIBLE);
                CartFragment cartFragment = new CartFragment();

                if (!(currentBackStackFragment instanceof CartFragment)) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout, cartFragment, "FRAGMENT").
                            addToBackStack(null).
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                            commit();
                }
                break;
            case R.id.tv_profile:
                tvProfile.setSelected(true);
                lineProfile.setVisibility(View.VISIBLE);
                toolbarLayout.setVisibility(View.GONE);
                ProfileFragment profileFragment = new ProfileFragment();

                if (!(currentBackStackFragment instanceof ProfileFragment)) {
                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_layout, profileFragment, "FRAGMENT").
                            addToBackStack(null).
                            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                            commit();
                }
                break;

            case R.id.tv_location:
                Intent intent = new Intent(this, SelectCityActivity.class);
//                        ArrayList<OtpVerifyPojo> otpVerifyPojosArrayList = new ArrayList<>();
//                        otpVerifyPojosArrayList = response.body().getData();

                startActivity(intent);
              //  finish();
                break;

        }
    }

    private void initFragment() {
        HomeFragment home = new HomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, home, "FRAGMENT").commit();
        tvHome.setSelected(true);
        lineHome.setVisibility(View.VISIBLE);
        //tvHome.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_active, 0, R.drawable.ic_arc);

    }
    @SuppressLint("WrongConstant")
    public void historyFragment() {
        tvHistory.setSelected(true);
        lineHistory.setVisibility(View.VISIBLE);
        resetBottomClickUI();
        HistoryFragments historyFragment = new HistoryFragments();
        if (!(currentBackStackFragment instanceof HistoryFragments)) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.frame_layout, historyFragment, "FRAGMENT").
                    addToBackStack(null).
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    commit();
        }
    }
    public void cartFragment() {
//        CartFragment cartFragment = new CartFragment();
//        resetBottomClickUI();
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, cartFragment, "FRAGMENT").detach(cartFragment)
//                .commitAllowingStateLoss();
//        tvCart.setSelected(true);
//        lineCart.setVisibility(View.VISIBLE);
        //tvHome.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_active, 0, R.drawable.ic_arc);
        CartFragment cartFragment = new CartFragment();
        resetBottomClickUI();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, cartFragment, "FRAGMENT").commit();
        tvCart.setSelected(true);
        lineCart.setVisibility(View.VISIBLE);
    }
    public void HistoryFragment() {
        HistoryFragments historyFragment = new HistoryFragments();
        resetBottomClickUI();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, historyFragment, "FRAGMENT").commit();
        tvHistory.setSelected(true);
        lineHistory.setVisibility(View.VISIBLE);
    }
    public void ProfileFragment() {
        ProfileFragment profileFragment = new ProfileFragment();
        resetBottomClickUI();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, profileFragment, "FRAGMENT").commit();
        tvProfile.setSelected(true);
        lineProfile.setVisibility(View.VISIBLE);
    }
}
