package com.tb.ch_sb_1_tb_baja_mulya.repository;

import com.tb.ch_sb_1_tb_baja_mulya.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByNameLike(String name);
}
