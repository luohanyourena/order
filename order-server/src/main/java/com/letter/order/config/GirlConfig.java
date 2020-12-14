package com.letter.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * create:luohan
 */
@Data
@Component
@ConfigurationProperties("girl")
@RefreshScope  //自动刷新
public class GirlConfig {
    private String name;
    private String age;
}
