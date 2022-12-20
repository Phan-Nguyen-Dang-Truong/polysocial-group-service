package com.polysocial.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO implements Serializable {

    private Long groupId;

    private String name;

    private Long totalMember;

    private String description;

    private Boolean status = true;

    private String className;

    private LocalDateTime createdDate = LocalDateTime.now();

    private Long adminId;

    private String avatar;

    private List<ContactDTO> listContact;

    private Long roomId;
}
