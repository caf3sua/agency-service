package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * The persistent class for the CODE_MANAGEMENT database table.
 * 
 */
@Entity
@Table(name="CODE_MANAGEMENT")
@NamedStoredProcedureQueries({
	   @NamedStoredProcedureQuery(name = "code_management_ot_mana", 
	                              procedureName = "Code_manager.OT_MANA",
	                              parameters = {
                            		  @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_ID", type = String.class),
                            		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_Company_id", type = String.class),
                            		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_Company_name", type = String.class),
                            		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_Deaprtment_id", type = String.class),
                            		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_Deaprtment_name", type = String.class),
                            		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_year", type = String.class),
                            		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_type", type = String.class),
                            		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_nv", type = String.class)
	                              })
})
@Getter
@Setter
public class CodeManagement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "CODE_MANAGEMENT_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="ID", unique=true)
	private String id;

	@Column(name="BVMVS_SYSDATE")
	private Date bvmvsSysdate;

	@Column(name="CODE_TYPE")
	private String codeType;

	@Column(name="COMPANY_CODE")
	private String companyCode;

	@Column(name="COMPANY_ID")
	private String companyId;

	@Column(name="DEPARMENT_ID")
	private String deparmentId;

	@Column(name="DEPARTMENT_CODE")
	private String departmentCode;

	@Column(name="ID_NUMBER")
	private String idNumber;

	@Column(name="ISSUE_NUMBER")
	private String issueNumber;

	@Column
	private String type;

	@Column
	private String year;
}