package com.baoviet.agency.service;


import java.util.List;

import com.baoviet.agency.domain.Car;
import com.baoviet.agency.dto.CarDTO;
import com.baoviet.agency.dto.SppCarDTO;

/**
 * Service Interface for managing TVC.
 */
public interface CarService {
	
	String InsertCar(CarDTO info);
	
	String getMinManufactureYear(String carId);
	
	String getMaxManufactureYear(String carId);
	
	String getPriceIdByCarAndYear(String car, Integer year);
	
	String getCarPriceWithYear(String carId,Integer year);
	
	List<SppCarDTO> getCarInfo();
	
	List<String> getCarMakes();
	
	List<SppCarDTO> getCarModel(String model);
	
	Car getById(String id);
	
	List<String> getAllYear();
}

