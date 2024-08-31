package com.tb.ch_sb_1_tb_baja_mulya.entity;

import com.tb.ch_sb_1_tb_baja_mulya.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.EMPLOYEE)
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "section")
    private String section;

}
