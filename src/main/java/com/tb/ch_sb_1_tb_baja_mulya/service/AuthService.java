package com.tb.ch_sb_1_tb_baja_mulya.service;

import com.tb.ch_sb_1_tb_baja_mulya.dto.request.AuthRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.LoginResponse;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest request);
    LoginResponse login(AuthRequest request);
}

