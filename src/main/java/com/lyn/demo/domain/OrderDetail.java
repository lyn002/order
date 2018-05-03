package com.lyn.demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer number;//物品数量
    private String orderId;//订单号
    private Integer commodityId;//商品ID
    private Double price;//商品单价
}