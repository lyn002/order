package com.lyn.demo.domain;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import sun.util.calendar.BaseCalendar;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "order_table")
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = -1583603994325395886L;
    @Id
    @GeneratedValue(generator = "orderId")
    @GenericGenerator(name = "orderId",strategy = "assigned")
    private String orderId;//订单号
    private Integer userId;//用户ID
    private Integer sellerId;//商家ID
    private Date createTime;//订单创建时间
    private Double payPrice;//付款金额
    private String adress;//收货地址
    private Integer state;//  1：未付款  2：付款未发货  3：发货未确认收货  4:已签收未确认收货  5：已确认收货，订单完成
    private Date payTime;//付款时间
    private Date deliverTime;//发货时间
    private String deliverFirm;//快递公司
    private String deliverNumber;//物流单号
    private Date updateTime;//订单更新时间
    private Date confirmTime;//确认收货时间
}

