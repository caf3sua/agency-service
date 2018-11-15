package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.dto.SppCarDTO;


/**
 * Spring Data JPA repository for the GnocCR module.
 */
@Repository
public interface CarRepositoryExtend {
	
	String getMinManufactureYear(String carID); 
	
	String getMaxManufactureYear(String carID); 
	
	String getCarPriceWithYear(String carId,Integer year);
	
	String getPriceIdByCarAndYear(String carId,Integer year);
	
	List<SppCarDTO> getCarInfo();
	
	List<String> getCarMakes();
	
	List<SppCarDTO> getCarModel(String model);
	
	List<String> getAllYear();
	
	String getCarModelIdByName(String modelName);
}