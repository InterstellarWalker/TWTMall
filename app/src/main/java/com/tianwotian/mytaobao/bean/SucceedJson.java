package com.tianwotian.mytaobao.bean;

/**
 * Created by user on 2016/8/31.
 */
public class SucceedJson {
    int error_code ;
    String reason;
    String result;

    public String getReason(){
        return  reason;
    }
    public int getError_code(){
        return error_code;
    }

}
