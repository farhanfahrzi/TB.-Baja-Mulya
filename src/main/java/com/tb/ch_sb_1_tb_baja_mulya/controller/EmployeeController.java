package com.tb.ch_sb_1_tb_baja_mulya.controller;

import com.tb.ch_sb_1_tb_baja_mulya.constant.APIUrl;
import com.tb.ch_sb_1_tb_baja_mulya.constant.ConstantMessage;
import com.tb.ch_sb_1_tb_baja_mulya.dto.request.NewEmployeeRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.CommonResponse;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Employee;
import com.tb.ch_sb_1_tb_baja_mulya.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.EMPLOYEE_API)
public class EmployeeController {
    public final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<CommonResponse<Employee>> createNewEmployee(@RequestBody NewEmployeeRequest employee) {
        Employee newEmployee = employeeService.create(employee);

        CommonResponse<Employee> commonResponse = CommonResponse.<Employee>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully Add New Employee")
                .data(newEmployee)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commonResponse);
    }
    // use parthVariable
    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> getById(@PathVariable String id){

        Employee employee = employeeService.getById(id);
        CommonResponse<Employee> commonResponse = CommonResponse.<Employee>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Fetch Data")
                .data(employee)
                .build();

        return ResponseEntity.ok(commonResponse);

    }
    // use request param
    @GetMapping
    public ResponseEntity<CommonResponse<List<Employee>>> getAllProduct(@RequestParam(name = "name", required = false) String name){
        List<Employee> employeeList = employeeService.getAll(name);

        // setting paginationnya

        CommonResponse<List<Employee>> commonResponse = CommonResponse.<List<Employee>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Get All Data")
                .data(employeeList)
                .build();

        return ResponseEntity.ok(commonResponse);
    }
    @PutMapping
    public ResponseEntity<CommonResponse<Employee>> updateEmployee(@RequestBody Employee employee){
        Employee updateEmployee = employeeService.update(employee);

        CommonResponse<Employee> commonResponse = CommonResponse.<Employee>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Update Data")
                .data(updateEmployee)
                .build();

        return ResponseEntity.ok(commonResponse);
    }
    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteById(@PathVariable String id){
        employeeService.deleteById(id);
        String msg = ConstantMessage.DELETESUCCESS + id;
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(msg)
                .build();

        return ResponseEntity.ok(commonResponse);
    }
}

