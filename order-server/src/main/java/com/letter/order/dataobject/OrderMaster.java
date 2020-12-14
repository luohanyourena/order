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
public class OrderMaster {
    @Id
    private String     orderId;
    private String     buyerName ;
    private String     buyerPhone ;
    private String     buyerAddress ;
    private String     buyerOpenid ;
    private BigDecimal orderAmount ;
    private Integer    orderStatus ;
    private Integer    payStatus ;
    @CreatedDate
    private Date  createTime ;
    @LastModifiedDate
    private Date  updateTime ;
}
