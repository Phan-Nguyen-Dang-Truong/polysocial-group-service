package com.polysocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.polysocial.entity.TaskFile;

@Repository
public interface TaskFileRepository extends JpaRepository<TaskFile, Long> {
    
    @Query("SELECT t FROM TaskFile t WHERE t.task.taskId =?1")
    TaskFile findByTaskEx(Long taskId);

}
    
