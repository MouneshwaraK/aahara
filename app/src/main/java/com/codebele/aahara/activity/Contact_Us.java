package com.codebele.aahara.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codebele.aahara.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Contact_Us extends BaseActivity {
    /** ButterKnife Code **/
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_contact_us)
    TextView tvContactUs;
    @BindView(R.id.card_image)
    androidx.cardview.widget.CardView cardImage;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_contact1)
    TextView tvContact1;
    @BindView(R.id.tv_contact2)
    TextView tvContact2;
    @BindView(R.id.tv_contact3)
    TextView tvContact3;
    @BindView(R.id.tv_contact4)
    TextView tvContact4;
    @BindView(R.id.tv_contact5)
    TextView tvContact5;
    @BindView(R.id.tv_contact6)
    TextView tvContact6;
    @BindView(R.id.tv_contact7)
    TextView tvContact7;
    @BindView(R.id.tv_contact8)
    TextView tvContact8;
    @BindView(R.id.tv_contact9)
    TextView tvContact9;
    @BindView(R.id.tv_contact10)
    TextView tvContact10;
    @BindView(R.id.tv_contact11)
    TextView tvContact11;
    @BindView(R.id.tv_contact12)
    TextView tvContact12;
    /** ButterKnife Code **/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
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
