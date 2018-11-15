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
	   @NamedStoredProcedureQuery(name = "bc_ty_le_doanh_thu_PGDT_theo_nam", 
           procedureName = "GYCBHPackage.AGENCY_TYLE_HTKH_PGD",
 		   resultSetMappings = {"bc_ty_le_doanh_thu_PGDT_theo_nam_mapping"},
           parameters = {
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngan_hang", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_chi_nhanh", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_phong_gd", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_year", type = Integer.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "cur_KQKD_LK", type = void.class)
   })
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name= "bc_ty_le_doanh_thu_PGDT_theo_nam_mapping", classes = {
	        @ConstructorResult(targetClass = BcTyLeDoanhThuPGDTheoNamDTO.class,
	            columns = {
	                @ColumnResult(name="duoi_50",type = Long.class),
	                @ColumnResult(name="tu_50_80",type = Long.class),
	                @ColumnResult(name="tu_80_100",type = Long.class),
	                @ColumnResult(name="tren_100",type = Long.class)
	            }
	        )
	})
})
public class BcTyLeDoanhThuPGDTheoNamDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private Long duoi50;
	private Long tu50den80;
	private Long tu80den100;
	private Long tren100;
	
	public BcTyLeDoanhThuPGDTheoNamDTO(Long duoi50, Long tu50den80, Long tu80den100, Long tren100) {
		super();
		this.duoi50 = duoi50;
		this.tu50den80 = tu50den80;
		this.tu80den100 = tu80den100;
		this.tren100 = tren100;
	}

}