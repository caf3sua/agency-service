package com.baoviet.agency.payment.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the AGENCY database table.
 * 
 */
@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
@AllArgsConstructor
@Entity
@NamedStoredProcedureQueries({
	   @NamedStoredProcedureQuery(name = "get_code_id", 
           procedureName = "Code_manager.OT_MANA",
           parameters = {
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_Company_id", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_Company_name", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_Deaprtment_id", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_Deaprtment_name", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_year", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_type", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_nv", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_ID", type = String.class)
   })
})
public class CodeId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String pId;

	public CodeId(String pId) {
		super();
		this.pId = pId;
	}
}