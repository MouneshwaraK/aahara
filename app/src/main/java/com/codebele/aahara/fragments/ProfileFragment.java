package com.codebele.aahara.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.codebele.aahara.LoginActivity;
import com.codebele.aahara.MainActivity;
import com.codebele.aahara.R;
import com.codebele.aahara.activity.About_Us;
import com.codebele.aahara.activity.Contact_Us;
import com.codebele.aahara.activity.EditProfileActivity;
import com.codebele.aahara.activity.How_ItWorks;
import com.codebele.aahara.activity.SelectCityActivity;
import com.codebele.aahara.activity.ViewAddress;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.codebele.SessionManagers.LoginSessionManager.KEY_Fullname;
import static com.codebele.SessionManagers.LoginSessionManager.KEY_email;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    /** ButterKnife Code **/
    @BindView(R.id.rl_profile)
    RelativeLayout rlProfile;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.iv_image)
    com.mikhaellopez.circularimageview.CircularImageView ivImage;
    @BindView(R.id.rl_update_location)
    RelativeLayout rlUpdateLocation;
    @BindView(R.id.iv_update)
    ImageView ivUpdate;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.rl_your_orders)
    RelativeLayout rlYourOrders;
    @BindView(R.id.iv_order)
    ImageView ivOrder;
    @BindView(R.id.txt_your_oredr)
    TextView txtYourOredr;
    @BindView(R.id.rl_address_book)
    RelativeLayout rlAddressBook;
    @BindView(R.id.iv_address)
    ImageView ivAddress;
    @BindView(R.id.rl_abt_us)
    RelativeLayout rlAbtUs;
    @BindView(R.id.rl_how_itworks)
    TextView rlHowItworks;
    @BindView(R.id.rl_faqs)
    RelativeLayout rlFaqs;
    @BindView(R.id.rl_contact_us)
    RelativeLayout rlContactUs;
    @BindView(R.id.rl_logout)
    RelativeLayout rlLogout;
    /** ButterKnife Code **/
    com.codebele.SessionManagers.LoginSessionManager sessionManager;
    private String access_token;
    HashMap<String, String> user;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainActivity mParent;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getContext());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profilee, container,false);
        sessionManager = new com.codebele.SessionManagers.LoginSessionManager(getContext());
        user = sessionManager.getUserDetails();
        ButterKnife.bind(this, view);
        tvName.setText(user.get(KEY_Fullname));
        tvEmail.setText(user.get(KEY_email));
        return view;
    }
    @OnClick({R.id.rl_your_orders, R.id.rl_address_book, R.id.rl_abt_us, R.id.rl_how_itworks, R.id.rl_faqs, R.id.rl_contact_us, R.id.rl_logout, R.id.iv_image, R.id.tv_edit, R.id.rl_update_location})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_your_orders:
                mParent.historyFragment();
                break;
            case R.id.rl_address_book:
                Intent intent1 = new Intent(getContext(), ViewAddress.class);
                startActivity(intent1);
                break;
            case R.id.tv_edit:
                startActivity(new Intent(getContext(), EditProfileActivity.class));
                break;
            case R.id.rl_logout:
                sessionManager.logoutUser();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.rl_contact_us:
                startActivity(new Intent(getContext(), Contact_Us.class));
                break;
            case R.id.rl_how_itworks:
                startActivity(new Intent(getContext(), How_ItWorks.class));
                break;
            case R.id.rl_abt_us:
                startActivity(new Intent(getContext(), About_Us.class));
                break;
            case R.id.rl_update_location:
                startActivity(new Intent(getContext(), SelectCityActivity.class));
                break;
            case R.id.rl_faqs:
                Uri uri1 = Uri.parse("https://www.aahara.co.in/faq"); // missing 'http://' will cause crashed
                Intent i = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(i);
                break;
        }
    }
}
