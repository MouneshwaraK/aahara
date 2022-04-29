package com.codebele.aahara.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codebele.aahara.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Dialog for selecting Country.
 *
 * Created by Joielechong on 11 May 2017.
 */
public class CountryCodeDialog extends Dialog {
  private static final String TAG = "CountryCodeDialog";

  private EditText mEdtSearch;
  private TextView mTvNoResult;
  private TextView mTvTitle;
  private ListView mLvCountryDialog;
  private com.codebele.aahara.custom.CountryCodePicker mCountryCodePicker;
  private LinearLayout mRlyDialog;
  private ImageView iv_back,iv_close;

  private List<com.codebele.aahara.custom.Country> masterCountries;
  private List<com.codebele.aahara.custom.Country> mFilteredCountries;
  private InputMethodManager mInputMethodManager;
  private CountryCodeArrayAdapter mArrayAdapter;
  private List<com.codebele.aahara.custom.Country> mTempCountries;

  public  CountryCodeDialog(CountryCodePicker countryCodePicker) {
    super(countryCodePicker.getContext());
    mCountryCodePicker = countryCodePicker;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.country_code_picker_layout_picker_dialog);
    getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
    setupUI();
    setupData();
  }


  private void setupUI() {
    mRlyDialog = findViewById(com.rilixtech.widget.countrycodepicker.R.id.dialog_rly);
    mLvCountryDialog = findViewById(com.rilixtech.widget.countrycodepicker.R.id.country_dialog_lv);
    mTvTitle = findViewById(com.rilixtech.widget.countrycodepicker.R.id.title_tv);
    mEdtSearch = findViewById(com.rilixtech.widget.countrycodepicker.R.id.search_edt);
    mTvNoResult = findViewById(com.rilixtech.widget.countrycodepicker.R.id.no_result_tv);
    iv_close = findViewById(R.id.iv_close);
    iv_back = findViewById(R.id.iv_back);
  }

  private void setupData() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      mLvCountryDialog.setLayoutDirection(mCountryCodePicker.getLayoutDirection());
    }

    if (mCountryCodePicker.getTypeFace() != null) {
      Typeface typeface = mCountryCodePicker.getTypeFace();
      mTvTitle.setTypeface(typeface);
      mEdtSearch.setTypeface(typeface);
      mTvNoResult.setTypeface(typeface);
    }

    if (mCountryCodePicker.getBackgroundColor() != mCountryCodePicker.getDefaultBackgroundColor()) {
      mRlyDialog.setBackgroundColor(mCountryCodePicker.getBackgroundColor());
    }

    if (mCountryCodePicker.getDialogTextColor() != mCountryCodePicker.getDefaultContentColor()) {
      int color = mCountryCodePicker.getDialogTextColor();
      mTvTitle.setTextColor(color);
      mTvNoResult.setTextColor(color);
      mEdtSearch.setTextColor(color);
      mEdtSearch.setHintTextColor(adjustAlpha(color, 0.7f));
    }

    mCountryCodePicker.refreshCustomMasterList();
    mCountryCodePicker.refreshPreferredCountries();
    masterCountries = mCountryCodePicker.getCustomCountries(mCountryCodePicker);

    mFilteredCountries = getFilteredCountries();
    setupListView(mLvCountryDialog);

    Context ctx = mCountryCodePicker.getContext();
    mInputMethodManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
    setSearchBar();
  }

  private void setupListView(ListView listView) {
    mArrayAdapter = new com.codebele.aahara.custom.CountryCodeArrayAdapter(getContext(), mFilteredCountries, mCountryCodePicker);

    if (!mCountryCodePicker.isSelectionDialogShowSearch()) {
      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listView.getLayoutParams();
      params.height = ListView.LayoutParams.WRAP_CONTENT;
      listView.setLayoutParams(params);
    }

    OnItemClickListener listener = new OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mFilteredCountries == null) {
          Log.e(TAG, "no filtered countries found! This should not be happened, Please report!");
          return;
        }

        if (mFilteredCountries.size() < position || position < 0) {
          Log.e(TAG, "Something wrong with the ListView. Please report this!");
          return;
        }

        com.codebele.aahara.custom.Country country = mFilteredCountries.get(position);
        /* view is only a separator, so the country is null and we ignore it.
         see {@link #getFilteredCountries(String)} */
        if (country == null) return;

        mCountryCodePicker.setSelectedCountry(country);
        mInputMethodManager.hideSoftInputFromWindow(mEdtSearch.getWindowToken(), 0);
        dismiss();
      }
    };
    listView.setOnItemClickListener(listener);
    listView.setAdapter(mArrayAdapter);
    iv_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
    iv_close.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEdtSearch.getText().clear();
        if (mEdtSearch.getText().toString().isEmpty()){
          iv_close.setVisibility(View.GONE);
        }
      }
    });
  }


  private int adjustAlpha(int color, float factor) {
    int alpha = Math.round(Color.alpha(color) * factor);
    int red = Color.red(color);
    int green = Color.green(color);
    int blue = Color.blue(color);
    return Color.argb(alpha, red, green, blue);
  }

  private void setSearchBar() {
    if (mCountryCodePicker.isSelectionDialogShowSearch()) {
      setTextWatcher();
    } else {
      mEdtSearch.setVisibility(View.GONE);
    }
  }


  /**
   * add textChangeListener, to apply new query each time editText get text changed.
   */
  private void setTextWatcher() {
    if (mEdtSearch == null) return;

    mEdtSearch.addTextChangedListener(new TextWatcher() {

      @Override public void afterTextChanged(Editable s) {

      }

      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        iv_close.setVisibility(View.GONE);
      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        applyQuery(s.toString());
        iv_close.setVisibility(View.VISIBLE);
        if (mEdtSearch.getText().toString().length()==0){
          iv_close.setVisibility(View.GONE);
        }
      }
    });

    if (mCountryCodePicker.isKeyboardAutoPopOnSearch()) {
      if (mInputMethodManager != null) {
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
      }else {
        iv_close.setVisibility(View.GONE);
      }
    }
  }

  /**
   * Filter country list for given keyWord / query.
   * Lists all countries that contains @param query in country's name, name code or phone code.
   *
   * @param query : text to match against country name, name code or phone code
   */
  private void applyQuery(String query) {
    mTvNoResult.setVisibility(View.GONE);
    query = query.toLowerCase();

    //if query started from "+" ignore it
    if (query.length() > 0 && query.charAt(0) == '+') {
      query = query.substring(1);
    }

    mFilteredCountries = getFilteredCountries(query);

    if (mFilteredCountries.size() == 0) {
      mTvNoResult.setVisibility(View.VISIBLE);
    }

    mArrayAdapter.notifyDataSetChanged();
  }

  private List<com.codebele.aahara.custom.Country> getFilteredCountries() {
    return getFilteredCountries("");
  }

  private List<com.codebele.aahara.custom.Country> getFilteredCountries(String query) {
    if (mTempCountries == null) {
      mTempCountries = new ArrayList<>();
    } else {
      mTempCountries.clear();
    }

    List<com.codebele.aahara.custom.Country> preferredCountries = mCountryCodePicker.getPreferredCountries();
    if (preferredCountries != null && preferredCountries.size() > 0) {
      for (com.codebele.aahara.custom.Country country : preferredCountries) {
        if (country.isEligibleForQuery(query)) {
          mTempCountries.add(country);
        }
      }

      if (mTempCountries.size() > 0) { //means at least one preferred country is added.
        mTempCountries.add(null); // this will add separator for preference countries.
      }
    }

    for (Country country : masterCountries) {
      if (country.isEligibleForQuery(query)) {
        mTempCountries.add(country);
      }
    }
    return mTempCountries;
  }
}
