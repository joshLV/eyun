package com.yjhl.billpass.facade.controller;

import com.yjhl.yqb.common.CommonResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * Created by zhaominglei on 2016/12/30.
 */
public class BaseController {
    private static final Log log = LogFactory.getLog(BaseController.class);

    @ExceptionHandler
    protected @ResponseBody CommonResponse exceptionHandler(HttpServletRequest request, Exception exception) {
//        if (exception instanceof RpcException) {
//            if(exception.getCause() instanceof ConstraintViolationException) {
//                return CommonResponse.fail((ConstraintViolationException)exception.getCause());
//            }
//            log.error("dubbo异常：", exception);
//        }
//        if (exception instanceof HttpMessageNotReadableException) {
//            log.warn("前台参数不正确" , exception);
//            return CommonResponse.fail("参数类型不正确");
//        }
//        log.error("异常：", exception);
        return CommonResponse.fail(exception.getMessage() + " : " + exception.toString());
    }
}
