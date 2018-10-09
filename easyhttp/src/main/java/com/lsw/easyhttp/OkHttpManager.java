package com.lsw.easyhttp;

import okhttp3.OkHttpClient;

public class OkHttpManager {

    private static volatile OkHttpManager mOkHttpManager = null;
    private OkHttpClient mOkHttpClient;


    private OkHttpManager(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
    }

    private static OkHttpManager createInstance(OkHttpClient client){
        if(mOkHttpManager  == null){
            synchronized (OkHttpManager.class){
                if(mOkHttpManager == null){
                    mOkHttpManager = new OkHttpManager(client);
                }
            }
        }
        return mOkHttpManager;
    }

    public static OkHttpManager getInstance(){
        return createInstance(null);
    }

    public static OkHttpManager initClient(OkHttpClient okHttpClient){
        return createInstance(okHttpClient);
    }



}
