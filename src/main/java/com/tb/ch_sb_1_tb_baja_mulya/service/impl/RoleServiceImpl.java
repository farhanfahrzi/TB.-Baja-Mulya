package com.tb.ch_sb_1_tb_baja_mulya.service.impl;

import com.tb.ch_sb_1_tb_baja_mulya.constant.UserRole;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Role;
import com.tb.ch_sb_1_tb_baja_mulya.repository.RoleRepository;
import com.tb.ch_sb_1_tb_baja_mulya.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public com.tb.ch_sb_1_tb_baja_mulya.entity.Role getOrSave(UserRole role) {

        return roleRepository.findByRole(role)
                .orElseGet(() -> roleRepository.saveAndFlush(Role.builder()
                        .role(role)
                        .build())
                );
    }

}
