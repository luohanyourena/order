package com.letter.order.service;

import com.letter.order.dto.OrderDTO;

public interface OrderService {
     OrderDTO saveOrder(OrderDTO dto);

     OrderDTO finishOrder(String orderId);
}
