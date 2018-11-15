package com.baoviet.agency.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.baoviet.agency.bean.ExchangeRateDTO;
import com.baoviet.agency.service.ExchangeRateService;

/**
 * Service Implementation for managing TVI.
 * 
 * @author CuongTT
 */
@Service
@Transactional
@CacheConfig(cacheNames = "common")
public class ExchangeRateServiceImpl implements ExchangeRateService {

	private final Logger log = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

	@Value("${spring.application.proxy.enable}")
	private boolean proxyEnable;
	
	@Value("${spring.application.proxy.address}")
	private String proxyAddress;
	
	@Value("${spring.application.proxy.port}")
	private String proxyPort;
	
	@Value("${spring.application.proxy.username}")
	private String proxyUsername;
	
	@Value("${spring.application.proxy.password}")
	private String proxyPassword;
	
	@Override
	@Cacheable
	public List<ExchangeRateDTO> getAllExchangeRateDataFromVCB() {
		
		// Set proxy
		if (proxyEnable) {
			System.getProperties().put("http.proxyHost", proxyAddress);
			System.getProperties().put("http.proxyPort", proxyPort);
			System.getProperties().put("http.proxyUser", proxyUsername);
			System.getProperties().put("http.proxyPassword", proxyPassword);
		}
		
		try {
			//String requestUrl = "https://www.vietcombank.com.vn/exchangerates/ExrateXML.aspx";
			String requestUrl = "http://103.11.172.38/exchangerates/ExrateXML.aspx";
			String content = org.apache.commons.io.IOUtils.toString(new URL(requestUrl), "utf8");
			log.debug(content);
			return fetchItemData(content);
		} catch (MalformedURLException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}   

		return null;
	}

	@Override
	public double getCurrentRate(String currencyCode1, String currencyCode2) {
		log.debug("Request to getCurrentRate: currencyCode1{}, currencyCode2{} : ", currencyCode1, currencyCode2);
		List<ExchangeRateDTO> data = getAllExchangeRateDataFromVCB();
		
		ExchangeRateDTO exRate1 = getExchangeRateByCode(data, currencyCode1);
		ExchangeRateDTO exRate2 = getExchangeRateByCode(data, currencyCode2);
		
		if (exRate1 != null && exRate1.getSell() > 0 && exRate2 != null && exRate2.getSell() > 0) {
			return exRate1.getSell()/exRate2.getSell();
		}
		return 0d;
	}
	
	private ExchangeRateDTO getExchangeRateByCode(List<ExchangeRateDTO> data, String currencyCode) {
		log.debug("Request to getExchangeRateByCode: List<ExchangeRateDTO>{}, currencyCode{} : ", data, currencyCode);
		for (ExchangeRateDTO exchangeRateDTO : data) {
			if (StringUtils.equals(currencyCode, exchangeRateDTO.getCurrencyCode())) {
				return exchangeRateDTO;
			}
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	private List<ExchangeRateDTO> fetchItemData(String content) {  
		List<ExchangeRateDTO> result = new ArrayList<>();
		
		// Add default VND
		ExchangeRateDTO itemVNDTO = new ExchangeRateDTO();     
		itemVNDTO.setCurrencyCode("VND");
		itemVNDTO.setCurrencyName("VND");
		itemVNDTO.setBuy(1d);
		itemVNDTO.setTransfer(1d);
		itemVNDTO.setSell(1d);
		
		result.add(itemVNDTO);
		
		try {   
			SAXBuilder builder = new SAXBuilder();   
			
			content = content.substring(content.indexOf("<ExrateList>"));
			
			Document document = (Document) builder.build(new InputSource(new StringReader(content)));    
			Element rootNode = document.getRootElement();    
			List<Element> listExRate = rootNode.getChildren("Exrate");     
			for (Element item : listExRate) {
				ExchangeRateDTO itemDTO = new ExchangeRateDTO();     
				String currencyCode = item.getAttributeValue("CurrencyCode");     
				String currencyName = item.getAttributeValue("CurrencyName");
				String buy = item.getAttributeValue("Buy");
				String transfer = item.getAttributeValue("Transfer");
				String sell = item.getAttributeValue("Sell");
				itemDTO.setCurrencyCode(currencyCode);
				itemDTO.setCurrencyName(currencyName);
				itemDTO.setBuy(Double.valueOf(buy));
				itemDTO.setTransfer(Double.valueOf(transfer));
				itemDTO.setSell(Double.valueOf(sell));
				// add to list
				result.add(itemDTO);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return result;
	}
	
	
}
