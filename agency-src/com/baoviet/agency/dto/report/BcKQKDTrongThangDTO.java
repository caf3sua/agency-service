package com.baoviet.agency.dto.report;

import java.io.Serializable;

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
	   @NamedStoredProcedureQuery(name = "bc_KQKD_trong_thang", 
           procedureName = "GYCBHPackage.AGENCY_KQKD_THANG_PGD",
 		   resultSetMappings = {"bc_KQKD_trong_thang_mapping"},
           parameters = {
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngan_hang", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_chi_nhanh", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_phong_gd", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_month", type = Integer.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_year", type = Integer.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "cur_KQKD_PGD", type = void.class)
   })
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name= "bc_KQKD_trong_thang_mapping", classes = {
	        @ConstructorResult(targetClass = BcKQKDTrongThangDTO.class,
	            columns = {
	                @ColumnResult(name="DT_PGD",type = Long.class),
	                @ColumnResult(name="so_don",type = String.class),
	                @ColumnResult(name="agency_p1_id",type = String.class),
	                @ColumnResult(name="agency_p1_name",type = String.class)
	            }
	        )
	})
})
public class BcKQKDTrongThangDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private Long dtPGD;
	private String soDon;
	private String maNhom;
	private String tenNhom;
	
	public BcKQKDTrongThangDTO(Long dtPGD, String soDon, String maNhom, String tenNhom) {
		super();
		this.dtPGD = dtPGD;
		this.soDon = soDon;
		this.maNhom = maNhom;
		this.tenNhom = tenNhom;
	}
	
}