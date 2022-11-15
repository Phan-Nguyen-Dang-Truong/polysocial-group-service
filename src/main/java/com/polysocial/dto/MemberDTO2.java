package com.polysocial.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO2 implements Serializable {
    private Long userId;
    private String fullName;
    private String email;
    private String avatar;

    public MemberDTO2(Long userId, String fullName, String email, String avatar) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.avatar = avatar;
    }
}
