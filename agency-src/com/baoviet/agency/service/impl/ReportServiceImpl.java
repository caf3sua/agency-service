package com.baoviet.agency.service.impl;

import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.baoviet.agency.bean.DashboardDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.PayActionDTO;
import com.baoviet.agency.dto.eclaim.EclaimDTO;
import com.baoviet.agency.dto.eclaim.EclaimResponseDTO;
import com.baoviet.agency.dto.report.BcAgencyDTO;
import com.baoviet.agency.dto.report.BcDoanhThuAdminDTO;
import com.baoviet.agency.dto.report.BcDoanhThuDTO;
import com.baoviet.agency.dto.report.BcHoaHongAdminDTO;
import com.baoviet.agency.dto.report.BcHoaHongDTO;
import com.baoviet.agency.dto.report.BcKhaiThacMotoDTO;
import com.baoviet.agency.dto.report.ReportDataDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ReportRepository;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.PayActionService;
import com.baoviet.agency.service.ReportService;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.ReportSearchCriterialVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementVM;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service Implementation for managing Reprot.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ReportServiceImpl implements ReportService {

	private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

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
	
	@Value("${spring.application.ws.eclaim.url}")
	private String eclaimWsUrl;
	
	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private AgreementService agreementService;

	@Autowired
	private PayActionService payActionService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public ReportDataDTO getBaoCaoDoanhThu(ReportSearchCriterialVM obj, String agentId) {
		log.debug("REST request to getBaoCaoDoanhThu, ReportSearchCriterialVM{} :", obj);
		ReportDataDTO returnData = new ReportDataDTO();
		
		List<BcAgencyDTO> finalData = calculateBaocaoDataDoanhthu(obj, agentId);
		
		// set data
		returnData.setData(finalData);
		
		calculateExtraInfoDoanhThu(returnData);
		
		return returnData;
	}
	
	@Override
	public ReportDataDTO getBaoCaoDoanhThuAdmin(ReportSearchCriterialVM obj, String adminId) {
		log.debug("REST request to getBaoCaoDoanhThu, ReportSearchCriterialVM{} :", obj);
		ReportDataDTO returnData = new ReportDataDTO();
		
		List<BcAgencyDTO> finalData = calculateBaocaoDataDoanhthuAdmin(obj, adminId);
		
		// set data
		returnData.setData(finalData);
		
		calculateExtraInfoDoanhThu(returnData);
		
		return returnData;
	}
	
	@Override
	public ReportDataDTO getBaoCaoHoaHong(ReportSearchCriterialVM obj, String agentId) {
		log.debug("REST request to getBaoCaoHoaHong, ReportSearchCriterialVM{} :", obj);
		ReportDataDTO returnData = new ReportDataDTO();
		
		// Doanh thu 
		ReportDataDTO doanhthuDTO = getBaoCaoDoanhThu(obj, agentId);
		
		// Get current data
		List<BcAgencyDTO> nowData = calculateBaocaoDataHoahong(obj, agentId);
		
		// Update search previous		
		formatPreviousSearch(obj);
		List<BcAgencyDTO> previousData = calculateBaocaoDataHoahong(obj, agentId);
		
		// set data
		returnData.setData(nowData);
		returnData.setOtherData(previousData);
		
		// calculate extra data
		calculateExtraInfoCommission(returnData);
		returnData.setTotalIncome(doanhthuDTO.getTotalIncome());
		
		return returnData;
	}
	
	@Override
	public ReportDataDTO getBaoCaoHoaHongAdmin(ReportSearchCriterialVM obj, String adminId) {
		log.debug("REST request to getBaoCaoHoaHong, ReportSearchCriterialVM{} :", obj);
		ReportDataDTO returnData = new ReportDataDTO();
		
		// Doanh thu 
		ReportDataDTO doanhthuDTO = getBaoCaoDoanhThuAdmin(obj, adminId);
		
		// Get current data
		List<BcAgencyDTO> nowData = calculateBaocaoDataHoahongAdmin(obj, adminId);
		
		// Update search previous		
		formatPreviousSearch(obj);
		List<BcAgencyDTO> previousData = calculateBaocaoDataHoahongAdmin(obj, adminId);
		
		// set data
		returnData.setData(nowData);
		returnData.setOtherData(previousData);
		
		// calculate extra data
		calculateExtraInfoCommission(returnData);
		returnData.setTotalIncome(doanhthuDTO.getTotalIncome());
		
		return returnData;
	}
	
	@Override
	public DashboardDTO getBaoCaoForDashboard(ReportSearchCriterialVM obj, String agentId) {
		log.debug("REST request to getBaoCaoForDashboard, ReportSearchCriterialVM{}, agentId{} :", obj, agentId);
		SearchAgreementVM param = new SearchAgreementVM();
		param.setFromDate(DateUtils.str2Date(obj.getFromDate()));
		param.setToDate(DateUtils.str2Date(obj.getToDate()));
		
		DashboardDTO data = agreementService.getDashboardInfo(param, agentId);
		
		return data;
	}
	
	@Override
	public DashboardDTO getBaoCaoForDashboardAdmin(ReportSearchCriterialVM obj, String adminId) {
		log.debug("REST request to getBaoCaoForDashboard, ReportSearchCriterialVM{}, adminId{} :", obj, adminId);
		SearchAgreementVM param = new SearchAgreementVM();
		param.setFromDate(DateUtils.str2Date(obj.getFromDate()));
		param.setToDate(DateUtils.str2Date(obj.getToDate()));
		
		DashboardDTO data = agreementService.getDashboardInfoAdmin(param, adminId);
		
		return data;
	}

	@Override
	public List<PayActionDTO> getBaoCaoTransfer(ReportSearchCriterialVM obj, String agentId) {
		log.debug("REST request to getBaoCaoTransfer, ReportSearchCriterialVM{}, agentId{} :", obj, agentId);
		List<PayActionDTO> data = payActionService.search(obj.getFromDate(), obj.getToDate(), agentId);
		return data;
	}
	
	@Override
	public List<PayActionDTO> getBaoCaoTransferAdmin(ReportSearchCriterialVM obj, String adminId) {
		log.debug("REST request to getBaoCaoTransfer, ReportSearchCriterialVM{}, adminId{} :", obj, adminId);
		List<PayActionDTO> data = payActionService.searchAdmin(obj.getFromDate(), obj.getToDate(), adminId);
		return data;
	}
	
	@Override
	public List<BcKhaiThacMotoDTO> getBaoCaoKtMoto(ReportSearchCriterialVM obj, String agentId) {
		 log.debug("REST request to getBaoCaoKtMoto, ReportSearchCriterialVM{}, agentId{} :", obj, agentId);
		 List<BcKhaiThacMotoDTO> data = agreementService.getBaoCaoKtMoto(obj, agentId);
		return data;
	}

	@Override
	public List<PayActionDTO> getBaoCaoHistoryPurchase(String contactId, String agentId) {
		log.debug("REST request to getBaoCaoHistoryPurchase, contactId{}, agentId{} :", contactId, agentId);
		List<PayActionDTO> data = payActionService.search(contactId, agentId);
		return data;
	}
	
	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	private List<BcAgencyDTO> calculateBaocaoDataDoanhthu(ReportSearchCriterialVM obj, String agentId) {
		log.debug("REST request to calculateBaocaoDataDoanhthu, ReportSearchCriterialVM{} :", obj);
		List<BcDoanhThuDTO> data = reportRepository.getBaoCaoDoanhThu(obj.getFromDate(), obj.getToDate(), agentId);
		
		// format data
		List<BcAgencyDTO> result = genrateDataBetweenDate(DateUtils.str2Date(obj.getFromDate()), DateUtils.str2Date(obj.getToDate()));
		
		// format
		formatBaocaoDoanhthu(result, data);
		
		// format by year
		List<BcAgencyDTO> finalData = formatBaocaoDoanhthuByYear(result, obj);
		
		log.debug("Result of get getBaoCaoDoanhThu, {}", data.size());
		
		return finalData;
	}
	
	private List<BcAgencyDTO> calculateBaocaoDataDoanhthuAdmin(ReportSearchCriterialVM obj, String adminId) {
		log.debug("REST request to calculateBaocaoDataDoanhthu, ReportSearchCriterialVM{} :", obj);
		List<BcDoanhThuAdminDTO> data = reportRepository.getBaoCaoDoanhThuAdmin(obj.getFromDate(), obj.getToDate(), adminId);
		
		// format data
		List<BcAgencyDTO> result = genrateDataBetweenDate(DateUtils.str2Date(obj.getFromDate()), DateUtils.str2Date(obj.getToDate()));
		
		// format
		formatBaocaoDoanhthuAdmin(result, data);
		
		// format by year
		List<BcAgencyDTO> finalData = formatBaocaoDoanhthuByYear(result, obj);
		
		log.debug("Result of get getBaoCaoDoanhThu, {}", data.size());
		
		return finalData;
	}
	
	private void formatPreviousSearch(ReportSearchCriterialVM obj) {
		log.debug("REST request to formatPreviousSearch, ReportSearchCriterialVM{} :", obj);
		String fromDate = obj.getFromDate();
		String toDate = obj.getToDate();
		int ranger = DateUtils.getNumberDaysBetween2DateStr(fromDate, toDate) + 1;
		obj.setFromDate(DateUtils.date2Str(DateUtils.addDay(DateUtils.str2Date(fromDate), -ranger)));
		obj.setToDate(DateUtils.date2Str(DateUtils.addDay(DateUtils.str2Date(toDate), -ranger)));
	}
	
	private List<BcAgencyDTO> calculateBaocaoDataHoahong(ReportSearchCriterialVM obj, String agentId) {
		log.debug("REST request to calculateBaocaoDataHoahong, ReportSearchCriterialVM{} :", obj);
		List<BcHoaHongDTO> data = reportRepository.getBaoCaoHoaHong(obj.getFromDate(), obj.getToDate(), agentId);
		
		// Format data
		List<BcAgencyDTO> result = genrateDataBetweenDate(DateUtils.str2Date(obj.getFromDate()), DateUtils.str2Date(obj.getToDate()));

		// format
		formatBaocaoHoahong(result, data);
		
		// format by year
		List<BcAgencyDTO> finalData = formatBaocaoHoahongByYear(result, obj);
				
		log.debug("Result of get getBaoCaoHoaHong, {}", data.size());
		
		return finalData;
	}
	
	private List<BcAgencyDTO> calculateBaocaoDataHoahongAdmin(ReportSearchCriterialVM obj, String agentId) {
		log.debug("REST request to calculateBaocaoDataHoahong, ReportSearchCriterialVM{} :", obj);
		List<BcHoaHongAdminDTO> data = reportRepository.getBaoCaoHoaHongAdmin(obj.getFromDate(), obj.getToDate(), agentId);
		
		// Format data
		List<BcAgencyDTO> result = genrateDataBetweenDate(DateUtils.str2Date(obj.getFromDate()), DateUtils.str2Date(obj.getToDate()));

		// format
		formatBaocaoHoahongAdmin(result, data);
		
		// format by year
		List<BcAgencyDTO> finalData = formatBaocaoHoahongByYear(result, obj);
				
		log.debug("Result of get getBaoCaoHoaHong, {}", data.size());
		
		return finalData;
	}
	
	
	private void calculateExtraInfoDoanhThu(ReportDataDTO result) {
		log.debug("REST request to calculateExtraInfoDoanhThu, ReportDataDTO{} :", result);
		long totalIncome = 0l;
		for (BcAgencyDTO item : result.getData()) {
			totalIncome = totalIncome + item.getTotalPremium();
		}
		result.setTotalIncome(totalIncome);
	}
	
	private void calculateExtraInfoCommission(ReportDataDTO result) {
		log.debug("REST request to calculateExtraInfoCommission, ReportDataDTO{} :", result);
		long totalCommission = 0l;
		long previousCommission = 0l;
		long expectCommission = 0l;
		
		for (BcAgencyDTO item : result.getData()) {
			expectCommission = expectCommission + item.getTongHoaHong();
		}
		
		for (BcAgencyDTO item : result.getOtherData()) {
			previousCommission = previousCommission + item.getTongHoaHong();
		}
		
		totalCommission = expectCommission + previousCommission;
		result.setTotalCommission(totalCommission);
		result.setPreviousCommission(previousCommission);
		result.setExpectCommission(expectCommission);
	}
	
	private void formatBaocaoHoahong(List<BcAgencyDTO> result, List<BcHoaHongDTO> data) {
		log.debug("REST request to formatBaocaoHoahong, BcAgencyDTO{}, BcHoaHongDTO{} :", result, data);
		for (BcAgencyDTO item : result) {
			for (BcHoaHongDTO hoahongDTO : data) {
				String dateCompare = DateUtils.date2Str(DateUtils.str2Date(item.getDatePayment()), "yyyyMMdd");
				if (StringUtils.equals(dateCompare, hoahongDTO.getDatePayment())) {
					item.setTotalPremium(hoahongDTO.getTotalPremium());
					item.setHoaHongSale(hoahongDTO.getHoaHongSale());
					item.setHoaHongTruongDv(hoahongDTO.getHoaHongTruongDv());
					item.setHoaHongDv(hoahongDTO.getHoaHongDv());
					item.setTongHoaHong(hoahongDTO.getTongHoaHong());
					data.remove(hoahongDTO);
					break;
				}
			}
		}
	}
	
	private void formatBaocaoHoahongAdmin(List<BcAgencyDTO> result, List<BcHoaHongAdminDTO> data) {
		log.debug("REST request to formatBaocaoHoahong, BcAgencyDTO{}, BcHoaHongDTO{} :", result, data);
		for (BcAgencyDTO item : result) {
			for (BcHoaHongAdminDTO hoahongDTO : data) {
				String dateCompare = DateUtils.date2Str(DateUtils.str2Date(item.getDatePayment()), "yyyyMMdd");
				if (StringUtils.equals(dateCompare, hoahongDTO.getDatePayment())) {
					item.setTotalPremium(hoahongDTO.getTotalPremium());
					item.setHoaHongSale(hoahongDTO.getHoaHongSale());
					item.setHoaHongTruongDv(hoahongDTO.getHoaHongTruongDv());
					item.setHoaHongDv(hoahongDTO.getHoaHongDv());
					item.setTongHoaHong(hoahongDTO.getTongHoaHong());
					data.remove(hoahongDTO);
					break;
				}
			}
		}
	}
	
	private List<BcAgencyDTO> formatBaocaoDoanhthuByYear(List<BcAgencyDTO> result, ReportSearchCriterialVM obj) {
		log.debug("REST request to formatBaocaoDoanhthuByYear, BcAgencyDTO{}, ReportSearchCriterialVM{} :", result, obj);
		List<BcAgencyDTO> data = buildEmptyListForYear();
		// Build YEAR period time
		if (StringUtils.equalsIgnoreCase("YEAR", obj.getPeriodTime())) {
			// Build list
			for (BcAgencyDTO bcAgencyDTO : result) {
				int month = DateUtils.getMonthFromDate(bcAgencyDTO.getDatePayment());
				BcAgencyDTO itemSum = data.get(month);
				// cal
				itemSum.setTotalPremium(itemSum.getTotalPremium() + bcAgencyDTO.getTotalPremium());
			}

			// Set again
			return data;
		}
		
		return result;
	}
	
	private List<BcAgencyDTO> formatBaocaoHoahongByYear(List<BcAgencyDTO> result, ReportSearchCriterialVM obj) {
		log.debug("REST request to formatBaocaoHoahongByYear, BcAgencyDTO{}, ReportSearchCriterialVM{} :", result, obj);
		List<BcAgencyDTO> data = buildEmptyListForYear();
		// Build YEAR period time
		if (StringUtils.equalsIgnoreCase("YEAR", obj.getPeriodTime())) {
			// Build list
			for (BcAgencyDTO bcAgencyDTO : result) {
				int month = DateUtils.getMonthFromDate(bcAgencyDTO.getDatePayment());
				BcAgencyDTO itemSum = data.get(month);
				// cal
				itemSum.setTotalPremium(itemSum.getTotalPremium() + bcAgencyDTO.getTotalPremium());
				itemSum.setHoaHongSale(itemSum.getHoaHongSale() + bcAgencyDTO.getHoaHongSale());
				itemSum.setHoaHongTruongDv(itemSum.getHoaHongTruongDv() + bcAgencyDTO.getHoaHongTruongDv());
				itemSum.setHoaHongDv(itemSum.getHoaHongDv() + bcAgencyDTO.getHoaHongDv());
				itemSum.setTongHoaHong(itemSum.getTongHoaHong() + bcAgencyDTO.getTongHoaHong());
			}

			// Set again
			return data;
		}
		
		return result;
	}
	
	private void formatBaocaoDoanhthu(List<BcAgencyDTO> result, List<BcDoanhThuDTO> data) {
		log.debug("REST request to formatBaocaoDoanhthu, BcAgencyDTO{}, BcDoanhThuDTO{} :", result, data);
		for (BcAgencyDTO item : result) {
			for (BcDoanhThuDTO doanhthuDTO : data) {
				String dateCompare = DateUtils.date2Str(DateUtils.str2Date(item.getDatePayment()), "yyyyMMdd");
				if (StringUtils.equals(dateCompare, doanhthuDTO.getDatePayment())) {
					item.setTotalPremium(doanhthuDTO.getTotalPremium());
					data.remove(doanhthuDTO);
					break;
				}
			}
		}
	}
	
	private void formatBaocaoDoanhthuAdmin(List<BcAgencyDTO> result, List<BcDoanhThuAdminDTO> data) {
		log.debug("REST request to formatBaocaoDoanhthu, BcAgencyDTO{}, BcDoanhThuDTO{} :", result, data);
		for (BcAgencyDTO item : result) {
			for (BcDoanhThuAdminDTO doanhthuDTO : data) {
				String dateCompare = DateUtils.date2Str(DateUtils.str2Date(item.getDatePayment()), "yyyyMMdd");
				if (StringUtils.equals(dateCompare, doanhthuDTO.getDatePayment())) {
					item.setTotalPremium(doanhthuDTO.getTotalPremium());
					data.remove(doanhthuDTO);
					break;
				}
			}
		}
	}
	
	private List<BcAgencyDTO> buildEmptyListForYear() {
		log.debug("REST request to buildEmptyListForYear, {}");
		List<BcAgencyDTO> data = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			BcAgencyDTO item = new BcAgencyDTO();
			data.add(item);
		}
		
		return data;
	}
	
	private List<BcAgencyDTO> genrateDataBetweenDate(Date fromDate, Date toDate) {
		log.debug("REST request to genrateDataBetweenDate, fromDate{}, toDate{} :", fromDate, toDate);
		List<BcAgencyDTO> data = new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
	    calendar.setTime(fromDate);
	     
	    Calendar endCalendar = new GregorianCalendar();
	    endCalendar.setTime(toDate);
	 
	    while (calendar.before(endCalendar) || calendar.equals(endCalendar)) {
	    	BcAgencyDTO item = new BcAgencyDTO();
	    	item.setDatePayment(DateUtils.date2Str(calendar.getTime()));
	        data.add(item);
	        
	        // +1 day
	        calendar.add(Calendar.DATE, 1);
	    }
        return data;
	}

	@Override
	public List<EclaimDTO> getBaoCaoHistoryLoss(String contactId, String agentId) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to getBaoCaoHistoryLoss, contactId{}, agentId{} :", contactId, agentId);
		// Get email or phone
//		Contact co = contactRepository.findByContactIdAndType(contactId, agentId);
//		if (co == null) {
//			throw new AgencyBusinessException("contactId", ErrorCode.INVALID, "Id khách hàng không được để trống");
//		}
		
		// disable SSL
		disableSSL();
		
		// Call service to get data (RestTemplate)
		// Set proxy
		if (proxyEnable) {
			System.getProperties().put("http.proxyHost", proxyAddress);
			System.getProperties().put("http.proxyPort", proxyPort);
			System.getProperties().put("http.proxyUser", proxyUsername);
			System.getProperties().put("http.proxyPassword", proxyPassword);
		}
		RestTemplate restTemplate = new RestTemplate();
		final String response = restTemplate.getForObject(eclaimWsUrl + "?email=thaihuychuong1@baoviet.com.vn", String.class);
		System.out.println(response);
		
		// Convert to object
		EclaimResponseDTO result = null;
		try {
			result = objectMapper.readValue(response,
					new TypeReference<EclaimResponseDTO>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AgencyBusinessException("contactId", ErrorCode.INVALID, "Lỗi khi tải dữ liệu từ hệ thống Eclaim");
		}
		
		// Convert/ extract data -> List object LossHistoryDTO
		
		return formatEclaimData(agentId, result.getData());
	}
	
	private List<EclaimDTO> formatEclaimData(String agentId, List<EclaimDTO> data) {
		List<EclaimDTO> result = new ArrayList<>();
		if (data == null || data.size() == 0) {
			return result;
		}
		
		
		List<String> lstPolicyNumber = getLstPolicyNumber(data);
		for (String policyNumber : lstPolicyNumber) {
			EclaimDTO eclaimDTO = sumEclaimDataByPolicyNumber(data, policyNumber);
			if (eclaimDTO != null) {
				// Get agreement
				AgreementDTO agreementDTO = agreementService.findByGycbhNumberAndAgentId(policyNumber, agentId);
				eclaimDTO.setAgreementDTO(agreementDTO);
				
				result.add(eclaimDTO);
			}
		}
		
		
		return result;
	}
	
	private EclaimDTO sumEclaimDataByPolicyNumber(List<EclaimDTO> data, String policyNumber) {
		EclaimDTO result = null;
		for (EclaimDTO item : data) {
			if (StringUtils.equals(item.getPolicy_number(), policyNumber)) {
				// Chua co
				if (result == null) {
					result = item;
					result.setNumberClaim(1);
				} else {
					// Da ton tai, tinh tong
					result.setClaim_amount(result.getClaim_amount() + item.getClaim_amount());
					result.setNumberClaim(result.getNumberClaim() + 1);
				}
			}
		}
		
		return result;
	}
	
	private List<String> getLstPolicyNumber(List<EclaimDTO> data) {
		List<String> lstPolicyNumber = new ArrayList<>();
		
		for (EclaimDTO item : data) {
			if (!lstPolicyNumber.contains(item.getPolicy_number())) {
				lstPolicyNumber.add(item.getPolicy_number());
			}
		}
		
		return lstPolicyNumber;
	}

	@SuppressWarnings("unused")
	private void disableSSL() {
		try {
	        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
				}
				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
				}
	        }};

	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
