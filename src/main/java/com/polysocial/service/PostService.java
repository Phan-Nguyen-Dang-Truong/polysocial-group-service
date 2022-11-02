package com.polysocial.service;

import java.util.List;

import com.polysocial.dto.GroupDTO;
import com.polysocial.entity.Group;
import com.polysocial.entity.User;

public interface PostService {

    GroupDTO getPost();
    
    List<Group> getAll();
    
    Group getOne(int id);
    
    void addMemberToGroup(User user, Group group);
}
