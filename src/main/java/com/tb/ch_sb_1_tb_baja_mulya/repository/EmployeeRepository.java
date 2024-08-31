package com.tb.ch_sb_1_tb_baja_mulya.repository;

import com.tb.ch_sb_1_tb_baja_mulya.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findAllByNameLike(String name);
}