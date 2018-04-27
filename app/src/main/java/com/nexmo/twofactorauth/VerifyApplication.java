package com.nexmo.twofactorauth;

import android.app.Application;

import com.nexmo.twofactorauth.utils.VerifyUtil;


public class VerifyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VerifyUtil.getInstance().init();
    }
}
