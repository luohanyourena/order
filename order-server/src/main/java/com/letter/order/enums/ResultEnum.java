package com.letter.order.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    FORM_EORR(1,"参数校验失败"),
    GWC_EORR(4,"购物车为空"),
    ORDER_NULL(3,"订单为空"),
    DETAIL_NULL(5,"订单详情为空"),
    STATUS_NULL(6,"订单状态错误"),
    JOSN_EORR(2,"JSON转换失败");
//    CLOSE(2,"取消");

    private Integer code;
    private String message;

    ResultEnum (Integer code,String message){
        this.code=code;
        this.message=message;
    }
}
