package com.baoviet.agency.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.BenifitTvi;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class BenifitTviRepositoryImpl implements BenifitTviRepositoryExtend {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public BenifitTvi getByParam(int pDay, String pType, String pArea, String planId) {
		BenifitTvi benifitTvi = null;
		BenifitTvi benifitTviLess = null;
		BenifitTvi benifitTviBig = null;
        if (pDay <= 14)
        {
            benifitTvi = new BenifitTvi();
            benifitTvi = getBenifitTviByParam(pDay, pType, pArea, planId);
        }
        else
        {
            benifitTvi = new BenifitTvi();
            benifitTviLess = new BenifitTvi();
            benifitTviBig = new BenifitTvi();

            int pDayCalc = pDay - 14;
            int phannguyen = 0;
            int phandu = 0;
            phannguyen = pDayCalc / 7;
            phandu = pDayCalc % 7;
            if (phannguyen == 0) {
                benifitTviLess = getBenifitTviByParam(14, pType, pArea, planId);
                benifitTviBig = getBenifitTviByParam(pDay, pType, pArea, planId);
                benifitTvi.setPremium(benifitTviLess.getPremium() + benifitTviBig.getPremium());
            } else {
            	benifitTviLess = getBenifitTviByParam(14, pType, pArea, planId);
                benifitTviBig = getBenifitTviByParam(pDay, pType, pArea, planId);
                if (phandu > 0)
                {
                	benifitTvi.setPremium(benifitTviLess.getPremium() + (phannguyen * benifitTviBig.getPremium() + benifitTviBig.getPremium()));
                }
                else
                {
                	benifitTvi.setPremium(benifitTviLess.getPremium() + (phannguyen * benifitTviBig.getPremium()));
                }
            }
        }

        return benifitTvi;
	}

	@SuppressWarnings("unchecked")
	private BenifitTvi getBenifitTviByParam(int pDay, String pType, String pArea, String planId) {
		// create the command for the stored procedure
        // Presuming the DataTable has a column named .                       
        String expression = "";
        if (!StringUtils.isEmpty(pArea)) {
        	expression = "SELECT * FROM BENIFIT_TVI WHERE FROM_DATE <= :pDay AND TO_DATE >= :pDay AND TYPE_OF_CONTACT_ID = :pType AND AREA_ID = :pArea AND TYPE_OF_PLAN_ID= :planId order by FROM_DATE DESC";
        } else {
        	expression = "SELECT * FROM BENIFIT_TVI WHERE FROM_DATE <= :pDay AND TO_DATE >= :pDay AND TYPE_OF_CONTACT_ID = :pType AND TYPE_OF_PLAN_ID= :planId order by FROM_DATE DESC";
        }
        
        Query query = entityManager.createNativeQuery(expression, BenifitTvi.class);
        query.setParameter("pDay", pDay);
        query.setParameter("pType", pType);
        query.setParameter("planId", planId);
        if (!StringUtils.isEmpty(pArea)) {
        	query.setParameter("pArea", pArea);
        }
        
        List<BenifitTvi> data = query.getResultList();
        
        if (data != null && data.size() > 0) {
        	return data.get(0);
        }
        return null;
    }
}