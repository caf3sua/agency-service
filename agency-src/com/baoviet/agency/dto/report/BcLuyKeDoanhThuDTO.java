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
	   @NamedStoredProcedureQuery(name = "bc_luy_ke_tong_doanh_thu", 
           procedureName = "GYCBHPackage.AGENCY_KQKD_LUYKE",
 		   resultSetMappings = {"bc_luy_ke_tong_doanh_thu_mapping"},
           parameters = {
        		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngan_hang", type = String.class),
        		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_chi_nhanh", type = String.class),
        		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_phong_gd", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_sale", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_year", type = Integer.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "cur_KQKD_LK", type = void.class)
   })
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name= "bc_luy_ke_tong_doanh_thu_mapping", classes = {
	        @ConstructorResult(targetClass = BcLuyKeDoanhThuDTO.class,
	            columns = {
	                @ColumnResult(name="DT",type = Long.class)
	            }
	        )
	})
})
public class BcLuyKeDoanhThuDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private Long dt;

	public BcLuyKeDoanhThuDTO(Long dt) {
		this.dt = dt;
	}

}