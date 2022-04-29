package com.codebele.aahara;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Codebele on 24-Jan-20.
 */
public class Utility {


    public static void enableclickView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                enableclickView(child, enabled);
            }
        }
    }
}
