package com.tb.ch_sb_1_tb_baja_mulya.controller;

import com.tb.ch_sb_1_tb_baja_mulya.entity.Customer;
import com.tb.ch_sb_1_tb_baja_mulya.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {
    public final CustomerService customerService;

    @RequestMapping
    public Customer create(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public Customer getById(@PathVariable String id){
        return customerService.getById(id);
    }

    @GetMapping
    public List<Customer> getAll(@RequestParam(name = "name", required = false)String name){
        return customerService.getAll(name);
    }

    @PutMapping
    public Customer update (@RequestBody Customer customers){
        return customerService.update(customers);
    }

    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public String delete (@PathVariable String id){
        customerService.deleteById(id);
        return ConstantMessage.DELETESUCCESS+id;
    }
}
