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
	   @NamedStoredProcedureQuery(name = "bc_top_10_doanh_thu", 
           procedureName = "GYCBHPackage.AGENCY_TOP_10_CN",
 		   resultSetMappings = {"bc_top_10_doanh_thu_mapping"},
           parameters = {
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_month", type = Integer.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_year", type = Integer.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "cur_TOP_CN", type = void.class)
   })
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name= "bc_top_10_doanh_thu_mapping", classes = {
	        @ConstructorResult(targetClass = BcTop10DoanhThuDTO.class,
	            columns = {
	                @ColumnResult(name="maphong",type = String.class),
	                @ColumnResult(name="tenphong",type = String.class),
	                @ColumnResult(name="DT",type = Long.class)
	            }
	        )
	})
})
public class BcTop10DoanhThuDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String maPhong;
	private String tenPhong;
	private Long dt;

	public BcTop10DoanhThuDTO(String maPhong, String tenPhong, Long dt) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.dt = dt;
	}

}