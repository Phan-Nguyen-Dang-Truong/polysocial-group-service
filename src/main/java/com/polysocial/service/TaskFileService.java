package com.polysocial.service;

import org.springframework.web.multipart.MultipartFile;

import com.polysocial.dto.TaskFileDTO;
import com.polysocial.entity.TaskFile;

public interface TaskFileService {
    
    TaskFileDTO createTaskFile(TaskFile taskFile);

    void deleteTaskFile(Long taskFileId);

    TaskFile saveFile(MultipartFile file, Long exId, Long userId, Long groupId);

    TaskFile updateFile(MultipartFile file, Long exId, Long userId, Long groupId);

    TaskFile getFileUploadGroup(Long exId, Long userId, Long groupId);

}
