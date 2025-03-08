package com.example.springbatchtutorial.repository;

import com.example.springbatchtutorial.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * OrdersRepository
 * author : Yookeun
 * 2025-03-08
 * desc :
 * </pre>
 */
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

}
