package com.polysocial.redis;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.polysocial.dto.MemberDTO2;

@Repository
public class MemberDTO2RedisRepo {
    private HashOperations hashOperations;

	public MemberDTO2RedisRepo(RedisTemplate redisTemplate) {
		this.hashOperations = redisTemplate.opsForHash();
	}

	public void createList(List<MemberDTO2> user) {
		hashOperations.put("ListMemberDTO2", "listMemberDTO2", user);
	}
	
	public MemberDTO2 getList(Long groupId) {
		return (MemberDTO2) hashOperations.get("ListMemberDTO2", groupId);
	}

	public Map<Long, List<MemberDTO2>>getAllList(){
		return hashOperations.entries("ListMemberDTO2");
	}
	
	public void updateList(MemberDTO2 member) {
		hashOperations.put("ListMemberDTO2", member.getUserId(), member);
	}
	
	public void deleteList(Long userId) {
		hashOperations.delete("ListMemberDTO2", userId);
	}

}
