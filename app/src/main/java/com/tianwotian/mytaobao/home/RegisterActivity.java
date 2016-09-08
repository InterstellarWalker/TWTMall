package com.tianwotian.mytaobao.home;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.tianwotian.mytaobao.bean.CheckMobile;
import com.tianwotian.mytaobao.bean.FailJson;
import com.tianwotian.mytaobao.bean.SucceedJson;
import com.tianwotian.mytaobao.view.ImageToast;
import com.tianwotian.mytaobaotest.R;
import com.tianwotian.tools.GenerateSixRandomNums;
import com.tianwotian.tools.RegexUtils;
import com.tianwotian.tools.SharedPreferenceUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText editPhoneOrEmail;
    private EditText editVerificationCode;
    private EditText editFirstInputPassword;
    private EditText editConfirmPassword;
    private EditText editReferer;
    private Button getVerificationCodeBtn;
    private Button registerConfirmBtn;
    private TextView tianwotian_store_agreement;
    private int countDownClock = 0;
    private String VCTAG = "VerificationCode";
    private String VCSUCCEED = "isVCSucceed"; //发送验证码是否成功
    private String ISPHONEHAVEREGISTERED = "isphonehaveregistered";//手机号是否注册过
    private String REGISTERSUCCEED = "registerSucceed";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //返回按钮监听
        toolbar.setNavigationIcon(R.mipmap.activity_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editPhoneOrEmail = (EditText) findViewById(R.id.edit_acount);
        editVerificationCode = (EditText) findViewById(R.id.edit_verification_code);
        editFirstInputPassword = (EditText) findViewById(R.id.first_input_passwd);
        editConfirmPassword = (EditText) findViewById(R.id.edit_confirm_pwd);
        editReferer = (EditText) findViewById(R.id.edit_referer);
        getVerificationCodeBtn = (Button) findViewById(R.id.get_verification_code_btn);
        registerConfirmBtn = (Button) findViewById(R.id.register_confirm_btn);
        tianwotian_store_agreement = (TextView) findViewById(R.id.tianwotian_store_agreement);

//        editFirstInputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD); 密码可见
//        editConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        getVerificationCodeBtn.setSelected(true);
        SharedPreferenceUtils.putString(this, VCTAG, null);
        SharedPreferenceUtils.putString(this, "editVC", null);
        SharedPreferenceUtils.putBoolean(this, "isCloseVC", false);
        SharedPreferenceUtils.putBoolean(this, VCSUCCEED, false);


        getVerificationCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownClock = 60;
                //正则表达式判断文本框输入是否为手机号或者邮箱号
                String phoneNumOrEmail = String.valueOf(editPhoneOrEmail.getText());

                if (RegexUtils.isPhoneNum(phoneNumOrEmail)) {
                    String codeNum = String.valueOf(GenerateSixRandomNums.generateSixNums());
                    SharedPreferenceUtils.putString(RegisterActivity.this, VCTAG, codeNum);
                    String codeUrl1 = "http://apis.haoservice.com/sms/send?mobile=";
                    String codeUrl2 = "&tpl_id=1194&tpl_value=%23code%23%3d";
                    String codeUrl3 = "%26&key=fb606707ce4b4b27be690c4a035d4d6a";
                    String codeURL = codeUrl1 + phoneNumOrEmail + codeUrl2 + codeNum + codeUrl3;
                    String checkPhoneRegisteredUrl;
                    String checkMobileResult = null;
                    if (editPhoneOrEmail.getText().toString().length() != 0) {
                        checkPhoneRegisteredUrl = "http://api.tianwotian.com/api/CheckPhone.aspx?\n" +
                                "mobile=" + phoneNumOrEmail;
                        checkMobileResult = conectURLForJson(checkPhoneRegisteredUrl, R.mipmap.button_bg_false, R.mipmap.button_bg_false, "网络失败，请检查网络", "", null, ISPHONEHAVEREGISTERED, true, false);
                    }

//                        Log.i("registerPhone", result);

                    if (checkMobileResult!=null&&checkMobileResult.contains("0")){
                        ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "您的手机可以注册").show();
                        //调用手机验证码接口发送验证码
                        String result = conectURLForJson(codeURL, R.mipmap.button_bg_false, R.mipmap.vc_succeed, "网络连接失败，请检查网络", "", editVerificationCode, VCSUCCEED, true, true);
                        if (result == null) {
                            ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "网络连接失败，请检查网络").show();
                        } else if (successdBackJson(result) == 0) {
                            Log.d("json", result);
                            int back = successdBackJson(result);
                            Log.d("VCResult", back + "");
                            //重新获取验证码按钮开始倒计时
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (countDownClock > 1) {
                                        try {
                                            Thread.sleep(1000);
                                            Message message = new Message();
                                            message.what = 1;
                                            handler.sendMessage(message);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    while (countDownClock == 1) {
                                        Message msg = new Message();
                                        msg.what = 2;
                                        handler.sendMessage(msg);
                                    }
                                }
                            }).start();
                        } else if (failBackJson(result) == 0) {
                            ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "验证码发送失败，请重新发送").show();

                        } else if (RegexUtils.isEmailAccount(phoneNumOrEmail)) {
                            //调用邮箱验证码接口发送验证码

                        } else if (phoneNumOrEmail.length() == 0) {
                            ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "请输入手机号码").show();
                        } else if (phoneNumOrEmail.length() != 11) {
                            ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "手机号码格式不正确").show();
                        }
                    } else{
                        ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "您的手机已经注册过").show();
                        Log.w("why", checkMobileResult + " " + checkMobile(checkMobileResult));
                    }
                }
            }
        });
        //输入手机号后检查手机号是否被注册过
        editPhoneOrEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {

                } else {
                    String phoneNum = editPhoneOrEmail.getText().toString();
                    if (phoneNum.length() != 11) {
                        ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "手机号码格式不正确").show();
                    } else {
                        String checkPhoneRegisteredUrl = "http://api.tianwotian.com/api/CheckPhone.aspx?\n" +
                                "mobile=" + phoneNum;
                        String result = conectURLForJson(checkPhoneRegisteredUrl, R.mipmap.button_bg_false, R.mipmap.button_bg_false, "网络失败，请检查网络", "", null, ISPHONEHAVEREGISTERED, true, false);
//                        Log.i("registerPhone", result);
                        if (result!=null&&result.contains("0")){
                            ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "您的手机可以注册").show();
                            getVerificationCodeBtn.setEnabled(true);
                        }else{
                            ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "您的手机已经注册过").show();
                            getVerificationCodeBtn.setEnabled(false);
                        }

                    }
                }
            }
        });
        //判断两次输入密码是否一致以及密码格式是否正确
        editFirstInputPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {

                } else {
                    if (!passwordFormat()) {
                        ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "密码格式不正确").show();
                    }
                }
            }
        });
        editConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (haveDifference()) {

                    } else {
                        ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "两次输入密码不一致").show();
                    }
                    if (!passwordFormat()) {
                        ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "密码格式不正确").show();
                    }
                }

            }
        });
        //当editVerificationCode不获得焦点的时候，将输入信息存入到SharedPreference中
        editVerificationCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                SharedPreferenceUtils.putString(RegisterActivity.this, "editVC", editVerificationCode.getText().toString());
            }
        });
        final String codeNum = SharedPreferenceUtils.getString(this, VCTAG, null);
        //确认注册按钮监听
        registerConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkPhoneRegisteredUrl = null;
                String checkPhoneResult = null;
                if (editPhoneOrEmail.getText().toString().length() != 0) {
                    checkPhoneRegisteredUrl = "http://api.tianwotian.com/api/CheckPhone.aspx?\n" +
                            "mobile=" + editPhoneOrEmail.getText().toString();
                    checkPhoneResult = conectURLForJson(checkPhoneRegisteredUrl, R.mipmap.button_bg_false, R.mipmap.button_bg_false, "网络失败，请检查网络", "", null, ISPHONEHAVEREGISTERED, true, false);
                }

                String codeNum = SharedPreferenceUtils.getString(RegisterActivity.this, VCTAG, null);
                if (codeNum != null)
                    Log.d("codeNum", codeNum);
                if (editVerificationCode.getText().length() == 0) {  //验证码不为空
//                    Log.d("editVCode", SharedPreferenceUtils.getString(RegisterActivity.this, VCTAG, null));

                    ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "请输入验证码").show();

                } else if (!passwordFormat()) {  //密码格式不正确

                    ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "密码格式不正确").show();

//                    Log.d("codeNum",codeNum);
                    Log.d("password", String.valueOf(passwordFormat()));
                } else if (!haveDifference()) { //密两次密码输入不一致

                    ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "两次输入密码不一致").show();

                } else if (!isCorrectVerificationCode(codeNum)) { //验证码错误

                    ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "验证码输入错误").show();

                } else if (checkPhoneResult != null && checkMobile(checkPhoneResult)=="1") {
                    ImageToast.makeImage(RegisterActivity.this, R.mipmap.button_bg_false, Toast.LENGTH_SHORT, "您的手机已经注册过").show();
                } else {
                    Log.d("TagVC", "验证码输入正确！");
                    Log.d("pwdformat", String.valueOf(passwordFormat()));
                    Log.d("samepwd", String.valueOf(haveDifference()));
                    Log.d("vc", codeNum);
                    Log.d("pwd", editConfirmPassword.getText().toString());
                    //调用注册接口
                    String phoneNum = editPhoneOrEmail.getText().toString();
                    String password = editConfirmPassword.getText().toString();
                    String registerUrl;

                    String referrer = editReferer.getText().toString();
                    if (referrer.length()!=0){
                        registerUrl= "http://api.tianwotian.com/api/Register.aspx?mobile=\n" + phoneNum +
                                "&password=" + password+"&referrer="+referrer;
                    } else {
                        registerUrl= "http://api.tianwotian.com/api/Register.aspx?mobile=\n" + phoneNum +
                                "&password=" + password;
                    }
                    String registerResult = conectURLForJson(registerUrl, R.mipmap.button_bg_false, R.mipmap.register_succeed, "注册失败，请检查网络", "", registerConfirmBtn, REGISTERSUCCEED, true, false);
                    if (registerResult != null && failBackJson(registerResult) == 1) {
                    } else {
                        ImageToast.makeImage(RegisterActivity.this, R.mipmap.register_succeed, Toast.LENGTH_SHORT, "").show();
                    }
                }
            }
        });
    }

    //Gson解析手机验证码接口
    public int successdBackJson(String json) {
        Gson gson = new Gson();
        SucceedJson back = gson.fromJson(json, SucceedJson.class);
        return back.getError_code();
    }

    //Gson解析验证码发送错误json
    public int failBackJson(String json) {
        Gson gson = new Gson();
        FailJson back = gson.fromJson(json, FailJson.class);
        return back.getError();
    }

    //Gson检验手机号是否注册过
    public String checkMobile(String json) {
        Gson gson = new Gson();
        CheckMobile checkMobile = new CheckMobile();
        return checkMobile.getError();
    }

    /**
     * 判断两次输入密码时候一致
     * 如果一致，返回true
     * 如果不一致，返回false
     */

    public boolean haveDifference() {
        if (editFirstInputPassword.getText().toString().equals(editConfirmPassword.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断密码格式是否正确
     * 如果格式正确，返回true
     * 如果格式错误，返回false
     */
    public boolean passwordFormat() {
        boolean correctPassword = RegexUtils.isCorrectPassword(editFirstInputPassword.getText().toString());
        return correctPassword;
    }

    /**
     * 判断验证码输入是否正确
     * 如果验证码正确，返回true
     * 如果验证码错误，返回false
     */
    public boolean isCorrectVerificationCode(String codeNum) {
        boolean isCorrectVC = editVerificationCode.getText().toString().equals(codeNum);
        return isCorrectVC;
    }

    /** 使用Okhttp框架调用手机验证码接口
     *  参数说明
     * @param url 请求网络的地址
     * @param failureImgId 请求网络失败显示toast的图片资源ID
     * @param sucessImgId  请求网络成功显示toast的图片资源ID
     * @param failureText  请求网络失败显示的文字
     * @param sucessText   请求网络成功显示的文字
     * @param textView     选择是否设置一个TextView或者其子类不可点击，填入null为无此操作
     * @param TAG           标记返回信息
     * @param showToastFailure  请求网络失败时是否显示toast，false为不显示
     * @param showToastSucceed  请求网络成功时是否显示toast，false为不显示
     * @return              返回存入sharedpreference的信息
     */
    public String conectURLForJson(String url, final int failureImgId, final int sucessImgId,
                                   final String failureText, final String sucessText, final TextView textView, final String TAG, final boolean showToastFailure, final boolean showToastSucceed) {
        OkHttpClient codeHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = codeHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                SharedPreferenceUtils.putString(RegisterActivity.this, "RESULTOFURL", "fail");
                if (showToastFailure)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferenceUtils.putBoolean(RegisterActivity.this, TAG, false);
                            ImageToast.makeImage(RegisterActivity.this, failureImgId, Toast.LENGTH_SHORT, failureText).show();
                            if (textView != null) {
                                textView.setEnabled(false);
                            }
                        }
                    });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("back", result);
                SharedPreferenceUtils.putString(RegisterActivity.this, "RESULTOFURL", result);
                if (showToastSucceed)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferenceUtils.putBoolean(RegisterActivity.this, TAG, true);
                            ImageToast.makeImage(RegisterActivity.this, sucessImgId, Toast.LENGTH_SHORT, sucessText).show();
                            if (textView != null) {
                                textView.setEnabled(true);
                            }
                        }
                    });
            }
        });
        return SharedPreferenceUtils.getString(RegisterActivity.this, "RESULTOFURL", null);
    }

    //活动关闭时将验证码重新设置为空
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferenceUtils.putString(this, VCTAG, null);
        SharedPreferenceUtils.putString(this, "editVC", null);
        SharedPreferenceUtils.putString(RegisterActivity.this, "RESULTOFURL", null);

    }

    //重新获取验证码操作的计时功能
    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    countDownClock--;
                    getVerificationCodeBtn.setText("重新获取" + countDownClock + "s");
                    getVerificationCodeBtn.setTextColor(getResources().getColor(R.color.reget_vertification_code));
                    getVerificationCodeBtn.setClickable(false);
                    break;
                case 2:
                    getVerificationCodeBtn.setText(R.string.get_vertification_code);
                    getVerificationCodeBtn.setTextColor(getResources().getColor(R.color.colorWhite));
                    getVerificationCodeBtn.setClickable(true);
                    break;
            }
        }
    };
}
