package com.example.springbatchtutorial.repository;

import com.example.springbatchtutorial.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * AccountsRepository
 * author : Yookeun
 * 2025-03-08
 * desc :
 * </pre>
 */
@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {

}
