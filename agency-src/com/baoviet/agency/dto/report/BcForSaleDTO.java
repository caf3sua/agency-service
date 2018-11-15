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
	   @NamedStoredProcedureQuery(name = "bc_phat_sinh_doanh_thu_thang", 
           procedureName = "GYCBHPackage.AGENCY_PSDT_SALE",
 		   resultSetMappings = {"bc_phat_sinh_doanh_thu_thang_mapping"},
           parameters = {
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_month", type = Integer.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_year", type = Integer.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "cur_PSDT_SALE", type = void.class)
   })
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name= "bc_phat_sinh_doanh_thu_thang_mapping", classes = {
	        @ConstructorResult(targetClass = BcForSaleDTO.class,
	            columns = {
	                @ColumnResult(name="tyle_khong_ps",type = Long.class),
	                @ColumnResult(name="tyle_1_3",type = Long.class),
	                @ColumnResult(name="tyle_3_6",type = Long.class),
	                @ColumnResult(name="tyle_6_10",type = Long.class),
	                @ColumnResult(name="tyle_tren_10",type = Long.class)
	            }
	        )
	})
})
public class BcForSaleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private Long tyleKhongPs;
	private Long tyle13;
	private Long tyle36;
	private Long tyle610;
	private Long tyleTren10;
	
	public BcForSaleDTO(Long tyleKhongPs, Long tyle13, Long tyle36, Long tyle610, Long tyleTren10) {
		super();
		this.tyleKhongPs = tyleKhongPs;
		this.tyle13 = tyle13;
		this.tyle36 = tyle36;
		this.tyle610 = tyle610;
		this.tyleTren10 = tyleTren10;
	}
}