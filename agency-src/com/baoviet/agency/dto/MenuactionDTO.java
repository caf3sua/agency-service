package com.baoviet.agency.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the MENUACTION database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class MenuactionDTO implements Serializable {
	//default serial version id, required for serializable classes.
		private static final long serialVersionUID = 1L;

		private String actionId;

		private String actionName;

		private String actionFunction;
}