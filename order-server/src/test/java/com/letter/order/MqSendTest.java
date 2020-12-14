//package com.letter.order;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Date;
//
////mq发送信息
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MqSendTest {
//
//    @Autowired
//    private AmqpTemplate amqpTemplate;
//
//    @Test
//    public void sendMessage(){
//        amqpTemplate.convertAndSend("orderChanage","smQueue","now"+new Date());
//    }
//
//    @Test
//    public void sendMessagesm(){
//        amqpTemplate.convertAndSend("orderChanage","orderSm","now"+new Date());
//    }
//
//    @Test
//    public void sendMessagesg(){
//        amqpTemplate.convertAndSend("orderChanage","orderSg","now"+new Date());
//    }
//
//    @Test
//    public void sendMessageProduct(){
//        amqpTemplate.convertAndSend("orderChanage","product","张三用户购买了皮蛋瘦肉粥！");
//    }
//}
