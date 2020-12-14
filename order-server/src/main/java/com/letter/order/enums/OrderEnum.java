package com.letter.order.enums;

import lombok.Getter;

@Getter
public enum OrderEnum {
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CLOSE(2,"取消");

    private Integer code;
    private String message;

     OrderEnum (Integer code,String message){
        this.code=code;
        this.message=message;
    }
}
