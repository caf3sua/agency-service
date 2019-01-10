package com.baoviet.agency.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.Menu;
import com.baoviet.agency.dto.MenuDTO;

/**
 * Spring Data JPA repository for the GnocCR module.
 */

@Repository
public class MenuRepositoryImpl implements MenuRepositoryExtend {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<MenuDTO> getRootMenuCollectionAgency(int pLevel, String pRoleName, String pSite) {
		String expression = "";
        if (pLevel == 0) {
        	expression = "select * from (select * from menu mu where mu.site = :pSite and mu.menu_id in (select m.menu_parent_id from menu m where m.is_menu = 1 and m.menu_id in (select ro.menu_name from spp_roles ro where ro.role_name= :pRoleName and ro.allowaccess=1))" +
                    " union " +
                    " select * from menu m where m.is_menu=1 and m.menu_parent_id is null" +
                    " and m.menu_id in (select ro.menu_name from spp_roles ro where ro.role_name= :pRoleName and ro.allowaccess=1)) order by menu_order";
        } else {
        	expression = "select * from menu m where m.site= :pSite and m.menu_level = 1 and m.is_menu=1 and m.menu_id in (select ro.menu_name from spp_roles ro where ro.role_name= :pRoleName and ro.allowaccess=1) order by m.MENU_ORDER";
        }
        
        Query query = entityManager.createNativeQuery(expression, Menu.class);
        query.setParameter("pSite", pSite);
        query.setParameter("pRoleName", pRoleName);

        List<MenuDTO> data = query.getResultList();
        
        if (data != null && data.size() > 0) {
        	return data;
        }
		return null;
	}

}