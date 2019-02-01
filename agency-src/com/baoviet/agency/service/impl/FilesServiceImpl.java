package com.baoviet.agency.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.Files;
import com.baoviet.agency.dto.FilesDTO;
import com.baoviet.agency.repository.FileRepository;
import com.baoviet.agency.service.FilesService;
import com.baoviet.agency.service.mapper.FilesMapper;

import sun.misc.BASE64Decoder;

/**
 * Service Implementation for managing TVI.
 * 
 * @author CuongTT
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class FilesServiceImpl implements FilesService {

	private final Logger log = LoggerFactory.getLogger(FilesServiceImpl.class);

	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private FilesMapper filesMapper;

	@Override
	public String save(FilesDTO fileInfo) {
		log.debug("Request to save files : {} ", fileInfo);

		try {
			Files entity = filesMapper.toEntity(fileInfo);

			// Convert to byte[]
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] imageByte = decoder.decodeBuffer(fileInfo.getContentFile());

			entity.setContent(imageByte);
			// upload date
			if (fileInfo.getUploadDate() == null) {
				entity.setUploadDate(new Date());
			}

			// length
			if (fileInfo.getLength() == null || fileInfo.getLength() == 0) {
				entity.setLength((double) imageByte.length);
			}

			entity = fileRepository.save(entity);
			return entity.getFileId();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public FilesDTO findById(String id) {
		log.debug("Request to findById files : {} ", id);

		FilesDTO fileDTO = null;
		try {
			Files entity = fileRepository.findOne(id);

			if (entity != null) {
				fileDTO = filesMapper.toDto(entity);

				// Convert byte[] to String base 64
				String encoded = Base64.getEncoder().encodeToString(entity.getContent());
				fileDTO.setContentFile(encoded);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}

		return fileDTO;
	}

}
