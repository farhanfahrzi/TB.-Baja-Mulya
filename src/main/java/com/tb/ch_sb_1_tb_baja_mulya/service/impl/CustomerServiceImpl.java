package com.tb.ch_sb_1_tb_baja_mulya.service.impl;

import com.tb.ch_sb_1_tb_baja_mulya.entity.Customer;
import com.tb.ch_sb_1_tb_baja_mulya.repository.CustomerRepository;
import com.tb.ch_sb_1_tb_baja_mulya.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    public final CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Customer getById(String id) {
        return FindByIdOrThrowNotFound(id);
    }

    @Override
    public List<Customer> getAll(String name) {
        if(name!= null){
            return customerRepository.findAllByNameLike("%" + name +"%");
        }
        return customerRepository.findAll();
    }


    @Override
    public Customer update(Customer customer) {
        FindByIdOrThrowNotFound(customer.getId());
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public void deleteById(String id) {
        Customer currentCustomer = FindByIdOrThrowNotFound(id);
        customerRepository.delete(currentCustomer);
    }

    public Customer FindByIdOrThrowNotFound(String id){
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer Not Found"));
    }
}