package com.pingan.springbootfan01.controller;

import com.pingan.springbootfan01.dao.NotesDao;
import com.pingan.springbootfan01.entity.LocalUser;
import com.pingan.springbootfan01.entity.Notes;
import com.pingan.springbootfan01.service.NotesService;
import com.pingan.springbootfan01.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.controller
 *  @文件名:   UserController
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/28 0:12
 *  @描述：    TODO
 */
@Controller
public class UserController {
    private static final String TAG = "UserController";


    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService  mUserService;

    @Autowired
    private NotesDao mNotesDao;

    @Autowired
    private NotesService mNotesService;

    @GetMapping("/")
    public String index(){
        return "login1";
    }

    @GetMapping("/sginsout")
    public String sginout(){
        return "login1";
    }

    @PostMapping("/localUser/login")
    public String loginuser(@RequestParam("username") String username, @RequestParam("password") String password,
                            Map<String,Object> map , HttpSession session){
        logger.debug("---UserController loginuser method param is username:{}  password:{}",username,password);
        //查询用户
        LocalUser localUser = mUserService.findUser(username);
        logger.debug("---mUserService.findUser method result is localUser:{}", localUser);
        //比较密码是否相等
        if (localUser == null){
            map.put("loginResult","用户不存在");
            return "login1";
        }else if (localUser.getPassword().equals(password)){
            session.setAttribute("loginUser",username);
            map.put("loginUser2",username);
            return "redirect:/main";
        }else {
            map.put("loginResult","密码错误");
            return "login1";
        }

    }

    @GetMapping("/main")
    public String mainPage(){
        return "dashboard";
    }

    @GetMapping("/main2")
    public String mainPage2(){
        return "dashboard";
    }
    //去会员页面
    @GetMapping("/super")
    public String superMenber(Model model){

        List<LocalUser> allLocalUser = mUserService.findAllUser();
        logger.debug("---UserController superMenber mUserService.findAllUser() result: allLocalUser size :{}",
                     allLocalUser.size());

        model.addAttribute("users", allLocalUser);

        return "supermenber/list";
    }



    //去添加页面
    @GetMapping("/adduser")
    public String addUser(){
        return "supermenber/add";
    }

    //去修改页面
    @GetMapping("/edituser/{id}")
    public String editUser(@PathVariable("id") int id,Model model){

        LocalUser one = mUserService.findOne(id);
        model.addAttribute("oneUser",one);

        return "supermenber/add";
    }
    //添加会员
    @PostMapping("/createuser")
    public String createuser(LocalUser localUser){
        logger.debug("---UserController createuser receive param localUser:{}", localUser);
        localUser.setCreatetime(new Date());
        LocalUser localUser1 = mUserService.addUser(localUser);
        return "redirect:/super";
    }

    @GetMapping("/delete/{id}")
    public String delUser(@PathVariable("id") int id){
        mUserService.deleteUser(id);
        return "redirect:/super";
    }

    @GetMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable("id") int id){
        mNotesDao.deleteById(id);
        return "redirect:/report";
    }

    @PostMapping("/notes/deleteAllNote")
    public String deleteAllNote(@RequestParam("username") String username){
        logger.debug("---UserController deleteAllNote param username:{}",username);
        LocalUser user = mUserService.findUser(username);


        if (user != null){
            String phone = "";
            String start = "";
            String end = "";
            List<Notes> notes = mNotesService.ifFindAllNote(user,phone,start,end);
            for (Notes note : notes){
                mNotesDao.deleteById(note.getId());
            }
        }else {
            logger.debug("---UserController deleteAllNote 删除异常，未找到user用户");
        }
        return "redirect:/report";
    }

    @GetMapping("/report")
    public String goReport(@RequestParam(name = "username",required = false) String username,@RequestParam(name = "phone",required = false)String phone ,@RequestParam(name = "start",required = false)String start,@RequestParam(name = "end",required = false)String end,Model model){
        logger.debug("---UserController goReport param username:{},phone:{},start:{},end:{}",username,phone,start,end);

        LocalUser user = null;
        if (username != "" && username != null){
            user = mUserService.findUser(username);
        }
        //查询所有的会员
        List<LocalUser> allUser = mUserService.findAllUser();
        model.addAttribute("allUser",allUser);
        //查询所有的记录
        List<Notes> allNotes = mNotesService.ifFindAllNote(user,phone,start,end);
        model.addAttribute("allNotes",allNotes);
        int testcard = 0;
        int month = 0;
        int quarter = 0;
        int half = 0;
        int year = 0;
        int xumonth = 0;
        int xuquarter = 0;
        int xuhalf = 0;
        int xuyear = 0;
        for (Notes notes : allNotes){
            if (notes.getType().equals("试用卡")){
                testcard += notes.getNumber();
            }else if (notes.getType().equals("月卡")){
                month += 1;
            }else if (notes.getType().equals("季卡")){
                quarter += 1;
            }else if (notes.getType().equals("半年卡")){
                half += 1;
            }else if (notes.getType().equals("年卡")){
                year += 1;
            }else if (notes.getType().equals("续费月卡")){
                xumonth += 1;
            }else if (notes.getType().equals("续费季卡")){
                xuquarter += 1;
            }else if (notes.getType().equals("续费半年卡")){
                xuhalf += 1;
            }else if (notes.getType().equals("续费年卡")){
                xuyear += 1;
            }else {

            }
        }
        model.addAttribute("testcard",testcard);
        model.addAttribute("month",month);
        model.addAttribute("quarter",quarter);
        model.addAttribute("half",half);
        model.addAttribute("year",year);
        model.addAttribute("xumonth",xumonth);
        model.addAttribute("xuquarter",xuquarter);
        model.addAttribute("xuhalf",xuhalf);
        model.addAttribute("xuyear",xuyear);
        return "count/countlist";
    }

    @GetMapping("/user/report")
    public String userGoReport(@RequestParam(name = "username") String username,@RequestParam(name = "phone",required = false)String phone ,@RequestParam(name = "start",required = false)String start,@RequestParam(name = "end",required = false)String end,Model model){
        logger.debug("---UserController goReport param username:{},phone:{},start:{},end:{}",username,phone,start,end);

        LocalUser user =mUserService.findUser(username);
        model.addAttribute("userself",user.getUsername());

        //查询所有的记录
        List<Notes> allNotes = mNotesService.ifFindAllNote(user,phone,start,end);
        model.addAttribute("allNotes",allNotes);
        int testcard = 0;
        int month = 0;
        int quarter = 0;
        int half = 0;
        int year = 0;
        int xumonth = 0;
        int xuquarter = 0;
        int xuhalf = 0;
        int xuyear = 0;
        for (Notes notes : allNotes){
            if (notes.getType().equals("试用卡")){
                testcard += notes.getNumber();
            }else if (notes.getType().equals("月卡")){
                month += 1;
            }else if (notes.getType().equals("季卡")){
                quarter += 1;
            }else if (notes.getType().equals("半年卡")){
                half += 1;
            }else if (notes.getType().equals("年卡")){
                year += 1;
            }else if (notes.getType().equals("续费月卡")){
                xumonth += 1;
            }else if (notes.getType().equals("续费季卡")){
                xuquarter += 1;
            }else if (notes.getType().equals("续费半年卡")){
                xuhalf += 1;
            }else if (notes.getType().equals("续费年卡")){
                xuyear += 1;
            }else {

            }
        }
        model.addAttribute("testcard",testcard);
        model.addAttribute("month",month);
        model.addAttribute("quarter",quarter);
        model.addAttribute("half",half);
        model.addAttribute("year",year);
        model.addAttribute("xumonth",xumonth);
        model.addAttribute("xuquarter",xuquarter);
        model.addAttribute("xuhalf",xuhalf);
        model.addAttribute("xuyear",xuyear);
        return "count/usercountlist";
    }

}
