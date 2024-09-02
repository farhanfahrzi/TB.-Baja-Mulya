package com.tb.ch_sb_1_tb_baja_mulya.security;

import com.tb.ch_sb_1_tb_baja_mulya.dto.response.JwtClaims;
import com.tb.ch_sb_1_tb_baja_mulya.entity.UserAccount;
import com.tb.ch_sb_1_tb_baja_mulya.service.JwtService;
import com.tb.ch_sb_1_tb_baja_mulya.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {
    final String AUTH_HEADER = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, // ini dari client
            HttpServletResponse response, // ini ketika kita kembalikan response
            FilterChain filterChain // si filterchainnya
    ) throws ServletException, IOException {
        try {
//			1. Get jwt token dari header
            String bearerToken = request.getHeader(AUTH_HEADER);
//			log.error("~Bearer Token~ : {}",bearerToken);
//
//			2. pencheck'an token di null dan valid(verify)
            if(bearerToken != null && jwtService.verifyJwtToken(bearerToken)){
//
//			3. if valid, kita claims
                JwtClaims decodeJwt = jwtService.getClaimsByToken(bearerToken);
//				log.error("ini adalah claims : {}",jwtClaims);
//
//			4. kita cari UserAccount berdasarkan accountId di subject jwt. yg sudah di claims
                UserAccount userAccountBySub = userService.getByUserId(decodeJwt.getUserAccountId());

//			5. kita masukkin atau daftarin ke Authentication spring security. biar nantinya di manage
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userAccountBySub.getUsername(),
                        null,
                        userAccountBySub.getAuthorities() // role
                );

//			6. kita save info detail tambahan di securiti contex, seperti ip Address, siapa yg ngehit
                authenticationToken.setDetails(new WebAuthenticationDetails(request));

//			7. Sehabis di seve kita Set Security Contextnya
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }


        }catch (Exception e){
            log.error("Cannot set user authentication: {}", e.getMessage());
        }finally {
//			8. semuanya kit bungkus dan kita panggil si dispatcher servletnya
//			langak ini kalau enggak kita lakukan seperti mandek, stack di filterchain
            filterChain.doFilter(request, response);
//			filterChain.dispatcheServlet()
            // kalau di bahas lain ini seperti next
        }
    }
}




