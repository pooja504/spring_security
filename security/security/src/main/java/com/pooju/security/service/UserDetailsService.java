package com.pooju.security.service;

import com.pooju.security.model.User;
import com.pooju.security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService
{
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByUsername(username);
        if (Objects.isNull(user)){
            System.out.print("no user found");
            throw new UsernameNotFoundException("no user found");
        }
        return new com.pooju.security.UserDetails(user);
    }
}
