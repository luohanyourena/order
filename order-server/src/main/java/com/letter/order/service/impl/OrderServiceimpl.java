package com.letter.order.service.impl;

import com.letter.order.dataobject.OrderDetail;
import com.letter.order.dataobject.OrderMaster;
import com.letter.order.dto.OrderDTO;
import com.letter.order.enums.OrderEnum;
import com.letter.order.enums.PayStatusEnum;
import com.letter.order.enums.ResultEnum;
import com.letter.order.exception.OrderException;
import com.letter.order.repositroy.OrderDetailRepository;
import com.letter.order.repositroy.OrderMasterRepositroy;
import com.letter.order.service.OrderService;
import com.letter.order.utils.KeyUtil;
import com.letter.product.client.ProductClient;
import com.letter.product.common.DecreaseStockInput;
import com.letter.product.common.ProductInfoOutput;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceimpl implements OrderService {
    @Autowired
    private OrderMasterRepositroy orderMasterRepositroy;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    @Transactional
    public OrderDTO saveOrder(OrderDTO dto) {
        String orderkey = KeyUtil.genUniqueKey();
        //查询商品信息
        List<OrderDetail> orderDetailList = dto.getOrderDetailList();
        List<String> productIds = orderDetailList.stream().
                map(OrderDetail::getProductId).
                collect(Collectors.toList());
        List<ProductInfoOutput> products = productClient.findByProductIdIn(productIds);

        //计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDetailList) {
            for (ProductInfoOutput product : products) {
                if (orderDetail.getProductId().equals(product.getProductId())){
                    orderAmount = product.getProductPrice().
                            multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmount);
                    BeanUtils.copyProperties(product,orderDetail);
                    orderDetail.setOrderId(orderkey);
                    orderDetail.setCreateTime(new Date());
                    orderDetail.setUpdateTime(new Date());
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }


        // 扣库存
        // 将扣除信息传输给mq，product服务订阅这个队列获取信息扣除库存
        List<DecreaseStockInput> decreaseStockInputs = dto.getOrderDetailList().stream().
                map(e-> new DecreaseStockInput(e.getProductId(),e.getProductQuantity())).
                collect(Collectors.toList());
//
//        productClient.buckleInventory(decreaseStockInputs);

        amqpTemplate.convertAndSend("orderChanage","product",decreaseStockInputs);
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
//        OrderDetail detail = new OrderDetail();
        dto.setOrderId(orderkey);
        BeanUtils.copyProperties(dto,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        orderMasterRepositroy.save(orderMaster);
        return  dto;
    }

    @Override
    @Transactional
    public OrderDTO finishOrder(String orderId) {
        Optional<OrderMaster> orderMasterOptional = orderMasterRepositroy.findById(orderId);
        if (!orderMasterOptional.isPresent()){
            throw new OrderException(ResultEnum.ORDER_NULL);
        }
        OrderMaster order = orderMasterOptional.get();
        //判断订单状态
        if (!OrderEnum.NEW.getCode().equals(order.getOrderStatus())){
            throw new OrderException(ResultEnum.STATUS_NULL);
        }

        //修改订单状态
        order.setOrderStatus(OrderEnum.FINISHED.getCode());
        orderMasterRepositroy.save(order);

        //返回结果，查询订单详情
        List<OrderDetail> details = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(details)){
            throw new OrderException(ResultEnum.DETAIL_NULL);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order,orderDTO);
        orderDTO.setOrderDetailList(details);
        return orderDTO;
    }


}
