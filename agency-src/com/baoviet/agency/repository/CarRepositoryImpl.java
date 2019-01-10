package com.baoviet.agency.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.SppCar;
import com.baoviet.agency.domain.SppPrices;
import com.baoviet.agency.dto.SppCarDTO;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class CarRepositoryImpl implements CarRepositoryExtend {

	@PersistenceContext
	private EntityManager entityManager;	
	
	@Override
	public String getMinManufactureYear(String carID) {
		String sql = "";
		if (!StringUtils.isEmpty(carID)) {
			sql = "select * from spp_prices where car = :pcar order by PRICE_YEAR ASC";
			Query query = entityManager.createNativeQuery(sql, SppPrices.class);
			query.setParameter("pcar", carID);
			List<SppPrices> lst = query.getResultList();
			if (lst != null && lst.size() > 0) {
				return lst.get(0).getPriceYear().toString();
			}
			return null;
		}
		else {
			return null;
		}
	}

	@Override
	public String getMaxManufactureYear(String carID) {
		String sql = "";
		if (!StringUtils.isEmpty(carID)) {
			sql = "select * from spp_prices where car = :pcar order by PRICE_YEAR DESC";
			Query query = entityManager.createNativeQuery(sql, SppPrices.class);
			query.setParameter("pcar", carID);
			List<SppPrices> lst = query.getResultList();
			if (lst != null && lst.size() > 0) {
				return lst.get(0).getPriceYear().toString();
			}
			return null;
		}
		else {
			return null;
		}
	}

	@Override
	public String getCarPriceWithYear(String carId, Integer year) {
		try {
			String sql = "select * from spp_prices where car = :pcar and price_year = :pPriceYear order by car";
			String maxYear = getMaxManufactureYear(carId);
			Query query = entityManager.createNativeQuery(sql, SppPrices.class);
			query.setParameter("pcar", carId);
			if (year > Integer.parseInt(maxYear)) {
				query.setParameter("pPriceYear", Integer.parseInt(maxYear));
			}
			else {
				query.setParameter("pPriceYear", year);
			}
			List<SppPrices> lst = query.getResultList();
			if (lst != null && lst.size() > 0) {
				long priceVnd = lst.get(0).getPriceVnd().longValue();
				return String.valueOf(priceVnd);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public List<SppCarDTO> getCarInfo() {	
		List<SppCarDTO> lstResult = new ArrayList<>();
		String sql = "SELECT * FROM SPP_CARS ORDER BY CAR_MANUFACTURER, CAR_ID, CAR_NAME";
		Query query = entityManager.createNativeQuery(sql, SppCar.class);
		List<SppCar> lst = query.getResultList();
		if (lst != null && lst.size() > 0) {
			for (SppCar item : lst) {
				SppCarDTO spp = new SppCarDTO();
				spp.setCarId(item.getCarId());
				spp.setCarName(item.getCarName());
				spp.setCarManufacturer(item.getCarManufacturer());
				lstResult.add(spp);
			}
		}
		return lstResult;		
	}

	@Override
	public List<String> getCarMakes() {
		String sql = "SELECT DISTINCT CAR_MANUFACTURER FROM SPP_CARS ORDER BY CAR_MANUFACTURER";
		Query query = entityManager.createNativeQuery(sql);
		List<String> lst = query.getResultList();
		
		return lst;		
	}
	
	@Override
	public List<SppCarDTO> getCarModel(String model) {
		List<SppCarDTO> lstResult = new ArrayList<>();
		String sql = "SELECT * FROM SPP_CARS where CAR_MANUFACTURER = :pCarManufacturer";
		Query query = entityManager.createNativeQuery(sql, SppCar.class);
		query.setParameter("pCarManufacturer", model);
		List<SppCar> lst = query.getResultList();
		if (lst != null && lst.size() > 0) {
			for (SppCar item : lst) {
				SppCarDTO spp = new SppCarDTO();
				spp.setCarId(item.getCarId());
				spp.setCarName(item.getCarName());
				spp.setCarManufacturer(item.getCarManufacturer());
				lstResult.add(spp);
			}
		}
		return lstResult;		
	}

	@Override
	public List<String> getAllYear() {
		String sql = "SELECT DISTINCT PRICE_YEAR FROM SPP_PRICES ORDER BY PRICE_YEAR DESC";
		Query query = entityManager.createNativeQuery(sql);
		List<BigDecimal> lst = query.getResultList();
		List<String> result = new ArrayList<>();
		for (BigDecimal item : lst) {
			result.add(String.valueOf(item));
		}
		return result;	
	}

	@Override
	public String getCarModelIdByName(String modelName) {
		String sql = "SELECT * FROM SPP_CARS where CAR_NAME = :pModelName";
		
		Query query = entityManager.createNativeQuery(sql, SppCar.class);
		query.setParameter("pModelName", modelName);
		List<SppCar> lst = query.getResultList();
		if (lst != null && lst.size() > 0) {
			String modelId = lst.get(0).getCarId();
			return modelId;
		}
		return null;
	}

	@Override
	public String getPriceIdByCarAndYear(String carId, Integer year) {
		try {
			String sql = "select * from spp_prices where car = :pcar and price_year = :pPriceYear order by car";
			Query query = entityManager.createNativeQuery(sql, SppPrices.class);
			
			query.setParameter("pcar", carId);
			query.setParameter("pPriceYear", year);
			List<SppPrices> lst = query.getResultList();
			if (lst != null && lst.size() > 0) {
				return lst.get(0).getPriceId();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

}