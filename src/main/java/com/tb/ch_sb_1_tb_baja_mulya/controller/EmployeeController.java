package com.tb.ch_sb_1_tb_baja_mulya.controller;

import com.tb.ch_sb_1_tb_baja_mulya.entity.Employee;
import com.tb.ch_sb_1_tb_baja_mulya.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.EMPLOYEE_API)
public class EmployeeController {
    public final EmployeeService employeeService;

    @RequestMapping
    public Employee create(@RequestBody Employee employee){
        return employeeService.create(employee);
    }

    @GetMapping(path = APIUrl.PATH_VAR_ID)
    public Employee getById(@PathVariable String id){
        return employeeService.getById(id);
    }

    @GetMapping
    public List<Employee> getAll(@RequestParam(name = "name", required = false)String name){
        return employeeService.getAll(name);
    }

    @PutMapping
    public Employee update (@RequestBody Employee employee){
        return employeeService.update(employee);
    }

    @DeleteMapping(path = APIUrl.PATH_VAR_ID)
    public String delete (@PathVariable String id){
        employeeService.deleteById(id);
        return ConstantMessage.DELETESUCCESS+id;
    }
}

