package com.pingan.springbootfan01.dao;

import com.pingan.springbootfan01.entity.LocalUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.dao
 *  @文件名:   UserDao
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/27 13:58
 *  @描述：    TODO
 */
@Repository
public interface UserDao extends JpaRepository<LocalUser,Integer> {

    public LocalUser findByUsername(String name);
}
