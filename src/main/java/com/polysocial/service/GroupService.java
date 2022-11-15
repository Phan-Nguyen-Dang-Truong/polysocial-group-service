package com.polysocial.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.polysocial.dto.StudentDTO;
import com.polysocial.dto.GroupDTO;
import com.polysocial.dto.MemberDTO;
import com.polysocial.dto.MemberDTO2;
import com.polysocial.dto.MemberGroupDTO;
import com.polysocial.dto.UserDTO;
import com.polysocial.entity.Groups;
import com.polysocial.entity.Members;
import com.polysocial.entity.Users;

public interface GroupService {
    
    Page<Groups> getAll(Pageable page);
    
    GroupDTO getOne(Long id);
    
    void addMemberToGroup(Users user, Groups group);
    
    void deleteMemberToGroup(Long groupId, Long userId);
    
    GroupDTO deleteGroup(Long groupId);
    
    UserDTO getTeacherFromGroup(Long groupId);
    
    Integer getUserId(String email);
    
    GroupDTO createGroup(GroupDTO group);
    
    UserDTO getOneMemberInGroup(String email, Long groupId);
    
    List<UserDTO> getMemberInGroup(Long id);
            
    void updateGroup(String name, Integer totalMember, String description, Long groupId);
    
    void createExcel(MultipartFile multipartFile) throws IOException;
    
    List<GroupDTO> findByKeywork(String keywork);
    
    MemberDTO saveMember(StudentDTO user);
    
    List<MemberGroupDTO> getAllGroupByStudent(Long userId);
    
    List<MemberGroupDTO> getAllGroupByTeacher(Long userId);

    Page<Groups> getAllGroupFalse(Pageable page);
    
            
}
