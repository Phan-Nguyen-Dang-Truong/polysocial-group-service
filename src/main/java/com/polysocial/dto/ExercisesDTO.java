package com.polysocial.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExercisesDTO {
    private Long exId;

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate = LocalDateTime.now();
    
    private Date deadline;

    private LocalDateTime endDate;

    private Boolean status = true;

    private Long groupId;
    
    public void formatEndDate() {
    	LocalDateTime utcDateTimeForCurrentDateTime = Instant.ofEpochMilli(deadline.getTime()).atZone(ZoneId.of("UTC")).toLocalDateTime();
    	DateTimeFormatter dTF2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	endDate = utcDateTimeForCurrentDateTime;
    }
}
