package com.polysocial.rest.controller;

import com.polysocial.consts.GroupAPI;
import com.polysocial.dto.GroupDTO;
import com.polysocial.dto.MemberDTO;
import com.polysocial.dto.UserDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class GroupController {
	
    @Autowired
	private GroupServiceImpl groupBusiness = new GroupServiceImpl();
	
    @GetMapping(GroupAPI.API_GET_ALL_GROUP)
	@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page> getAll(@RequestParam Integer page, @RequestParam Integer limit){
    	try {
    		Pageable pageable = PageRequest.of(page, limit);
    		Page<Groups> list = groupBusiness.getAll(pageable);
    		return ResponseEntity.ok(list);
    	}catch(Exception e) {
    		return null;
    	}
    	
    }
    
    @GetMapping(GroupAPI.API_GET_ONE_GROUP)
    public ResponseEntity getOne(@RequestParam Long groupId){
    	try {
    		GroupDTO group = groupBusiness.getOne(groupId);
    		return ResponseEntity.ok(group);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    	
    }
    
    @PostMapping(GroupAPI.API_CREATE_GROUP)
    public ResponseEntity createGroup(@RequestBody Groups group) {
    	try {
    		Object o =  groupBusiness.createGroup(group);
    		return ResponseEntity.ok(o);
    	}catch(Exception e) {
    		return null;
    	}
    	
    }
    
    @PutMapping(GroupAPI.API_UPDATE_GROUP)
	public ResponseEntity updateGroup(@RequestBody Groups group) {
		try {
			if(group.getGroupId() == null) return null;
			Object o = groupBusiness.createGroup(group);
			return ResponseEntity.ok(o);
		}catch(Exception e) {
			return null;
		}
	}
    
    @GetMapping(GroupAPI.API_GET_TEACHER)
    public ResponseEntity getTeacherFromGroup(@RequestParam Long groupId) {
    	try{
    		Object object = groupBusiness.getTeacherFromGroup(groupId);
    		return ResponseEntity.ok(object);
    	}catch(Exception e) {
    		return null;
    	}
    }
    
    @GetMapping(GroupAPI.API_GET_ALL_STUDENT)
    public ResponseEntity getStudentByIdClass(@RequestParam Long groupId){
    	try {
    		List<MemberDTO> list = groupBusiness.getMemberInGroup(groupId);
    		return ResponseEntity.ok(list);
    	}catch(Exception e) {
    		return null;
    	}
    	
    }
    
    @GetMapping(GroupAPI.API_GET_STUDENT)
    public ResponseEntity getUserInGroup(@RequestParam String email, @RequestParam Long groupId ) {
    	try {
    		UserDTO user =  groupBusiness.getOneMemberInGroup(email, groupId);
    		return ResponseEntity.ok(user);
    	}catch(Exception e) {
    		return null;
    	}
    }
    
    @PostMapping(GroupAPI.API_CREATE_STUDENT)
    public ResponseEntity createStudentGroup(@RequestParam Long userId, @RequestParam Long groupId) {
    	try {
    		MemberDTO member = groupBusiness.saveMember(userId, groupId);
    		return ResponseEntity.ok(member);
    	}catch(Exception e) {
    		return null;
    	}
    }
    
    @GetMapping(GroupAPI.API_FIND_GROUP)
    public ResponseEntity findGroup(@RequestParam(required = false, name="keywork")	 String keyword){
    	try {
    		List<GroupDTO> list = groupBusiness.findByKeywork(keyword);
    		return ResponseEntity.ok(list);
    	}catch(Exception e) {
    		return null;
    	}
    }
    
	@DeleteMapping(GroupAPI.API_DELETE_GROUP)
	public ResponseEntity deleteGroup(@RequestParam Long groupId) {
		try {
			GroupDTO group = groupBusiness.deleteGroup(groupId);
			return ResponseEntity.ok(group);
		}catch(Exception e) {
			return null;
		}
	}
    
    @DeleteMapping(GroupAPI.API_REMOVE_STUDENT)
    public ResponseEntity removeStudentToClass(@RequestParam Long groupId, @RequestParam Long userId){
    	try {
        	groupBusiness.deleteMemberToGroup(groupId, userId);
        	return ResponseEntity.ok("OK");
    	}catch(Exception e) {
			e.printStackTrace();
    		return null;
    	}
    }	
    
	@RequestMapping(value = GroupAPI.API_CREATE_GROUP_EXCEL, method = RequestMethod.POST)
	public ResponseEntity uploadFile(@RequestParam MultipartFile file)
			throws IOException {
		try {
			groupBusiness.createExcel(file);
			return ResponseEntity.ok("OK");
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping(GroupAPI.API_GET_ALL_GROUP_STUDENT)
	public ResponseEntity getAllGroupStudent(@RequestParam Long userId) {
		try {
			List<Members> list = groupBusiness.getAllGroupByStudent(userId);
			return ResponseEntity.ok(list);
		}catch(Exception e) {
			return null;
		}
	}
	
	@GetMapping(GroupAPI.API_GET_ALL_GROUP_TEACHER)
	public ResponseEntity getAllGroupTeacher(@RequestParam Long userId) {
		try {
			List<Members> list = groupBusiness.getAllGroupByTeacher(userId);
			return ResponseEntity.ok(list);
		}catch(Exception e) {
			return null;
		}
	}

	@GetMapping(GroupAPI.API_GET_ALL_GROUP_FALSE)
	public ResponseEntity getAllGroupFalse(@RequestParam Integer page, @RequestParam Integer limit) {
		try {
			Pageable pageable = PageRequest.of(page, limit);
			Page<Groups> list = groupBusiness.getAllGroupFalse(pageable);
			return ResponseEntity.ok(list);
		}catch(Exception e) {
			return null;
		}
	}
	
        
}
