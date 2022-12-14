package com.polysocial.entity;

import lombok.*;
import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Groups implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    private String name;

    private Long totalMember;

    private String description;

    private Boolean status = true;
    
    private String className;

    private LocalDateTime createdDate = LocalDateTime.now();

    private String avatar;

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private List<Members> members;
    

    public Groups(String name, Long totalMember) {
		this.name = name;
		this.totalMember = totalMember;
	}

    @OneToMany(mappedBy = "group")
    private List<Exercises> exercises;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Groups groups = (Groups) o;
        return groupId != null && Objects.equals(groupId, groups.groupId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
