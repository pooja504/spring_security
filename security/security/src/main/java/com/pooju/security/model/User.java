package com.pooju.security.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Entity

@NoArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    private Integer id;
    private String username;
    private String password;
}
