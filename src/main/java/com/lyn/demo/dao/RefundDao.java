package com.lyn.demo.dao;

import com.lyn.demo.domain.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundDao extends JpaRepository<Refund,String> {
}
