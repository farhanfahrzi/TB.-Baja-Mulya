package com.tb.ch_sb_1_tb_baja_mulya.service;

import com.tb.ch_sb_1_tb_baja_mulya.entity.Product;

import java.util.List;

public interface ProductService {

    Product create (Product product);
    Product getById (String id);
    List<Product> getAll(String name);
    Product update (Product product);
    void deleteById(String id);
}
