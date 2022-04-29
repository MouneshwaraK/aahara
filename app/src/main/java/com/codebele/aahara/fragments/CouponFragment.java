package com.codebele.aahara.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codebele.aahara.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.ButterKnife;

public class CouponFragment extends BottomSheetDialogFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme);
        Bundle mArgs = getArguments();
       /* english_text = mArgs.getString("english_text");
        chine_english_text = mArgs.getString("chine_english_text");
        chines_text = mArgs.getString("chines_text");*/

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container,false);
        ButterKnife.bind(this, view);
        return view;
    }
}
