package com.polysocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.polysocial.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long>{

	Users findByEmailAndIsActive(String email, boolean isActive);

    Users findByUserIdAndIsActive(Long userId, boolean isActive);

    Users findByUserId(Long userId);

	@Query("SELECT o.userId FROM Users o WHERE o.email =?1")
	Integer getIdUserByEmail(String email);
	
}
