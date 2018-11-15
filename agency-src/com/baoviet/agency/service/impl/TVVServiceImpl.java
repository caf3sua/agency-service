package com.baoviet.agency.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.ProductGenInfo;
import com.baoviet.agency.repository.ProductGenInfoRepository;
import com.baoviet.agency.repository.TVVRepository;
import com.baoviet.agency.service.TVVService;
import com.baoviet.agency.web.rest.vm.TVVBaseVM;

/**
 * Service Implementation for managing Reprot.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class TVVServiceImpl implements TVVService {

	private final Logger log = LoggerFactory.getLogger(TVVServiceImpl.class);

	@Autowired
	private ProductGenInfoRepository productGenInfoRepository;
	
	@Autowired
	private TVVRepository tVVRepository;

	@Override
	public List<ProductGenInfo> getDefaultNews(String kenhPhanPhoi) {
		List<ProductGenInfo> resultData = new ArrayList<>();
		if (!StringUtils.isEmpty(kenhPhanPhoi)) {
			List<ProductGenInfo> data = productGenInfoRepository.getProductGenInfosforhomepage(kenhPhanPhoi);
			if (data != null) {
				for (ProductGenInfo item : data) {
					if (item.getIsDefault().equals("True")) {
						resultData.add(item);
					}
				}
			}
		}
		return resultData;
	}

	@Override
	public List<ProductGenInfo> getHighlightNews(String kenhPhanPhoi) {
		if (!StringUtils.isEmpty(kenhPhanPhoi)) {
			List<ProductGenInfo> data = productGenInfoRepository.getProductGenInfosforhomepage(kenhPhanPhoi);
			return data;
		}
		return null;
	}

	@Override
	public List getDoanhThuTheoSanPham(TVVBaseVM param) {
		// TODO Auto-generated method stub
		return null;
	}

}
