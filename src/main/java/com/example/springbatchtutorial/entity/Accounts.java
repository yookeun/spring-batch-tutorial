package com.example.springbatchtutorial.entity;

import ch.qos.logback.core.util.Loader;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * Accounts
 * author : Yookeun
 * 2025-03-08
 * desc :
 * </pre>
 */
@NoArgsConstructor
@Getter
@ToString
@Entity
public class Accounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String orderItem;
    private Integer price;
    private LocalDate orderDate;
    private LocalDate accountDate;

    public Accounts(Orders orders) {
        this.orderItem = orders.getOrderItem();
        this.price = orders.getPrice();
        this.orderDate = orders.getOrderDate();
        this.accountDate = LocalDate.now();
    }
}
