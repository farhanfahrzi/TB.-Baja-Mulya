package com.tb.ch_sb_1_tb_baja_mulya.controller;

import com.tb.ch_sb_1_tb_baja_mulya.constant.APIUrl;
import com.tb.ch_sb_1_tb_baja_mulya.constant.ConstantMessage;
import com.tb.ch_sb_1_tb_baja_mulya.dto.request.UpdateCustomerRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.CommonResponse;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.CustomerResponse;
import com.tb.ch_sb_1_tb_baja_mulya.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CUSTOMER_API)
public class CustomerController {
    public final CustomerService customerService;

//    @PostMapping
//    public ResponseEntity<CommonResponse<Customer>> createNewCustomer(@RequestBody NewCustomerRequest customer) {
//        Customer newCustomer = customerService.create(customer);
//        CommonResponse<Customer> commonResponse = CommonResponse.<Customer>builder()
//                .statusCode(HttpStatus.CREATED.value())
//                .message("Successfully Create New Customer")
//                .data(newCustomer)
//                .build();
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(commonResponse);
//    }


    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable String id) {
        CustomerResponse customer = customerService.getOneById(id);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("successfully fetch data")
                .data(customer)
                .build();
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomer() {
        List<CustomerResponse> customerResponseList = customerService.getAll();
        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("successfully fetch data")
                .data(customerResponseList)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@RequestBody UpdateCustomerRequest customer) {
        CustomerResponse updateCustomer = customerService.update(customer);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("successfully update data")
                .data(updateCustomer)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<String>> deleteCustomerById(@PathVariable String id) {
        customerService.deleteById(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ConstantMessage.DELETESUCCESS + id)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path =APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<String>> updateStatus(
            @PathVariable String id,
            @RequestParam(name = "status") Boolean status
    ){
        customerService.updateStatusById(id,status);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Oke success update status")
                .build();
        return ResponseEntity.ok(response);
    }

}

