package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the ADMIN_USER database table.
 * 
 */
@Entity
@Table(name="ADMIN_USER")
@Getter
@Setter
public class AdminUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "ADMIN_USER_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="ID", unique=true)
	private String id;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
      name = "ADMIN_USER_USER_ROLE",
      joinColumns = {@JoinColumn(name = "ADMIN_ID", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")})
	@BatchSize(size = 20)
	private Set<AdminRole> roles = new HashSet<>();
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private String fullname;
	
	@Column
	private String email;
	
}