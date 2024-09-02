package com.tb.ch_sb_1_tb_baja_mulya.service.impl;

import com.tb.ch_sb_1_tb_baja_mulya.constant.UserRole;
import com.tb.ch_sb_1_tb_baja_mulya.dto.request.AuthRequest;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.LoginResponse;
import com.tb.ch_sb_1_tb_baja_mulya.dto.response.RegisterResponse;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Customer;
import com.tb.ch_sb_1_tb_baja_mulya.entity.Role;
import com.tb.ch_sb_1_tb_baja_mulya.entity.UserAccount;
import com.tb.ch_sb_1_tb_baja_mulya.repository.UserAccountRepository;
import com.tb.ch_sb_1_tb_baja_mulya.service.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.List.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Value("${ch_sb_1_tb_baja_mulya.superadmin.username}")
    private String superAdminUsername;
    @Value("${ch_sb_1_tb_baja_mulya.superadmin.password}")
    private String superAdminPassword;

    @Value("${ch_sb_1_tb_baja_mulya.admin.username}")
    private String AdminUsername;
    @Value("${ch_sb_1_tb_baja_mulya.admin.password}")
    private String AdminPassword;

    @Value("${ch_sb_1_tb_baja_mulya.employee.username}")
    private String EmployeeUsername;
    @Value("${ch_sb_1_tb_baja_mulya.employee.password}")
    private String EmployeePassword;


    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initSuperAdmin(){//Super admin dapat melakukan semua perubahan yang ada di aplikasi

        Optional<UserAccount> currentUserSuperAdmin = userAccountRepository.findByUsername(/*"superadmin"*/ superAdminUsername);
        if(currentUserSuperAdmin.isPresent()) return; // kalau ada account super admin, langsung return aja

        //ROLE_SUPER_ADMIN, ROLE_ADMIN, ROLE_CUSTOMER
        Role superAdmin = roleService.getOrSave(UserRole.ROLE_SUPER_ADMIN);
        Role admin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role customer = roleService.getOrSave(UserRole.ROLE_CUSTOMER);
        Role employee = roleService.getOrSave(UserRole.ROLE_EMPLOYEE);

        UserAccount account = UserAccount.builder()
                .username(/*"superadmin"*/ superAdminUsername)
                .password(passwordEncoder.encode(/*"password"*/ superAdminPassword))
                .role(List.of(superAdmin, admin, customer, employee))
                .isEnable(true)
                .build();
        // kita save ke database
        userAccountRepository.save(account);
    }

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initAdmin(){//Admin dapat mengatur product,customer,transaksi

        Optional<UserAccount> currentUserAdmin = userAccountRepository.findByUsername(/*"admin"*/ AdminUsername);
        if(currentUserAdmin.isPresent()) return; // kalau ada account admin, langsung return aja

        //ROLE_ADMIN, ROLE_CUSTOMER
        Role admin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role customer = roleService.getOrSave(UserRole.ROLE_CUSTOMER);
        Role employee = roleService.getOrSave(UserRole.ROLE_EMPLOYEE);

        UserAccount account = UserAccount.builder()
                .username(/*"admin"*/ AdminUsername)
                .password(passwordEncoder.encode(/*"password"*/ AdminPassword))
                .role(List.of(admin, customer, employee))
                .isEnable(true)
                .build();
        // kita save ke database
        userAccountRepository.save(account);

    }

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initEmployee(){//Employee hanya bisa mengupdate data product dan dapat melakukan transaksi jika ada customer yang melakukan pemabayaran cash ke toko langsung

        Optional<UserAccount> currentUserEmployee = userAccountRepository.findByUsername(/*"employee"*/ EmployeeUsername);
        if(currentUserEmployee.isPresent()) return; // kalau ada account employee, langsung return aja

        //ROLE_EMPLOYEE
        Role employee = roleService.getOrSave(UserRole.ROLE_EMPLOYEE);

        UserAccount account = UserAccount.builder()
                .username(/*"employee"*/ EmployeeUsername)
                .password(passwordEncoder.encode(/*"password"*/ EmployeePassword))
                .role(List.of(employee))
                .isEnable(true)
                .build();
        // kita save ke database
        userAccountRepository.save(account);

    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(AuthRequest request) throws DataIntegrityViolationException {
        Role role = roleService.getOrSave(UserRole.ROLE_CUSTOMER);

        // cara encrypt password?
        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(of(role))
                .isEnable(true)
                .build();

        userAccountRepository.saveAndFlush(account);
        Customer customer = Customer.builder()
                .status(true)
                .userAccount(account)
                .build();
        customerService.create(customer);


        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }


    @Transactional(readOnly = true)
    @Override
    public LoginResponse login(AuthRequest request) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(authentication);

        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();

        String token = jwtService.generateToken(userAccount);
        return LoginResponse.builder()
                .token(token)
                .username(userAccount.getUsername())
                .roles(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }


}



