package com.letter.order.controller;

import com.letter.order.converter.OrderFormTO2OrderDTOConverter;
import com.letter.order.dto.OrderDTO;
import com.letter.order.enums.ResultEnum;
import com.letter.order.exception.OrderException;
import com.letter.order.form.OrderForm;
import com.letter.order.message.StreamClient;
import com.letter.order.service.OrderService;
import com.letter.order.utils.ResultVoUtil;
import com.letter.order.vo.ResultVO;
import com.letter.product.client.ProductClient;
import com.letter.product.common.DecreaseStockInput;
import com.letter.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderCtrl {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductClient productClient;
    @Value("env")
    private String env;
    @Autowired
    private StreamClient streamClient;


    //BindingResult 表单验证
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        //表单验证不通过
        if (bindingResult.hasErrors()){
            log.error("【参数校验失败】，orderForm={}",orderForm);
            throw new OrderException(ResultEnum.FORM_EORR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        //OderForm->OrderDTO
        OrderDTO orderdto = OrderFormTO2OrderDTOConverter.converter(orderForm);
        if (CollectionUtils.isEmpty(orderdto.getOrderDetailList())){
            log.error("【创建订单】购物车为空");
            throw new OrderException(ResultEnum.GWC_EORR);
        }
        // 写入数据库
        orderService.saveOrder(orderdto);

        Map<String,String> map= new HashMap<>();
        map.put("orderId",orderdto.getOrderId());

        return ResultVoUtil.seccuss(map);
    }

    @GetMapping("/msg")
    public String i_getproductMsg(){
        return productClient.productMsg();
    }

    @GetMapping("/getProducts")
    public String i_getProduct(){
        List<ProductInfoOutput> productInfos = productClient.findByProductIdIn(Arrays.asList("157875196366160022"));
        for (ProductInfoOutput productInfo : productInfos) {
            log.info("productInfo={}",productInfo.toString());
        }
        return "ok";
    }

    @GetMapping("/test/buckleInventory")
    public String test_buckleInventory(){
        DecreaseStockInput decreaseStockInput = new DecreaseStockInput("164103465734242707",10);
        productClient.buckleInventory(Arrays.asList(decreaseStockInput));
        return "ok";
    }

    @GetMapping("/println")
    public String println(){
        return env;
    }


    @GetMapping("/sendToMQ")
    public void sendSteamMQ(){
//        String message ="now:"+new Date();
        OrderDTO orderDTO = new OrderDTO();
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }

    @PostMapping("/finish")
    public ResultVO<OrderDTO> finishOrder(@RequestParam("orderId") String orderId){
        return ResultVoUtil.seccuss(orderService.finishOrder(orderId));
    }
}
