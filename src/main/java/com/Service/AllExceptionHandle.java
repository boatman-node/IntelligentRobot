package com.Service;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AllExceptionHandle {
    /**全局捕获异常*/
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public String exceptionHandler(Exception e){
        System.out.println("全局异常捕获>>>:"+e);
        return "全局异常捕获,错误原因>>>"+e.getMessage();
    }
}
