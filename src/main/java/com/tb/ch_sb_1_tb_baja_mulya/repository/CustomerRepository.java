package com.tb.ch_sb_1_tb_baja_mulya.repository;

import com.tb.ch_sb_1_tb_baja_mulya.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Modifying// haru dikasih, untuk merubah data
    // query manual / HQL
    @Query(value = "UPDATE m_customer SET status= :status WHERE id= :id", nativeQuery = true)
    void updateStatus(@Param("id") String id, @Param("status") Boolean status);
}
