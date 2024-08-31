package com.tb.ch_sb_1_tb_baja_mulya.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private String customerId;// atau employeeId tergantung siapa yang melakukan transaksi
    private Date transDate;
    private List<TransactionDetailResponse> transactionDetails;
}
