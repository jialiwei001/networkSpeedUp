package com.pingan.springbootfan01.exception;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.exception
 *  @文件名:   UserNotExistException
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/28 11:25
 *  @描述：    TODO
 */
public class UserNotExistException extends RuntimeException {
    private static final String TAG = "UserNotExistException";

    public UserNotExistException(){
        super("用户不存在");
    }
}
