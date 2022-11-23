package com.polysocial.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO2 implements Serializable {
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Long userId;
    private String fullName;
    private String email;
    private String avatar;
    private Boolean confirm;

    public MemberDTO2(Long userId, String fullName, String email, String avatar) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.avatar = avatar;
    }
}
