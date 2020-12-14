package com.letter.order.controller;

import com.letter.order.config.GirlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create:luohan
 */
@RestController
public class girlCtrl {
    @Autowired
    private GirlConfig girlConfig;

    @GetMapping("/priln")
    public String priln(){
        return "name："+girlConfig.getName()+"  age："+girlConfig.getAge();
    }

}
