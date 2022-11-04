package com.polysocial.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Exercises implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exId;

    private String content;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime endDate;

    private Boolean status = true;

    @ManyToOne
    @JoinColumn(name = "groupId", insertable = false, updatable = false)
    private Groups group;
}
