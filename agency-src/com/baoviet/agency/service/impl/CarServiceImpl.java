package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Car;
import com.baoviet.agency.dto.CarDTO;
import com.baoviet.agency.dto.SppCarDTO;
import com.baoviet.agency.repository.CarRepository;
import com.baoviet.agency.service.CarService;
import com.baoviet.agency.service.mapper.CarMapper;

/**
 * Service Implementation for managing TVI.
 * 
 * @author CuongTT
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class CarServiceImpl implements CarService {

	private final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);
	
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private CarMapper carMapper;

	@Override
	public String InsertCar(CarDTO info) {
		log.debug("Request to InsertCar : CarDTO {} : ", info);
		if (info != null) {
			try {
				Car entity = carRepository.save(carMapper.toEntity(info));
				return entity.getCarId();

			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public String getMinManufactureYear(String carId) {
		log.debug("Request to getMinManufactureYear : carId {} : ", carId);
		return carRepository.getMinManufactureYear(carId);
	}

	@Override
	public String getMaxManufactureYear(String carId) {
		log.debug("Request to getMaxManufactureYear : carId {} : ", carId);
		return carRepository.getMaxManufactureYear(carId);
	}

	@Override
	public String getCarPriceWithYear(String carId, Integer year) {
		log.debug("Request to getCarPriceWithYear : carId {}, year {} : ", carId, year);
		return carRepository.getCarPriceWithYear(carId, year);
	}

	@Override
	public List<SppCarDTO> getCarInfo() {
		log.debug("Request to getCarInfo : ");
		return carRepository.getCarInfo();
	}

	@Override
	public List<String> getCarMakes() {
		log.debug("Request to getCarMakes : ");
		return carRepository.getCarMakes();
	}

	@Override
	public List<SppCarDTO> getCarModel(String model) {
		log.debug("Request to getCarModel, model {} : ", model);
		return carRepository.getCarModel(model);
	}

	@Override
	public Car getById(String id) {
		log.debug("Request to getById - Car, id {} : ", id);
		return carRepository.findOne(id);
	}

	@Override
	public List<String> getAllYear() {
		return carRepository.getAllYear();
	}

	@Override
	public String getPriceIdByCarAndYear(String car, Integer year) {
		log.debug("Request to getPriceIdByCarAndYear, car {}, year{} : ", car, year);
		return carRepository.getPriceIdByCarAndYear(car, year);
	}
}
