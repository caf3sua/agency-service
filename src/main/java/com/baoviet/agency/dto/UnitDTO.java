package com.baoviet.agency.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Nam, Nguyen Hoais
 */
@Getter
@Setter
public class UnitDTO implements Serializable {
	private static final long serialVersionUID = -4214381285557568526L;
	
	private String unitId;
    private String unitName;
    private String unitCode;
    private String parentUnitId;
    private String status;
    private String locationId;
    
    public UnitDTO() {}
    
	public UnitDTO(String unitId, String unitName, String unitCode, String parentUnitId, String status,
			String locationId) {
		super();
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitCode = unitCode;
		this.parentUnitId = parentUnitId;
		this.status = status;
		this.locationId = locationId;
	}
    
}
