package com.rainsoft.base.common.aspectj;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rainsoft.base.common.annotation.Log;
import com.rainsoft.base.common.model.SystemModuleEnum;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.web.vo.AuthenToken;
import com.rainsoft.base.web.system.dao.impl.LogOptRecordDaoImpl;
import com.rainsoft.base.web.system.model.LogOptRecord;

@Aspect
@Component
public class LogAop {
	@Resource(name="logOptRecordDao")
	private LogOptRecordDaoImpl logOptRecordDao;
//	
	/** 日志开始时间 */
	Date startTime = null;
	
	@Pointcut("@annotation(com.rainsoft.base.common.annotation.Log)")
	public void log() {
	}

	/**
	 * 在所有标注@Log的地方切入
	 * 
	 * @param joinPoint
	 */
	@Before("log()")
	public void beforeExec(JoinPoint joinPoint) {
		startTime = Calendar.getInstance().getTime();
	}

	@After("log()")
	public void afterExec(JoinPoint joinPoint) {
		saveLog(joinPoint, null);
	}
	
	@AfterThrowing(pointcut = "log()", throwing = "e")  
    public void afterThrowExec(JoinPoint joinPoint, Exception e) {  
		saveLog(joinPoint, e);
    }

	@Around("log()")
	public Object aroundExec(ProceedingJoinPoint pjp) throws Throwable {
		return pjp.proceed();
	}

	@SuppressWarnings("rawtypes")
	private void saveLog(JoinPoint joinPoint, Exception e) {
		LogOptRecord logOptRecord = new LogOptRecord();
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		// 当前用户取得,取得失败，抛出异常，由异常模块写入异常日志
		AuthenToken authenToken = (AuthenToken) request.getSession().getAttribute("AuthenToken");
        Integer userId = 0;
        if (null != authenToken) {
			userId = authenToken.getUserId();
		} else {
			throw new NullPointerException();
		}
		
        Date endTime = Calendar.getInstance().getTime(); 											// 当前时间
        String ip = SpringMvcUtil.getIpAddr(request);						// IP
        // 注解类实体取得
        Class clazz = joinPoint.getTarget().getClass();
        // 注解方法实体取得
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		Method method = ms.getMethod();
		// 类描述值
		@SuppressWarnings("unchecked")
		String clazzDesc = ((Log) clazz.getAnnotation(Log.class)).clazzDesc();
		// 方法描述值
		String methodDesc = method.getAnnotation(Log.class).methodDesc();
		
        String packages = clazz.getName();
        
        String code = packages.substring(packages.indexOf("web.") + 4, packages.lastIndexOf(".controller"));
        // 通过包名取得系统模块名称
        String moduleName = SystemModuleEnum.getValue(code);
        
		logOptRecord.setCreator(String.valueOf(userId));
		logOptRecord.setStartTime(startTime);
		logOptRecord.setEndTime(endTime);
		logOptRecord.setIp(ip);
		logOptRecord.setOptModule(moduleName);
		logOptRecord.setOptAction(clazzDesc + "-" + methodDesc);
		logOptRecord.setPlaceCode(SpringMvcUtil.getDefaultPalceCode());
		// 正常Log写入
		if (null == e) {
			logOptRecord.setStatus("成功");
			logOptRecord.setRemark("");
		} else {
			// 异常Log写入
			logOptRecord.setStatus("失败");
			logOptRecord.setRemark(e + "");
		}
		logOptRecordDao.save(logOptRecord);
	}
}
