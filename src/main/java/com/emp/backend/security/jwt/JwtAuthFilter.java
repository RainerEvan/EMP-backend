// package com.emp.backend.security.jwt;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.extern.slf4j.Slf4j;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.util.StringUtils;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.emp.backend.security.detail.CustomUserDetailsService;

// import java.io.IOException;

// @Component
// @Slf4j
// public class JwtAuthFilter extends OncePerRequestFilter {

//     @Autowired
//     private JwtTokenUtil jwtTokenUtil;

//     @Autowired
//     private CustomUserDetailsService customUserDetailsService;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//         throws ServletException, IOException {
//         try {
//             String jwt = parseJwt(request);
//             if (jwt != null && jwtTokenUtil.validateToken(jwt)) {
//                 String username = jwtTokenUtil.getUsernameFromToken(jwt);
//                 UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
//                     userDetails.getAuthorities());
//                 authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//             }
//         } catch (Exception e) {
//             log.error("Cannot set account authentication: {}", e);
//         }
//         filterChain.doFilter(request, response);
//     }

//     private String parseJwt(HttpServletRequest request) {
//         String headerAuth = request.getHeader("Authorization");
//         if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//           return headerAuth.substring(7, headerAuth.length());
//         }
//         return null;
//     }
// }
