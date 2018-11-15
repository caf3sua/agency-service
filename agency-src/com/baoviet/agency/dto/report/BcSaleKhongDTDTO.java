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
	   @NamedStoredProcedureQuery(name = "bc_sale_khong_DT", 
           procedureName = "GYCBHPackage.AGENCY_SALE_KO_PSDT",
 		   resultSetMappings = {"bc_sale_khong_DT_mapping"},
           parameters = {
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_ngan_hang", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_chi_nhanh", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_phong_gd", type = String.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_month", type = Integer.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_year", type = Integer.class),
	     		  @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "cur_KQKD", type = void.class)
   })
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name= "bc_sale_khong_DT_mapping", classes = {
	        @ConstructorResult(targetClass = BcSaleKhongDTDTO.class,
	            columns = {
	                @ColumnResult(name="STT",type = String.class),
	                @ColumnResult(name="Ma_Sale",type = String.class),
	                @ColumnResult(name="Ten_Sale",type = String.class),
	                @ColumnResult(name="Ma_phong_giao_dich",type = String.class),
	                @ColumnResult(name="Ten_phong_giao_dich",type = String.class),
	                @ColumnResult(name="Ma_chi_nhanh",type = String.class),
	                @ColumnResult(name="Ten_chi_nhanh",type = String.class),
	                @ColumnResult(name="Ten_Ngan_Hang",type = String.class)
	            }
	        )
	})
})
public class BcSaleKhongDTDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String stt;
	private String maSale;
	private String tenSale;
	private String maPhongGiaoDich;
	private String tenPhongGiaoDich;
	private String maChiNhanh;
	private String tenChiNhanh;
	private String tenNganHang;
	
	public BcSaleKhongDTDTO(String stt, String maSale, String tenSale, String maPhongGiaoDich, String tenPhongGiaoDich,
			String maChiNhanh, String tenChiNhanh, String tenNganHang) {
		super();
		this.stt = stt;
		this.maSale = maSale;
		this.tenSale = tenSale;
		this.maPhongGiaoDich = maPhongGiaoDich;
		this.tenPhongGiaoDich = tenPhongGiaoDich;
		this.maChiNhanh = maChiNhanh;
		this.tenChiNhanh = tenChiNhanh;
		this.tenNganHang = tenNganHang;
	}
	
}