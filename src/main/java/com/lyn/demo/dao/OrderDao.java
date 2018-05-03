package com.lyn.demo.dao;


import com.lyn.demo.domain.Order;
import org.aspectj.weaver.ast.Or;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order,String> {
    List<Order> findAllByStateAndUserId(Integer userId,Integer state);
    List<Order> findAllByStateAndSellerId(Integer sellerId,Integer state);
    Page<Order> findAllByUserIdAndUserDeleteState(Integer userId,Pageable pageable,Boolean userDeleteState);
    Page<Order> findAllBySellerId(Integer sellerId,Pageable pageable);
    List<Order> findAllByUserIdAndUserDeleteState(Integer userId,Boolean userDeleteState);
    List<Order> findAllBySellerId(Integer sellerId);
    Order findByOrderId(String orderid);
    Page<Order> findAll(Specification specification, Pageable pageable);
}
