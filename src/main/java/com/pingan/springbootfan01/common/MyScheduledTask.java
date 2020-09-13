package com.pingan.springbootfan01.common;

import com.pingan.springbootfan01.dao.MemberDao;
import com.pingan.springbootfan01.entity.Member;
import com.pingan.springbootfan01.util.UserRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.common
 *  @文件名:   MyScheduledTask
 *  @创建者:   Administrator
 *  @创建时间:  2020/6/12 21:39
 *  @描述：    TODO
 */
@Component
public class MyScheduledTask {
    private static final String TAG = "MyScheduledTask";

    @Autowired
    private UserRegister mUserRegister;

    @Autowired
    private MemberDao mMemberDao;


    @Scheduled(initialDelay = 1000000, fixedRate = 7200000)
    public void sheduledTask1(){
        System.out.println("查询流量定时任务执行。。。。");
        List<Member> all = mMemberDao.findAll();

        all.forEach(member -> {

            String month = mUserRegister.DataUsageForTotal(member.getMenberEmail());
            String allTotal = mUserRegister.DataUsageForAllTotal(member.getMenberEmail());
            if (month.equals("0.0") || month.length() > 30){
                member.setT("0.0");
            }else {
                member.setT(month.substring(0,month.indexOf(".")+4));
            }
            member.setU(allTotal);
            mMemberDao.save(member);
        });

    }
}
