package com.baoviet.agency.utils;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class DbUtil {

	public static Date getSysDate(EntityManager em) {
		String queryString = "SELECT sysdate FROM dual";
		Query queryObject = em.createNativeQuery(queryString);
		return (Date) queryObject.getSingleResult();
	}
}
