package com.lyn.demo.dao;

import com.lyn.demo.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findAllByOrderId(String orderId);
}
