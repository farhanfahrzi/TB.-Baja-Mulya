package com.tb.ch_sb_1_tb_baja_mulya.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtClaims {
    // ini adalab data yg kita dapatkan dari Payload: Data Jwtnya
    private String userAccountId;
    private List<String> roles;
}

