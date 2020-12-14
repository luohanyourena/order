package com.letter.order.dto;

import com.letter.order.dataobject.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private String     orderId;
    private String     buyerName ;
    private String     buyerPhone ;
    private String     buyerAddress ;
    private String     buyerOpenid ;
    private BigDecimal orderAmount ;
    private Integer    orderStatus ;
    private Integer    payStatus ;
    private List<OrderDetail> orderDetailList;
}
