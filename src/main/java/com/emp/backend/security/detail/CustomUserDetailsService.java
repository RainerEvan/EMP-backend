// package com.emp.backend.security.detail;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.emp.backend.dao.UserDao;
// import com.emp.backend.model.User;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {

//     @Autowired
//     private UserDao userDao;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         User user = userDao.getUserByUsername(username);
//         return CustomUserDetails.build(user);
//     }
// }
