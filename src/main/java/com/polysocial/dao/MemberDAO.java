package com.polysocial.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polysocial.entity.Member;

public interface MemberDAO extends JpaRepository<Member, Integer>{

}
