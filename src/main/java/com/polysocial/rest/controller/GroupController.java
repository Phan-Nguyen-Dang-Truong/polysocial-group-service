package com.polysocial.rest.controller;

import com.polysocial.consts.GroupAPI;
import com.polysocial.dao.GroupDAO;
import com.polysocial.dao.MemberDAO;
import com.polysocial.dao.UserDAO;
import com.polysocial.dto.GroupDTO;
import com.polysocial.entity.Group;
import com.polysocial.entity.Member;
import com.polysocial.entity.User;
import com.polysocial.service.PostService;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {

    @Autowired
    private PostService postService;
    
    @Autowired
    private GroupDAO groupDao;
    
    @Autowired
    private UserDAO userDao;
    
    @Autowired
    private MemberDAO memberDao;

    @GetMapping(GroupAPI.API_GET_GROUP)
    public GroupDTO getGroup(){
        GroupDTO response = postService.getPost();
        return response;
    }
    @GetMapping(GroupAPI.API_GET_ALL_GROUP)
    public List<Group> getAll(){
    	return postService.getAll();
    }
    
    @GetMapping(GroupAPI.API_GET_ONE+"/{id}")
    public Group getOne(@PathVariable("id") Integer id){
    	return postService.getOne(id);
    }
    
    @GetMapping("/test/{id}")
    public List<Member> getTest(@PathVariable("id")Integer groupId){
    	return groupDao.getMemberInGroup(groupId);
    }
    
    @GetMapping("/")
    public String getT(){
    	return "index";
    }
    
    @PostMapping("/save/{groupid}/{userid}")
    public void save(@PathVariable("groupid")Integer groupId, @PathVariable("userid") Integer userId) {
    	User user = userDao.findById(userId).get();
    	Group group = groupDao.findById(groupId).get();
    	Member member = new Member(4,user, group, false);
    	memberDao.save(member);
    }

}
