package com.tb.ch_sb_1_tb_baja_mulya.dto.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private String customerId;// atau employeeId tergantung siapa yang melakukan transaksi
    private List<TransactionDetailRequest> transactionDetails;
}
