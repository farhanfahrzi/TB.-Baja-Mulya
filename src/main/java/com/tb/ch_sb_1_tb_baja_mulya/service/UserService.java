package com.tb.ch_sb_1_tb_baja_mulya.service;

import com.tb.ch_sb_1_tb_baja_mulya.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserAccount getByUserId(String id);

    UserAccount getByContext();
}
