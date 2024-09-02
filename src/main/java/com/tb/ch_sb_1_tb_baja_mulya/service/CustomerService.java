package com.tb.ch_sb_1_tb_baja_mulya.service;

import com.tb.ch_sb_1_tb_baja_mulya.dto.request.NewCustomerRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.request.UpdateCustomerRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.CustomerResponse;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer create(Customer customer);
    Customer getById(String id);
    CustomerResponse getOneById(String id);
    List<CustomerResponse> getAll();
    CustomerResponse update(UpdateCustomerRequest customer);
    void deleteById(String id);

    void updateStatusById(String id,Boolean status);
}

