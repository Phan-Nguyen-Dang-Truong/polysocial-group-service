package com.polysocial.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polysocial.entity.User;

public interface UserDAO extends JpaRepository<User, Integer>{

}
