package com.tb.ch_sb_1_tb_baja_mulya.entity;

import com.tb.ch_sb_1_tb_baja_mulya.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.USER_ACCOUNT)
public class UserAccount implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_enable")
    private Boolean isEnable;

    // ROLE
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> role;
    // lazy, ketika di panggil. pertama hanya menampilkan main entitynya aja, untuk relasinya di tampikan nanti setelah di panggil
    // eager, kalau ini dari awal sudah menampikan semuanya


    // part ini yg nanti akan ngemange UserAccount kita
//	GrantedAuthority{
//		authorities: ROLE_CUSTOMER
//	}
//	GrantedAuthority{
//		authorities: ROLE_ADMIN
//	}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream().map(role -> new SimpleGrantedAuthority(
                role
                        .getRole()
                        .name())).toList();
    }

    @Override
    public String getUsername() { // kalau mau pakai email, propertynya boleh di ganti namanya jadi email. tapi getternya tetep getUsername()
        return username;  /// email
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }
}


