package com.example.springbatchtutorial.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;

/**
 * <pre>
 * Orders
 * author : Yookeun
 * 2025-03-08
 * desc :
 * </pre>
 */
@Entity
@ToString
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String orderItem;
    private Integer price;
    private LocalDate orderDate;
}
