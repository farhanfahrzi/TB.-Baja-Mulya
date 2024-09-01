package com.tb.ch_sb_1_tb_baja_mulya.controller;

import com.tb.ch_sb_1_tb_baja_mulya.constant.APIUrl;
import com.tb.ch_sb_1_tb_baja_mulya.dto.request.AuthRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.CommonResponse;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.LoginResponse;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.RegisterResponse;
import com.tb.ch_sb_1_tb_baja_mulya.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.AUTH_API)
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<CommonResponse<?>> registerUser(@RequestBody AuthRequest request){
        RegisterResponse register = authService.register(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully regist data")
                .data(register)
                .build();
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<CommonResponse<?>> login(@RequestBody AuthRequest request){
        LoginResponse loginResponse = authService.login(request);
        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Login Successfully")
                .data(loginResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
