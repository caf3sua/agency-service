package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Files;


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
public interface FileRepository extends JpaRepository<Files, String> {
}