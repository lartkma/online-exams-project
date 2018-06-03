package com.oess.model.repository;

import org.springframework.data.repository.Repository;

import com.oess.model.entity.User;

public interface UserRepository extends Repository<User, Long> {

    void save(User user);
    
    User findByEmailIgnoreCase(String email);

}
