package com.baoviet.agency.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Upload;
import com.baoviet.agency.dto.UploadDTO;
import com.baoviet.agency.repository.UploadRepository;
import com.baoviet.agency.service.UploadService;
import com.baoviet.agency.service.mapper.UploadMapper;

/**
 * Service Implementation for managing Upload.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class UploadServiceImpl implements UploadService {

	private final Logger log = LoggerFactory.getLogger(AgreementServiceImpl.class);
	
	@Autowired
	private UploadRepository uploadRepository;

	@Autowired
	private UploadMapper uploadMapper;

	@Override
	public String insertUpload(UploadDTO info) {
		log.debug("Request to save insertUpload, {}", info);
		if (info != null) {
			try {
				Upload entity = uploadRepository.save(uploadMapper.toEntity(info));
				return entity.getId();

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public UploadDTO save(UploadDTO uploadDTO) {
		log.debug("Request to save Upload, {}", uploadDTO);
		// Convert to Entity
		Upload entity = uploadMapper.toEntity(uploadDTO);
		Upload result = uploadRepository.save(entity);
		return uploadMapper.toDto(result);
	}

}
