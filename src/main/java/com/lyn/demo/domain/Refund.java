package com.lyn.demo.domain;

import lombok.Data;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "refund")
public class Refund {

    @Id
    private String orderId;
    private Integer userId;//用户ID
    private Integer sellerId;//商家ID
    private Double payPrice;//付款金额
    private String adress;//收货地址
    private Integer state;//    1：未发货  2：已发货  3：确认收货  4：退款完成
    private String deliverFirm;//物流公司
    private String deliverNumber;//物流单号
}
