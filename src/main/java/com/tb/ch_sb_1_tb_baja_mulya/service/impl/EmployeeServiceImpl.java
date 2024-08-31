package com.tb.ch_sb_1_tb_baja_mulya.service.impl;

import com.tb.ch_sb_1_tb_baja_mulya.dto.request.NewEmployeeRequest;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Employee;
import com.tb.ch_sb_1_tb_baja_mulya.repository.EmployeeRepository;
import com.tb.ch_sb_1_tb_baja_mulya.service.EmployeeService;
import com.tb.ch_sb_1_tb_baja_mulya.utils.Validationutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    public final EmployeeRepository employeeRepository;
    private final Validationutil validationutil;

    @Override
    public Employee create(NewEmployeeRequest employeeRequest) {
        validationutil.validate(employeeRequest);
        Employee employee = Employee.builder()
                .name(employeeRequest.getName())
                .section(employeeRequest.getSection())
                .build();
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public Employee getById(String id) {
        return FindByIdOrThrowNotFound(id);
    }

    @Override
    public List<Employee> getAll(String name) {
        if(name!= null){
            return employeeRepository.findAllByNameLike("%" + name +"%");
        }
        return employeeRepository.findAll();
    }

    @Override
    public Employee update(Employee employee) {
        FindByIdOrThrowNotFound(employee.getId());
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public void deleteById(String id) {
        Employee currentEmployee = FindByIdOrThrowNotFound(id);
        employeeRepository.delete(currentEmployee);
    }

    public Employee FindByIdOrThrowNotFound(String id){
        return employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Employee Not Found"));
    }
}