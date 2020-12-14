package com.letter.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * create:luohan
 */
public interface StreamClient {
    String INPUT="orderQueue";
    //消费玩信息后返回的队列
    String INPUT_BACK = "orderQueueBack";

    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    @Output(StreamClient.INPUT_BACK)
    MessageChannel output();

//    @Input(StreamClient.INPUT_BACK)
//    SubscribableChannel inputBack();
//
//    @Output(StreamClient.INPUT_BACK)
//    MessageChannel outputBack();
}
