package com.pingan.springbootfan01.service.impl;

import com.pingan.springbootfan01.dao.NotesDao;
import com.pingan.springbootfan01.entity.LocalUser;
import com.pingan.springbootfan01.entity.Notes;
import com.pingan.springbootfan01.service.NotesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.service.impl
 *  @文件名:   NotesServiceImpl
 *  @创建者:   Administrator
 *  @创建时间:  2020/4/5 15:19
 *  @描述：    TODO
 */
@Service
public class NotesServiceImpl implements NotesService {
    private static final String TAG = "NotesServiceImpl";

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NotesDao mNotesDao;

    //按条件查询记录
    public List<Notes>  ifFindAllNote(LocalUser localUser,String phone,String start,String end){
        logger.debug("---NotesServiceImpl ifFindAllNote param localuser:{},phone:{},start:{},end:{}",localUser,phone,start,end);
        Specification<Notes> specification = new Specification<Notes>() {

            @Override
            public Predicate toPredicate(Root<Notes> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder)
            {
                List<Predicate> predicates = new ArrayList<>();
                Predicate  conjunction = criteriaBuilder.conjunction();
                if (localUser!=null) {
                    predicates.add(criteriaBuilder.equal(root.get("mLocalUser"), localUser));
                }
                if (phone != "" && phone != null){
                    predicates.add(criteriaBuilder.equal(root.get("content"),phone));
                }
                if (start != "" && start != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(String.class),start));
                }
                if (end != "" && end != null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(String.class),end));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        //PageRequest page = PageRequest.of(0, 1000);
        List<Notes> all  = mNotesDao.findAll(specification);
        logger.info("根据条件查找到的记录为：" ,all.toString());

        return all;
    }


}
