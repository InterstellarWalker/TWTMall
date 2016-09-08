package com.tianwotian.tools;

/**
 * 生成6位随机数
 */
public class GenerateSixRandomNums {

    public  static int count;

    public static int generateSixNums(){
        count = (int) ((Math.random()*9+1)*100000);
        return count;
    }

}
