package com.pingan.springbootfan01;

import com.pingan.springbootfan01.dao.MemberDao;
import com.pingan.springbootfan01.dao.UserDao;
import com.pingan.springbootfan01.entity.Member;
import com.pingan.springbootfan01.util.UserRegister;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootFan01ApplicationTests {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserDao mUserDao;

   @Autowired
    MemberDao mMemberDao;

   @Autowired
   UserRegister mUserRegister;
//
//    @Autowired
//    MemberService mMemberService;
//
//
//    @Autowired
//    private JdbcTemplate mJdbcTemplate;
//
//    RestTemplate template = new RestTemplate();

    @Test
    public void testMemberDao(){
        //System.out.println("开始测试。。。");
        //Optional<Member> byId = mMemberDao.findById(27);
        //System.out.println("测试结果："+ byId.get().toString());

        Member member = mMemberDao.findById(103).get();

        String today = mUserRegister.DataUsageForToday(member.getMenberEmail());
        String month = mUserRegister.DataUsageForTotal(member.getMenberEmail());
        if (today.equals("0.0")){
            member.setD(today);
        }else {
            member.setD(today.substring(0,today.indexOf(".")+4));
        }
        if (month.equals("0.0")){
            member.setT(today);
        }else {
            member.setT(month.substring(0,month.indexOf(".")+4));
        }
        mMemberDao.save(member);

        //String aa1 = "123.1232233443";
        //System.out.println(Float.parseFloat(aa1));
        //System.out.println("-------"+aa1.substring(0,aa1.indexOf(".")+4));
    }

    @Test
    //@Transactional
    void contextLoads() {


        /*Member member1 = new Member();
        member1.setUsername("用户A");
        member1.setPassword("abc");
        member1.setType("月卡");
        member1.setPhonenumber("12222222");
        member1.setAmount(1);
        member1.setStarttime(new Date());
        member1.setEndtime(new Date());*/

//        LocalUser localUser = new LocalUser();
//        localUser.setUsername("admin");
//        localUser.setPassword("jiao080312wei");
//        localUser.setCreatetime(new Date());
//        localUser.setEmail("792517415@qq.com");
//        localUser.setIntroducer("admin");



        //localUser.getMemberSet().add(member1);
        //member1.setLocalUser(localUser);

        //mUserDao.save(localUser);
        //mMemberDao.save(member1);

        /*Optional<LocalUser> oneUser = mUserDao.findById(2);
        if (oneUser.isPresent()){
            System.out.println("查询到的会员为：" + oneUser.get());
        }

        Optional<Member> oneMember = mMemberDao.findById(2);
        if (oneMember.isPresent()){
            System.out.println("查到的用户为："+ oneMember.get());
        }*/
    }


}
