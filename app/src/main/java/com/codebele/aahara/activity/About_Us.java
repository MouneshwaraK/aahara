package com.codebele.aahara.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codebele.aahara.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class About_Us extends BaseActivity {
    /** ButterKnife Code **/
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.verification)
    TextView verification;
    @BindView(R.id.card_image)
    androidx.cardview.widget.CardView cardImage;
    @BindView(R.id.img_ahara)
    ImageView imgAhara;
    @BindView(R.id.tv_about_us)
    TextView tvAboutUs;
    @BindView(R.id.tv_contact1)
    TextView tvContact1;
    @BindView(R.id.tv_contact2)
    TextView tvContact2;
    @BindView(R.id.tv_contact3)
    TextView tvContact3;
    /** ButterKnife Code **/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.img_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }
}
