package com.codebele.aahara.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codebele.aahara.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class How_ItWorks extends BaseActivity {
    /** ButterKnife Code **/
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_how_it)
    TextView tvHowIt;
    @BindView(R.id.card_text1)
    androidx.cardview.widget.CardView cardText1;
    @BindView(R.id.tv_work)
    TextView tvWork;
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
    @BindView(R.id.tv_contact13)
    TextView tvContact13;
    @BindView(R.id.tv_contact14)
    TextView tvContact14;
    @BindView(R.id.tv_contact15)
    TextView tvContact15;
    @BindView(R.id.tv_contact16)
    TextView tvContact16;
    @BindView(R.id.tv_contact17)
    TextView tvContact17;
    @BindView(R.id.tv_contact18)
    TextView tvContact18;
    @BindView(R.id.tv_contact19)
    TextView tvContact19;
    @BindView(R.id.tv_contact20)
    TextView tvContact20;
    @BindView(R.id.tv_contact21)
    TextView tvContact21;
    /** ButterKnife Code **/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_it_works);
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
