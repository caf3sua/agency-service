package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the MENUACTION database table.
 * 
 */
@Entity
@Getter
@Setter
public class Menuaction implements Serializable {
	//default serial version id, required for serializable classes.
		private static final long serialVersionUID = 1L;

		@Id
		@Column(name="ACTION_ID", unique=true)
		@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "MENUACTION_SEQ") })
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
		private String actionId;

		@Column(name="ACTION_NAME")
		private String actionName;

		@Column(name="ACTION_FUNCTION")
		private String actionFunction;
}