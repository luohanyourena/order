package com.letter.order.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrderDetail {
    @Id
    private String detailId ;
    private String  orderId ;
    private String productId ;
    private String productName ;
    private BigDecimal productPrice ;
    private Integer productQuantity ;
    private String  productIcon ;
    @CreatedDate
    private Date createTime;
    @LastModifiedDate
    private Date  updateTime ;
}
