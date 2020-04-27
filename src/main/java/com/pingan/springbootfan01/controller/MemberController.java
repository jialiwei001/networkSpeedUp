package com.pingan.springbootfan01.controller;

import com.pingan.springbootfan01.dao.MemberDao;
import com.pingan.springbootfan01.dao.NotesDao;
import com.pingan.springbootfan01.entity.LocalUser;
import com.pingan.springbootfan01.entity.Member;
import com.pingan.springbootfan01.entity.Notes;
import com.pingan.springbootfan01.service.MemberService;
import com.pingan.springbootfan01.service.UserService;
import com.pingan.springbootfan01.util.UserRegister;
import com.pingan.springbootfan01.util.UtilTools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.controller
 *  @文件名:   MemberController
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/28 0:12
 *  @描述：    TODO
 */
@Controller
public class MemberController {
    private static final String TAG = "MemberController";
    Logger  logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService  mUserService;

    @Autowired
    private MemberService mMemberService;

    @Autowired
    private UserRegister mUserRegister;

    @Autowired
    private NotesDao mNotesDao;

    @Autowired
    private MemberDao mMemberDao;


    //删除用户和计费记录
    @GetMapping("/menber/deletemenber/{id}")
    @Transactional
    public String deleteMenberAndNote(@PathVariable("id") int id,Model model){
        Member findresult = mMemberService.findOnebyId(id);
        String result = mUserRegister.AddDays(findresult.getMenberEmail(), -366);
        if (result == null){
//            Notes  byContent  = mNotesDao.findByContent(findresult.getPhonenumber());
//            if (byContent != null){
//                mNotesDao.deleteById(byContent.getId());
//            }
            mMemberDao.deleteById(id);
        }else {
            System.out.println("---MemberController  deleteMenberAndNote  真实user表删除失败");
            model.addAttribute("deletefail","user表删除失败,请重新尝试-"+result);
        }
        return "redirect:/findUserMenbers";
    }

    //分页查询去用户列表页
    @GetMapping("/menber")
    public String menberlist(@RequestParam("username")String username, Model model,@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize){
        LocalUser localUser = mUserService.findUser(username);
        logger.debug("---MemberController menberlist mUserService.findUser result localUser:{}",
                     localUser);

        Page<Member> memberSet = mMemberService.findAll(localUser, pageNum, pageSize);
        model.addAttribute("menbers",memberSet);
        model.addAttribute("user",username);

        return "menber/list";
    }


    //按条件查询用户
    @PostMapping("/findmenbers")
    public String findMenber(@RequestParam("username")String username,  @RequestParam(value = "email",required = false)String email, @RequestParam(value = "phone",required = false)String phone,@RequestParam(value = "typename",required = false) String type,
                             @RequestParam(value = "startdate",required = false)String startdate, @RequestParam(value = "enddate", required = false)String enddate, Model model, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize){
        logger.debug("---MemberController findMenber recive param username:{},type:{},email:{},phone:{},startdate:{},enddate:{}",username,type,email,phone,startdate,enddate);
        LocalUser localUser = mUserService.findUser(username);
        logger.debug("---MemberController menberlist mUserService.findUser result localUser:{}",
                     localUser);

        Page<Member> memberSet = mMemberService.findMenbers(localUser,email,phone,type,startdate,enddate, pageNum, pageSize);
        model.addAttribute("menbers",memberSet);
        model.addAttribute("user",username);

        return "menber/list";
    }

    //查询所有会员用户,可以不输入参数
    @GetMapping("/findUserMenbers")
    public String findUserMenbers(@RequestParam(value = "username",required = false)String username,  @RequestParam(value = "email",required = false)String email, @RequestParam(value = "phone",required = false)String phone,@RequestParam(value = "typename",required = false) String type,
                             @RequestParam(value = "startdate",required = false)String startdate, @RequestParam(value = "enddate", required = false)String enddate, Model model, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize){
        logger.debug("---MemberController findUserMenbers recive param username:{},email:{},phone:{},type:{},startdate:{},enddate:{},pageNum:{},pageSize:{}",username,email,phone,type,startdate,enddate,pageNum,pageSize);
        LocalUser localUser = mUserService.findUser(username);
        logger.debug("---MemberController menberlist mUserService.findUser result localUser:{}",
                     localUser);
        List<LocalUser> allUser = mUserService.findAllUser();
        model.addAttribute("allUser",allUser);
        logger.debug("---查询前的参数为： username:{},email:{},phone:{},type:{},startdate:{},enddate:{},pageNum:{},pageSize:{}",username,email,phone,type,startdate,enddate,pageNum,pageSize);

        Page<Member> memberSet = mMemberService.findMenbers(localUser,email,phone,type,startdate,enddate, pageNum, pageSize);
        model.addAttribute("menbers",memberSet);
        model.addAttribute("user",username);

        return "menber/allmenberlist";
    }

    //查询所有会员用户
    @PostMapping("/findUserMenbers2")
    public String findUserMenbers2(@RequestParam(value = "username",required = false)String username,  @RequestParam(value = "email",required = false)String email, @RequestParam(value = "phone",required = false)String phone,@RequestParam(value = "typename",required = false) String type,
                                  @RequestParam(value = "startdate",required = false)String startdate, @RequestParam(value = "enddate", required = false)String enddate, Model model, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize){
        logger.debug("---MemberController findUserMenbers recive param username:{},email:{},phone:{},type:{},startdate:{},enddate:{},pageNum:{},pageSize:{}",username,email,phone,type,startdate,enddate,pageNum,pageSize);
        LocalUser localUser = mUserService.findUser(username);
        logger.debug("---MemberController menberlist mUserService.findUser result localUser:{}",
                     localUser);
        //查询所有的会员
        List<LocalUser> allUser = mUserService.findAllUser();
        model.addAttribute("allUser",allUser);
        logger.debug("---查询前的参数为： username:{},email:{},phone:{},type:{},startdate:{},enddate:{},pageNum:{},pageSize:{}",username,email,phone,type,startdate,enddate,pageNum,pageSize);

        Page<Member> memberSet = mMemberService.findMenbers(localUser,email,phone,type,startdate,enddate, pageNum, pageSize);
        model.addAttribute("menbers",memberSet);
        model.addAttribute("user",username);

        return "menber/allmenberlist";
    }


    //创建卡
    @PostMapping("/localUser/createMonth")
    @ResponseBody
    @Transactional
    public String createMonthMember(String username,String phone,String type,@RequestParam(value = "number",required = false,defaultValue = "0") int number){
        logger.debug("---MemberController  createMonthMember rceive param username:{},phone:{},type:{}",username,phone,type);
        //先设置他的user
        LocalUser localUser = mUserService.findUser(username);
        if (localUser == null){
            return "会员不存在,请重新登录";
        }
        //手机号重复不能创建
        String phone1 = phone.trim();
        Member onebyPhone = mMemberService.findOnebyPhone(phone1);
        if (onebyPhone != null){
            return "该用户已经创建，请勿重复创建";
        }
        Notes notes = new Notes();
        notes.setLocalUser(localUser);
        int days = 0;
        if (type.equals("月卡")){
            days = 30;
            notes.setType("月卡");
            notes.setNumber(1);
            notes.setContent(phone1);
        }else if (type.equals("季卡")){
            days = 91;
            notes.setType("季卡");
            notes.setNumber(1);
            notes.setContent(phone1);
        }else if (type.equals("半年卡")){
            days = 182;
            notes.setType("半年卡");
            notes.setNumber(1);
            notes.setContent(phone1);
        }else if (type.equals("年卡")){
            days = 365;
            notes.setType("年卡");
            notes.setNumber(1);
            notes.setContent(phone1);
        }else if (type.equals("试用卡")){
            days = number;
            notes.setType("试用卡");
            notes.setNumber(number);
            notes.setContent(phone1);
        }else {
            return "卡类型匹配错误，请联系工作人员";
        }
        //String phone1 = phone;
        String emial = UtilTools.autoEmail();
        String name = UtilTools.autoMenberName();
        String chatNumber = UtilTools.autoChatNumber();
        //String createResult = UtilTools.createUser(emial, name, chatNumber, days);
        String createResult = mUserRegister.regist(emial, name, chatNumber, days);
        if (!createResult.equals("1")){
            //创建用户
            Member member = new Member();
            //设置用户名
            member.setUsername(name);
            //设置密码
            member.setPassword("abcd1234");
            //设置邮箱
            member.setMenberEmail(emial);
            //设置手机
            member.setPhonenumber(phone1);
            //设置订阅地址
            member.setSubUrl(createResult);
            //设置类型
            member.setType(type);
            //设置数量
            member.setAmount(0);
            //设置当前时间
            //获取当前时间
            Date cureTime = new Date();
            member.setStarttime(cureTime);
            //设置结束时间
            //时间加一个月
            Calendar instance = Calendar.getInstance();
            instance.setTime(cureTime);
            instance.add(Calendar.DAY_OF_MONTH,days);
            member.setEndtime(instance.getTime());
            member.setLocalUser(localUser);
            Member addResult = mMemberService.addMember(member);
            notes.setCreateTime(new Date());
            mNotesDao.save(notes);
            return addResult.toString2();

        }else {
            return "创建失败，请重新创建";
        }
    }

    //去续费页面
    @GetMapping("/menberaddtime/{id}")
    public String menberaddtime(@PathVariable("id") int id, Model model){

        Member oneMenber1 = mMemberService.findOnebyId(id);
        model.addAttribute("oneMenber",oneMenber1);

        return "menber/addtime";
    }




    //续费方法
    @PostMapping("/menber/addtime")
    @ResponseBody
    @Transactional
    public String addTime(String username,String phone,String type,Map<String,Object> map)
            throws InterruptedException
    {
        logger.debug("---MemberController  addTime rceive param username:{}, menberName:{},days:{}",username,phone,type);
        LocalUser localUser = mUserService.findUser(username);
        if (localUser == null){
            return "会员不存在,请重新登录";
        }
        //查找用户
        Member oneMenber = mMemberService.findOnebyPhone(phone);
        if (oneMenber == null){
            return "查找用户失败,请重新尝试";
        }
        Notes notes = new Notes();
        notes.setLocalUser(localUser);
        int days = 0;
        if (type.equals("续费月卡")){
            days = 30;
            notes.setType("续费月卡");
            notes.setNumber(1);
            notes.setContent(phone);
        }else if (type.equals("续费季卡")){
            days = 91;
            notes.setType("续费季卡");
            notes.setNumber(1);
            notes.setContent(phone);
        }else if (type.equals("续费半年卡")){
            days = 182;
            notes.setType("续费半年卡");
            notes.setNumber(1);
            notes.setContent(phone);
        }else if (type.equals("续费年卡")){
            days = 365;
            notes.setType("续费年卡");
            notes.setNumber(1);
            notes.setContent(phone);
        }else {
            return "时间未配置上，请联系工作人员";
        }
        //String result = UtilTools.addtime1(oneMenber.getPhonenumber(), days);
        String result  = mUserRegister.AddDays(oneMenber.getMenberEmail(), days);
        if (result != null){         //result != null
            return "续费失败，请重新续费";
        }
        Date endtime = oneMenber.getEndtime();
        //延长时间
        Calendar instance = Calendar.getInstance();
        instance.setTime(endtime);
        instance.add(Calendar.DAY_OF_MONTH,days);
        //设置结束时间
        oneMenber.setEndtime(instance.getTime());

        Member addResult = mMemberService.addMember(oneMenber);
        oneMenber.setAmount(oneMenber.getAmount()+1);
        notes.setCreateTime(new Date());
        mNotesDao.save(notes);
        return "续费成功~~~";

    }
//
//    //创建季卡
//    @PostMapping("/localUser/createQuarter")
//    @ResponseBody
//    public String createQuarterMember(String username,String phone){
//        logger.debug("---MemberController  createQuarterMember rceive param username:{},phone:{}",username,phone);
//        //先设置他的user
//        LocalUser localUser = mUserService.findUser(username);
//
//        Member member = new Member();
//        member.setUsername(phone);
//        member.setPassword("12345678");
//        member.setMenberEmail(UtilTools.autoEmail());
//        member.setPhonenumber(phone);
//        member.setAmount(1);
//        member.setType("季卡");
//        //获取当前时间
//        Date cureTime = new Date();
//        member.setStarttime(cureTime);
//        //时间加一个月
//        Calendar instance = Calendar.getInstance();
//        instance.setTime(cureTime);
//        instance.add(Calendar.MONTH,3);
//        member.setEndtime(instance.getTime());
//        member.setLocalUser(localUser);
//
//        Member addResult = mMemberService.addMember(member);
//        return addResult.toString2();
//
//    }
//
//    //创建季卡
//    @PostMapping("/localUser/createHalfYear")
//    @ResponseBody
//    public String createHalfYear(String username,String phone){
//        logger.debug("---MemberController  createYearMember rceive param username:{},phone:{}",username,phone);
//        //先设置他的user
//        LocalUser localUser = mUserService.findUser(username);
//
//        Member member = new Member();
//        member.setUsername(phone);
//        member.setPassword("123456");
//        member.setMenberEmail(UtilTools.autoEmail());
//        member.setPhonenumber(phone);
//        member.setAmount(1);
//        member.setType("半年卡");
//        //获取当前时间
//        Date cureTime = new Date();
//        member.setStarttime(cureTime);
//        //时间加一个月
//        Calendar instance = Calendar.getInstance();
//        instance.setTime(cureTime);
//        instance.add(Calendar.MONTH,6);
//        member.setEndtime(instance.getTime());
//        member.setLocalUser(localUser);
//
//        Member addResult = mMemberService.addMember(member);
//        return addResult.toString2();
//
//    }
//
//    //创建季卡
//    @PostMapping("/localUser/createYear")
//    @ResponseBody
//    public String createYearMember(String username,String phone){
//        logger.debug("---MemberController  createYearMember rceive param username:{},phone:{}",username,phone);
//        //先设置他的user
//        LocalUser localUser = mUserService.findUser(username);
//
//        Member member = new Member();
//        member.setUsername(phone);
//        member.setPassword("123456");
//        member.setMenberEmail(UtilTools.autoEmail());
//        member.setPhonenumber(phone);
//        member.setAmount(1);
//        member.setType("年卡");
//        //获取当前时间
//        Date cureTime = new Date();
//        member.setStarttime(cureTime);
//        //时间加一个月
//        Calendar instance = Calendar.getInstance();
//        instance.setTime(cureTime);
//        instance.add(Calendar.YEAR,1);
//        member.setEndtime(instance.getTime());
//        member.setLocalUser(localUser);
//
//        Member addResult = mMemberService.addMember(member);
//        return addResult.toString2();
//
//    }




}
