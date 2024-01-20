package com.example.zpi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import com.example.zpi.entities.UserEntity;
import com.example.zpi.repositories.UserRepository;
import com.example.zpi.security.AuthRequest;
import com.example.zpi.security.JwtService;
import com.example.zpi.security.UserInfoService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth") 
public class UserController { 
  
    @Autowired
    private UserInfoService service; 
  
    @Autowired
    private JwtService jwtService; 
  
    @Autowired
    private AuthenticationManager authenticationManager; 
    
    @Autowired
    private UserRepository userRepository;


    //register
    @PostMapping("/register")
    public UserEntity addNewUser(@RequestBody UserEntity userInfo) { 
        userInfo.setPoints(0l);
        return service.addUser(userInfo); 
    }

    //login
    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())); 
        if (authentication.isAuthenticated()) { 
            return jwtService.generateToken(authRequest.getEmail()); 
        } else { 
            throw new UsernameNotFoundException("invalid user request !"); 
        } 
    }
  
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Optional<UserEntity> userProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization"); 
        String token = null; 
        String email = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) { 
            token = authHeader.substring(7); 
            email = jwtService.extractUsername(token); 
        }
        
        if (email != null) { 
            return userRepository.findByEmail(email);
        } 
        throw new UsernameNotFoundException("invalid user request !"); 
    }  
} 
