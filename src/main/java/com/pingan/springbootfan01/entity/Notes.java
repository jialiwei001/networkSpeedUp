package com.pingan.springbootfan01.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.entity
 *  @文件名:   Notes
 *  @创建者:   Administrator
 *  @创建时间:  2020/4/5 11:50
 *  @描述：    TODO
 */
@Entity(name = "notes")
public class Notes {
    private static final String TAG = "Notes";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
    private String content;

    private int number;

    private double price;

    private Date createTime;

    //外键
    @ManyToOne
    @JoinColumn(name = "cid")
    private LocalUser mLocalUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public LocalUser getLocalUser() {
        return mLocalUser;
    }

    public void setLocalUser(LocalUser localUser) {
        mLocalUser = localUser;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Notes{" + "id=" + id + ", type='" + type + '\'' + ", content='" + content + '\'' + ", number=" + number + ", price=" + price + ", createTime=" + createTime + '}';
    }
}
