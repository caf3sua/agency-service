package com.baoviet.agency.payment.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
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
	   @NamedStoredProcedureQuery(name = "get_code_man", 
           procedureName = "CODE_MANAGEMENTPackage.GetCODE_MANAGEMENT",
 		   resultSetMappings = {"get_code_man_mapping"},
           parameters = {
        		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ID", type = String.class),
 	     		  @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "cur_CODE_MANAGEMENT", type = void.class)
   })
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name= "get_code_man_mapping", classes = {
	        @ConstructorResult(targetClass = CodeMan.class,
	            columns = {
	                @ColumnResult(name="COMPANY_ID",type = String.class), 
	                @ColumnResult(name="COMPANY_CODE",type = String.class),
	                @ColumnResult(name="DEPARMENT_ID",type = String.class),
	                @ColumnResult(name="DEPARTMENT_CODE",type = String.class),
	                @ColumnResult(name="YEAR",type = String.class),
	                @ColumnResult(name="ID_NUMBER",type = String.class),
	                @ColumnResult(name="BVMVS_SYSDATE",type = Date.class),
	                @ColumnResult(name="TYPE",type = String.class),
	                @ColumnResult(name="ISSUE_NUMBER",type = String.class),
	                @ColumnResult(name="CODE_TYPE",type = String.class),
	            }
	        )
	})
})
public class CodeMan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String companyId;
	
	private String companyCode;
	
	private String deparmentId;
	
	private String deparmentCode;
	
	private String year;
	
	private String idNumber;
	
	private Date bvmvsSysdate;
	
	private String type;
	
	private String issueNumber;
	
	private String codeType;

	public CodeMan(String companyId, String companyCode, String deparmentId, String deparmentCode, String year,
			String idNumber, Date bvmvsSysdate, String type, String issueNumber, String codeType) {
		super();
		this.companyId = companyId;
		this.companyCode = companyCode;
		this.deparmentId = deparmentId;
		this.deparmentCode = deparmentCode;
		this.year = year;
		this.idNumber = idNumber;
		this.bvmvsSysdate = bvmvsSysdate;
		this.type = type;
		this.issueNumber = issueNumber;
		this.codeType = codeType;
	}
}