package com.pingan.springbootfan01;

import com.pingan.springbootfan01.dao.MemberDao;
import com.pingan.springbootfan01.dao.UserDao;
import com.pingan.springbootfan01.entity.LocalUser;
import com.pingan.springbootfan01.entity.Member;
import com.pingan.springbootfan01.service.MemberService;

import net.minidev.json.JSONObject;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@SpringBootTest
class SpringbootFan01ApplicationTests {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserDao mUserDao;

    @Autowired
    MemberDao mMemberDao;

    @Autowired
    MemberService mMemberService;


    @Autowired
    private JdbcTemplate mJdbcTemplate;

    RestTemplate template = new RestTemplate();



    @Test
    //@Transactional
    void contextLoads() {


        Member member1 = new Member();
        member1.setUsername("用户A");
        member1.setPassword("abc");
        member1.setType("月卡");
        member1.setPhonenumber("12222222");
        member1.setAmount(1);
        member1.setStarttime(new Date());
        member1.setEndtime(new Date());

        LocalUser localUser = new LocalUser();
        localUser.setUsername("admin");
        localUser.setPassword("123");
        localUser.setCreatetime(new Date());
        localUser.setEmail("792517415@qq.com");
        localUser.setIntroducer("admin");
        //localUser.getMemberSet().add(member1);
        //member1.setLocalUser(localUser);

        mUserDao.save(localUser);
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
    @Test
    public void test02(){
        int cid =1;
        Optional<LocalUser> user = mUserDao.findById(1);

        Specification<Member> specification = new Specification<Member>() {

            @Override
            public Predicate toPredicate(Root<Member> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder)
            {
                List<Predicate> predicates = new ArrayList<>();
                if (user.isPresent()) {
                    predicates.add(criteriaBuilder.equal(root.get("mLocalUser"), user.get()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //Optional<LocalUser> user = mUserDao.findById(1);
        PageRequest page = PageRequest.of(0, 1);
        Page<Member> all = mMemberDao.findAll(specification, page);
        System.out.println("查询到的结果为：" + all.toString());

    }

    @Test
    public void logTest(){

        logger.trace("这是track。。。");
        logger.debug("这是debug。。。");
        logger.info("这是日志info。。。");
        logger.warn("这是日志warn。。。");
        logger.error("这是日志error。。。");
    }
    @Test
    public void  jdbcTest(){
//        List<Map<String, Object>> maps = mJdbcTemplate.queryForList("select * from user");
//
//        for (int i = 0; i < maps.size(); i++) {
//            System.out.println(maps.get(i).toString());
//        }
        String     name   = "焦立伟测试04";
        String     email  = "2123324@qq.com";
        String     passwd = "zJNSFx";
        BigInteger phone  =new BigInteger("1567095125");
        String     sql    = "INSERT INTO `user` (`user_name`, `email`, `pass`, `passwd`, `t`, `u`, `d`, `plan`, `transfer_enable`, `port`, `switch`, `enable`, `type`, `last_get_gift_time`, `last_check_in_time`, `last_rest_pass_time`, `reg_date`, `invite_num`, `money`, `ref_by`, `expire_time`, `method`, `is_email_verify`, `reg_ip`, `node_speedlimit`, `node_connector`, `is_admin`, `im_type`, `im_value`, `last_day_t`, `sendDailyMail`, `class`, `class_expire`, `expire_in`, `theme`, `ga_token`, `ga_enable`, `pac`, `remark`, `node_group`, `auto_reset_day`, `auto_reset_bandwidth`, `protocol`, `protocol_param`, `obfs`, `obfs_param`, `forbidden_ip`, `forbidden_port`, `disconnect_ip`, `is_hide`, `is_multi_user`, `telegram_id`, `discord`) VALUES( ?, ?, '1ac114388fc0bdd94afa9cdd39103efb928e0f8cac7352970474da86068e6e8b', ?, ?, 36965883, 490596287, 'A', 537072238592, 60001, 1, 1, 1, 0, 1551752362, 0, '2017-12-06 23:38:51', 5, '470.00', 0, 0, 'chacha20-ietf', 0, '47.52.223.79', '2.00', 2, 1, 0, '', 527562170, 1, 0, '2017-12-07 01:38:51', '2291-10-20 23:38:51', 'material', 'STGWEV5SLCEJW62P', 0, NULL, '', 0, 0, '100.00', 'auth_aes128_md5', '', 'http_simple', 'jd.hk', '127.0.0.0/8,::1/128', '', NULL, 0, 0, NULL, NULL)";

        int result = mJdbcTemplate.update(sql,name,email,passwd,phone);
        //String sql2 = "select email from user where id = 380";
        //String result = mJdbcTemplate.queryForObject(sql2, String.class);
        System.out.println("插入结果："+ result);
    }

    @Test
    public void liqiang(){

        String url = "http://angrybird.top:8081/auth/register";
        HttpHeaders  headers = new HttpHeaders();
        headers.setExpires(0);
        // Cache-Control: private, no-store, max-age=0
        headers.setCacheControl("private, no-store, max-age=0");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email","7925174160@qq.com");
        map.add("name","jiao202060");
        map.add("passwd","12345679");
        map.add("repasswd","12345679");
        map.add("wechat","12131360");
        map.add("imtype","1");
        map.add("code","0");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(
                map,
                headers);
        ResponseEntity<String> responseEntity = template.postForEntity(url,
                                                                             entity,
                                                                             String.class);
        System.out.println("请求头结果1：" + responseEntity.getStatusCode());
        System.out.println("请求体结果1：" + responseEntity.getBody());
    }

    @Test
    public void jiaoliwewei() {
        //RestTemplate template = new RestTemplate();
        String url = "http://angrybird.top:8081/auth/login";
        HttpHeaders  headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // Force the request expires
        headers.setExpires(0);
        // Cache-Control: private, no-store, max-age=0
        headers.setCacheControl("private, no-store, max-age=0");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email","79251741578@qq.com");
        map.add("passwd","12345679");
        map.add("code","0");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(
                map,
                headers);
        ResponseEntity<JSONObject> responseEntity = template.postForEntity(url,
                                                                       entity,
                                                                       JSONObject.class);
        //Thread.sleep(1000);
        HttpHeaders headers1 = responseEntity.getHeaders();
        String cookie = headers1.getFirst(headers.SET_COOKIE);

        //ResponseEntity<String> responseEntity2 = template.postForEntity(url,entity,String.class);

        //String url1 = "http://angrybird.top:8081/user";

        //String forObject = template.getForObject(url1, String.class);
        System.out.println("请求cookie：" + cookie);
        System.out.println("请求体结果1：" + responseEntity.toString());


        //System.out.println("请求头结果：" + responseEntity.getStatusCode());
        //System.out.println("请求体结果：" + responseEntity.getBody());
        //System.out.println("请求头结果：" + responseEntity2.getStatusCode());
        //System.out.println("请求体结果：" + responseEntity2.getBody());

    }

    @Test
    public void restlogin2(){

        RestTemplate template = new RestTemplate();
        String url = "http://angrybird.top:8081/auth/login";
        HttpHeaders  headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        Map<String, String> map = new HashMap<>();
        map.put("email","7925174159@qq.com");
        map.put("passwd","12345679");
        map.put("code","0");

        //HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map,headers);
        ResponseEntity<String> responseEntity = template.getForEntity(url,
                                                                       String.class,map);



        System.out.println("请求头结果：" + responseEntity.getStatusCode());
        System.out.println("请求体结果：" + responseEntity.getBody());
    }

    @Test
    public void testFindMenber(){
        String userName = "冯海峰";
        LocalUser byUsername = mUserDao.findByUsername(userName);
        String name = "";
        String email ="";
        String phone = "";
        String startdate = "";
        String enddate = "";
        int pageNum = 1;
        int pageSize = 20;

        Page<Member> menbers = mMemberService.findMenbers(byUsername,
                                                          name,
                                                          email,
                                                          phone,
                                                          startdate,
                                                          enddate,
                                                          pageNum,
                                                          pageSize);

        System.out.println("查询到的结果："+menbers);


    }

    @Test
    public void findUserTest(){
        String name = "焦立伟";
        Optional<LocalUser> byId = mUserDao.findById(1);
        System.out.println("查询结果为："+byId.get());
    }

    @Test
    public void findMenberTest(){
        Member byPhonenumber = mMemberDao.findByPhonenumber("3213214");
        System.out.println("查询的结果为：" + byPhonenumber);
    }
}
