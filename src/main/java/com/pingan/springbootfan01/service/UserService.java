package com.pingan.springbootfan01.service;

import com.pingan.springbootfan01.entity.LocalUser;

import java.util.List;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.service
 *  @文件名:   UserService
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/28 0:13
 *  @描述：    TODO
 */
public interface UserService {
    //根据用户名查询一个
    LocalUser findUser(String name);

    //查询所有
    public List<LocalUser> findAllUser();

    //添加用户
    public LocalUser addUser(LocalUser localUser);

    public LocalUser findOne(int id);

    public void deleteUser(int id);
}
