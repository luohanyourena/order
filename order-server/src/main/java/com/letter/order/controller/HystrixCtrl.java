package com.letter.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.utils.FallbackMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * create:luohan
 */
@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixCtrl {

    //对单个方法设置超时时间
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
//    })

    //服务熔断
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"), //开启熔断
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),//监控的服务次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "60000"), // 熔断窗口开启时间
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "6")//监控服务次数的熔点百分比。
    })
    @GetMapping("productList")
    public String findProductList(@RequestParam("num") int num){
        if (num%2==0){
            return "成功调用";
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8081/product/productInfoByIds", Arrays.asList("157875196366160022"),String.class);
    }
    
    private String fallback(){
        return "当前网络不稳定，请稍后再试！";
    }

    private String defaultFallback(){
        return "当前请求过多，请稍后再试！";
    }
}
