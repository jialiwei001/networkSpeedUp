package com.pingan.springbootfan01.util;

import java.util.Date;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.util
 *  @文件名:   UtilTools
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/31 21:33
 *  @描述：    TODO
 */
public class UtilTools {
    private static final String TAG = "UtilTools";



    public static String autoEmail(){

        long  time = new Date().getTime();
        String autoEmail  = String.valueOf(time) + "@qq.com";

        return autoEmail;
    }

    public static String autoMenberName(){
        String autoName = String.valueOf(new Date().getTime());
        return autoName;
    }
    public static String autoChatNumber(){
        String chatNumber = String.valueOf(new Date().getTime());
        return chatNumber;
    }

    public static String createUser(String email,String name,String charNumber ,int days){
        return "0i2R7cqifDKyzPE3";
    }

    public static String addtime1(String email,int days){
        return "0i2R7cqifDKyzPE3";
    }

}
