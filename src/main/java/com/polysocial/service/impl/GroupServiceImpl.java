package com.polysocial.service.impl;

import com.polysocial.dto.GroupDTO;
import com.polysocial.entity.Book;
import com.polysocial.entity.Groups;
import com.polysocial.entity.Members;
import com.polysocial.entity.Post;
import com.polysocial.entity.Users;
import com.polysocial.repository.GroupRepository;
import com.polysocial.repository.MemberRepository;
import com.polysocial.repository.UserRepository;
import com.polysocial.service.ExcelService;
import com.polysocial.service.FileUploadUtil;
import com.polysocial.service.GroupService;
import com.polysocial.utils.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GroupRepository groupRepo;
    @Autowired
    private MemberRepository memberRepo;
    @Autowired
    private UserRepository userRepo;
    

	@Override
	public Page<Groups> getAll(Pageable page) {
		return groupRepo.getAll(page);
	}

	@Override
	public Groups getOne(Long id) {
		return groupRepo.getGroup(id);
	}

	@Override
	public void addMemberToGroup(Users user, Groups group) {
		Members member = new Members(user.getUserId(), group.getGroupId(), false);
		memberRepo.save(member);
		
	}

	@Override
	public void deleteMemberToGroup(Long groupdId, Long userId) {
		memberRepo.removeUserToGroup(groupdId, userId);;
	}

	@Override
	public Object getTeacherFromGroup(Long groupId) {
		return memberRepo.getTeacherFromGroup(groupId);
	}

	@Override
	public Integer getUserId(String email) {
		return userRepo.getUserId(email);
	}

	@Override
	public Groups createGroup(Groups group) {
		return groupRepo.save(group);
	}

	@Override
	public List<Members> getMemberInGroup(Long id) {
		return memberRepo.getMemberInGroup(id);
	}

	@Override
	public List<Groups> findByGroupName(String name) {
		return groupRepo.findByGroupName(name);
	}

	@Override
	public Users getUserById(Long userId) {
		return userRepo.findById(userId).get();
	}

	@Override
	public void updateGroup(String name, Integer totalMember, String description, Long groupId) {
		groupRepo.updateGroup(name, totalMember, description, groupId);
	}

	@Override
	public void createExcel(MultipartFile multipartFile) throws IOException {
		ExcelService excel = new ExcelService();
		HashMap<Integer, Users> map = new HashMap();
//		long size = multipartFile.getSize();
		String filecode = FileUploadUtil.saveFile("student_in_class.xlsx", multipartFile);
		String excelFilePath = "./Files/student_in_class.xlsx";
		Long userId = (long) 1;
		String groupName = "";
		List<Book> books = excel.readExcel(excelFilePath);
		for (int i = 0; i < books.size()-1; i++) {
			userId = Long.parseLong(userRepo.getUserId(books.get(i).getEmail())+"");
			map.put(i, userRepo.findById(userId).get());
		}

		groupName = books.get(0).getGroupName();
		Groups group = new Groups(groupName, Long.parseLong(map.size()+""));
		groupRepo.save(group);
		map.entrySet().forEach(entry -> {
			Users user = entry.getValue();
			Members member = new Members(user.getUserId(), group.getGroupId(), false);
			memberRepo.save(member);
		});
		
	}

	@Override
	public void deleteGroup(Long groupId) {
		Groups group = groupRepo.findById(groupId).get();
		group.setStatus(false);
		groupRepo.save(group);
	}

	@Override
	public List<Groups> findByKeywork(String keywork) {
		return groupRepo.findByGroupName(keywork);
	}

	@Override
	public Users getOneMemberInGroup(String email, Long groupId) {
		Long userId = (long)userRepo.getUserId(email);
		Members member = memberRepo.getOneMemberInGroup(userId, groupId);
		return userRepo.findById(member.getUserId()).get();
	}	

	@Override
	public Members saveMember(Long userId, Long groupId) {
	    	Members member = new Members(userId, groupId, false);
	    	return memberRepo.save(member);
		
	}	
	
}
