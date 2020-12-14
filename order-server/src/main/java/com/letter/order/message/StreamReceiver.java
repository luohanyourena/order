package com.letter.order.message;

import com.letter.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * create:luohan
 */
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

//    @StreamListener(StreamClient.INPUT)
//    public void process(Object msg){
//        log.info("StreamReceiver, {}",msg);
//    }

    @StreamListener(StreamClient.INPUT)
    @SendTo(StreamClient.INPUT_BACK)
    public String process(OrderDTO msg){
        log.info("StreamReceiver, {}",msg);

        return "消息被我成功消费完成~";
    }

    @StreamListener(StreamClient.INPUT_BACK)
    public void processBack(String msg){
        log.info("StreamReceiver, {}",msg);
    }
}
