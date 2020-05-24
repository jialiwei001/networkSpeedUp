package com.pingan.springbootfan01.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.pingan.springbootfan01.common.LocalLock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;



/*
 *  @项目名：  springboot-fan01
 *  @包名：    com.pingan.springbootfan01.config
 *  @文件名:   LockMethodInterceptor
 *  @创建者:   Administrator
 *  @创建时间:  2020/5/24 23:37
 *  @描述：    TODO
 */
@Aspect
@Configuration
public class LockMethodInterceptor {
    private static final String TAG = "LockMethodInterceptor";

    private static final Cache<String,Object> CACHES = CacheBuilder.newBuilder()
                                                                   .maximumSize(100)
                                                                   .expireAfterWrite(40, TimeUnit.SECONDS)
                                                                   .build();
    @Around("execution(public * *(..)) && @annotation(com.pingan.springbootfan01.common.LocalLock)")
    public Object interceptor(ProceedingJoinPoint pjp){
        MethodSignature signature = (MethodSignature) pjp.getSignature();

        Method method = signature.getMethod();
        LocalLock localLock = method.getAnnotation(LocalLock.class);
        String key = getKey(localLock.key(), pjp.getArgs());
        if (!StringUtils.isEmpty(key)){
            if (CACHES.getIfPresent(key) != null ){
                throw new RuntimeException("请勿重复提交");
            }
            //R如果是第一次请求，就将key当前对象亚茹缓存中
            CACHES.put(key,key);
        }
        try {
            return pjp.proceed();
        }catch (Throwable throwable){
            throw new RuntimeException("服务器异常");
        }
    }

    private String getKey(String keyExpress,Object[] args){
        for (int i = 0;i < args.length;i++){
            keyExpress = keyExpress.replace("arg["+i+"]",args[i].toString());
        }
        return keyExpress;
    }
}
