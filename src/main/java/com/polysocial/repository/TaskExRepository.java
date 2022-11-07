package com.polysocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.polysocial.entity.TaskEx;

@Repository
public interface TaskExRepository extends JpaRepository<TaskEx, Long> {
    
    @Query("SELECT o FROM TaskEx o WHERE o.exercise.exId = ?1 AND o.member.userId = ?2 AND o.member.groupId = ?3")
    TaskEx findByExIdAndUserIdAndGroupId(Long exId, Long userId, Long groupId);
}
