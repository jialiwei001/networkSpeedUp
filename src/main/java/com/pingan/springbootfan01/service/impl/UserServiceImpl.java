package com.pingan.springbootfan01.service.impl;

import com.pingan.springbootfan01.dao.UserDao;
import com.pingan.springbootfan01.entity.LocalUser;
import com.pingan.springbootfan01.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.service
 *  @文件名:   UserServiceImpl
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/28 16:38
 *  @描述：    TODO
 */
@Service
public class UserServiceImpl implements UserService {
    private static final String TAG = "UserServiceImpl";

    @Autowired
    private UserDao mUserDao;

    public LocalUser findUser(String name){

        LocalUser localUser = mUserDao.findByUsername(name);
        System.out.println("service 查询user :" + localUser);

        return localUser;
    }

    public List<LocalUser>  findAllUser(){
        List<LocalUser> allLocalUser = mUserDao.findAll();

        return allLocalUser;
    }

    public LocalUser addUser(LocalUser localUser){
        LocalUser save = mUserDao.save(localUser);
        return save;
    }

    public LocalUser findOne(int id){
        Optional<LocalUser> oneresult = mUserDao.findById(id);

        return oneresult.get();
    }

    public void deleteUser(int id){
        mUserDao.deleteById(id);
    }
}
