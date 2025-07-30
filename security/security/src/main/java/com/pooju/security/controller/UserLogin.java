package com.pooju.security.controller;


import com.pooju.security.model.User;
import com.pooju.security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLogin {

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userRepo.save(user);
    }
    @PostMapping("/login")
    public String userlogin(@RequestBody User userl){
        User byUsername = userRepo.findByUsername(userl.getUsername());
        if (byUsername==null){
            return "no user";
        }else {
            return "susess";
        }
    }
}
