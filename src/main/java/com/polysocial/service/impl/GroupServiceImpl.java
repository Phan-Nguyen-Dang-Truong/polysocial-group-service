package com.polysocial.service.impl;

import com.polysocial.dto.StudentDTO;
import com.polysocial.dto.GroupDTO;
import com.polysocial.dto.MemberDTO;
import com.polysocial.dto.UserDTO;
import com.polysocial.entity.Book;
import com.polysocial.entity.Groups;
import com.polysocial.entity.Members;
import com.polysocial.entity.Users;
import com.polysocial.repository.GroupRepository;
import com.polysocial.repository.MemberRepository;
import com.polysocial.repository.UserRepository;
import com.polysocial.service.ExcelService;
import com.polysocial.service.FileUploadUtil;
import com.polysocial.service.GroupService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public GroupDTO getOne(Long id) {
		Groups group = groupRepo.getGroup(id);
		GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
		return groupDTO;
	}

	@Override
	public void addMemberToGroup(Users user, Groups group) {
		Members member = new Members(user.getUserId(), group.getGroupId(), false);
		memberRepo.save(member);

	}

	@Override
	public void deleteMemberToGroup(Long groupdId, Long userId) {
		memberRepo.removeUserToGroup(groupdId, userId);
	}

	@Override
	public Object getTeacherFromGroup(Long groupId) {
		return memberRepo.getTeacherFromGroup(groupId);
	}

	@Override
	public Integer getUserId(String email) {
		return userRepo.getIdUserByEmail(email);
	}

	@Override
	public GroupDTO createGroup(GroupDTO group) {
		Groups groupEntity = modelMapper.map(group, Groups.class);
		Groups groups = groupRepo.save(groupEntity);
		GroupDTO groupDTO = modelMapper.map(groups, GroupDTO.class);
		return groupDTO;
	}

	@Override
	public List<MemberDTO> getMemberInGroup(Long id) {
		List<Members> listMember =  memberRepo.getMemberInGroup(id);
		List<MemberDTO> listMemberDTO = listMember.stream().map(member -> modelMapper.map(member, MemberDTO.class)).collect(Collectors.toList());
		return listMemberDTO;
	}

	@Override
	public void updateGroup(String name, Integer totalMember, String description, Long groupId) {
		groupRepo.updateGroup(name, totalMember, description, groupId);
	}

	@Override
	public void createExcel(MultipartFile multipartFile) throws IOException {
		ExcelService excel = new ExcelService();
		HashMap<Integer, Users> map = new HashMap();
		FileUploadUtil.saveFile("abc.xlsx", multipartFile);
		String excelFilePath = "./Files/abc.xlsx";
		Long userId = (long) 1;
		String groupName = "";
		List<Book> books = excel.readExcel(excelFilePath);
		for (int i = 0; i < books.size() - 1; i++) {
			userId = Long.parseLong(userRepo.getIdUserByEmail(books.get(i).getEmail()) + "");
			map.put(i, userRepo.findById(userId).get());
		}

		groupName = books.get(0).getGroupName();
		Groups group = new Groups(groupName, Long.parseLong(map.size() + ""));
		groupRepo.save(group);
		map.entrySet().forEach(entry -> {
			Users user = entry.getValue();
			Members member = new Members(user.getUserId(), group.getGroupId(), false);
			memberRepo.save(member);
		});

	}

	@Override
	public GroupDTO deleteGroup(Long groupId) {
		Groups group = groupRepo.findById(groupId).get();
		group.setStatus(false);
		groupRepo.save(group);
		GroupDTO groupDTO = modelMapper.map(group, GroupDTO.class);
		return groupDTO;
	}

	@Override
	public List<GroupDTO> findByKeywork(String keywork) {
		List<Groups> groups = groupRepo.findByGroupName(keywork);
		List<GroupDTO> groupDTO = groups.stream().map(group -> modelMapper.map(group, GroupDTO.class))
				.collect(Collectors.toList());
		return groupDTO;
	}

	@Override
	public UserDTO getOneMemberInGroup(String email, Long groupId) {
		Long userId = (long) userRepo.getIdUserByEmail(email);
		memberRepo.getOneMemberInGroup(userId, groupId);
		Users users = userRepo.findById(userId).get();
		UserDTO userDTO = modelMapper.map(users, UserDTO.class);
		return userDTO;
	}

	@Override
	public MemberDTO saveMember(StudentDTO user) {
		Members member = new Members(user.getUserId(), user.getGroupId(), false);
		MemberDTO memberDTO = modelMapper.map(memberRepo.save(member), MemberDTO.class);
		return memberDTO;
	}

	@Override
	public List<Members> getAllGroupByStudent(Long userId) {
		List<Members> list = memberRepo.getAllGroupByStudent(userId);
		return list;
	}

	@Override
	public List<Members> getAllGroupByTeacher(Long userId) {
		List<Members> list = memberRepo.getAllGroupByTeacher(userId);
		return list;
	}

	@Override
	public Page<Groups> getAllGroupFalse(Pageable page) {
		return groupRepo.getAllGroupFalse(page);
	}

}
