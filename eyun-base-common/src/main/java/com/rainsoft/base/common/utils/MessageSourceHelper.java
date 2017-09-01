package com.rainsoft.base.common.utils;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageSourceHelper {
	
	@Resource
	private ResourceBundleMessageSource messageSource;
	
	private Locale locale;
		
	/**
	 * @param code Key
	 * @param args 参数
	 * @param defaultMessage 默认i18nMessage
	 * @param locale 当前语言
	 * @return i18nMessage
	 */
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        String msg = messageSource.getMessage(code, args, defaultMessage, locale);
        return msg != null ? msg.trim() : msg;
    }
	
	/**
	 * @param code Key
	 * @param args 参数
	 * @param locale 当前语言
	 * @return i18nMessage
	 */
	public String getMessage(String code, Object[] args, Locale locale) {
        String msg = messageSource.getMessage(code, args, null, locale);
        return msg != null ? msg.trim() : msg;
    }

	/**
	 * @param code Key
	 * @param args 参数
	 * @return i18nMessage
	 */
	public String getMessage(String code, Object[] args) {
		String msg = messageSource.getMessage(code, args, null, locale);
        return msg != null ? msg.trim() : msg;
    }
	
	/**
	 * @param code Key
	 * @param locale 当前语言
	 * @return i18nMessage
	 */
	public String getMessage(String code, Locale locale) {
        String msg = messageSource.getMessage(code, null, null, locale);
        return msg != null ? msg.trim() : msg;
    }
	
	/**
	 * @param code Key
	 * @return i18nMessage
	 */
	public String getMessage(String code) {
        String msg = messageSource.getMessage(code, null, null, locale);
        return msg != null ? msg.trim() : msg;
    }
   
    public void setMessageSource(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    public void setLocale(Locale locale){
		this.locale = locale;
	}
}
