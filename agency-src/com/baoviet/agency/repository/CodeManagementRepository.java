package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.CodeManagement;


@Repository
public interface CodeManagementRepository extends JpaRepository<CodeManagement, String>, CodeManagementRepositoryExtend {
	@Procedure(name = "code_management_ot_mana")
	String getCode(@Param("p_Company_id") String companyId, @Param("p_Deaprtment_id") String departmentId, @Param("p_Company_name") String companyName
			, @Param("p_Deaprtment_name") String departmentName, @Param("p_year") String year, @Param("p_type") String type, @Param("p_nv") String nv);
}

//@StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_ID", type = String.class),
//@StoredProcedureParameter(mode = ParameterMode.IN, name = "", type = String.class),
//@StoredProcedureParameter(mode = ParameterMode.IN, name = "", type = String.class),
//@StoredProcedureParameter(mode = ParameterMode.IN, name = "", type = String.class),
//@StoredProcedureParameter(mode = ParameterMode.IN, name = "", type = String.class),
//@StoredProcedureParameter(mode = ParameterMode.IN, name = "", type = String.class),
//@StoredProcedureParameter(mode = ParameterMode.IN, name = "", type = String.class),
//@StoredProcedureParameter(mode = ParameterMode.IN, name = "", type = String.class)