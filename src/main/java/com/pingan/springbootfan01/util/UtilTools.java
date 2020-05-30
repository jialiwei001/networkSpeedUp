package com.pingan.springbootfan01.util;

import com.pingan.springbootfan01.entity.Member;

import java.util.Date;
import java.util.regex.Pattern;

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

    public static boolean isNumeric(int time){
        String str = Integer.toString(time);
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static String addtime1(String email,int days){
        return "0i2R7cqifDKyzPE3";
    }


    public static void fixUserType(Member oneMenber){
        //续费后更改卡类型
        Long usedData = (oneMenber.getEndtime().getTime() - oneMenber.getStarttime().getTime())/(24*60*60*1000);
        if (usedData >= 30 && usedData < 90){
            oneMenber.setType("月卡");
        }else if (usedData >= 90 && usedData < 180){
            oneMenber.setType("季卡");
        }else if (usedData >= 180 && usedData < 365){
            oneMenber.setType("半年卡");
        }else if (usedData >= 365){
            oneMenber.setType("年卡");
        }
    }
}
