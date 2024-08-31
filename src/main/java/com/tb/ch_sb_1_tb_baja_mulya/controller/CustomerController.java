package com.tb.ch_sb_1_tb_baja_mulya.controller;

import com.tb.ch_sb_1_tb_baja_mulya.constant.APIUrl;
import com.tb.ch_sb_1_tb_baja_mulya.constant.ConstantMessage;
import com.tb.ch_sb_1_tb_baja_mulya.dto.request.NewCustomerRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.CommonResponse;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Customer;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Employee;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Product;
import com.tb.ch_sb_1_tb_baja_mulya.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {
    public final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CommonResponse<Customer>> createNewCustomer(@RequestBody NewCustomerRequest customer) {
        Customer newCustomer = customerService.create(customer);
        CommonResponse<Customer> commonResponse = CommonResponse.<Customer>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully Create New Customer")
                .data(newCustomer)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }


    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> getById(@PathVariable String id){
        Customer customer = customerService.getById(id);
        CommonResponse<Customer> commonResponse = CommonResponse.<Customer>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Fetch Data")
                .data(customer)
                .build();
        return ResponseEntity.ok(commonResponse);
    }


    @GetMapping
    public ResponseEntity<CommonResponse<List<Customer>>> getAllCustomer(@RequestParam(name = "name", required = false) String name){
        List<Customer> customerList = customerService.getAll(name);

        CommonResponse<List<Customer>> commonResponse = CommonResponse.<List<Customer>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Get All Data")
                .data(customerList)
                .build();

        return ResponseEntity.ok(commonResponse);
    }
    @PutMapping
    public ResponseEntity<CommonResponse<Customer>> updateCustomer(@RequestBody Customer customer){
        Customer updateCustomer = customerService.update(customer);

        CommonResponse<Customer> commonResponse = CommonResponse.<Customer>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Update Data")
                .data(updateCustomer)
                .build();

        return ResponseEntity.ok(commonResponse);
    }
    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteById(@PathVariable String id){
        customerService.deleteById(id);
        String msg = ConstantMessage.DELETESUCCESS + id;
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(msg)
                .build();

        return ResponseEntity.ok(commonResponse);
    }
}
