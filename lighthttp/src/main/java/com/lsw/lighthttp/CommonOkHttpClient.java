package com.lsw.lighthttp;

import com.lsw.lighthttp.https.HttpsUtils;
import com.lsw.lighthttp.listener.DisposeDataHandle;
import com.lsw.lighthttp.response.CommonJsonCallback;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class CommonOkHttpClient {
    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient = null;

    //为mOkHttpClient去配置参数  类加载的时候开始创建静态代码块，并且只执行一次
    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.followRedirects(true); //设置重定向 其实默认也是true

        /*--添加请求头  这个看个人需求 --*/
//        okHttpClientBuilder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request()
//                        .newBuilder()
//                        .addHeader("User-Agent", "Android—Mobile") // 标明发送本次请求的客户端
//                        .build();
//                return chain.proceed(request);
//            }
//        });

        //添加https支持
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        /**
         * trust all the https point
         */
        okHttpClientBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());
        mOkHttpClient = okHttpClientBuilder.build();
    }

    /**
     * 发送具体的http/https的请求
     *
     * @param request
     * @param commonCallback
     * @return Call
     */
    public static Call sendRequest(Request request, CommonJsonCallback commonCallback) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(commonCallback);
        return call;
    }

    /**
     * 发送具体的http/https的请求
     *
     * @param request
     * @param commonCallback
     * @return Call
     */
    public static Call sendRequest(Request request, Callback commonCallback) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(commonCallback);
        return call;
    }

    //GET请求
    public static Call get(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    //POST请求
    public static Call post(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

}
