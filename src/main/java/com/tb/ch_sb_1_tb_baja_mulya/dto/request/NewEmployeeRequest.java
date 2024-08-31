package com.tb.ch_sb_1_tb_baja_mulya.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEmployeeRequest {

    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Section is Required")
    private String section;

//    @NotNull(message = "Nomor telepon is Required")
//    @Pattern(regexp = "^08\\d{9,11}$", message = "Nomor telepon hasus valid dan diawali dengan '08' diikuti oleh 9 hingga 11 angka.")
//    private String mobilePhoneNo;
}

