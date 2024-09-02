package com.tb.ch_sb_1_tb_baja_mulya.dto.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCustomerRequest {
    private String name;

    @Pattern(regexp = "^08\\d{9,11}$", message = "Nomor telepon hasus valid dan diawali dengan '08' diikuti oleh 9 hingga 11 angka.")
    private String mobilePhoneNo;

    private String address;

    @NotNull(message = "Format tanggal harus 'yyyy-MM-dd'")
    @Temporal(TemporalType.DATE) // Menggunakan DATE untuk menyimpan hanya tanggal
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Pattern(regexp = "^\\\\d{4}-\\\\d{2}-\\\\d{2}$", message = "Format tanggal harus 'yyyy-MM-dd'")
    private Date birthDate;

//	@Email(message = "Format email tidak valid")
//	private String email;

    private String status;
}


