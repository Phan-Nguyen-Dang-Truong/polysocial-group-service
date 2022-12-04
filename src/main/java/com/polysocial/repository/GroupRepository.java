package com.polysocial.repository;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.polysocial.entity.Groups;
@Transactional
public interface GroupRepository extends JpaRepository<Groups, Long> {
	@Query("SELECT o FROM Groups o WHERE o.groupId =?1")
	Groups getGroup(Long id);
	
	@Query("SELECT o FROM Groups o WHERE o.status = 1 and o.totalMember not like 0")
	Page<Groups> getAll(Pageable page);
	
	@Query("SELECT o FROM Groups o WHERE o.name LIKE %?1%")
	List<Groups> findByGroupName(String name);
	
	
	@Modifying
	@Query("UPDATE FROM Groups o SET o.name =?1 , o.totalMember =?2 , o.description =?3 WHERE o.groupId =?4")
	void updateGroup(String name, Integer totalMember, String description, Long groupId);

	@Query("SELECT o FROM Groups o WHERE o.status = 0")
	Page<Groups> getAllGroupFalse(Pageable page);
}

