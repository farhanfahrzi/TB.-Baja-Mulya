package com.tb.ch_sb_1_tb_baja_mulya.entity;

import com.tb.ch_sb_1_tb_baja_mulya.constant.ConstantTable;
import com.tb.ch_sb_1_tb_baja_mulya.constant.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.USER_ROLE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}

