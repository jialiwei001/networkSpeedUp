package com.pingan.springbootfan01.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.common
 *  @文件名:   LocalLock
 *  @创建者:   Administrator
 *  @创建时间:  2020/5/24 23:33
 *  @描述：    TODO
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LocalLock {

    String key() default "";

    int expire() default 5;

}
