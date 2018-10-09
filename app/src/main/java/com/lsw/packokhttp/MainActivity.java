package com.lsw.packokhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lsw.lighthttp.CommonOkHttpClient;
import com.lsw.lighthttp.exception.OkHttpException;
import com.lsw.lighthttp.listener.DisposeDataHandle;
import com.lsw.lighthttp.listener.DisposeDataListener;
import com.lsw.lighthttp.request.CommonRequest;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLightOkHttp();
    }

    public void getLightOkHttp(){
        Request request = CommonRequest.createGetRequest("http://www.baidu.com",null);
        CommonOkHttpClient.get(request,new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object o) {
                Log.i(TAG, "getLightOkHttp--onSuccess: ");
            }

            @Override
            public void onFailure(Object o) {
                OkHttpException httpException = (OkHttpException)o;
                Log.i(TAG, "getLightOkHttp--onFailure: msg = " + httpException.getEmsg() + ";code = " + httpException.getEcode());
            }
        }));
    }
    
}
