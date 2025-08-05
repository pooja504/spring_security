package com.pooju.security.service;


import com.pooju.security.model.User;
import com.pooju.security.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JasonToken jasonToken;

    public UserService(UserRepo userRepo, AuthenticationManager authenticationManager, JasonToken jasonToken) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.jasonToken = jasonToken;
    }

    public String verify(User userl) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userl.getUsername(), userl.getPassword()
                )
        );

        //User byUsername =userRepo.findByUsername(userl.getUsername());
        if (authenticate.isAuthenticated()){
            return jasonToken.CreateJasonToken(userl);
        }else {
            return "fail";
        }
    }
}
