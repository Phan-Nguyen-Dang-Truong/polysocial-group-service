package com.polysocial.service;

import org.springframework.web.multipart.MultipartFile;

import com.polysocial.dto.TaskExDTO;
import com.polysocial.entity.TaskEx;

public interface TaskExService {
    
    TaskExDTO createTaskEx(TaskEx taskExDTO);

    TaskExDTO updateTaskEx(Long taskId,TaskEx taskExDTO);

    void deleteTaskEx(Long taskId);

}
