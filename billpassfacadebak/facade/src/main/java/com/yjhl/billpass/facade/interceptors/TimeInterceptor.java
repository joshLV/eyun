package com.yjhl.billpass.facade.interceptors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Created by zhouyu on 2016/11/22.
 * 服务执行时间统计。
 */
//@Aspect
//@Component
public class TimeInterceptor {
    /**
     * 阈值
     */
    private long threshold;

    /**
     * 统计方法执行耗时Around环绕通知
     * @param joinPoint
     * @return
     */
    public Object timeAround(ProceedingJoinPoint joinPoint) {
        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        long start = System.currentTimeMillis();
        try {
            obj = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            //logger.error("统计某方法执行耗时环绕通知出错", e);
        }
        long end = System.currentTimeMillis();
        if(end-start>=threshold) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            System.out.println(signature.getDeclaringTypeName() + "." + signature.getName());
        }
        return obj;
    }
}
