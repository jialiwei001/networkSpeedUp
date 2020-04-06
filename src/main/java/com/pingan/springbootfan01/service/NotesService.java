package com.pingan.springbootfan01.service;

import com.pingan.springbootfan01.entity.LocalUser;
import com.pingan.springbootfan01.entity.Notes;

import java.util.List;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.service
 *  @文件名:   NotesService
 *  @创建者:   Administrator
 *  @创建时间:  2020/4/5 15:19
 *  @描述：    TODO
 */
public interface NotesService {
    public List<Notes> ifFindAllNote(LocalUser localUser, String phone, String start, String end);
}
