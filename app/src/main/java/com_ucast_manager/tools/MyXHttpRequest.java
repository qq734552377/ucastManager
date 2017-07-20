package com_ucast_manager.tools;

import android.app.ProgressDialog;
import android.content.Context;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

import com_ucast_manager.R;
import com_ucast_manager.entity.BaseReturnMsg;


/**
 * Created by pj on 2017/7/12.
 */

public class MyXHttpRequest {
    /*
      * 请求返回的状态码
     */
    public static final String SUCCESS = "true";
    public static final String TIMEOUT = "Timeout";
    public static final String ERROR = "Error";

    public static void postParamsRequestWithToken(final Context context, String url, Map<String, String> params,
                                                final MyHttpSucessCallback callback) {


        final ProgressDialog progressDialog=MyDialog.createProgressDialog(context,context.getString(R.string.querrying));
        progressDialog.show();
        RequestParams requestParams = new RequestParams(url);
//        requestParams.setMultipart(true);
        requestParams.addHeader("Authorization","Basic " + SavePasswd.getInstace().get(MyTools.TOKEN));

        if (params!=null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                requestParams.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseReturnMsg baseHttpResponseMsg = JSON.parseObject(result, BaseReturnMsg.class);
                if (baseHttpResponseMsg==null){
                    return;
                }
                if (baseHttpResponseMsg.getResult().equals(SUCCESS)) {
                    //请求成功
                    if (callback != null)
                        callback.sucess(baseHttpResponseMsg.getDate());
                }else {
                    MyDialog.showDialog(context, baseHttpResponseMsg.getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                MyDialog.showDialog(context, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                progressDialog.cancel();
            }
        });


    }



}
