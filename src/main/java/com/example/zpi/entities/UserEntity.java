package com.example.zpi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@JsonIgnoreProperties({"password"})
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String roles; 
    private Long points;

    public UserEntity(String email, String password){
        this.email = email;
        this.password = password;
        this.points = (long) 0;
    }

    public UserEntity(String email, String password, String roles){
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.points = (long) 0;
    }

    public UserEntity(String email, String password, String roles, Long points){
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.points = points;
    }
}

