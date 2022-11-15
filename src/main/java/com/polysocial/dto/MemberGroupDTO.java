package com.polysocial.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberGroupDTO implements Serializable{
    private Long groupId;
    private String groupName;
    private Boolean isTeacher;
    private Long totalMember;

    public MemberGroupDTO(Long groupId, String groupName, Boolean isTeacher, Long totalMember) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.isTeacher = isTeacher;
        this.totalMember = totalMember;
    }
}
