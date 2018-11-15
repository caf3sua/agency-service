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
	   @NamedStoredProcedureQuery(name = "bc_kq_kinh_doanh_hang", 
           procedureName = "GYCBHPackage.AGENCY_KQKD_toan_hang",
 		   resultSetMappings = {"bc_kq_kinh_doanh_hang_mapping"},
           parameters = {
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_year", type = Integer.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "cur_KQKD_toan_hang", type = void.class)
   })
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name= "bc_kq_kinh_doanh_hang_mapping", classes = {
	        @ConstructorResult(targetClass = BcKQKinhDoanhHangDTO.class,
	            columns = {
	            		@ColumnResult(name="t1_kh",type = Long.class),
	            		@ColumnResult(name="t1_th",type = Long.class),
	            		@ColumnResult(name="t1_sd",type = Long.class),
	            		@ColumnResult(name="t2_kh",type = Long.class),
	            		@ColumnResult(name="t2_th",type = Long.class),
	            		@ColumnResult(name="t2_sd",type = Long.class),
	            		@ColumnResult(name="t3_kh",type = Long.class),
	            		@ColumnResult(name="t3_th",type = Long.class),
	            		@ColumnResult(name="t3_sd",type = Long.class),
	            		@ColumnResult(name="t4_kh",type = Long.class),
	            		@ColumnResult(name="t4_th",type = Long.class),
	            		@ColumnResult(name="t4_sd",type = Long.class),
	            		@ColumnResult(name="t5_kh",type = Long.class),
	            		@ColumnResult(name="t5_th",type = Long.class),
	            		@ColumnResult(name="t5_sd",type = Long.class),
	            		@ColumnResult(name="t6_kh",type = Long.class),
	            		@ColumnResult(name="t6_th",type = Long.class),
	            		@ColumnResult(name="t6_sd",type = Long.class),
	            		@ColumnResult(name="t7_kh",type = Long.class),
	            		@ColumnResult(name="t7_th",type = Long.class),
	            		@ColumnResult(name="t7_sd",type = Long.class),
	            		@ColumnResult(name="t8_kh",type = Long.class),
	            		@ColumnResult(name="t8_th",type = Long.class),
	            		@ColumnResult(name="t8_sd",type = Long.class),
	            		@ColumnResult(name="t9_kh",type = Long.class),
	            		@ColumnResult(name="t9_th",type = Long.class),
	            		@ColumnResult(name="t9_sd",type = Long.class),
	            		@ColumnResult(name="t10_kh",type = Long.class),
	            		@ColumnResult(name="t10_th",type = Long.class),
	            		@ColumnResult(name="t10_sd",type = Long.class),
	            		@ColumnResult(name="t11_kh",type = Long.class),
	            		@ColumnResult(name="t11_th",type = Long.class),
	            		@ColumnResult(name="t11_sd",type = Long.class),
	            		@ColumnResult(name="t12_kh",type = Long.class),
	            		@ColumnResult(name="t12_th",type = Long.class),
	            		@ColumnResult(name="t12_sd",type = Long.class)
	            }
	        )
	})
})
public class BcKQKinhDoanhHangDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private Long t1_kh;
	private Long t1_th;
	private Long t1_sd;
	private Long t2_kh;
	private Long t2_th;
	private Long t2_sd;
	private Long t3_kh;
	private Long t3_th;
	private Long t3_sd;
	private Long t4_kh;
	private Long t4_th;
	private Long t4_sd;
	private Long t5_kh;
	private Long t5_th;
	private Long t5_sd;
	private Long t6_kh;
	private Long t6_th;
	private Long t6_sd;
	private Long t7_kh;
	private Long t7_th;
	private Long t7_sd;
	private Long t8_kh;
	private Long t8_th;
	private Long t8_sd;
	private Long t9_kh;
	private Long t9_th;
	private Long t9_sd;
	private Long t10_kh;
	private Long t10_th;
	private Long t10_sd;
	private Long t11_kh;
	private Long t11_th;
	private Long t11_sd;
	private Long t12_kh;
	private Long t12_th;
	private Long t12_sd;
	
	public BcKQKinhDoanhHangDTO(Long t1_kh, Long t1_th, Long t1_sd, Long t2_kh, Long t2_th, Long t2_sd, Long t3_kh,
			Long t3_th, Long t3_sd, Long t4_kh, Long t4_th, Long t4_sd, Long t5_kh, Long t5_th, Long t5_sd, Long t6_kh,
			Long t6_th, Long t6_sd, Long t7_kh, Long t7_th, Long t7_sd, Long t8_kh, Long t8_th, Long t8_sd, Long t9_kh,
			Long t9_th, Long t9_sd, Long t10_kh, Long t10_th, Long t10_sd, Long t11_kh, Long t11_th, Long t11_sd,
			Long t12_kh, Long t12_th, Long t12_sd) {
		super();
		this.t1_kh = t1_kh;
		this.t1_th = t1_th;
		this.t1_sd = t1_sd;
		this.t2_kh = t2_kh;
		this.t2_th = t2_th;
		this.t2_sd = t2_sd;
		this.t3_kh = t3_kh;
		this.t3_th = t3_th;
		this.t3_sd = t3_sd;
		this.t4_kh = t4_kh;
		this.t4_th = t4_th;
		this.t4_sd = t4_sd;
		this.t5_kh = t5_kh;
		this.t5_th = t5_th;
		this.t5_sd = t5_sd;
		this.t6_kh = t6_kh;
		this.t6_th = t6_th;
		this.t6_sd = t6_sd;
		this.t7_kh = t7_kh;
		this.t7_th = t7_th;
		this.t7_sd = t7_sd;
		this.t8_kh = t8_kh;
		this.t8_th = t8_th;
		this.t8_sd = t8_sd;
		this.t9_kh = t9_kh;
		this.t9_th = t9_th;
		this.t9_sd = t9_sd;
		this.t10_kh = t10_kh;
		this.t10_th = t10_th;
		this.t10_sd = t10_sd;
		this.t11_kh = t11_kh;
		this.t11_th = t11_th;
		this.t11_sd = t11_sd;
		this.t12_kh = t12_kh;
		this.t12_th = t12_th;
		this.t12_sd = t12_sd;
	}
}