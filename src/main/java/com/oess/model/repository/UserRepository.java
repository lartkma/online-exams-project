package com.oess.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.oess.model.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
