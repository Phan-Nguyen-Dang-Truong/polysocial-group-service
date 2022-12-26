package com.polysocial.redis;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.polysocial.dto.MemberGroupDTO;
import com.polysocial.entity.Groups;

@Repository
public class GroupRedisRepo {
	private HashOperations hashOperations;

	public GroupRedisRepo(RedisTemplate redisTemplate) {
		this.hashOperations = redisTemplate.opsForHash();
	}

	public void createList(List<MemberGroupDTO> group) {
		hashOperations.put("GROUP", "listGroup", group);
        // logger.info(String.format("User with ID %s saved", user.getName()));
	}
	
	public Groups getList(Long groupId) {
		return (Groups) hashOperations.get("GROUP", groupId);
	}

	public Map<Long, List<MemberGroupDTO>>getAllList(){
		return hashOperations.entries("GROUP");
	}
	
	public void updateList(Groups group) {
		hashOperations.put("GROUP", group.getGroupId(), group);
	}
	
	public void deleteList(String UsersId) {
		hashOperations.delete("GROUP", UsersId);
	}
	
}
