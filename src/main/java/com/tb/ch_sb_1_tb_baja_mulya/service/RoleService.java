package com.tb.ch_sb_1_tb_baja_mulya.service;

import com.tb.ch_sb_1_tb_baja_mulya.constant.UserRole;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Role;

public interface RoleService {

    Role getOrSave(UserRole role);
}
