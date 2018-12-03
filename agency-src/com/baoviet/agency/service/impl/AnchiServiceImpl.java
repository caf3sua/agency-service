package com.baoviet.agency.service.impl;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import javax.transaction.Transactional;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.tempuri.BVACAGENCY;
import org.tempuri.BVACAGENCYSoap;

import com.baoviet.agency.domain.Anchi;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.AnchiDTO;
import com.baoviet.agency.dto.printedpaper.PrintedPaperDTO;
import com.baoviet.agency.dto.printedpaper.PrintedPaperTypeDTO;
import com.baoviet.agency.dto.printedpaper.PrintedPaperUpdateDTO;
import com.baoviet.agency.dto.printedpaper.UpdateAnChiResultDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AnchiRepository;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.AnchiService;
import com.baoviet.agency.service.mapper.AnchiMapper;
import com.baoviet.agency.utils.logging.SOAPLoggingHandler;
import com.baoviet.agency.web.rest.vm.AgreementAnchiVM;
import com.baoviet.agency.web.rest.vm.SearchPrintedPaperVM;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sun.misc.BASE64Decoder;

/**
 * Service Implementation for managing Anchi.
 * 
 * @author CuongTT
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class AnchiServiceImpl implements AnchiService {

	private final Logger log = LoggerFactory.getLogger(AnchiServiceImpl.class);

	@Value("${spring.application.ws.printed-paper.baoviet.url}")
	private String wsUrl;

	@Value("${spring.application.ws.printed-paper.baoviet.username}")
	private String wsUsername;

	@Value("${spring.application.ws.printed-paper.baoviet.password}")
	private String wsPassword;

	@Autowired
	private AgreementService agreementService;

	@Autowired
	private AnchiRepository anchiRepository;
	
	@Autowired
	private AnchiMapper anchiMapper;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<AnchiDTO> search(SearchPrintedPaperVM param, String type) {
		List<Anchi> data = anchiRepository.search(param, type);
		List<AnchiDTO> result = anchiMapper.toDto(data);
		
		// Get status
		if (result != null && result.size() > 0) {
			for (AnchiDTO anchi : result) {
				AgreementDTO agreementDTO = agreementService.findByGycbhNumberAndAgentId(anchi.getPolicyNumber(), type);
				if (agreementDTO != null) {
					anchi.setPolicyStatusId(agreementDTO.getStatusPolicyId());
				}
			}
		}
		
		return result;
	}

	@Override
	public List<PrintedPaperTypeDTO> getLoaiAnchi(String agencyType)
			throws JsonParseException, JsonMappingException, IOException {
		// D000013844
		log.debug("Request to getLoaiAnchi, agencyType {}", agencyType);
		String listAnchiType = getService().getDMLoaiAnChi(agencyType);

		// Convert to object
		List<PrintedPaperTypeDTO> result = objectMapper.readValue(listAnchiType,
				new TypeReference<List<PrintedPaperTypeDTO>>() {
				});

		List<PrintedPaperTypeDTO> data = new ArrayList<>();
		if (result != null && result.size() > 0) {
			for (PrintedPaperTypeDTO printedPaperTypeDTO : result) {
				if (StringUtils.isNotEmpty(printedPaperTypeDTO.getProductCode()) 
						&& StringUtils.isNotEmpty(printedPaperTypeDTO.getProductName())) {
					data.add(printedPaperTypeDTO);
				}
			}
		}
		
		return data;
	}

	@Override
	public List<PrintedPaperDTO> searchNew(SearchPrintedPaperVM param, String agencyType)
			throws JsonParseException, JsonMappingException, IOException {
		log.debug("Request to searchNew PrintedPaperDTO, SearchPrintedPaperVM {}, agencyType {}", param, agencyType);
		
		disableSSL();
		
		String lstPrintedPaper = getService().getAnChiByAgency(agencyType);

		// Convert to object
		List<PrintedPaperDTO> result = objectMapper.readValue(lstPrintedPaper,
				new TypeReference<List<PrintedPaperDTO>>() {
				});

		// search
		return filterPrintedPaper(result, param, agencyType);
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
	
	@Override
	public String wsUpdateAnChi(AgreementAnchiVM obj) throws AgencyBusinessException {
		log.debug("Request to wsUpdateAnChi, AgreementAnchiVM {} :", obj);
		// Build/convert to PrintedPaperUpdateDTO
		List<PrintedPaperUpdateDTO> data = new ArrayList<>();
		PrintedPaperUpdateDTO updateDTO = new PrintedPaperUpdateDTO();
		// set/get data
		updateDTO.setAchiSoAnchi(obj.getSoAnchi());
		updateDTO.setAchiStienvn(obj.getTongTienTT());
		updateDTO.setAchiTungay(obj.getNgayHieulucTu());
		updateDTO.setAchiDenngay(obj.getNgayHieulucDen());
		updateDTO.setAchiBmbh(obj.getContactCode());

		// data to list
		data.add(updateDTO);

		// Convert to json list object
		String anChis = null;
		try {
			anChis = objectMapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			throw new AgencyBusinessException(ErrorCode.INVALID, "Dữ liệu không hợp lệ");
		}

		// Call service
		String result = "0";
		try {
			result = getService().updateListAnChi(anChis);

			// Parse to UpdateAnChiResultDTO
			// Convert to object
			List<UpdateAnChiResultDTO> lstUpdateAnchiDTO = objectMapper.readValue(result,
					new TypeReference<List<UpdateAnChiResultDTO>>() {
					});

			if (lstUpdateAnchiDTO != null && lstUpdateAnchiDTO.size() > 0) {
				UpdateAnChiResultDTO item = lstUpdateAnchiDTO.get(0);
				if (StringUtils.equals("0", item.getStatus())) {
					log.debug("Update ấn chỉ thành công");
				} else if (StringUtils.contains("-1", item.getStatus())) {
					log.error("Không tìm thấy ấn chỉ trong hệ thống");
					throw new AgencyBusinessException(ErrorCode.PRINTED_PAPER_NOT_FOUND,
							"Không tìm thấy ấn chỉ trong hệ thống");
				} else if (StringUtils.contains("1", item.getStatus())) {
					log.error("Trạng thái cuối cùng của ấn chỉ đã được thay đổi");
					throw new AgencyBusinessException(ErrorCode.CREATE_YCBH_ANCHI_ERROR,
							"Trạng thái cuối cùng của ấn chỉ đã được thay đổi");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new AgencyBusinessException(ErrorCode.CREATE_YCBH_ANCHI_UNKNOW_ERROR, "Không thể cập nhật ấn chỉ");
		}

		return result;
	}

	@Override
	public String save(AnchiDTO param) {
		log.debug("Request to save AnchiDTO, AnchiDTO {} :", param);
		try {
			Anchi entity = anchiMapper.toEntity(param);

			// Convert to byte[]
			BASE64Decoder decoder = new BASE64Decoder();
			if (StringUtils.isNotEmpty(param.getImgGcnContent())) {
				byte[] imgGcn = decoder.decodeBuffer(param.getImgGcnContent());
				entity.setImgGcn(imgGcn);
			}
			if (StringUtils.isNotEmpty(param.getImgGycbhContent())) {
				byte[] imgGycbh = decoder.decodeBuffer(param.getImgGycbhContent());
				entity.setImgGycbh(imgGycbh);
			}

			if (StringUtils.isNotEmpty(param.getImgHdContent())) {
				byte[] imgHd = decoder.decodeBuffer(param.getImgHdContent());
				entity.setImgHd(imgHd);
			}

			// upload date
			if (param.getModifyDate() == null) {
				entity.setModifyDate(new Date());
			}

			entity = anchiRepository.save(entity);
			return entity.getAnchiId();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/***************************************
	 * Private method
	 **************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private BVACAGENCYSoap getService() {
		BVACAGENCY anchiSoap = new BVACAGENCY();
		BVACAGENCYSoap anchiService = anchiSoap.getBVACAGENCYSoap();

		// Set url ws
		((BindingProvider) anchiService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsUrl);
		log.debug("An chi ws url: " + wsUrl);

		Binding binding = ((BindingProvider) anchiService).getBinding();
		List handlerChain = binding.getHandlerChain();
		handlerChain.add(new SOAPLoggingHandler(wsUsername, wsPassword));
		binding.setHandlerChain(handlerChain);

		return anchiService;
	}

	private List<PrintedPaperDTO> filterPrintedPaper(List<PrintedPaperDTO> data, SearchPrintedPaperVM param, String agencyType) {
		log.debug("Request to filterPrintedPaper, List<PrintedPaperDTO> {}, SearchPrintedPaperVM {} :", data, param);
		List<PrintedPaperDTO> result = checkAnchiDaSuDung(data, agencyType);
		if (StringUtils.isNotEmpty(param.getType())) {
			result = filterTypePrintedPaper(result, param.getType());
		}

		if (StringUtils.isNotEmpty(param.getUrn())) {
			result = filterUrnPrintedPaper(result, param.getUrn());
		}

		if (StringUtils.isNotEmpty(param.getNumber())) {
			result = filterNumberPrintedPaper(result, param.getNumber());
		}

		return result;
	}

	private List<PrintedPaperDTO> checkAnchiDaSuDung(List<PrintedPaperDTO> data, String agencyType) {
		log.debug("Request to checkAnchiDaSuDung, List<PrintedPaperDTO> {}, :", data);
		List<PrintedPaperDTO> result = new ArrayList<>();
		// get ấn chỉ đã sử dụng trong bảng ấn chỉ
		List<Anchi> lstanchi = anchiRepository.findByStatusAndAchiDonvi("90", agencyType);

		for (PrintedPaperDTO printedPaperDTO : data) {
			if (lstanchi.size() > 0) {
				for (Anchi anchi : lstanchi) {
					if (!StringUtils.isEmpty(printedPaperDTO.getAchiSoAnchi())
							&& !StringUtils.isEmpty(anchi.getAchiSoAnchi())
							&& StringUtils.equals(printedPaperDTO.getAchiSoAnchi(), anchi.getAchiSoAnchi())) {
						printedPaperDTO.setAnchiUsing(true);
						if (!result.contains(printedPaperDTO)) {
							result.add(printedPaperDTO);	
						}
					} else {
						if (!result.contains(printedPaperDTO)) {
							result.add(printedPaperDTO);	
						}
					}
				}	
			} else {
				result.add(printedPaperDTO);
			}
			
		}

		return result;
	}

	private List<PrintedPaperDTO> filterTypePrintedPaper(List<PrintedPaperDTO> data, String type) {
		log.debug("Request to filterTypePrintedPaper, List<PrintedPaperDTO> {}, type {} :", data, type);
		List<PrintedPaperDTO> result = new ArrayList<>();

		// ACHI_LOAI_ANCHI_ID
		for (PrintedPaperDTO printedPaperDTO : data) {
			if (printedPaperDTO.getProductCode() != null && printedPaperDTO.getProductCode().equals(type)) {
				result.add(printedPaperDTO);
			}
		}

		return result;
	}

	private List<PrintedPaperDTO> filterNumberPrintedPaper(List<PrintedPaperDTO> data, String number) {
		log.debug("Request to filterNumberPrintedPaper, List<PrintedPaperDTO> {}, number {} :", data, number);
		List<PrintedPaperDTO> result = new ArrayList<>();

		// ACHI_SO_ANCHI
		for (PrintedPaperDTO printedPaperDTO : data) {
			if (printedPaperDTO.getAchiSoAnchi().indexOf(number) >= 0) {
				result.add(printedPaperDTO);
			}
		}

		return result;
	}

	private List<PrintedPaperDTO> filterUrnPrintedPaper(List<PrintedPaperDTO> data, String urn) {
		log.debug("Request to filterUrnPrintedPaper, List<PrintedPaperDTO> {}, urn {} :", data, urn);
		List<PrintedPaperDTO> result = new ArrayList<>();

		// ACHI_MAVACH
		for (PrintedPaperDTO printedPaperDTO : data) {
			if (printedPaperDTO.getAchiMavach().equals(urn)) {
				result.add(printedPaperDTO);
			}
		}

		return result;
	}

	@Override
	public Anchi getBySoAnchi(String soAnchi, String type) {
		log.debug("Request to getBySoAnchi, soAnchi {}, type {} :", soAnchi, type);
		Anchi data = anchiRepository.findByAchiSoAnchiAndAchiDonvi(soAnchi, type);
		return data;
	}

}
