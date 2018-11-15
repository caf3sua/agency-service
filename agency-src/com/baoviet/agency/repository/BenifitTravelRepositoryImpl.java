package com.baoviet.agency.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.BenifitTravel;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class BenifitTravelRepositoryImpl implements BenifitTravelRepositoryExtend {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public BenifitTravel getByParam(int pDay, String pType, String pArea, String planId) {
		BenifitTravel pBenifitTravel = null;
		BenifitTravel pBenifitTravelLess = null;
		BenifitTravel pBenifitTravelBig = null;
        if (pDay <= 14)
        {
        	pBenifitTravel = GetBENIFIT_TRAVELByPram(pDay, pType, pArea, planId);
        }
        else
        {
        	pBenifitTravel = new BenifitTravel();
            pBenifitTravelLess = new BenifitTravel();
            pBenifitTravelBig = new BenifitTravel();

            int p_Day_Calc = pDay - 14;
            int phannguyen = 0;
            int phandu = 0;
            phannguyen = p_Day_Calc / 7;
            phandu = p_Day_Calc % 7;

            if (phannguyen == 0)
            {
                pBenifitTravelLess = GetBENIFIT_TRAVELByPram(14, pType, pArea, planId);
                pBenifitTravelBig = GetBENIFIT_TRAVELByPram(pDay, pType, pArea, planId);
                if (pBenifitTravelLess != null && pBenifitTravelBig != null) {
                	pBenifitTravel.setPremium(pBenifitTravelLess.getPremium() + pBenifitTravelBig.getPremium());	
                } else {
                	return null;
                }
            }
            else
            {
                if (phandu > 0)
                {
                    pBenifitTravelLess = GetBENIFIT_TRAVELByPram(14, pType, pArea, planId);
                    pBenifitTravelBig = GetBENIFIT_TRAVELByPram(pDay, pType, pArea, planId);
                    if (pBenifitTravelLess != null && pBenifitTravelBig != null) {
                    	pBenifitTravel.setPremium(pBenifitTravelLess.getPremium() + (phannguyen * pBenifitTravelBig.getPremium() + pBenifitTravelBig.getPremium()));	
                    } else {
                    	return null;
                    }
                }
                else
                {
                    pBenifitTravelLess = GetBENIFIT_TRAVELByPram(14, pType, pArea, planId);
                    pBenifitTravelBig = GetBENIFIT_TRAVELByPram(pDay, pType, pArea, planId);
                    if (pBenifitTravelLess != null && pBenifitTravelBig != null) {
                    	pBenifitTravel.setPremium(pBenifitTravelLess.getPremium() + (phannguyen * pBenifitTravelBig.getPremium()));	
                    } else {
                    	return null;                    	
                    }
                }
            }
        }

        return pBenifitTravel;
	}
	
	@SuppressWarnings("unchecked")
	private BenifitTravel GetBENIFIT_TRAVELByPram(int pDay, String pType, String pArea, String planId) {
        // create the command for the stored procedure
        // Presuming the DataTable has a column named .  
		String expression = "";
        if (!StringUtils.isEmpty(pArea)) {
        	expression = "SELECT * FROM BENIFIT_TRAVEL WHERE  FROM_DATE <= :pDay AND TO_DATE >= :pDay AND TYPE_OF_CONTACT_ID = :pType AND AREA_ID = :pArea AND TYPE_OF_PLAN_ID= :planId order by FROM_DATE DESC";
        } else {
        	expression = "SELECT * FROM BENIFIT_TRAVEL WHERE  FROM_DATE <= :pDay AND TO_DATE >= :pDay AND TYPE_OF_CONTACT_ID = :pType AND TYPE_OF_PLAN_ID= :planId order by FROM_DATE DESC";
        }
        
        Query query = entityManager.createNativeQuery(expression, BenifitTravel.class);
        query.setParameter("pDay", pDay);
        query.setParameter("pType", pType);
        query.setParameter("planId", planId);
        if (!StringUtils.isEmpty(pArea)) {
        	query.setParameter("pArea", pArea);
        }
        
        List<BenifitTravel> data = query.getResultList();
        
        if (data != null && data.size() > 0) {
        	return data.get(0);
        }
        return null;
    }

}