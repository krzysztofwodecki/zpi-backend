package com.example.zpi.security;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;

import com.example.zpi.entities.UserEntity;
import com.example.zpi.repositories.UserRepository;

import java.util.Optional; 
  
@Service
public class UserInfoService implements UserDetailsService { 
  
    @Autowired
    private UserRepository repository; 
  
    // @Autowired
    private PasswordEncoder encoder;

    public UserInfoService() {
        this.encoder = new BCryptPasswordEncoder();
    }
    // private final UserRepository repository;
    // private final PasswordEncoder encoder;

    // @Autowired
    // public UserInfoService(UserRepository repository, PasswordEncoder encoder) {
    //     this.repository = repository;
    //     this.encoder = encoder;
    // }

  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
  
        Optional<UserEntity> userDetail = repository.findByEmail(username); 
  
        // Converting userDetail to UserDetails 
        return userDetail.map(UserInfoDetails::new) 
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
    } 
  
    public String addUser(UserEntity userInfo) { 
        userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
        repository.save(userInfo); 
        return "User Added Successfully"; 
    } 
  
  
} 
