package com.pingan.springbootfan01.controller;

import com.pingan.springbootfan01.common.LocalLock;
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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        String result = mUserRegister.AddDays(findresult.getMenberEmail(), -366,true);
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
    public String menberlist(@RequestParam("username")String username, Model model,@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "100") int pageSize){
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
    public String findMenber(@RequestParam("username")String username,
                             @RequestParam(value = "urlAdress",required = false)String urlAdress,
                             @RequestParam(value = "phone",required = false)String phone,
                             @RequestParam(value = "email",required = false)String email,
                             @RequestParam(value = "sortOrder",required = false)String sortOrder,
                             @RequestParam(value = "typename",required = false) String typename,
                             @RequestParam(value = "startdate",required = false)String startdate,
                             @RequestParam(value = "enddate", required = false)String enddate,
                             Model model,
                             @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "100") int pageSize){
        logger.debug("---MemberController findMenber recive param username:{},type:{},urlAdress:{},phone:{},email:{},sortOrder:{},startdate:{},enddate:{}",username,typename,urlAdress,phone,email,sortOrder,startdate,enddate);
        LocalUser localUser = mUserService.findUser(username);
        logger.debug("---MemberController menberlist mUserService.findUser result localUser:{}",
                     localUser);

        String phone1 = "";
        if (phone != "" && phone != null){
            phone1 = phone.trim();
        }
        String email1 = "";
        if (email != "" && email != null){
            email1 = email.trim();
        }
        String urlAdress1 = "";
        if (urlAdress != "" && urlAdress != null){
            urlAdress1 = urlAdress.trim();
        }
        Page<Member> memberSet = mMemberService.findMenbers(localUser,urlAdress1,phone1,email1,sortOrder,typename,startdate,enddate, pageNum, pageSize);
        model.addAttribute("menbers",memberSet);
        model.addAttribute("user",username);
        model.addAttribute("phone",phone1);
        model.addAttribute("email",email1);
        model.addAttribute("sort",sortOrder);
        model.addAttribute("type",typename);
        model.addAttribute("starttime",startdate);
        model.addAttribute("endtime",enddate);

        return "menber/list";
    }

    //按条件查询，点击页码分页查询
    @GetMapping("/findmenbers2")
    public String findMenber2(@RequestParam("username")String username,
                              @RequestParam(value = "urlAdress",required = false)String urlAdress,
                              @RequestParam(value = "phone",required = false)String phone,
                              @RequestParam(value = "email",required = false)String email,
                              @RequestParam(value = "sortOrder",required = false)String sortOrder,
                              @RequestParam(value = "typename",required = false) String typename,
                             @RequestParam(value = "startdate",required = false)String startdate,
                              @RequestParam(value = "enddate", required = false)String enddate,
                              Model model,
                              @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "100") int pageSize){
        logger.debug("---MemberController findMenber recive param username:{},type:{},urlAdress:{},phone:{},email:{},sortOrder:{},startdate:{},enddate:{}",username,typename,urlAdress,phone,email,sortOrder,startdate,enddate);
        LocalUser localUser = mUserService.findUser(username);
        logger.debug("---MemberController menberlist mUserService.findUser result localUser:{}",
                     localUser);

        String phone1 = "";
        if (phone != "" && phone != null && !phone.equalsIgnoreCase("null")){
            phone1 = phone.trim();
        }
        String email1 = "";
        if (email != "" && email != null){
            email1 = email.trim();
        }
        String urlAdress1 = "";
        if (urlAdress != "" && urlAdress != null){
            urlAdress1 = urlAdress.trim();
        }
        Page<Member> memberSet = mMemberService.findMenbers(localUser,urlAdress1,phone1,email1,sortOrder,typename,startdate,enddate, pageNum, pageSize);
        model.addAttribute("menbers",memberSet);
        model.addAttribute("user",username);
        model.addAttribute("phone",phone1);
        model.addAttribute("email",email1);
        model.addAttribute("sort",sortOrder);
        model.addAttribute("type",typename);
        model.addAttribute("starttime",startdate);
        model.addAttribute("endtime",enddate);

        return "menber/list";
    }

    //查询所有会员用户,可以不输入参数
    @GetMapping("/findUserMenbers")
    public String findUserMenbers(@RequestParam(value = "username",required = false)String username,
                                  @RequestParam(value = "urlAdress",required = false)String urlAdress,
                                  @RequestParam(value = "phone",required = false)String phone,
                                  @RequestParam(value = "email",required = false)String email,
                                  @RequestParam(value = "sortOrder",required = false)String sortOrder,
                                  @RequestParam(value = "typename",required = false) String typename,
                                  @RequestParam(value = "startdate",required = false)String startdate,
                                  @RequestParam(value = "enddate", required = false)String enddate,
                                  Model model,
                                  @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "100") int pageSize){
        logger.debug("---MemberController findUserMenbers recive param username:{},urlAdress:{},phone:{},email:{},sortOrder:{},type:{},startdate:{},enddate:{},pageNum:{},pageSize:{}",username,urlAdress,phone,email,sortOrder,typename,startdate,enddate,pageNum,pageSize);
        LocalUser localUser = mUserService.findUser(username);
        logger.debug("---MemberController menberlist mUserService.findUser result localUser:{}",
                     localUser);
        List<LocalUser> allUser = mUserService.findAllUser();
        model.addAttribute("allUser",allUser);
        logger.debug("---查询前的参数为： username:{},urlAdress:{},phone:{},email:{},type:{},startdate:{},enddate:{},pageNum:{},pageSize:{}",username,urlAdress,phone,email,typename,startdate,enddate,pageNum,pageSize);
        String phone1 = "";
        if (phone != "" && phone != null){
            phone1 = phone.trim();
        }
        String email1 = "";
        if (email != "" && email != null){
            email1 = email.trim();
        }
        String urlAdress1 = "";
        if (urlAdress != "" && urlAdress != null){
            urlAdress1 = urlAdress.trim();
        }
        Page<Member> memberSet = mMemberService.findMenbers(localUser,urlAdress1,phone1,email1,sortOrder,typename,startdate,enddate, pageNum, pageSize);
        model.addAttribute("menbers",memberSet);
        model.addAttribute("user",username);
        model.addAttribute("phone",phone1);
        model.addAttribute("email",email1);
        model.addAttribute("sort",sortOrder);
        model.addAttribute("type",typename);
        model.addAttribute("starttime",startdate);
        model.addAttribute("endtime",enddate);

        return "menber/allmenberlist";
    }

    //查询所有会员用户
    @PostMapping("/findUserMenbers2")
    public String findUserMenbers2(@RequestParam(value = "username",required = false)String username,
                                   @RequestParam(value = "urlAdress",required = false)String urlAdress,
                                   @RequestParam(value = "phone",required = false)String phone,
                                   @RequestParam(value = "email",required = false)String email,
                                   @RequestParam(value = "sortOrder",required = false)String sortOrder,
                                   @RequestParam(value = "typename",required = false) String typename,
                                  @RequestParam(value = "startdate",required = false)String startdate,
                                   @RequestParam(value = "enddate", required = false)String enddate,
                                   Model model,
                                   @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "100") int pageSize){
        logger.debug("---MemberController findUserMenbers2 recive param username:{},urlAdress:{},phone:{},email:{},sortOrder:{},type:{},startdate:{},enddate:{},pageNum:{},pageSize:{}",username,urlAdress,phone,email,sortOrder,typename,startdate,enddate,pageNum,pageSize);
        LocalUser localUser = mUserService.findUser(username);
        logger.debug("---MemberController findUserMenbers2 menberlist mUserService.findUser result localUser:{}",
                     localUser);
        //查询所有的会员
        List<LocalUser> allUser = mUserService.findAllUser();
        model.addAttribute("allUser",allUser);
        logger.debug("---查询前的参数为： username:{},urlAdress:{},phone:{},email:{},sortOrder:{},type:{},startdate:{},enddate:{},pageNum:{},pageSize:{}",username,urlAdress,phone,email,sortOrder,typename,startdate,enddate,pageNum,pageSize);

        String phone1 = "";
        if (phone != "" && phone != null){
            phone1 = phone.trim();
        }
        String email1 = "";
        if (email != "" && email != null){
            email1 = email.trim();
        }
        String urlAdress1 = "";
        if (urlAdress != "" && urlAdress != null){
            urlAdress1 = urlAdress.trim();
        }
        Page<Member> memberSet = mMemberService.findMenbers(localUser,urlAdress1,phone1,email,sortOrder,typename,startdate,enddate, pageNum, pageSize);
        model.addAttribute("menbers",memberSet);
        model.addAttribute("user",username);
        model.addAttribute("phone",phone1);
        model.addAttribute("email",email1);
        model.addAttribute("sort",sortOrder);
        model.addAttribute("type",typename);
        model.addAttribute("starttime",startdate);
        model.addAttribute("endtime",enddate);

        return "menber/allmenberlist";
    }


    //通过账号或订阅地址删除用户和计费记录
    @PostMapping("/menber/deleteMemberByUser")
    @ResponseBody
    @Transactional
    public String deleteMemberByUser(String username){
        String phone1 = "";
        Member findMember = null;
        String resultData = "";
        if (username != "" && username != null){
            phone1 = username;
            if (phone1.length() > 30){
                if (mMemberDao.findBySubUrl(phone1) != null){
                    findMember = mMemberDao.findBySubUrl(phone1);
                }else {
                    return "没有查到用户,请重新输入";
                }

            }else {
                findMember = mMemberDao.findByPhonenumber(phone1);
                if (mMemberDao.findByPhonenumber(phone1) != null){
                    findMember = mMemberDao.findByPhonenumber(phone1);
                }else {
                    return "没有查到用户,请重新输入";
                }
            }
        }
        if (findMember != null){
            String result = mUserRegister.AddDays(findMember.getMenberEmail(), -366,true);
            if (result == null){
//            Notes  byContent  = mNotesDao.findByContent(findresult.getPhonenumber());
//            if (byContent != null){
//                mNotesDao.deleteById(byContent.getId());
//            }
                mMemberDao.deleteById(findMember.getId());
                resultData = "成功删除用户：【"+findMember.getPhonenumber()+"】";
            }else {
                resultData = "删除用户失败，请查询原因";
                System.out.println("---MemberController  deleteMenberAndNote  查询泰坦星user表失败,删除失败");
            }
        }
        return resultData;
    }

    //创建卡
    @LocalLock(key = "book:arg[1]")
    @PostMapping("/localUser/createMonth")
    @ResponseBody
    @Transactional
    public String createMonthMember(String username,String phone,String type,@RequestParam(value = "number",required = false,defaultValue = "0") int number) throws InterruptedException{
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
        String type1 = type;
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
            if (number >= 29 && number < 32){
                notes.setType("月卡");
                type1 ="月卡";
            }else if (number >= 32 && number < 92){
                notes.setType("季卡");
                type1 ="季卡";
            }else if (number >= 92 && number < 183){
                notes.setType("半年卡");
                type1 ="半年卡";
            }else if (number >= 183 && number < 366){
                notes.setType("年卡");
                type1 ="年卡";
            }else if (number < 29){
                notes.setType("试用卡");
                type1 ="试用卡";
            }else {
                notes.setType("异常卡");
                type1 ="异常卡";
            }
            notes.setNumber(number);
            notes.setContent(phone1);
        }else {
            return "卡类型匹配错误，请联系工作人员";
        }
        //String phone1 = phone;
        String email = phone+"@qq.com";
        //String emial = UtilTools.autoEmail();
        String name = UtilTools.autoMenberName();
        String chatNumber = UtilTools.autoChatNumber();
        //String createResult = UtilTools.createUser(emial, name, chatNumber, days);
        String createResult = mUserRegister.regist(email, name, chatNumber, days);
        if (!createResult.equals("1")){
            //创建用户
            Member member = new Member();
            //设置用户名
            member.setUsername(name);
            //设置密码
            member.setPassword("abcd1234");
            //设置邮箱
            member.setMenberEmail(email);
            //设置手机
            member.setPhonenumber(phone1);
            //设置订阅地址
            member.setSubUrl(createResult);
            //设置类型
            member.setType(type1);
            //设置数量
            member.setAmount(0);
            //查询数据库并设置总流量
            String allTotal = mUserRegister.DataUsageForAllTotal(email);
            if (allTotal.length() > 20){
                member.setU("创建失败");
            }else {
                member.setU(allTotal);
            }
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

    //操作剪切板的方法
    public static void setClipboardString(String text) {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(text);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }


    //去续费页面
    @GetMapping("/menberaddtime/{id}")
    public String menberaddtime(@PathVariable("id") int id, Model model){

        Member oneMenber1 = mMemberService.findOnebyId(id);
        model.addAttribute("oneMenber",oneMenber1);

        return "menber/addtime";
    }

    @PostMapping("/menber/findPhone")
    @ResponseBody
    public String findPhone(String phone){
        String phone1 = "";
        String result = null;
        Member findMember = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (phone != "" && phone != null){
            phone1 = phone;
            if (phone1.length() > 30){
                findMember = mMemberDao.findBySubUrl(phone1);
                String endDate = sdf.format(findMember.getEndtime());
                String allTotal = findMember.getU();
                if (findMember != null){
                    result = "你的账户名是："+findMember.getPhonenumber();
                    return result+"\r\n"+"到期时间:【"+endDate+"】"+"\r\n"+"总流量为：【"+allTotal+"】";
                }
            }else {
                findMember = mMemberDao.findByPhonenumber(phone1);
                String endDate = sdf.format(findMember.getEndtime());
                String allTotal = findMember.getU();
                if (findMember != null){
                    result = "你的订阅地址是："+findMember.getSubUrl();
                    return result+"\r\n"+"到期时间:【"+endDate+"】"+"\r\n"+"总流量为：【"+allTotal+"】";
                }
            }
        }
        return "未查到用户信息，请重新输入···";
    }

    //普通续费方法
    @LocalLock(key = "book:arg[1]")
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
        String phone1 = phone.trim();
        Member oneMenber = null;
        if (phone1.length() > 30){
            oneMenber = mMemberDao.findBySubUrl(phone1);
            if (oneMenber == null){
                return "查找订阅地址失败,请输入正确的订阅地址";
            }
        }else {
            oneMenber = mMemberService.findOnebyPhone(phone1);
            if (oneMenber == null){
                return "查找用户失败,请输入正确用户";
            }
        }
        String typeName = "";
        Notes notes = new Notes();
        notes.setLocalUser(localUser);
        int days = 0;
        if (type.equals("续费月卡")){
            days = 30;
            notes.setType("续费月卡");
            notes.setNumber(1);
            notes.setContent(oneMenber.getPhonenumber());
            typeName = "月卡";
        }else if (type.equals("续费季卡")){
            days = 91;
            notes.setType("续费季卡");
            notes.setNumber(1);
            notes.setContent(oneMenber.getPhonenumber());
            typeName = "季卡";
        }else if (type.equals("续费半年卡")){
            days = 182;
            notes.setType("续费半年卡");
            notes.setNumber(1);
            notes.setContent(oneMenber.getPhonenumber());
            typeName = "半年卡";
        }else if (type.equals("续费年卡")){
            days = 365;
            notes.setType("续费年卡");
            notes.setNumber(1);
            notes.setContent(oneMenber.getPhonenumber());
            typeName = "年卡";
        }else {
            return "时间未配置上，请联系工作人员";
        }
        boolean flag = true;

        Date endtime = oneMenber.getEndtime();
        //延长时间
        Calendar instance = Calendar.getInstance();
        instance.setTime(endtime);
        //判断用户到期时间是否小于当前时间，如果小于，从当前时间开始计算添加时间
        Calendar crruentTime = Calendar.getInstance();
        crruentTime.setTime(new Date());
        if (instance.before(crruentTime)){
            instance.setTime(new Date());
            flag = false;
        }

        //String result = UtilTools.addtime1(oneMenber.getPhonenumber(), days);
        String result  = mUserRegister.AddDays(oneMenber.getMenberEmail(), days,flag);
        if (result != null){         //result != null
            return "续费失败，请重新续费";
        }

        //查询用户在泰坦星中的过期时间
        String endTime  = mUserRegister.findMemberEndTime(oneMenber.getMenberEmail());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date formatEndTime = dateFormat.parse(endTime);
            //设置结束时间
            oneMenber.setEndtime(formatEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //instance.add(Calendar.DAY_OF_MONTH,days);
        //续费后更改卡类型
        UtilTools.fixUserType(oneMenber);
        oneMenber.setAmount(oneMenber.getAmount()+1);
        Member addResult = mMemberService.addMember(oneMenber);
        notes.setCreateTime(new Date());
        mNotesDao.save(notes);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = sdf.format(addResult.getEndtime());

        return "账户:【"+oneMenber.getPhonenumber() +"】成功续费【"+typeName+"】" +"~~"+"\n"+"下次到期是:【"+endDate+"】"+"~~";

    }



    //admin按天续费方法
    @LocalLock(key = "book:arg[1]")
    @PostMapping("/menber/addtimeDay")
    @ResponseBody
    @Transactional
    public String addTimeDay(String username,String phone,@RequestParam(value = "time",required = false,defaultValue = "0")int time,Map<String,Object> map)
            throws InterruptedException
    {
        logger.debug("---MemberController  addTime rceive param username:{}, phone:{},days:{}",username,phone,time);
        LocalUser localUser = mUserService.findUser(username);
        if (localUser == null){
            return "会员不存在,请重新登录";
        }
        if (!localUser.getUsername().equals("admin")){
            return "会员不是admin账号，无权加时间";
        }
        //查找用户
        String phone1 = phone.trim();
        Member oneMenber = null;
        if (phone1.length() > 20){
            oneMenber = mMemberDao.findBySubUrl(phone1);
            if (oneMenber == null){
                return "查找订阅地址失败,请输入正确的订阅地址";
            }
        }else {
            oneMenber = mMemberService.findOnebyPhone(phone1);
            if (oneMenber == null){
                return "查找用户失败,请输入正确用户";
            }
        }
        if (time == 0 || time > 1000){
            return "请输入正确天数";
        }
        /*if (!UtilTools.isNumeric(time)){
            return "收到天数为小数，请输入整数";
        }*/
        Notes notes = new Notes();
        notes.setLocalUser(localUser);
        int days = 0;
        days = time;
        notes.setType("续时【"+time+"】天");
        notes.setNumber(1);
        notes.setContent(phone);

        boolean flag = true;

        Date endtime = oneMenber.getEndtime();
        //延长时间
        Calendar instance = Calendar.getInstance();
        instance.setTime(endtime);
        //判断用户到期时间是否小于当前时间，如果小于，从当前时间开始计算添加时间
        Calendar crruentTime = Calendar.getInstance();
        crruentTime.setTime(new Date());
        if (instance.before(crruentTime)){
            instance.setTime(new Date());
            flag = false;
        }

        //String result = UtilTools.addtime1(oneMenber.getPhonenumber(), days);
        String result  = mUserRegister.AddDays(oneMenber.getMenberEmail(), days,flag);
        if (result != null){         //result != null
            return "续费失败，请重新续费";
        }
        //查询用户在泰坦星中的过期时间
        String endTime  = mUserRegister.findMemberEndTime(oneMenber.getMenberEmail());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date formatEndTime = dateFormat.parse(endTime);
            //设置结束时间
            oneMenber.setEndtime(formatEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //instance.add(Calendar.DAY_OF_MONTH,days);
        //续费后更改卡类型
        UtilTools.fixUserType(oneMenber);
        oneMenber.setAmount(oneMenber.getAmount()+1);
        Member addResult = mMemberService.addMember(oneMenber);
        notes.setCreateTime(new Date());
        mNotesDao.save(notes);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = sdf.format(addResult.getEndtime());
        return "账户:【"+oneMenber.getPhonenumber() +"】成功续时"+"【"+time+"天】"+"~~"+"\n"+"下次到期是:【"+endDate+"】"+"~~";

    }


    @PostMapping("/menber/editInfo")
    @ResponseBody
    public String editInfo(Integer id,String phone,String endTime,String speed,Model model,String totalData)
    {
        logger.debug("---MemberController editInfo rceive param id:{}, phone:{},endTime:{},speed:{},totalData:{}",
                     id,
                     phone,
                     endTime,
                     speed,
                     totalData);
        String fixPhone = "";
        String fixData = "";
        String fixSpeed = "";
        String fixEndTime = "";
        //查找用户
        Member oneMenber = mMemberService.findOnebyId(id);
        if (oneMenber == null) {
            return "没有查找到用户";
        }
        if (!oneMenber.getPhonenumber().equals(phone)){
            Member oneMenber2 = mMemberService.findOnebyPhone(phone);
            if (oneMenber2  == null){
                String phone1 = "";
                if (phone != "" && phone != null){
                    phone1 = phone.trim();
                    oneMenber.setPhonenumber(phone1);
                    fixPhone = "【手机号】";
                }
            }else {
                logger.debug("---MemberController editInfo 手机号被占用...");
            }
        }
        if (totalData != null && !totalData.equals("") && !totalData.equals(oneMenber.getU())){
            String result = mUserRegister.fixTotalData(oneMenber.getMenberEmail(), totalData);
            if (result == null){
                oneMenber.setU(totalData);
                fixData = "【总流量】";
            }else {
                logger.info("修改总流量失败...");
            }
        }
        if (speed != null && !speed.equals("") && !speed.equals(oneMenber.getD())){
            String result = mUserRegister.fixSpeedLimit(oneMenber.getMenberEmail(), speed);
            if (result == null){
                oneMenber.setD(speed);
                fixSpeed = "【用户速度】";
            }else {
                logger.info("修改用户速度失败...");
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parseDate = sdf.parse(endTime);
            if (parseDate.compareTo(oneMenber.getEndtime()) != 0){
                oneMenber.setEndtime(parseDate);
                fixEndTime = "【结束时间】";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Member addResult = mMemberService.addMember(oneMenber);
        model.addAttribute("oneMenber",addResult);
        logger.debug("---MemberController editInfo execution complete");
        return "成功修改了+"+fixPhone+"+"+fixData + "+" + fixEndTime+"+"+fixSpeed;
    }

    @LocalLock(key = "book:arg[0]")
    @GetMapping("/testSubmit")
    public String testSubmit(@RequestParam String token){
        System.out.println("收到了请求");
        return "success-"+token;
    }



}
