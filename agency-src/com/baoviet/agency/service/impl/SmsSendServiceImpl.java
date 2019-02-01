package com.baoviet.agency.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.tempuri.ReceiveMT;
import org.tempuri.ReceiveMTSoap;

import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.SmsSend;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.SmsSendDTO;
import com.baoviet.agency.repository.SmsSendRepository;
import com.baoviet.agency.service.SmsSendService;
import com.baoviet.agency.service.mapper.SmsSendMapper;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.logging.SOAPLoggingHandler;
import com.baoviet.agency.web.rest.ProductHomeResource;

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
	
	@Value("${spring.application.ws.sms.requestid}")
	private String wsRequestid;
	
	@Value("${spring.application.ws.sms.typemt}")
	private String wsTypemt;
	
	@Value("${spring.application.ws.sms.pkCorp}")
	private String wsPkCorp;
	
	@Value("${spring.application.ws.sms.deptcode1}")
	private String wsDeptcode1;
	
	@Value("${spring.application.ws.sms.deptcode2}")
	private String wsDeptcode2;
	
	private static final Integer INPUT_TYPE = 117;
	private static final Integer MESS_TYPE = 0;

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
	@Async
	// 1 , 2: success; 0: error
	public void sendSMS(AgreementDTO voAg, Contact contact, String phone, String content) {
		log.debug("Request to sendSMS, phone {}, content {}", phone, content);
		
		String pkServiceid = voAg.getAgreementId() + "_" + DateUtils.date2StrHHMMSS(new Date());
		
		String result = getService().inputMT(INPUT_TYPE, MESS_TYPE, phone, content, wsRequestid, wsTypemt, "", wsUsername, wsPassword, wsPkCorp, pkServiceid, "", wsDeptcode1, wsDeptcode2);
		
        JSONArray arr;
        String status = "";
		try {
			JSONObject obj = new JSONObject(result);
			arr = obj.getJSONArray("results");
			for (int i = 0; i < arr.length(); i++) {
	            status = arr.getJSONObject(i).getString("error");
	        }
		} catch (JSONException e) {
			e.printStackTrace();
		}
		int iStatus = Integer.parseInt(status);
		
		// Save
		insertSmsSend(voAg, contact, phone, iStatus);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ReceiveMTSoap getService() {
//		SMS smsSoap = new SMS();
//		SMSSoap smsService = smsSoap.getSMSSoap();
//
//		// Set url ws
//		((BindingProvider) smsService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsUrl);
//
//		Binding binding = ((BindingProvider) smsService).getBinding();
//		List handlerChain = binding.getHandlerChain();
//		handlerChain.add(new SOAPLoggingHandler(wsUsername, wsPassword));
//		binding.setHandlerChain(handlerChain);
		
		ReceiveMT smsSoap = new ReceiveMT();
		ReceiveMTSoap smsService = smsSoap.getReceiveMTSoap();

		// Set url ws
		((BindingProvider) smsService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsUrl);

		Binding binding = ((BindingProvider) smsService).getBinding();
		List handlerChain = binding.getHandlerChain();
		handlerChain.add(new SOAPLoggingHandler(wsUsername, wsPassword));
		binding.setHandlerChain(handlerChain);

		log.debug("REST request to getService : {}", smsService);
		return smsService;
	}

	protected void insertSmsSend(AgreementDTO voAg, Contact contact, String content, Integer status) {
		log.debug("Request to insertSmsSend : AgreementDTO {}, Contact {}", voAg, contact);
		
		SmsSendDTO smsSend = new SmsSendDTO();
		smsSend.setContent(content);
		if (!StringUtils.isEmpty(contact.getPhone())) {
			smsSend.setPhoneNumber(contact.getPhone());	
		} else {
			smsSend.setPhoneNumber(voAg.getReceiverMoible());
		}
		smsSend.setNumberSuccess(status);
		smsSend.setNumberFails(0);
		smsSend.setUserId("AGREEMENT");                        
		smsSend.setUserName("AGREEMENT");                        
		smsSend.setFullname("AGREEMENT");
		smsSend.setSmsSysdate(new Date());
		
		this.save(smsSend);
	}
}
