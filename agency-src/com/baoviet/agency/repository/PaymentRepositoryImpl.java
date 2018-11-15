package com.baoviet.agency.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.GiftCode;
import com.baoviet.agency.dto.GiftCodeDTO;
import com.baoviet.agency.payment.common.Constants;
import com.baoviet.agency.payment.domain.CodeMan;
import com.baoviet.agency.payment.domain.PaymentBank;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public List<PaymentBank> getBanksByPaymentCode(String paymentCode) {
		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("get_list_bank_by_pay_code")
				.setParameter("p_PAYMENT_CODE", paymentCode);

		query.execute();

		List<PaymentBank> result = query.getResultList();

		return result;
	}
	
	@Override
	@Transactional
	public GiftCodeDTO getGiftCodeByCodeAndEmail(String couponCode, String email) {
		List<GiftCodeDTO> lstResult = new ArrayList<>();
		
		// Check if giftcode exist in system
		String sql = "SELECT * FROM giftcode g WHERE UPPER(g.giftcode) = UPPER(:couponCode) AND g.email = :email AND TO_CHAR(SYSDATE, 'MMDDYYYY') <= '12312018'";
		Query query = entityManager.createNativeQuery(sql, GiftCode.class);
		query.setParameter("couponCode", couponCode);
		query.setParameter("email", email);
		List<GiftCode> lst = query.getResultList();
		if (lst != null && lst.size() > 0) {
			// Check if giftcode is used
			sql = "select count(*) from pay_action p where p.gif3_code = :couponCode and (p.status = 91 or (p.status = 90 and p.pay_end_date is null))";
			query = entityManager.createNativeQuery(sql);
			query.setParameter("couponCode", couponCode);
			int usedCount = (int) query.getSingleResult();
			if(usedCount > 0) {
				return null;
			} else {
				return lstResult.get(0);		
			}
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public CodeMan getCode(String companyId, String departmentId, String companyName, String departmentName, String processYear) {
		StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("get_code_id")
				.setParameter("p_Company_id", companyId)
				.setParameter("p_Company_name", companyName)
				.setParameter("p_Deaprtment_id", departmentId)
				.setParameter("p_Deaprtment_name", departmentName)
				.setParameter("p_year", processYear)
				.setParameter("p_type", Constants.PAY)
				.setParameter("p_nv", Constants.PAY);

		query.execute();

		String pId = (String) query.getOutputParameterValue("p_ID");
		
		query = entityManager.createNamedStoredProcedureQuery("get_code_man")
				.setParameter("p_ID", pId);
		
		query.execute();
		
		CodeMan result = (CodeMan) query.getSingleResult();
		
		return result;
	}
}