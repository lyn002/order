package com.lyn.demo.result;

import lombok.Data;

@Data
public class Result {

    private Integer code;
    private String msg;
    private Object data;
}
