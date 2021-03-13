package com.pingan.springbootfan01.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.entity
 *  @文件名:   Member
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/27 13:44
 *  @描述：    TODO
 */
@Entity(name = "member")
public class Member {
    private static final String    TAG = "Member";
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private              Integer   id;
    //用户名
    @NotNull
    private              String    username;
    //密码
    @NotNull
    private              String    password;
    //邮箱
    @NotNull
    private String menberEmail;
    //订阅地址
    @NotNull
    private String subUrl;
    //手机号
    private              String    phonenumber;
    //开通时间
    @NotNull
    private              Date      starttime;
    //结束时间
    @NotNull
    private              Date      endtime;
    //会员类型
    @NotNull
    private              String    type;
    //续费次数
    @NotNull
    private              int       amount;

    private Integer n;
    //总流量
    private String u;

    //限速字段
    private String d;
    //已用流量
    private String t;

    //外键 mLocalUser
    @ManyToOne
    @JoinColumn(name = "cid")
    private  LocalUser mLocalUser;


    public String getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }

    public LocalUser getLocalUser() {
        return mLocalUser;
    }

    public void setLocalUser(LocalUser localUser) {
        this.mLocalUser = localUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMenberEmail() {
        return menberEmail;
    }

    public void setMenberEmail(String menberEmail) {
        this.menberEmail = menberEmail;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    //    @ManyToOne(targetEntity = LocalUser.class)
//    @JoinColumn(name = "cid",referencedColumnName = "id")
//    public Integer getCid() {
//        return cid;
//    }
//
//    public void setCid(Integer cid) {
//        this.cid = cid;
//    }


    @Override
    public String toString() {
        return "Member{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", menberEmail='" + menberEmail + '\'' + ", subUrl='" + subUrl + '\'' + ", phonenumber='" + phonenumber + '\'' + ", starttime=" + starttime + ", endtime=" + endtime + ", type='" + type + '\'' + ", amount=" + amount + ", n=" + n + ", u='" + u + '\'' + ", d='" + d + '\'' + ", t='" + t + '\'' + ", mLocalUser=" + mLocalUser + '}';
    }

    public String toString2() {
        return subUrl;
    }
}
