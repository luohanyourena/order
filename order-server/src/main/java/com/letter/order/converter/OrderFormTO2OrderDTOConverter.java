package com.letter.order.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.letter.order.dataobject.OrderDetail;
import com.letter.order.dto.OrderDTO;
import com.letter.order.enums.ResultEnum;
import com.letter.order.exception.OrderException;
import com.letter.order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderFormTO2OrderDTOConverter {

    public static OrderDTO converter(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetails = new ArrayList<>();
        Gson gson = new Gson();
        try {
            orderDetails =  gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){
                    }.getType());
        }catch (Exception exception){
            log.error("【JOSN转换异常】，String={}",orderForm.getItems());
            throw new OrderException(ResultEnum.JOSN_EORR);
        }
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

}
