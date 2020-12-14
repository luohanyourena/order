package com.letter.order.exception;

import com.letter.order.enums.ResultEnum;
import lombok.Data;

@Data
public class OrderException extends RuntimeException{
    private Integer code;

    public OrderException (Integer code,String message){
        super(message);
        this.code=code;
    }
    public OrderException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();

    }
}
