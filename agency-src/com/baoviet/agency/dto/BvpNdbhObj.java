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
public class BvpNdbhObj implements Serializable {
	private static final long serialVersionUID = 1L;

	private BvpNdbhXML NGUOIDBH;

}