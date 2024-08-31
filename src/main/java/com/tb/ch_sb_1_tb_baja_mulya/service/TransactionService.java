package com.tb.ch_sb_1_tb_baja_mulya.service;

import com.tb.ch_sb_1_tb_baja_mulya.dto.request.TransactionRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse create(TransactionRequest trxRequest);
    List<TransactionResponse> getAll();
}
