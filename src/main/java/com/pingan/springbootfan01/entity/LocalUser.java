package com.pingan.springbootfan01.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.entity
 *  @文件名:   LocalUser
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/27 13:36
 *  @描述：    TODO
 */
@Entity(name = "localuser")
public class LocalUser {
    private static final String TAG = "LocalUser";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //用户名
    private String username;
    //介绍人
    private String introducer;
    //邮箱
    private String email;
    //密码
    private String password;
    //创建时间
    private Date createtime;
    //创建记录

    //member记录
    @OneToMany(mappedBy = "mLocalUser")
    private List<Member> mMemberSet = new ArrayList<>();
    //private Set<Member> mMemberSet = new <>();

    //notes表的关联字段名
    @OneToMany(mappedBy = "mLocalUser")
    private List<Notes> mNotes = new ArrayList<>();

    public List<Notes> getNotes() {
        return mNotes;
    }

    public void setNotes(List<Notes> notes) {
        mNotes = notes;
    }

    public List<Member> getMemberSet() {
        return mMemberSet;
    }

    public void setMemberSet(List<Member> memberSet) {
        mMemberSet = memberSet;
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

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "LocalUser{" + "id=" + id + ", username='" + username + '\'' + ", introducer='" + introducer + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ", createtime=" + createtime +  '}';
    }
}
