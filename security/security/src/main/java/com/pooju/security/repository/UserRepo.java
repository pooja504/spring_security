package com.pooju.security.repository;

import com.pooju.security.controller.UserLogin;
import com.pooju.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}

