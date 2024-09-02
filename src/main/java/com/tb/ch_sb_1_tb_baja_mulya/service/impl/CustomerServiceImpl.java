package com.tb.ch_sb_1_tb_baja_mulya.service.impl;

import com.tb.ch_sb_1_tb_baja_mulya.dto.request.UpdateCustomerRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.CustomerResponse;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Customer;
import com.tb.ch_sb_1_tb_baja_mulya.entity.UserAccount;
import com.tb.ch_sb_1_tb_baja_mulya.repository.CustomerRepository;
import com.tb.ch_sb_1_tb_baja_mulya.service.CustomerService;
import com.tb.ch_sb_1_tb_baja_mulya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    public final CustomerRepository customerRepository;
    private final UserService userService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public Customer getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream().map(this::convertCustomerToCustomerResponse).toList();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse update(UpdateCustomerRequest customerRequest) {
        Customer currentCustomer = findByIdOrThrowNotFound(customerRequest.getId());

        UserAccount userByContext = userService.getByContext();

        // jadi kita check userAccount yg ada di Security Context dan userAccount yg ada di inputan dari client apakah sama?
        // kalau sama berarti bisa edit. kalau tidak sama berarti kita lempar error. mungkin commentnya user not found
        // enakan kita handle itu error dulu
        if(!userByContext.getId().equals(currentCustomer.getUserAccount().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "user not found");
        }

        // kalau otoritas atau Authorizationnya sesuai, kalian memiliki akses yg benar. maka bisa edit datanya

        currentCustomer.setName(customerRequest.getName());
        currentCustomer.setMobilePhoneNo(customerRequest.getMobilePhoneNo());
        currentCustomer.setAddress(customerRequest.getAddress());
        currentCustomer.setBirthDate(Date.valueOf(customerRequest.getBirthDate()));
        customerRepository.saveAndFlush(currentCustomer);
        return convertCustomerToCustomerResponse(currentCustomer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Customer currentCustomer = findByIdOrThrowNotFound(id);
        customerRepository.delete(currentCustomer);
    }

    public Customer findByIdOrThrowNotFound(String id){
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found"));
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponse getOneById(String id) {
        Customer customerById = findByIdOrThrowNotFound(id);
        return convertCustomerToCustomerResponse(customerById);
    }

    private CustomerResponse convertCustomerToCustomerResponse(Customer customer) {

        String userId;
        if (customer.getUserAccount() == null){
            userId = null;
        } else {
            userId = customer.getUserAccount().getId();
        }

        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .mobilePhoneNo(customer.getMobilePhoneNo())
                .status(customer.getStatus())
                .userAccountId(userId)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusById(String id, Boolean status) {
        findByIdOrThrowNotFound(id);
        customerRepository.updateStatus(id, status);
    }
}
