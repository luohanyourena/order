package com.letter.order.message;

import com.letter.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

//mq接收消息
@Component
@Slf4j
public class MqReceiver {

    private static final String PRODUCT_INFO_STOCK ="PRODUCT_IN_STOCK_";

    @Autowired
    private StringRedisTemplate redisTemplate;
//    @RabbitListener(queues = "myQueue")
//    public void process(String mesaage){
//        log.info("MqReceiver: {}",mesaage);
//    }


//    //自动创建队列
//    @RabbitListener(queuesToDeclare =@Queue("myQueue"))
//    public void process(String mesaage){
//        log.info("MqReceiver: {}",mesaage);
//    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("orderChanage"),
            key = "orderSg",
            value = @Queue("sgQueue")
    ))
    public void processSG(String mesaage){
        log.info("MqReceiver: {}",mesaage);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("orderChanage"),
            key = "orderSm",
            value = @Queue("smQueue")
    ))
    public void processSM(String mesaage){
        log.info("MqReceiver: {}",mesaage);
    }

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void processProductInfo(List<ProductInfoOutput> outputs){
        //将信息存入缓存中Redis
        log.info("processProductInfo,{}",outputs.size());
        for (ProductInfoOutput output : outputs) {
            redisTemplate.opsForValue().set(PRODUCT_INFO_STOCK+""+output.getProductId(),
                    String.valueOf(output.getProductStock()));
        }
    }
}
