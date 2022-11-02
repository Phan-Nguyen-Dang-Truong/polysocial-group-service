package com.polysocial.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.polysocial.entity.Group;
import com.polysocial.entity.Member;

public interface GroupDAO extends JpaRepository<Group, Integer> {
	
	@Query("SELECT o FROM Group o WHERE o.groupId =?1")
	Group getGroup(int id);
	
	@Query("SELECT o FROM Member o WHERE o.group.groupId =?1")
	List<Member> getMemberInGroup(int groupId);

	
	
}

