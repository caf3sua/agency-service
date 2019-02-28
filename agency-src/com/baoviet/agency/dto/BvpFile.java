package com.baoviet.agency.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the BVP database table.
 * 
 */
@Getter
@Setter
public class BvpFile implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String type;
	private byte[] content;
	private String contentStr;
}