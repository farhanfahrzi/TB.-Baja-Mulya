package com.tb.ch_sb_1_tb_baja_mulya.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tb.ch_sb_1_tb_baja_mulya.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.TRANSACTION_DETAIL)
@Builder
public class TransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "trx_id", nullable = false)
    @JsonBackReference
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private  Product product;

    @Column(name = "product_price", updatable = false, nullable = false)
    private  Long productPrice;

    @Column(name = "qty", nullable = false)
    private Integer qty;

}
