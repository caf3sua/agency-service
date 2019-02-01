package com.baoviet.agency.service.impl;

import java.net.URISyntaxException;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.service.CommonService;
import com.baoviet.agency.service.MailService;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.SendMaillVM;

/**
 * Service Implementation for managing Common.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
public class CommonServiceImpl implements CommonService {

	private final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

	@Autowired
	private MailService mailService;

	@Override
	public SendMaillVM sendMail(SendMaillVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to sendMail : {}", param);
		if (StringUtils.isNotEmpty(param.getFrom())) {
			if (!ValidateUtils.isEmail(param.getFrom())) {
				throw new AgencyBusinessException(param.getFrom(), ErrorCode.INVALID , "EmailFrom không đúng định dạng.");
			}
		}
		if (StringUtils.isNotEmpty(param.getTo())) {
			if (!ValidateUtils.isEmail(param.getTo())) {
				throw new AgencyBusinessException(param.getTo(), ErrorCode.INVALID , "EmailTo không đúng định dạng.");
			}
		}
		if (param.getCc() != null && param.getCc().size() > 0) {
			for (String item : param.getCc()) {
				if (StringUtils.isNotEmpty(item)) {
					if (!ValidateUtils.isEmail(item)) {
						throw new AgencyBusinessException("cc", ErrorCode.INVALID , "EmailCc không đúng định dạng.");
					}
				}
			}
		}
		if (param.getFrom() == null) {
			param.setFrom("");
		}
		if (param.getTo() == null) {
			param.setTo("");
		}
		if (param.getSubject() == null) {
			param.setSubject("");
		}
		if (param.getContent() == null) {
			param.setContent("");
		}
		// Send mail
		mailService.sendEmail(param.getFrom(), param.getTo(), param.getCc(), param.getSubject(), param.getContent(), false, false);
		
		param.setResult(true);
		return param;
	}

	

}
