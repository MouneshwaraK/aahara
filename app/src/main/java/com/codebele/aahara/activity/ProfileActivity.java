package com.codebele.aahara.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codebele.aahara.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {
    /** ButterKnife Code **/
    @BindView(R.id.txt_profile)
    TextView txtProfile;
    @BindView(R.id.txt_view)
    TextView txtView;
    @BindView(R.id.bookmark)
    ImageView bookmark;
    @BindView(R.id.txt_bookmark)
    TextView txtBookmark;
    @BindView(R.id.notification)
    ImageView notification;
    @BindView(R.id.txt_notification)
    TextView txtNotification;
    @BindView(R.id.settings)
    ImageView settings;
    @BindView(R.id.txt_settings)
    TextView txtSettings;
    @BindView(R.id.img_payments)
    ImageView imgPayments;
    @BindView(R.id.txt_payments)
    TextView txtPayments;
    @BindView(R.id.your_order)
    ImageView yourOrder;
    @BindView(R.id.txt_your_oredr)
    TextView txtYourOredr;
    @BindView(R.id.your_order_right)
    ImageView yourOrderRight;
    @BindView(R.id.addres_order)
    ImageView addresOrder;
    @BindView(R.id.txt_addres_oredr)
    TextView txtAddresOredr;
    @BindView(R.id.favorite_order_right)
    ImageView favoriteOrderRight;
    @BindView(R.id.favorite_order)
    ImageView favoriteOrder;
    @BindView(R.id.txt_favorite_oredr)
    TextView txtFavoriteOredr;
    @BindView(R.id.addres_book_right)
    ImageView addresBookRight;
    @BindView(R.id.online_ordering_order)
    ImageView onlineOrderingOrder;
    @BindView(R.id.txt_online_ordaring_oredr)
    TextView txtOnlineOrdaringOredr;
    @BindView(R.id.online_ordering_order_right)
    ImageView onlineOrderingOrderRight;
    @BindView(R.id.txt_about)
    TextView txtAbout;
    @BindView(R.id.about_arrow)
    ImageView aboutArrow;
    @BindView(R.id.about_line)
    View aboutLine;
    @BindView(R.id.txt_send_feedback)
    TextView txtSendFeedback;
    @BindView(R.id.feedback_arrow)
    ImageView feedbackArrow;
    @BindView(R.id.txt_rate_us)
    TextView txtRateUs;
    @BindView(R.id.txt_rate_us_arrow)
    ImageView txtRateUsArrow;
    @BindView(R.id.txt_logout)
    TextView txtLogout;
    @BindView(R.id.txt_logout_arrow)
    ImageView txtLogoutArrow;
    /** ButterKnife Code **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.fragment_profile);
        ButterKnife.bind(this);
    }

}
