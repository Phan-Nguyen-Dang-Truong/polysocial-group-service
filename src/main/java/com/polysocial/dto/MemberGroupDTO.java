package com.polysocial.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.polysocial.entity.Contacts;
import com.polysocial.entity.RoomChats;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberGroupDTO implements Serializable{
    private Long groupId;
    private String groupName;
    private Boolean isTeacher;
    private Long totalMember;
    private List<ContactDTO> listContact;

    public MemberGroupDTO(Long groupId, String groupName, Boolean isTeacher, Long totalMember) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.isTeacher = isTeacher;
        this.totalMember = totalMember;
    }
}
