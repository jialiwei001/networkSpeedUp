package com.pingan.springbootfan01.service.impl;

import com.pingan.springbootfan01.dao.MemberDao;
import com.pingan.springbootfan01.entity.LocalUser;
import com.pingan.springbootfan01.entity.Member;
import com.pingan.springbootfan01.service.MemberService;
import com.pingan.springbootfan01.util.UtilTools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.service.impl
 *  @文件名:   MemberServiceImpl
 *  @创建者:   Administrator
 *  @创建时间:  2020/3/28 19:13
 *  @描述：    TODO
 */
@Service
public class MemberServiceImpl implements MemberService {
    private static final String TAG = "MemberServiceImpl";
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberDao mMemberDao;

    //保存用户
    public Member addMember(Member member){
        Member addResult = mMemberDao.save(member);

        return addResult;
    }

    public Member findOnebyId(int id){
        Optional<Member> oneMenber = mMemberDao.findById(id);
        return oneMenber.get();
    }

    public Member findOnebyPhone(String phone){
        Member oneMenber = mMemberDao.findByPhonenumber(phone);
        return oneMenber;
    }

    public Member findOneByEmail(String email){
        Member menber = mMemberDao.findByMenberEmail(email);
        return menber;
    }

    public Page<Member> findAll(LocalUser localUser, int pageNum, int pagesize){
        logger.debug("MemberServiceImpl findAll method recive param localUser:{} pageNum:{} pagesize:{}");
        PageRequest  page = PageRequest.of(pageNum, pagesize);

        Specification<Member> specification = new Specification<Member>() {

            @Override
            public Predicate toPredicate(Root<Member> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder)
            {
                List<Predicate> predicates = new ArrayList<>();
                if (localUser!=null) {
                    predicates.add(criteriaBuilder.equal(root.get("mLocalUser"), localUser));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //Optional<LocalUser> user = mUserDao.findById(1);
        Page<Member> allMenber = mMemberDao.findAll(specification, page);
        logger.info("根据指定会员查询到的分页用户为：" + allMenber);

        return allMenber;
    }

    @Override
    public Page<Member> findMenbers(LocalUser localUser,
                                String urlAdress,
                                String phone,
                                String type,
                                String start,
                                String end,
                                int pageNum,
                                int pagesize)
    {
        logger.debug("---MemberServiceImpl findMenbers method recive param localUser:{} urlAdress:{},phone:{},type:{},startdate:{},enddate:{},pageNum:{},pagesize:{}",localUser,urlAdress,phone,type,start,end,pageNum,pagesize);
        PageRequest  page = PageRequest.of(pageNum, pagesize);

//        if (localUser == null){
//            Page<Member> all = mMemberDao.findAll(page);
//            return all;
//        }

        Specification<Member> specification = new Specification<Member>() {

            @Override
            public Predicate toPredicate(Root<Member> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder)
            {
                List<Predicate> predicates = new ArrayList<>();
                Predicate  conjunction = criteriaBuilder.conjunction();
                if (localUser!=null) {
                    predicates.add(criteriaBuilder.equal(root.get("mLocalUser"), localUser));
                }
                if (!"".equals(phone) && phone != null){
                    predicates.add(criteriaBuilder.equal(root.get("phonenumber"),phone));
                }
                if (!"".equals(urlAdress) && urlAdress != null){
                    predicates.add(criteriaBuilder.equal(root.get("subUrl"),urlAdress));
                }
                if (!"".equals(type) && !"null".equals(type) && type != null){
                    predicates.add(criteriaBuilder.equal(root.get("type"),type));
                }
                if (!"".equals(start) && !"null".equals(start) && start != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("endtime").as(String.class),start));
                }
                if (!"".equals(end) && !"null".equals(end) && end != null){
                    predicates.add(criteriaBuilder.lessThan(root.get("endtime").as(String.class),end));
                    query.orderBy(criteriaBuilder.asc(root.get("endtime")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //Optional<LocalUser> user = mUserDao.findById(1);
        Page<Member> allMenber = mMemberDao.findAll(specification, page);
        //根据已经试用时间调整显示用户类型
        allMenber.forEach(member -> {
            UtilTools.fixUserType(member);
        });
        logger.info("根据指定会员查询到的分页用户为：" + allMenber);

        return allMenber;
    }


}
