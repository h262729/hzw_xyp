package com.hzw.base.exception;

import com.hzw.base.controller.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalDefultExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(GlobalDefultExceptionHandler.class);

 /*   @ExceptionHandler(value = Exception.class)
    void handleException(Exception e, HttpServletResponse response) throws IOException {
        Map<String,Object> map=new HashMap<>();
        map.put("code",100);
        map.put("msg",e.getMessage());
        e.printStackTrace(response.getWriter());
    }*/

    @ExceptionHandler(value = BusinessException.class)
    void handleException(BusinessException e, HttpServletResponse response) throws Exception {
        ResponseResult.create().error(e.getCode(), e.getMessage()).print(response);
    }
}
