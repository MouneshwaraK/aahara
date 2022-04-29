package com.codebele.aahara.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codebele.aahara.MainActivity;
import com.codebele.aahara.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Order_Successful extends BaseActivity {
    /** ButterKnife Code **/
    @BindView(R.id.tv_order_success)
    TextView tvOrderSuccess;
    @BindView(R.id.btn_track)
    Button btnTrack;
    @BindView(R.id.btn_home)
    Button btnHome;
    /** ButterKnife Code **/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordersuccessful);
        ButterKnife.bind(this);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().add(android.R.id.content, new HistoryFragment()).commit();
//        }else{
//            getSupportFragmentManager().beginTransaction().add(android.R.id.content, new HomeFragment ()).commit();
//        }
    }

    @OnClick({R.id.btn_track,R.id.btn_home})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_track:
                Intent intent = new Intent(Order_Successful.this, MainActivity.class);
                intent.putExtra("tag", "history");
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                break;
            case R.id.btn_home:
                Intent intent1 = new Intent(Order_Successful.this, MainActivity.class);
                intent1.putExtra("tag", "home");
                startActivity(intent1);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                break;
        }
    }
}
