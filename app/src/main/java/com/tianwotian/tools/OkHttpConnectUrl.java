package com.tianwotian.tools;

import android.app.Activity;
import android.widget.Toast;


import com.tianwotian.mytaobao.view.ImageToast;
import com.tianwotian.mytaobaotest.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by user on 2016/8/31.
 */
public class OkHttpConnectUrl {
     OkHttpClient codeHttpClient = new OkHttpClient();
     Activity activity;
    public OkHttpConnectUrl(Activity activity) {
        this.activity=activity;
    }

    //使用Okhttp框架调用手机验证码接口
    public String getVerificationCodeFromURL(String url) {
        final String[] result = {null};

        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = codeHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageToast.makeImage(activity, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "请输入账号").show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result[0] = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageToast.makeImage(activity, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "请输入账号").show();
                    }
                });
            }
        });
        return result[0];
    }
}
