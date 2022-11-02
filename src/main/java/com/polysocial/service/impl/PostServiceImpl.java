package com.polysocial.service.impl;

import com.polysocial.dao.GroupDAO;
import com.polysocial.dao.MemberDAO;
import com.polysocial.dto.GroupDTO;
import com.polysocial.entity.Group;
import com.polysocial.entity.Member;
import com.polysocial.entity.Post;
import com.polysocial.entity.User;
import com.polysocial.service.PostService;
import com.polysocial.utils.Logger;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GroupDAO groupDao;
    
    @Autowired
    private MemberDAO memberDao;
    
    @Override
    public GroupDTO getPost() {
        Logger.info("Start getPost service");
        try {
            Post exercise = new Post("Cao Thăng", "Mậu Phi", "Hoàng Duy", "Đăng Trường");
            GroupDTO response = modelMapper.map(exercise, GroupDTO.class);
            return response;
        }catch (Exception ex){
            Logger.error("Get post exception: " + ex);
            return null;
        }
    }

	@Override
	public List<Group> getAll() {
		return groupDao.findAll();
	}

	@Override
	public Group getOne(int id) {
		return groupDao.getGroup(id);
	}

	@Override
	public void addMemberToGroup(User user, Group group) {
//		Member member = new Member(1, user, group, false);
		Member member = new Member(user, group, false);
		memberDao.save(member);
		
	}
}
