package com.lsw.lighthttp.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

public class CommonRequest {

    /**
     * @param url
     * @param params
     * @return 返回一个创建好get的Request对象
     */
    public  static Request createGetRequest(String  url, RequestParams params){

        StringBuilder urlBuilder=new StringBuilder(url).append("?");
        if(params!=null){
            for(Map.Entry<String,String> entry: params.urlParams.entrySet()){
                // 将请求参数逐一添加到请求体中
                urlBuilder.append(entry.getKey()).append("=")
                        .append(entry.getValue())
                        .append("&");
            }
        }
        return  new Request.Builder()
                .url(urlBuilder.substring(0,urlBuilder.length()-1)) //要把最后的&符号去掉
                .get()
                .build();
    }

    /**
     * @param url
     * @param params
     * @return 返回一个创建好post的Request对象
     */
    public  static Request createPostRequest(String  url, RequestParams params){

        FormBody.Builder mFormBodybuilder = new FormBody.Builder();
        if(params!=null){
            for(Map.Entry<String,String> entry: params.urlParams.entrySet()){
                // 将请求参数逐一添加到请求体中
                mFormBodybuilder.add(entry.getKey(),entry.getValue());
            }
        }
        FormBody mFormBody=mFormBodybuilder.build();
        return  new Request.Builder()
                .url(url)
                .post(mFormBody)
                .build();
    }
}
