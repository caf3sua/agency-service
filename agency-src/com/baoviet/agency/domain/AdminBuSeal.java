package com.baoviet.agency.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the FILES database table.
 * 
 */
@Entity
@Table(name="ADMIN_BU_SEAL")
@Getter
@Setter
public class AdminBuSeal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", unique=true)
	private String id;

	@Column
	private String name;

	@Column
	private String seal;

	@Column
	private String representative;
	
	@Column
	private String position;

	@Lob
	private byte[] picture;
	
	@Column
	private String terminalCode;
}