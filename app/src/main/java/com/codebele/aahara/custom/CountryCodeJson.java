package com.codebele.aahara.custom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryCodeJson {

    @SerializedName("en")
    @Expose
    private String en;
    @SerializedName("es")
    @Expose
    private String es;
    @SerializedName("zh")
    @Expose
    private String zh;
    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("code")
    @Expose
    private Integer code;

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getEs() {
        return es;
    }

    public void setEs(String es) {
        this.es = es;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public class Example {

        @SerializedName("CountryCodeJson")
        @Expose
        private List<CountryCodeJson> countryCodeJson = null;

        public List<CountryCodeJson> getCountryCodeJson() {
            return countryCodeJson;
        }

        public void setCountryCodeJson(List<CountryCodeJson> countryCodeJson) {
            this.countryCodeJson = countryCodeJson;
        }

    }
}
