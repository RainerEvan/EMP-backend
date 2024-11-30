// package com.emp.backend.security.detail;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import com.emp.backend.model.User;

// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// import java.util.Collection;
// import java.util.List;
// import java.util.ArrayList;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// public class CustomUserDetails implements UserDetails {

//     public String username;
//     public String password;
//     public String role;
//     private Collection<? extends GrantedAuthority> authorities;

//     public static CustomUserDetails build(User user){
//         List<GrantedAuthority> authorities = new ArrayList<>();
//         authorities.add(new SimpleGrantedAuthority(user.getRole()));

//         return new CustomUserDetails(
//             user.getUsername(),
//             user.getPassword(),
//             user.getRole(),
//             authorities
//         );
//     }

//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         return authorities;
//     }

//     @Override
//     public String getPassword() {
//         return password;
//     }

//     @Override
//     public String getUsername() {
//         return username;
//     }

//     @Override
//     public boolean isAccountNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         return true;
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true;
//     }

//     @Override
//     public boolean isEnabled() {
//         return true;
//     }
// }
