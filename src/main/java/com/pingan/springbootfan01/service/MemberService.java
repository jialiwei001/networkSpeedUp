package com.pingan.springbootfan01.service;

import com.pingan.springbootfan01.entity.LocalUser;
import com.pingan.springbootfan01.entity.Member;

import org.springframework.data.domain.Page;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.service
 *  @文件名:   MemberService
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/28 0:13
 *  @描述：    TODO
 */
public interface MemberService {

    Member addMember(Member member);
    public Member findOnebyId(int id);
    public Member findOnebyPhone(String phone);

    public Member findOneByEmail(String email);

    public Page<Member> findAll(LocalUser localUser, int pa, int si);

    public Page<Member> findMenbers(LocalUser localUser,  String email, String phone,String type, String start,String end, int pa, int si);
   // public Page<Member> findTotalMenbers(LocalUser localUser, String start, String end, int pageNum, int pagesize);
}
