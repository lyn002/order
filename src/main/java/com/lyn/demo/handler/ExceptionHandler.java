package com.lyn.demo.handler;

import com.lyn.demo.enums.ResultEnum;
import com.lyn.demo.result.Result;
import com.lyn.demo.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public Result handlerAuthorizeException(Exception e){
        log.warn("{}",e);
        return ResultUtils.wrapResult(ResultEnum.FAIL,"发生错误，错误信息为："+e.getMessage());
    }
}
