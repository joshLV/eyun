package com.yjhl.billpass.facade.exception;

/**
 * Created by caijun on 2017/1/11.
 */
public class MessageCheckException extends RuntimeException{
    public MessageCheckException(String message) {
        super(message);
    }

    public MessageCheckException() {
        super();
    }
}
