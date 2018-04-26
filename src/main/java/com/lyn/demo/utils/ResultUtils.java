package com.lyn.demo.utils;

import com.lyn.demo.enums.ResultEnum;
import com.lyn.demo.result.Result;

public class ResultUtils {
    public static Result wrapResult(ResultEnum resultEnum, Object data){
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        result.setData(data);
        return result;
    }
}
