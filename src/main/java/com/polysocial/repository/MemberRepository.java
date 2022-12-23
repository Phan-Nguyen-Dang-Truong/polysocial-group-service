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
	Members getTeacherFromGroup(Long groupId);
	
	@Query("SELECT o FROM Members o WHERE o.groupId =?1 and o.isTeacher = 0 and o.confirm =1")
	List<Members> getMemberInGroup(Long groupId);
	
	@Query("SELECT o FROM Members o WHERE o.userId =?1 and o.groupId =?2 and o.confirm=1")
	Members getOneMemberInGroup(Long userId, Long groupId);
	
	@Query("SELECT o FROM Members o WHERE o.userId =?1 and o.isTeacher = 0 and o.confirm = 1")
	List<Members> getAllGroupByStudent(Long userId);
	
	@Query("SELECT o FROM Members o WHERE o.userId =?1 and o.isTeacher =1")
	List<Members> getAllGroupByTeacher(Long userId);
	
	@Query("SELECT o FROM Members o WHERE o.groupId =?1 and o.confirm=0 and o.isTeacher=0")
	List<Members> getUserJoin(Long groupId);

	@Modifying
	@Transactional
	@Query("UPDATE Members o SET o.confirm = 1 WHERE o.groupId =?1 and o.userId =?2")
	void confirmUser(Long groupId, Long userId);

	@Modifying
	@Transactional
	@Query("UPDATE Members o SET o.confirm = 1 WHERE o.groupId =?1")
	void confirmAllUser(Long groupId);

	@Modifying
	@Transactional
	@Query("UPDATE Members o SET o.confirm = 0 WHERE o.groupId =?1 and o.userId =?2")
	void memberLeaveGroup(Long groupId, Long userId);

	@Query("SELECT o FROM Members o WHERE o.userId =?1 and o.confirm=1")
	List<Members> getAllGroupByUser(Long userId);

	@Query("SELECT count(o) FROM Members o WHERE o.groupId =?1 and o.confirm=1")
	Long countMemberInGroup(Long groupId);
}
