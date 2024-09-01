package com.tb.ch_sb_1_tb_baja_mulya.repository;

import com.tb.ch_sb_1_tb_baja_mulya.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByUsername(String username);
}
