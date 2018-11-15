package com.baoviet.agency.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.CodeManagement;
import com.baoviet.agency.dto.CodeManagementDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.CodeManagementRepository;
import com.baoviet.agency.service.CodeManagementService;
import com.baoviet.agency.service.mapper.CodeManagementMapper;
import com.baoviet.agency.utils.DateUtils;


/**
 * Service Implementation for managing CodeManagement.
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class CodeManagementServiceImpl implements CodeManagementService {

    private final Logger log = LoggerFactory.getLogger(CodeManagementServiceImpl.class);
    
    @Autowired
    private CodeManagementMapper codeManagementMapper;
    
    @Autowired
    private CodeManagementRepository codeManagementRepository;
    
    @Value("${spring.application.CID}")
	private String companyId;
    
    @Value("${spring.application.DID}")
	private String departmentId;
    
    public List<String> lineIds = Arrays.asList("TVI", "CAR", "MOT", "TVC", "HOM", "KHC", "KCR", "BVP", "PAS", "HHV", "GFI", "TNC");
    		
	@Override
	public String getCode(String year, String type, String nv) {
		// String companyId = String companyName, , String departmentId = String departmentName,
		String id = codeManagementRepository.getCode(companyId, departmentId, companyId, departmentId, year, type, nv);
		log.debug("Request to getCode, return id {}", id);
		return id;
	}

	@Override
	public CodeManagementDTO getById(String id) {
		log.debug("Request to getById, id {}", id);
		CodeManagement entity = codeManagementRepository.findOne(id);
		return codeManagementMapper.toDto(entity);
	}

	@Override
	public CodeManagementDTO getCodeManagement(String productName) throws AgencyBusinessException {
		// Check lineId
		if (!lineIds.contains(productName)) {
			throw new AgencyBusinessException("productName", ErrorCode.LINE_ID_NOT_FOUND , "Bảo Việt hiện chưa có chính sách cấp đơn cho sản phẩm có mã này. LineID: " + productName);
		}

		String year = DateUtils.getCurrentYear().substring(2);
		// String companyId = String companyName, , String departmentId = String departmentName,
		String id = codeManagementRepository.getCode(companyId, departmentId, companyId, departmentId, year, "YC", productName);
		
		return getById(id);
	}

	@Override
	public String getIssueNumber(String type, String nv) {
		log.debug("Request to getIssueNumber, type {}, nv {}", type, nv);
		String year = DateUtils.getCurrentYear().substring(2);
		String id = codeManagementRepository.getCode(companyId, departmentId, companyId, departmentId, year, type, nv);
		CodeManagement entity = codeManagementRepository.findOne(id);
		
		return entity.getIssueNumber();
	}

}
