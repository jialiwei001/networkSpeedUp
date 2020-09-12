package com.pingan.springbootfan01.dao;

import com.pingan.springbootfan01.entity.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.dao
 *  @文件名:   MemberDao
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/27 14:01
 *  @描述：    TODO
 */
@Repository
public interface MemberDao extends JpaRepository<Member,Integer> {

    //Page<Member> findByCidAndAmount(Integer cid,Integer a, PageRequest pageRequest);

    Page<Member> findAll(Specification<Member> specification, Pageable pageable);

    Member findByPhonenumber(String phone);

    Member  findByMenberEmail(String email);

    Member  findBySubUrl(String url);

}
