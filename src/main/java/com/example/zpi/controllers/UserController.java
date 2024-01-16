package com.example.zpi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.example.zpi.entities.UserEntity;
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
    
    //register
    @PostMapping("/register") 
    public String addNewUser(@RequestBody UserEntity userInfo) { 
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
    public String userProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization"); 
        String token = null; 
        String email = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) { 
            token = authHeader.substring(7); 
            email = jwtService.extractUsername(token); 
        } 
    } 
    
    
  
} 
