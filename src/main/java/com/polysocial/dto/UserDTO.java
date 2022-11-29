package com.polysocial.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserDTO implements Serializable{
    
    private Long userId;

    private String fullName;

    private String studentCode;

    private String email;

    private String password;

    private String avatar;

    private boolean isActive;

    private LocalDateTime createdDate;

    private Long roleId;
}
