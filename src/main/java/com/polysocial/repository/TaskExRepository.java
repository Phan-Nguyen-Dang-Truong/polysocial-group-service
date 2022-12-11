package com.polysocial.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.polysocial.entity.TaskEx;

@Repository
public interface TaskExRepository extends JpaRepository<TaskEx, Long> {
    
    @Query("SELECT o FROM TaskEx o WHERE o.exercise.exId = ?1 AND o.member.userId = ?2 AND o.member.groupId = ?3")
    TaskEx findByExIdAndUserIdAndGroupId(Long exId, Long userId, Long groupId);

    @Query("SELECT o FROM TaskEx o WHERE o.exercise.exId = ?1 AND o.member.groupId = ?2")
    List<TaskEx> findByExIdAndGroupId(Long exId, Long groupId);

    @Transactional
    @Modifying
    @Query("UPDATE TaskEx o SET o.mark = ?1 WHERE o.exercise.exId = ?2 AND o.member.userId = ?3 AND o.member.groupId = ?4")
    void updateMark(Float mark, Long exId, Long userId, Long groupId);


}
