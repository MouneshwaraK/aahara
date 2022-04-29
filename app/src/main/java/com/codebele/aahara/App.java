package com.codebele.aahara;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;


/**
 * Created by Codebele on 08-Aug-19.
 */
public class App extends Application {
    public    static App pInstance;

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;
    @Override
    public void onCreate() {
        super.onCreate();
        pInstance=this;
       // SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
        super.onCreate();

        //初始化屏幕宽高
        getScreenSize();
//        AutoLayoutConifg.getInstance().useDeviceSize().init(this);
//        BaseApiImpl.getBaseApi().initWebView(this);
      //  DataKeeper.init(pInstance);
   /*     TopViewKit.init(this,userInfoKeeper);
        getLoginInfo();
        initFileDownload();*/
    }



    public static synchronized App getpInstance(){
        return pInstance;

    }

    public void getScreenSize() {
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if(SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }



}
