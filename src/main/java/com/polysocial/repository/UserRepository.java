package com.polysocial.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.polysocial.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long>{

	@Query("SELECT o.userId FROM Users o WHERE o.email =?1")
	Integer getUserId(String email);
	
}
