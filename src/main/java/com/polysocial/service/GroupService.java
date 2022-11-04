package com.polysocial.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.polysocial.dto.GroupDTO;
import com.polysocial.entity.Groups;
import com.polysocial.entity.Members;
import com.polysocial.entity.Users;

public interface GroupService {
    
    Page<Groups> getAll(Pageable page);
    
    Groups getOne(Long id);
    
    void addMemberToGroup(Users user, Groups group);
    
    String deleteMemberToGroup(Long groupId, Long userId);
    
    Groups deleteGroup(Long groupId);
    
    Object getTeacherFromGroup(Long groupId);
    
    Integer getUserId(String email);
    
    Groups createGroup(Groups group);
    
    Users getOneMemberInGroup(String email, Long groupId);
    
    List<Members> getMemberInGroup(Long id);
    
    List<Groups> findByGroupName(String name);
    
    Users getUserById(Long userId);
    
    void updateGroup(String name, Integer totalMember, String description, Long groupId);
    
    void createExcel(MultipartFile multipartFile) throws IOException;
    
    List<Groups> findByKeywork(String keywork);
    
    Members saveMember(Long userId, Long groupId);
    
    List<Members> getAllGroupByStudent(Long userId);
    
    List<Members> getAllGroupByTeacher(Long userId);
    
            
}
