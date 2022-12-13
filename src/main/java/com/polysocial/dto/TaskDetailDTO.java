package com.polysocial.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDetailDTO implements Serializable{ 
    private Long taskId;
    private Long exId;
    private Long userId;
    private Long groupId;
    private String fullName;
    private String url;
    private String avatar;
    private String content;
    private LocalDateTime endDate;;
    private LocalDateTime createdDate;
    private Float mark = 0.0F;
}
