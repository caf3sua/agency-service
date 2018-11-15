package com.baoviet.agency.dto.report;

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
	   @NamedStoredProcedureQuery(name = "bc_doanh_thu_admin", 
           procedureName = "BAO_CAOPackage.REPORT_DOANHTHU_ADMIN",
 		   resultSetMappings = {"bc_doanh_thu_admin_mapping"},
           parameters = {
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_tu_ngay", type = Date.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_den_ngay", type = Date.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_agent_id", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "cur_BC_DOANHTHU", type = void.class)
   })
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name= "bc_doanh_thu_admin_mapping", classes = {
	        @ConstructorResult(targetClass = BcDoanhThuAdminDTO.class,
	            columns = {
	                @ColumnResult(name="date_payment",type = String.class),
	                @ColumnResult(name="total_premium",type = Long.class)
	            }
	        )
	})
})
public class BcDoanhThuAdminDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String datePayment;
	private Long totalPremium;
	
	public BcDoanhThuAdminDTO(String datePayment, Long totalPremium) {
		super();
		this.datePayment = datePayment;
		this.totalPremium = totalPremium;
	}
	
}