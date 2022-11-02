package com.polysocial.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="`member`")
public class Member implements Serializable{
	@Id @Column(name="memberid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberId;
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;
	@ManyToOne
	@JoinColumn(name ="groupid")
	private Group group;
	
	@Column(name="isteacher")
	private Boolean isTeacher;

	public Member(User user, Group group, Boolean isTeacher) {
		super();
		this.user = user;
		this.group = group;
		this.isTeacher = isTeacher;
	}

}
