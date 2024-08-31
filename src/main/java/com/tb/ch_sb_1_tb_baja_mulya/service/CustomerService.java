package com.tb.ch_sb_1_tb_baja_mulya.service;

import com.tb.ch_sb_1_tb_baja_mulya.dto.request.NewCustomerRequest;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer create (NewCustomerRequest customer);
    Customer getById (String id);
    List<Customer> getAll(String name);
    Customer update (Customer customer);
    void deleteById(String id);
}
