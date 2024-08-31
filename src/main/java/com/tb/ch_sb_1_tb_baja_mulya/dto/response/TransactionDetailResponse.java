package com.tb.ch_sb_1_tb_baja_mulya.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class TransactionDetailResponse {
    private String id;
    private String productId;
    private Long productPrice;
    private Integer quantity;
}
