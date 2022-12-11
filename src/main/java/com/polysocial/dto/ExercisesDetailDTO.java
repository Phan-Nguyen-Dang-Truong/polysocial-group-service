package com.polysocial.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExercisesDetailDTO {
    
    private Long exId;

    private String content;

    private LocalDateTime endDate;
    
    private String deadline;

    private Boolean status = true;

    private Long groupId;

    private String url;

    private Boolean isSubmit;


    public void formatEndDate() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	this.endDate = LocalDateTime.parse(deadline, formatter);
    	
    }
    
}
