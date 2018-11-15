package com.baoviet.agency.service;

import java.util.List;

import com.baoviet.agency.domain.ProductGenInfo;
import com.baoviet.agency.web.rest.vm.TVVBaseVM;

/**
 * Service Interface for managing Report.
 */
public interface TVVService {
	
	List<ProductGenInfo> getDefaultNews(String kenhPhanPhoi);
	
	List<ProductGenInfo> getHighlightNews(String kenhPhanPhoi);
	
	List getDoanhThuTheoSanPham(TVVBaseVM param);
}

