package com.pingan.springbootfan01;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01
 *  @文件名:   SpringTest03
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/30 22:00
 *  @描述：    TODO
 */
public class SpringTest03 {
    private static final String TAG = "SpringTest03";


    @Test
    public void liqiang(){
        RestTemplate template = new RestTemplate();
        String url = "http://angrybird.top:8081/auth/register";
        HttpHeaders  headers = new HttpHeaders();
        headers.setExpires(0);
        // Cache-Control: private, no-store, max-age=0
        headers.setCacheControl("private, no-store, max-age=0");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email","7925174161@qq.com");
        map.add("name","jiao202061");
        map.add("passwd","12345678");
        map.add("repasswd","12345678");
        map.add("wechat","12131361");
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

    /**/

    @Test
    public void jiaoliwewei() {

        RestTemplate template = new RestTemplate();
        String      url     = "http://angrybird.top:8081/auth/login";
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // Force the request expires
        //headers.setExpires(0);
        // Cache-Control: private, no-store, max-age=0
        //headers.setCacheControl("private, no-store, max-age=0");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email","7925174161@qq.com");
        map.add("passwd","12345678");
        //map.add("code","0");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(
                map,
                headers);
        ResponseEntity<String> responseEntity = template.postForEntity(url,
                                                                           entity,
                                                                           String.class);

        System.out.println("请求头结果：" + responseEntity.getStatusCode());
        System.out.println("请求体结果：" + responseEntity.getBody());
        HttpHeaders headers1 = responseEntity.getHeaders();
        String cookie = headers1.getFirst(headers.SET_COOKIE);
        System.out.println("获取请求头：" + headers1.toString());
        System.out.println("查看cookie：" + cookie.toString());

        HttpHeaders headers2 = new HttpHeaders();
        //headers2.setAccept(new ArrayList<>().add(MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML_XML));

        headers2 = headers1;
        String url2 = "http://angrybird.top:8081/user";
        HttpEntity request = new HttpEntity(headers2);
        //ResponseEntity<String> forEntity = template.getForEntity(url2, String.class ,entity2);

        ResponseEntity<String> response = template.exchange(url2, HttpMethod.GET, request, String.class);
        System.out.println("请求头结果2：" + response.getStatusCode());
        System.out.println("请求体结果2：" + response.getBody());




        //Thread.sleep(1000);
        //HttpHeaders headers1 = responseEntity.getHeaders();
        //headers1.setContentType(MediaType.MULTIPART_FORM_DATA);
        //String cookie = headers1.getFirst(headers.SET_COOKIE);
        //String cookie = headers1.getFirst(headers.COOKIE);
        //headers.add("Cookie",cookie);
       /* //headers.setCacheControl("private, no-store, max-age=0");
        HttpEntity<MultiValueMap<String, String>> entity1 = new HttpEntity<>(
                map,
                headers);
        ResponseEntity<String> responseEntity1 = template.postForEntity(url,
                                                                       entity1,
                                                                       String.class);*/


        //String url1 = "http://angrybird.top:8081/user";

        //String forObject = template.getForObject(url1, String.class,headers1);
        //ResponseEntity<Notice> forEntity = template.getForEntity(url1, Notice.class, headers1);

        //        MultiValueMap<String, String> map2 = new LinkedMultiValueMap<>();
//        map.add("email","7925174159@qq.com");
//        map.add("passwd","12345679");
//        map.add("code","0");






        //ResponseEntity<String> responseEntity2 = template.postForEntity(url,entity,String.class);

        //String url1 = "http://angrybird.top:8081/user";

        //String forObject = template.getForObject(url1, String.class);
        //System.out.println("请求cookie：" + cookie.toString());
        //System.out.println("请求体结果1：" + responseEntity.toString());


        //System.out.println("请求体结果：" + forObject);
        //System.out.println("请求头结果2：" + forEntity.getStatusCode());
        //System.out.println("请求头结果2：" + forEntity.getBody());

       // System.out.println("请求头结果：" + responseEntity1.getStatusCode());
        //System.out.println("请求体结果：" + responseEntity1.getBody());

    }

    @Test
    public void testDate(){
        System.out.println("方法1："+System.currentTimeMillis());
        System.out.println("方法2："+Calendar.getInstance().getTimeInMillis());
        System.out.println("方法3："+new Date().getTime());


        /*Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
　　　　　　　　　//增加筛选条件
                Predicate predicate = cb.conjunction();
                predicate.getExpressions().add(cb.equal(root.get("cardId"), couponDetailId));
　　　　　　　　　　　　//起始日期
                if (startTime != null && !startTime.trim().equals("")) {
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime").as(String.class), startTime));
                }
　　　　　　　　　　　　//结束日期
                if (endTime != null && !endTime.trim().equals("")) {
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createTime").as(String.class), endTime));
                }
                return predicate;
            }
        };*/

    }
}
