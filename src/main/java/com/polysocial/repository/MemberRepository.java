package com.polysocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.polysocial.entity.Members;

public interface MemberRepository extends JpaRepository<Members, Long>{
	@Transactional
	@Modifying
	@Query("DELETE FROM Members o WHERE o.groupId =?1 and o.userId =?2")
	void removeUserToGroup(Long groupId, Long userId);
	
	@Query("SELECT o FROM Members o WHERE o.groupId =?1 and isTeacher like 1")
	Object getTeacherFromGroup(Long groupId);
	
	@Modifying
	@Query("SELECT o FROM Members o WHERE o.groupId =?1 and o.isTeacher = false")
	List<Members> getMemberInGroup(Long groupId);
	
	@Query("SELECT o FROM Members o WHERE o.userId =?1 and o.groupId =?2")
	Members getOneMemberInGroup(Long userId, Long groupId);
	

}
