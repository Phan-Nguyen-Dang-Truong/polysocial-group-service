package com.polysocial.rest.controller;

import com.polysocial.consts.GroupAPI;
import com.polysocial.dto.GroupDTO;
import com.polysocial.entity.Groups;
import com.polysocial.entity.Members;
import com.polysocial.entity.Users;
import com.polysocial.repository.GroupRepository;
import com.polysocial.repository.MemberRepository;
import com.polysocial.repository.UserRepository;
import com.polysocial.service.GroupService;
import com.polysocial.service.impl.GroupServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GroupController {
	
    @Autowired
	private GroupServiceImpl groupBusiness = new GroupServiceImpl();
	

    @GetMapping("/api/get/all")
    public ResponseEntity getAll(@RequestParam Integer page, @RequestParam Integer limit){
    	try {
    		Pageable pageable = PageRequest.of(page, limit);
    		Page<Groups> list = groupBusiness.getAll(pageable);
    		return ResponseEntity.ok(list);
    	}catch(Exception e) {
    		return null;
    	}
    	
    }
    
    @GetMapping("/api/get/class")
    public ResponseEntity getOne(@RequestParam Long groupId){
    	try {
    		Groups group = groupBusiness.getOne(groupId);
    		return ResponseEntity.ok(group);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    	
    }
    
    @PostMapping("/api/create-group")
    public ResponseEntity createGroup(@RequestBody Groups group) {
    	try {
    		Object o =  groupBusiness.createGroup(group);
    		return ResponseEntity.ok(o);
    	}catch(Exception e) {
    		return null;
    	}
    	
    }
    
    @PutMapping("/api/update-group")
	public ResponseEntity updateGroup(@RequestBody Groups group) {
		try {
			if(group.getGroupId() == null) return null;
			Object o = groupBusiness.createGroup(group);
			return ResponseEntity.ok(o);
		}catch(Exception e) {
			return null;
		}
	}
    
    @GetMapping("/api/get-teacher")
    public ResponseEntity getTeacherFromGroup(@RequestParam Long groupId) {
    	try{
    		Object object = groupBusiness.getTeacherFromGroup(groupId);
    		return ResponseEntity.ok(object);
    	}catch(Exception e) {
    		return null;
    	}
    }
    
    @GetMapping("/api/get/all-student")
    public ResponseEntity getStudentByIdClass(@RequestParam Long groupId){
    	try {
    		List<Members> list = groupBusiness.getMemberInGroup(groupId);
    		return ResponseEntity.ok(list);
    	}catch(Exception e) {
    		return null;
    	}
    	
    }
    
    @GetMapping("/api/get-student")
    public ResponseEntity getUserInGroup(@RequestParam String email, @RequestParam Long groupId ) {
    	try {
    		Users user =  groupBusiness.getOneMemberInGroup(email, groupId);
    		return ResponseEntity.ok(user);
    	}catch(Exception e) {
    		return null;
    	}
    }
    
    @PostMapping("/api/create-student")
    public ResponseEntity createStudentGroup(@RequestParam Long userId, @RequestParam Long groupId) {
    	try {
    		Members member = groupBusiness.saveMember(userId, groupId);
    		return ResponseEntity.ok(member);
    	}catch(Exception e) {
    		return null;
    	}
    }
    
    @GetMapping("/api/find-group")
    public ResponseEntity findGroup(@RequestParam(required = false, name="keywork")	 String keyword){
    	try {
    		List<Groups> list = groupBusiness.findByKeywork(keyword);
    		return ResponseEntity.ok(list);
    	}catch(Exception e) {
    		return null;
    	}
    }
    
	@DeleteMapping("/api/delete-group")
	public ResponseEntity deleteGroup(@RequestParam Long groupId) {
		try {
			groupBusiness.deleteGroup(groupId);
			return (ResponseEntity) ResponseEntity.ok();
		}catch(Exception e) {
			return null;
		}
	}
    
    @DeleteMapping("/api/remove-student")
    public ResponseEntity removeStudentToClass(@RequestParam Long groupId, @RequestParam Long userId){
    	try {
        	groupBusiness.deleteMemberToGroup(groupId, userId);
        	return (ResponseEntity) ResponseEntity.ok();
    	}catch(Exception e) {
    		return null;
    	}
    }	
    
    
	@PostMapping("/api/create-file")
	public ResponseEntity<Object> uploadFile(@RequestParam(value="file") MultipartFile multipartFile)
			throws IOException {
		try {
			groupBusiness.createExcel(multipartFile);
			return ResponseEntity.ok("OK");
		}catch(Exception e) {
			return null;
		}
	}
	
	
	
        
}
