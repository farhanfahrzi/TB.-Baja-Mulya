package com.tb.ch_sb_1_tb_baja_mulya.service.impl;

import com.tb.ch_sb_1_tb_baja_mulya.dto.request.TransactionRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.TransactionDetailResponse;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.TransactionResponse;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Customer;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Product;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Transaction;
import com.tb.ch_sb_1_tb_baja_mulya.entity.TransactionDetail;
import com.tb.ch_sb_1_tb_baja_mulya.repository.TransactionRepository;
import com.tb.ch_sb_1_tb_baja_mulya.service.CustomerService;
import com.tb.ch_sb_1_tb_baja_mulya.service.ProductService;
import com.tb.ch_sb_1_tb_baja_mulya.service.TransactionDetailService;
import com.tb.ch_sb_1_tb_baja_mulya.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    // transactionRepo, transactionDetailService, customerService, productService.
    private final TransactionRepository transactionRepository;
    // private final TransactionDetailRepository transactionDetailRepository; ini salah secara teory
    private final TransactionDetailService transactionDetailService;
    private final CustomerService customerService;
    private final ProductService productService;

    @org.springframework.transaction.annotation.Transactional(rollbackFor = Exception.class) // ini akan auto commit, trus nanti di rollback kalau ada Exception.
    @Override
    public TransactionResponse create(TransactionRequest trxRequest) {
        // cari dan validasi customer yg melakukan transaksi
        Customer customerById = customerService.getById(trxRequest.getCustomerId());

        // 1. save ke transaction
        // Transaction transaction = new Transaction("", customerById, List.of(), new Date());
        Transaction transaction = Transaction.builder()
                .customer(customerById)
                .transDate(new Date())
                .build();
        transactionRepository.saveAndFlush(transaction);

        // 2. save ke transactionDetail
        // start map lamda stream
        List<TransactionDetail> transactionDetails = trxRequest.getTransactionDetails().stream()
                .map(detailRequest -> {
                    Product product = productService.getById(detailRequest.getProductId());

                    // 5											7
                    if (product.getStock() - detailRequest.getQty() < 0) {
                        throw new RuntimeException("Out of stock");
                    }

                    product.setStock(product.getStock() - detailRequest.getQty());

                    return TransactionDetail.builder()
                            .product(product)
                            .transaction(transaction)
                            .qty(detailRequest.getQty())
                            .productPrice(product.getPrice())
                            .build();
                }).toList();
        // end map lamda stream
        transactionDetailService.createBulk(transactionDetails);
        // sampai sini kita sudah berhasil save transaksi kita ke db.

        transaction.setTransactionDetails(transactionDetails);
        // ubah data dari Entity ke Response
        // jadi dari Entity Transaction dan TransactionDetail ke TransactionResponse dan TransactionDetailResponse.

        List<TransactionDetailResponse> trxDetailResponse = transactionDetails.stream()
                .map(detail -> {
                    return TransactionDetailResponse.builder()
                            .id(detail.getId())
                            .productId(detail.getProduct().getId())
                            .productPrice(detail.getProductPrice())
                            .quantity(detail.getQty())
                            .build();
                }).toList();

        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transDate(transaction.getTransDate())
                .transactionDetails(trxDetailResponse)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransactionResponse> getAll() {
        List<Transaction> transactionAll = transactionRepository.findAll();

        // start outer array
        return transactionAll.stream().map(trx -> {
//			trx.get
            // start inner array
            List<TransactionDetailResponse> trxDetailResponse = trx.getTransactionDetails().stream().map(detail -> {
//				detail.get
                return TransactionDetailResponse.builder()
                        .id(detail.getId())
                        .productId(detail.getProduct().getId())
                        .productPrice(detail.getProductPrice())
                        .quantity(detail.getQty())
                        .build();
            }).toList();
            // end inner array

            return TransactionResponse.builder()
                    .id(trx.getId())
                    .customerId(trx.getCustomer().getId())
                    .transDate(trx.getTransDate())
                    .transactionDetails(trxDetailResponse)
                    .build();
        }).toList();
        // end outer array
    }
}
