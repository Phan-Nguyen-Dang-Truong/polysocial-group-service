package com.polysocial.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)@Entity
@Table(name ="`user`")

public class User implements Serializable{
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userid")
	private Integer userId;
	@Column(name="fullname")
	private String fullName;
	@Column(name="studentcode")
	private String studentCode;
	@Column(name="email")
	private String email;
	@Column(name="avatar")
	private String avatar;
	@Column(name="isactive")
	private Boolean isActive;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	List<Member> member;


}
