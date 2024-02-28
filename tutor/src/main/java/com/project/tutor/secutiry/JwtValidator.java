package com.project.tutor.secutiry;

import com.project.tutor.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.JwtException;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

@Component
public class JwtValidator extends OncePerRequestFilter {
    private JwtProvider jwtProvider;
    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }
    private UserService userService;

    @Autowired
    public void setUserService (UserService userService){
        this.userService = userService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String jwt = ((HttpServletRequest) request).getHeader(JwtConstant.JWT_HEADER);
//        if (jwt != null) {
//            jwt = jwt.substring(7);
//            try {
//                // AUTHORIZATION JWT
//                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SERECT_KEY.getBytes());
//                // DECRYPTION JWT
//                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
//                // GET VALUE OF USERNAME
//                String username = String.valueOf(claims.get("username"));
//                // GET VALUE OF AUTHORITIES TO PAYLOAD OF JWT
//                String authorities = String.valueOf(claims.get("authorities"));
//
//                System.out.println(authorities);
//
//                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
//                Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,auths);
//
//                // SET AUTHENTICATION IN THE SECURITY CONTEXT
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            } catch (JwtException e) {
//                throw new BadCredentialsException("Invalid token ... from JWT header");
//            }
//        }
//        filterChain.doFilter(request, response);
//    }

//    System.out.println("1");
//    String jwt = ((HttpServletRequest) request).getHeader(JwtConstant.JWT_HEADER);
//        System.out.println("2");
//        if (jwt != null) {
//        System.out.println("3");
//        jwt = jwt.substring(7);
//        System.out.println("4");
//        try {
//            // AUTHORIZATION JWT
//            System.out.println("5");
//            SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SERECT_KEY.getBytes());
//            System.out.println("6");
//            // DECRYPTION JWT
//            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
//            System.out.println("7");
//            // GET VALUE OF USERNAME
//            String username = String.valueOf(claims.get("username"));
//            System.out.println("8");
//            // GET VALUE OF AUTHORITIES TO PAYLOAD OF JWT
//            String authorities = String.valueOf(claims.get("authorities"));
//            System.out.println("9");
//
//            System.out.println("authorities : " + authorities);
//            System.out.println("10");
//            List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
//            System.out.println("11");
//            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,auths);
//            System.out.println("12");
//            // SET AUTHENTICATION IN THE SECURITY CONTEXT
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        } catch (JwtException e) {
//            throw new BadCredentialsException("Invalid token ... from JWT header");
//        }
//    }
//        filterChain.doFilter(request, response);
//}
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader!=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtProvider.extractUsername(token);
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (jwtProvider.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
