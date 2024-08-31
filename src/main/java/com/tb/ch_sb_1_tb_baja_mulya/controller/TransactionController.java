package com.tb.ch_sb_1_tb_baja_mulya.controller;

import com.tb.ch_sb_1_tb_baja_mulya.constant.APIUrl;
import com.tb.ch_sb_1_tb_baja_mulya.dto.request.TransactionRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.TransactionResponse;
import com.tb.ch_sb_1_tb_baja_mulya.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.TRANSACTION_API)
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public TransactionResponse createNewTransaction(
            @RequestBody TransactionRequest trxRequest
    ){
        // logic untuk memanggil service
        return transactionService.create(trxRequest);
    }

    @GetMapping
    public List<TransactionResponse> getAllTransaction() {
        // logic untuk memanggil service
        return transactionService.getAll();
    }

}
