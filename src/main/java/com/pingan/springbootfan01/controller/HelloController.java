package com.pingan.springbootfan01.controller;

import com.pingan.springbootfan01.exception.UserNotExistException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.controller
 *  @文件名:   HelloController
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/28 0:59
 *  @描述：    TODO
 */
@Controller
public class HelloController {
    private static final String TAG = "HelloController";

//    @GetMapping("/login")
//    public String login()
//    {
//
//        return "login";
//    }

    @GetMapping("/hello")
    @ResponseBody
    public String testhello(@RequestParam("user") String user)
    {
        if (user.equals("aaa")){

            throw new UserNotExistException();
        }

        return "helloworld";
    }

    @RequestMapping("/seccess")
    public String success(Map<String,Object> map){
        map.put("hello","<h1>欢迎来到首页</h1>");
        map.put("users", Arrays.asList("zhangsan","lisi","wangwu"));
        return "success";
    }
}
