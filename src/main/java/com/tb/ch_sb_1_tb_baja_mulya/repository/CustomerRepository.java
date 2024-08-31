package com.tb.ch_sb_1_tb_baja_mulya.repository;

import com.tb.ch_sb_1_tb_baja_mulya.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findAllByNameLike (String name);
}
