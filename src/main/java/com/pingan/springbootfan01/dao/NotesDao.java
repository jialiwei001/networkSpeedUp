package com.pingan.springbootfan01.dao;

import com.pingan.springbootfan01.entity.Notes;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.dao
 *  @文件名:   NotesDao
 *  @创建者:   Administrator
 *  @创建时间:  2020/4/5 12:27
 *  @描述：    TODO
 */
@Repository
public interface NotesDao extends JpaRepository<Notes,Integer> {
    List<Notes>  findAll(Specification<Notes> specification);

    List<Notes> findByContent(String content);
    void  deleteByContent(String content);

}
