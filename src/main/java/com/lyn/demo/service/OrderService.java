package com.lyn.demo.service;

import com.lyn.demo.dao.OrderDao;
import com.lyn.demo.dao.OrderDetailDao;
import com.lyn.demo.domain.Order;
import com.lyn.demo.domain.OrderDetail;
import com.lyn.demo.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    //创建订单
    public Order addOrder(Integer userid,Integer sellerId,List<Integer> commodityid,String adress,List<Integer> number,List<Double> price) {
        Double total = 0d;
        Order order1 = new Order();
        Date day = new Date();
        order1.setUserId(userid);
        order1.setSellerId(sellerId);
        order1.setCreateTime(day);
        order1.setUpdateTime(day);
        order1.setState(1);
        UUID uuid = UUID.randomUUID();
        order1.setOrderId(uuid.toString());
        for(int i = 0;i<number.size();i++){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(uuid.toString());
            orderDetail.setCommodityId(commodityid.get(i));
            orderDetail.setNumber(number.get(i));
            orderDetail.setPrice(price.get(i));
            total +=number.get(i)*price.get(i);
        }
        order1.setPayPrice(total);
        return orderDao.save(order1);
    }

    //删除订单
    public void deleteOrder(String orderid) throws Exception {
        List<OrderDetail> orderDetail = orderDetailDao.findAllByOrderId(orderid);
        Order order = orderDao.getOne(orderid);
        if (orderDetail == null){
            throw new Exception("删除订单，未找到订单详情。");
        }
        else {
            if (order.getState() == 2||order.getState() == 3||order.getState() == 4)
                throw new Exception("订单未完成，无法删除。");
            for (int i = 0;i<orderDetail.size();i++) {
                orderDetailDao.delete(orderDetail.get(i));
            }
            orderDao.delete(orderid);
        }
    }

    //买家分页查询订单
    public Pagination<Order> userQueryOrder(Integer userId,Integer pageNo,Integer pageSize){
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
    }

    //商家分页查询订单
    public Pagination<Order> sellerQueryOrder(Integer sellerId,Integer pageNo,Integer pageSize){
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
    }

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
        return orderDao.findAllByUserId(userId);
    }

    //根据商家ID查询订单
    public List<Order> findBySellerId(Integer sellerId){
        return orderDao.findAllByUserId(sellerId);
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
    public Order updateOder(String orderId) {
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
    public Order updateDeliver(String orderId,String deliverFirm, String delivernumber) {
        Order order1 = orderDao.findOne(orderId);
        if (order1 == null)
            return null;
        else {
            Date day = new Date();
            order1.setDeliverTime(day);
            order1.setUpdateTime(day);
            order1.setDeliverFirm(deliverFirm);
            order1.setDeliverNumber(delivernumber);
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
}
