package com.baoviet.agency.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.domain.SmsSend;
import com.baoviet.agency.dto.SmsSendDTO;
import com.baoviet.agency.repository.SmsSendRepository;
import com.baoviet.agency.service.SmsSendService;
import com.baoviet.agency.service.mapper.SmsSendMapper;
import com.baoviet.agency.utils.logging.SOAPLoggingHandler;
import com.baoviet.agency.web.rest.ProductHomeResource;

import vn.bluezone.w2m.SMS;
import vn.bluezone.w2m.SMSSoap;

/**
 * Service Implementation for managing SmsSend.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class SmsSendServiceImpl implements SmsSendService {

	private final Logger log = LoggerFactory.getLogger(ProductHomeResource.class);

	@Autowired
	private SmsSendRepository smsSendRepository;

	@Autowired
	private SmsSendMapper smsSendMapper;
	
	@Value("${spring.application.ws.sms.url}")
	private String wsUrl;

	@Value("${spring.application.ws.sms.username}")
	private String wsUsername;

	@Value("${spring.application.ws.sms.password}")
	private String wsPassword;

	@Override
	public String save(SmsSendDTO info) {
		log.debug("REST request to save SmsSend : {}", info);
		if (info != null) {
			try {
				SmsSend entity = smsSendRepository.save(smsSendMapper.toEntity(info));
				return entity.getSmsId();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	// 1 , 2: success; 0: error
	public int sendSMS(String phone, String content) {
		log.debug("Request to sendSMS, phone {}, content {}", phone, content);
		String status = getService().sendMT(phone, content, "", "", wsUsername, wsPassword);
		return Integer.parseInt(status);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private SMSSoap getService() {
		SMS smsSoap = new SMS();
		SMSSoap smsService = smsSoap.getSMSSoap();

		// Set url ws
		((BindingProvider) smsService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsUrl);

		Binding binding = ((BindingProvider) smsService).getBinding();
		List handlerChain = binding.getHandlerChain();
		handlerChain.add(new SOAPLoggingHandler(wsUsername, wsPassword));
		binding.setHandlerChain(handlerChain);

		return smsService;
	}

}
