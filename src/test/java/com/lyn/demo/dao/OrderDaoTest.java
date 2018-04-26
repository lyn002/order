package com.lyn.demo.dao;

import com.lyn.demo.domain.Order;
import com.lyn.demo.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void findByOderId() {
        OrderService orderService = new OrderService();
        Order order = new Order();
        order.setOrderId("qaaa");
        orderDao.save(order);

    }
}
