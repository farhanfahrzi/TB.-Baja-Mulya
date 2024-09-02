package com.tb.ch_sb_1_tb_baja_mulya.service;

import com.tb.ch_sb_1_tb_baja_mulya.dto.response.JwtClaims;
import com.tb.ch_sb_1_tb_baja_mulya.entity.UserAccount;

public interface JwtService {

    String generateToken (UserAccount userAccount);

    boolean verifyJwtToken(String token);

    JwtClaims getClaimsByToken(String token);

}
