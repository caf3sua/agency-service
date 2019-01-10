package com.baoviet.agency.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.PayAction;
import com.baoviet.agency.utils.DateUtils;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class PayActionRepositoryImpl implements PayActionRepositoryExtend {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<PayAction> search(String fromDate, String toDate, String type) {
		String expression = "select DISTINCT(pa.MCI_ADD_ID) as MCI_ADD_ID\r\n" + 
				",pa.PAY_ACTION_ID\r\n" + 
				",pa.FROM_INVITE_CODE_ID\r\n" + 
				",pa.FROM_CONTACT_ID\r\n" + 
				",pa.FROM_CONTACT_NAME\r\n" + 
				",pa.FROM_EMAIL\r\n" + 
				",pa.FROM_INVITE_CODE\r\n" + 
				",pa.FROM_ACCOUNT_NUM\r\n" + 
				",pa.FROM_PHONE\r\n" + 
				",pa.FROM_SENDMAIL\r\n" + 
				",pa.TO_INVITE_CODE_ID\r\n" + 
				",pa.TO_CONTACT_ID\r\n" + 
				",pa.TO_CONTACT_NAME\r\n" + 
				",pa.TO_EMAIL\r\n" + 
				",pa.TO_INVITE_CODE\r\n" + 
				",pa.TO_ACCOUNT_NUM\r\n" + 
				",pa.TO_PHONE\r\n" + 
				",pa.TO_SENDMAIL\r\n" + 
				",pa.TRANSACTION_ID\r\n" + 
				",pa.REFUND_STATUS\r\n" + 
				",pa.STATUS\r\n" + 
				",pa.BANKCODE\r\n" + 
				",pa.PAYMENT_GATEWAY\r\n" + 
				",pa.NET_AMOUNT\r\n" + 
				",pa.REFUND_AMOUNT\r\n" + 
				",pa.AMOUNT\r\n" + 
				",pa.TRANSACTION_DATE\r\n" + 
				",pa.DISCOUNT_AMOUNT\r\n" + 
				",pa.GIF1_AMOUNT\r\n" + 
				",pa.GIF1_PERCENT\r\n" + 
				",pa.GIF1_CODE\r\n" + 
				",pa.GIF1_INFO\r\n" + 
				",pa.GIF1_REFUND\r\n" + 
				",pa.GIF2_AMOUNT\r\n" + 
				",pa.GIF2_PERCENT\r\n" + 
				",pa.GIF2_CODE\r\n" + 
				",pa.GIF2_INFO\r\n" + 
				",pa.GIF2_REFUND\r\n" + 
				",pa.GIF3_AMOUNT\r\n" + 
				",pa.GIF3_PERCENT\r\n" + 
				",pa.GIF3_CODE\r\n" + 
				",pa.GIF3_INFO\r\n" + 
				",pa.GIF3_REFUND\r\n" + 
				",pa.GIF4_AMOUNT\r\n" + 
				",pa.GIF4_PERCENT\r\n" + 
				",pa.GIF4_CODE\r\n" + 
				",pa.GIF4_INFO\r\n" + 
				",pa.GIF4_REFUND\r\n" + 
				",pa.GIF5_AMOUNT\r\n" + 
				",pa.GIF5_PERCENT\r\n" + 
				",pa.GIF5_CODE\r\n" + 
				",pa.GIF5_INFO\r\n" + 
				",pa.GIF5_REFUND\r\n" + 
				",pa.PAY_START_DATE\r\n" + 
				",pa.PAY_END_DATE\r\n" + 
				",pa.POLICY_NUMBERS\r\n" + 
				",pa.PAY_LOG\r\n" + 
				",pa.STATUS_EMAIL_FROM\r\n" + 
				",pa.STATUS_EMAIL_TO\r\n" + 
				",pa.STATUS_PAY_FROM\r\n" + 
				",pa.PAYMENT_DISCOUNT\r\n" + 
				",pa.PAYMENT_REFUND\r\n" + 
				",pa.STATUS_PAYMENT_REFUND\r\n" + 
				",pa.NUM_CARD\r\n" + 
				",pa.BANK_NAME\r\n" + 
				",pa.CARD_NAME\r\n" + 
				",pa.ADDRESS\r\n" + 
				",pa.STATUS_CARD\r\n" + 
				",pa.CARD_UPDATE_DATE\r\n" + 
				",pa.STATUS_EMAIL_PAY_FROM from pay_action pa "
					+ "INNER JOIN AGREEMENT ag on PA.MCI_ADD_ID = AG.MCI_ADD_ID "
					+ "where PA.STATUS = '91' AND AG.AGENT_ID = :pType "
					+ "AND TRANSACTION_DATE >= :pFromDate AND TRANSACTION_DATE <= :pToDate";
		
		Query query = entityManager.createNativeQuery(expression, PayAction.class);
		query.setParameter("pType", type);
		// Date
		query.setParameter("pFromDate", DateUtils.str2Date(fromDate));
		query.setParameter("pToDate", DateUtils.str2Date(toDate));
		
		List<PayAction> data = query.getResultList();
		return data;
	}
	
	@Override
	public List<PayAction> searchAdmin(String fromDate, String toDate, String adminId) {
		String expression = "select DISTINCT(pa.MCI_ADD_ID) as MCI_ADD_ID\r\n" + 
				",pa.PAY_ACTION_ID\r\n" + 
				",pa.FROM_INVITE_CODE_ID\r\n" + 
				",pa.FROM_CONTACT_ID\r\n" + 
				",pa.FROM_CONTACT_NAME\r\n" + 
				",pa.FROM_EMAIL\r\n" + 
				",pa.FROM_INVITE_CODE\r\n" + 
				",pa.FROM_ACCOUNT_NUM\r\n" + 
				",pa.FROM_PHONE\r\n" + 
				",pa.FROM_SENDMAIL\r\n" + 
				",pa.TO_INVITE_CODE_ID\r\n" + 
				",pa.TO_CONTACT_ID\r\n" + 
				",pa.TO_CONTACT_NAME\r\n" + 
				",pa.TO_EMAIL\r\n" + 
				",pa.TO_INVITE_CODE\r\n" + 
				",pa.TO_ACCOUNT_NUM\r\n" + 
				",pa.TO_PHONE\r\n" + 
				",pa.TO_SENDMAIL\r\n" + 
				",pa.TRANSACTION_ID\r\n" + 
				",pa.REFUND_STATUS\r\n" + 
				",pa.STATUS\r\n" + 
				",pa.BANKCODE\r\n" + 
				",pa.PAYMENT_GATEWAY\r\n" + 
				",pa.NET_AMOUNT\r\n" + 
				",pa.REFUND_AMOUNT\r\n" + 
				",pa.AMOUNT\r\n" + 
				",pa.TRANSACTION_DATE\r\n" + 
				",pa.DISCOUNT_AMOUNT\r\n" + 
				",pa.GIF1_AMOUNT\r\n" + 
				",pa.GIF1_PERCENT\r\n" + 
				",pa.GIF1_CODE\r\n" + 
				",pa.GIF1_INFO\r\n" + 
				",pa.GIF1_REFUND\r\n" + 
				",pa.GIF2_AMOUNT\r\n" + 
				",pa.GIF2_PERCENT\r\n" + 
				",pa.GIF2_CODE\r\n" + 
				",pa.GIF2_INFO\r\n" + 
				",pa.GIF2_REFUND\r\n" + 
				",pa.GIF3_AMOUNT\r\n" + 
				",pa.GIF3_PERCENT\r\n" + 
				",pa.GIF3_CODE\r\n" + 
				",pa.GIF3_INFO\r\n" + 
				",pa.GIF3_REFUND\r\n" + 
				",pa.GIF4_AMOUNT\r\n" + 
				",pa.GIF4_PERCENT\r\n" + 
				",pa.GIF4_CODE\r\n" + 
				",pa.GIF4_INFO\r\n" + 
				",pa.GIF4_REFUND\r\n" + 
				",pa.GIF5_AMOUNT\r\n" + 
				",pa.GIF5_PERCENT\r\n" + 
				",pa.GIF5_CODE\r\n" + 
				",pa.GIF5_INFO\r\n" + 
				",pa.GIF5_REFUND\r\n" + 
				",pa.PAY_START_DATE\r\n" + 
				",pa.PAY_END_DATE\r\n" + 
				",pa.POLICY_NUMBERS\r\n" + 
				",pa.PAY_LOG\r\n" + 
				",pa.STATUS_EMAIL_FROM\r\n" + 
				",pa.STATUS_EMAIL_TO\r\n" + 
				",pa.STATUS_PAY_FROM\r\n" + 
				",pa.PAYMENT_DISCOUNT\r\n" + 
				",pa.PAYMENT_REFUND\r\n" + 
				",pa.STATUS_PAYMENT_REFUND\r\n" + 
				",pa.NUM_CARD\r\n" + 
				",pa.BANK_NAME\r\n" + 
				",pa.CARD_NAME\r\n" + 
				",pa.ADDRESS\r\n" + 
				",pa.STATUS_CARD\r\n" + 
				",pa.CARD_UPDATE_DATE\r\n" + 
				",pa.STATUS_EMAIL_PAY_FROM from pay_action pa "
					+ "INNER JOIN AGREEMENT ag on PA.MCI_ADD_ID = AG.MCI_ADD_ID "
					+ "where PA.STATUS = '91' AND AG.BAOVIET_DEPARTMENT_ID IN ( SELECT BU_ID FROM ADMIN_USER_BU WHERE ADMIN_ID = :pType ) AND AG.LINE_ID IN ( SELECT B.LINE_ID FROM ADMIN_USER_PRODUCT_GROUP A JOIN ADMIN_PRODUCT_GROUP_PRODUCT B ON A.GROUP_ID = B.GROUP_ID WHERE A.ADMIN_ID = :pType ) "
					+ "AND TRANSACTION_DATE >= :pFromDate AND TRANSACTION_DATE <= :pToDate";
		
		Query query = entityManager.createNativeQuery(expression, PayAction.class);
		query.setParameter("pType", adminId);
		// Date
		query.setParameter("pFromDate", DateUtils.str2Date(fromDate));
		query.setParameter("pToDate", DateUtils.str2Date(toDate));
		
		List<PayAction> data = query.getResultList();
		return data;
	}

	@Override
	public List<PayAction> search(String contactId, String type) {
		String expression = "select pa.* from pay_action pa "
				+ "INNER JOIN AGREEMENT ag on PA.MCI_ADD_ID = AG.MCI_ADD_ID "
				+ "where PA.STATUS = '91' AND AG.AGENT_ID = :pType "
				+ "AND AG.CONTACT_ID = :pContactId";
	
		Query query = entityManager.createNativeQuery(expression, PayAction.class);
		query.setParameter("pType", type);
		query.setParameter("pContactId", contactId);
		
		List<PayAction> data = query.getResultList();
		return data;
	}
}