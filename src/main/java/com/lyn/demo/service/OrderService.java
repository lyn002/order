package com.lyn.demo.service;

import com.lyn.demo.dao.OrderDao;
import com.lyn.demo.dao.OrderDetailDao;
import com.lyn.demo.dao.RefundDao;
import com.lyn.demo.domain.Order;
import com.lyn.demo.domain.OrderDetail;
import com.lyn.demo.domain.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private RefundDao refundDao;

    //创建订单
    public Order addOrder(Integer userId,Integer sellerId,String adress,
                          List<Integer> commodityId,List<Integer> number,List<Double> price
    ) {
        Double total = 0d;
        Order order1 = new Order();
        UUID uuid = UUID.randomUUID();
        order1.setOrderId(uuid.toString());
        order1.setUserDeleteState(false);
        Date day = new Date();
        order1.setUserId(userId);
        order1.setSellerId(sellerId);
        order1.setCreateTime(day);
        order1.setUpdateTime(day);
        order1.setState(1);
        order1.setAdress(adress);
        String s = uuid.toString();
       for(int i = 0;i<commodityId.size();i++){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(s);
            orderDetail.setCommodityId(commodityId.get(i));
            orderDetail.setNumber(number.get(i));
            orderDetail.setPrice(price.get(i));
            orderDetailDao.save(orderDetail);
            total += number.get(i)*price.get(i);
        }
        order1.setPayPrice(total);
        return orderDao.save(order1);
}

    //删除订单
    public void deleteOrder(String orderId) throws Exception {
        List<OrderDetail> orderDetail = orderDetailDao.findAllByOrderId(orderId);
        Order order = orderDao.getOne(orderId);
        if (orderDetail == null){
            throw new Exception("删除订单，未找到订单详情。");
        }
        else {
            if (order.getState() == 2||order.getState() == 3||order.getState() == 4)
                throw new Exception("订单未完成，无法删除。");
            for (int i = 0;i<orderDetail.size();i++) {
                orderDetailDao.delete(orderDetail.get(i));
            }
            orderDao.delete(orderId);
        }
    }

    //买家删除订单显示
    public void deleteUserOrder(String orderId) throws Exception{
        Order order = orderDao.getOne(orderId);
        if (order== null){
            throw new Exception("删除订单，未找到订单。");
        }
        else{
            order.setUserDeleteState(true);
            orderDao.save(order);
        }
    }

    //买家分页查询订单
    public Page<Order> userQueryOrder(Integer userId, Integer pageNumber, Integer pageSize){
        pageNumber = (pageNumber == null ? 1 : pageNumber-1);
        pageSize = (pageSize == null ? 1 : pageSize);
        Pageable pageable = new PageRequest(pageNumber,pageSize, Sort.Direction.DESC,"createTime");
        Page<Order> orderList = orderDao.findAllByUserIdAndUserDeleteState(userId,pageable,false);
        return orderList;
    }
    /*public Pagination<Order> userQueryOrder(Integer userId,Integer pageNo,Integer pageSize,Boolean userDeleteState){
        Specification<Order> specification = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                Path<Integer> _userId = root.get("userId");
                Predicate _key = criteriaBuilder.ge(_userId,userId);
                return criteriaBuilder.and(_key);
            }
        };
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(pageNo - 1, pageSize, sort);
        Page page1 = orderDao.findAll(specification, pageable);
        Pagination<Order> pagination = new Pagination(pageNo,pageSize,page1.getSize(),page1.getContent());
        return pagination;
    }*/

    //商家分页查询订单
    public Page<Order> sellerQueryOrder(Integer sellerId, Integer pageNumber, Integer pageSize){
        pageNumber = (pageNumber == null ? 1 : pageNumber-1);
        pageSize = (pageSize == null ? 1 : pageSize);
        Pageable pageable = new PageRequest(pageNumber,pageSize, Sort.Direction.DESC,"createTime");
        Page<Order> orderList = orderDao.findAllBySellerId(sellerId,pageable);
        return orderList;
    }
    /*public Pagination<Order> sellerQueryOrder(Integer sellerId,Integer pageNo,Integer pageSize){
        Specification<Order> specification = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                Path<Integer> _userId = root.get("userId");
                Predicate _key = criteriaBuilder.ge(_userId,sellerId);
                return criteriaBuilder.and(_key);
            }
        };
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = new PageRequest(pageNo - 1, pageSize, sort);
        Page page1 = orderDao.findAll(specification, pageable);
        Pagination<Order> pagination = new Pagination(pageNo,pageSize,page1.getSize(),page1.getContent());
        return pagination;
    }*/

    //查找所有订单
    public List<Order> orderList() {
        return orderDao.findAll();
    }

    //通过订单号查找订单
    public Order findByOderId(String orderId) {
        return orderDao.findOne(orderId);
    }

    //根据用户ID查询订单
    public List<Order> findByUserId(Integer userId){
        return orderDao.findAllByUserIdAndUserDeleteState(userId,false);
    }

    //根据商家ID查询订单
    public List<Order> findBySellerId(Integer sellerId){
        return orderDao.findAllBySellerId(sellerId);
    }

    //根据用户ID和订单状态查询订单
    public List<Order> findByStateAndUserId(Integer userId,Integer state){
        return orderDao.findAllByStateAndUserId(userId,state);
    }

    //根据用户ID和订单状态查询订单
    public List<Order> findByStateAndSellerId(Integer sellerId,Integer state){
        return orderDao.findAllByStateAndUserId(sellerId,state);
    }

    //修改订单付款状态和付款时间
    public Order orderPay(String orderId) {
        Order order1 = orderDao.findOne(orderId);
        if (order1 == null)
            return null;
        else {
            order1.setState(2);
            Date day = new Date();
            order1.setPayTime(day);
            order1.setUpdateTime(day);
            return orderDao.save(order1);
        }
    }

    //发货：填写快递公司、物流单号、修改发货状态和发货时间
    public Order updateDeliver(String orderId,String deliverFirm, String deliverNumber) {
        Order order1 = orderDao.findOne(orderId);
        if (order1 == null)
            return null;
        else {
            Date day = new Date();
            order1.setDeliverTime(day);
            order1.setUpdateTime(day);
            order1.setDeliverFirm(deliverFirm);
            order1.setDeliverNumber(deliverNumber);
            order1.setState(3);
            return orderDao.save(order1);
        }
    }

    //确认收货
    public Order confirmReceipt(String orderId){
        Order order = orderDao.findOne(orderId);
        if (order == null)
            return null;
        else {
            Date day = new Date();
            order.setState(4);
            order.setConfirmTime(day);
            order.setUpdateTime(day);
            return orderDao.save(order);
        }
    }

    //修改物流单号
    public Order updateDeliverNumber(String orderid, String delivernumber) {
        Order order = orderDao.findOne(orderid);
        if (order == null) {
            return null;
        } else {
            order.setDeliverNumber(delivernumber);
            Date day = new Date();
            order.setUpdateTime(day);
        }
        return orderDao.save(order);
    }

    //添加订单详情
    public  OrderDetail addorderdetail(Integer id,Integer number,String orderId,Integer commodityId,Double price){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(id);
        orderDetail.setNumber(number);
        orderDetail.setOrderId(orderId);
        orderDetail.setPrice(price);
        orderDetail.setCommodityId(commodityId);
        return orderDetailDao.save(orderDetail);
    }

    //退货申请创建
    public Refund addReund(Order order,String adress){
        Refund refund = new Refund();
        refund.setAdress(adress);
        refund.setOrderId(order.getOrderId());
        refund.setPayPrice(order.getPayPrice());
        refund.setUserId(order.getUserId());
        refund.setSellerId(order.getSellerId());
        refund.setState(1);
        return refundDao.save(refund);
    }

    //退货填写或修改物流单号
    public Refund updateDeliverNumber(String orderId,String deliverFirm,String deliverNumber){
        Refund refund =  refundDao.findOne(orderId);
        refund.setDeliverFirm(deliverFirm);
        refund.setDeliverNumber(deliverNumber);
        refund.setState(2);
        return refundDao.save(refund);
    }

    public Refund refundCofirmReceipt(String orderId){
        Refund refund =  refundDao.findOne(orderId);
        refund.setState(3);
        return refundDao.save(refund);
    }

    //完成退款
    public Refund refundsuccess(String orderId){
        Refund refund =  refundDao.findOne(orderId);
        refund.setState(4);
        return refundDao.save(refund);
    }

    //商家删除退款订单
    public void deleteRefund(String orderId){
        refundDao.delete(orderId);
    }

}
