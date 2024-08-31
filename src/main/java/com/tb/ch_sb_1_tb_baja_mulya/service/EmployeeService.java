package com.tb.ch_sb_1_tb_baja_mulya.service;

import com.tb.ch_sb_1_tb_baja_mulya.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee create (Employee employee);
    Employee getById (String id);
    List<Employee> getAll(String name);
    Employee update (Employee employee);
    void deleteById(String id);
}
