package com.tianwotian.tools;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/8/31.
 */
public class RegexUtils {
    //正则表达式判断是否为手机号码
    public static boolean isPhoneNum(String phoneNum){

        boolean  isPhoneNum  = phoneNum.matches("^1[3|4|5|7|8][0-9]\\d{8}$");

        return  isPhoneNum;
    }

    //正则表达式判断是否为邮箱账号
    public static boolean isEmailAccount(String emailAccount){
        boolean isEmailAccount = emailAccount.matches("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");
        return  isEmailAccount;
    }

    //正则表达式判断密码格式是否在正确
    public static boolean isCorrectPassword(String password){
        boolean isCorrectPassword = password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
        return  isCorrectPassword;
    }

    //正则表达式是判断有几个数字
    public static List<String> numofNum(String c_num) {
        Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(c_num);
        List<String> digitList = new ArrayList<String>();
        String result = m.replaceAll("");
        for (int i = 0; i < result.length(); i++) {
            digitList.add(result.substring(i, i+1));
//            Log.d("num", digitList.get(i));
        }
        return digitList;
    }
}
