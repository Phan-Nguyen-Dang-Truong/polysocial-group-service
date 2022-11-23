package com.polysocial.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupDTO implements Serializable {

    private Long groupId;

    private String name;

    private Long totalMember;

    private String description;

    private Boolean status = true;

    private LocalDateTime createdDate = LocalDateTime.now();

    private Long adminId;
}
