package com.baoviet.agency.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Base repository.
 * 
 * @author Nam, Nguyen Hoai
 */
@Repository
public abstract class BaseRepository {
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;  

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	protected Session getSession() {
		Session session = entityManager.unwrap(Session.class);
		return session;
	}
	
	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
