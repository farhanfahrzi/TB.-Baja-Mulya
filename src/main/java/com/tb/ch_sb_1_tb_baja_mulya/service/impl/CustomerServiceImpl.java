package com.tb.ch_sb_1_tb_baja_mulya.service.impl;

import com.tb.ch_sb_1_tb_baja_mulya.dto.request.NewCustomerRequest;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Customer;
import com.tb.ch_sb_1_tb_baja_mulya.repository.CustomerRepository;
import com.tb.ch_sb_1_tb_baja_mulya.service.CustomerService;
import com.tb.ch_sb_1_tb_baja_mulya.utils.Validationutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    public final CustomerRepository customerRepository;
    private final Validationutil validationutil;

    @Override
    public Customer create(NewCustomerRequest customerRequest) {
        validationutil.validate(customerRequest);
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .mobilePhoneNo(customerRequest.getMobilePhoneNo())
                .address(customerRequest.getAddress())
                .birthDate(customerRequest.getBirthDate())
                .email(customerRequest.getEmail())
                .build();
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
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer Not Found"));
    }
}