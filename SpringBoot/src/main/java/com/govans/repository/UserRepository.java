package com.govans.repository;

import org.springframework.data.repository.CrudRepository;

import com.govans.pojo.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String name);
}
