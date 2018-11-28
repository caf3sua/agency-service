package com.baoviet.agency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Agency;


/**
 * Spring Data JPA repository for the GnocCR module.
 */
//@SuppressWarnings("unused")
//@Repository
//public interface AgencyRepository {
//	
//	List<KpiWODateWMYDTO> loginAgency(String email, String password);
//}


@Repository
public interface AgencyRepository extends JpaRepository<Agency, String> {
	
	Agency findOneByEmail(String email);
	
	Agency findOneByEmailIgnoreCase(String email);
	
	Agency findById(String type);
	
	Agency findByMa(String ma);
	
	List<Agency> findByMaDonVi(String madonvi);
}