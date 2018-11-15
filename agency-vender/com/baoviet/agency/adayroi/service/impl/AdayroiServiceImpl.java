package com.baoviet.agency.adayroi.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.baoviet.agency.adayroi.dto.ConfirmOrder;
import com.baoviet.agency.adayroi.dto.CustomerInvoiceDetail;
import com.baoviet.agency.adayroi.dto.DeliveryAddress;
import com.baoviet.agency.adayroi.dto.McVariantInfoDTO;
import com.baoviet.agency.adayroi.dto.ObjMemoResponses;
import com.baoviet.agency.adayroi.dto.ObjectJsonBVP;
import com.baoviet.agency.adayroi.dto.Order;
import com.baoviet.agency.adayroi.dto.Product;
import com.baoviet.agency.adayroi.dto.ProductConfirmDTO;
import com.baoviet.agency.adayroi.dto.PurchaseOrder;
import com.baoviet.agency.adayroi.dto.QtyWarehouseConfirmDTO;
import com.baoviet.agency.adayroi.dto.ResponseBvpDTO;
import com.baoviet.agency.adayroi.dto.ResponseConfirmDTO;
import com.baoviet.agency.adayroi.dto.ResponseDTO;
import com.baoviet.agency.adayroi.service.AdayroiService;
import com.baoviet.agency.bean.InvoiceInfoDTO;
import com.baoviet.agency.bean.ReceiverUserInfoDTO;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.CodeManagementDTO;
import com.baoviet.agency.dto.ContactDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.service.CodeManagementService;
import com.baoviet.agency.service.ContactService;
import com.baoviet.agency.service.ProductBVPService;
import com.baoviet.agency.service.impl.AgencyServiceImpl;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.ContactCreateVM;
import com.baoviet.agency.web.rest.vm.ProductBvpVM;
import com.baoviet.agency.web.rest.vm.SKTDAddVM;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service Implementation for managing.
 * 
 * @author Duc Le Minh
 */
@Service
@Transactional
public class AdayroiServiceImpl implements AdayroiService {

	private final Logger log = LoggerFactory.getLogger(AgencyServiceImpl.class);

	 @Value("${spring.application.vendor.adayroi.url}")
	 private String url;
	
	 @Value("${spring.application.vendor.adayroi.grant_type}")
	 private String grant_type;
	
	 @Value("${spring.application.vendor.adayroi.client_id}")
	 private String client_id;
	
	 @Value("${spring.application.vendor.adayroi.client_secret}")
	 private String client_secret;
	
	 @Value("${spring.application.vendor.adayroi.username}")
	 private String username;
	
	 @Value("${spring.application.vendor.adayroi.password}")
	 private String password;

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

	private static final String Question_1 = "Người được bảo hiểm kê khai trên đã từng phải điều trị, nằm viện hay phẫu thuật trong một bệnh viện, viện điều dưỡng, phòng khám hoặc các tổ chức y tế khác trong vòng 3 năm gần đây không?";
	private static final String Question_2 = "Người được bảo hiểm có đang được theo dõi hoặc điều trị thương tật, bệnh hoặc có triệu chứng sức khỏe không ổn định hoặc cần phải điều trị bệnh viện trong vòng 12 tháng tới không?";
	private static final String Question_3 = "Trong 3 năm qua, Người được bảo hiểm có mắc hay điều trị một hay nhiều triệu chứng các bệnh sau: ung thư, u các loại, huyết áp, tim mạch, loét dạ dày, viêm đa khớp mãn tính, loét ruột, viêm gan, viêm màng trong dạ con, trĩ, sỏi trong các hệ thống tiết niệu và đường mật, lao, đục nhân mắt, viêm xoang, tiểu đường, thoát vị đĩa đệm hay các bệnh khác không?";

	@Autowired
	private ProductBVPService productBVPService;

	@Autowired
	private CodeManagementService codeManagementService;

	@Autowired
	private ContactService contactService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String getAccessToken() {
		log.debug("START method getAccessToken");

		// Set proxy
		if (proxyEnable) {
			System.getProperties().put("http.proxyHost", proxyAddress);
			System.getProperties().put("http.proxyPort", proxyPort);
			System.getProperties().put("http.proxyUser", proxyUsername);
			System.getProperties().put("http.proxyPassword", proxyPassword);
		}

		RestTemplate restTemplate = new RestTemplate();
		String param = String.format("grant_type=%s&username=%s&password=%s&client_id=%s&client_secret=%s", grant_type, username, password, client_id, client_secret);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(param, headers);

		String resource = url + "/token";
		ResponseEntity<ResponseDTO> response = restTemplate.exchange(resource, HttpMethod.POST, request,
				ResponseDTO.class);
		return response.getBody().getAccess_token();
	}

	@Override
	public void confirmPurchaseOrder(String accessToken, ConfirmOrder order) throws JsonProcessingException {
		// get access token
		for (ProductConfirmDTO item : order.getProductConfirms()) {
			confirmPurchaseOrder(accessToken, item);
		}
	}

	@Override
	public List<Order> getBvpProduct(String accessToken)
			throws URISyntaxException, AgencyBusinessException, JsonParseException, JsonMappingException, IOException {
		List<Order> resultData = new ArrayList<>();
		// get access token
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + accessToken);
		HttpEntity<ResponseBvpDTO> request = new HttpEntity<ResponseBvpDTO>(headers);

		String resource = url + "/merchant/orders";

		ResponseEntity<ResponseBvpDTO> response = restTemplate.exchange(resource, HttpMethod.GET, request, ResponseBvpDTO.class);

		List<Order> data = response.getBody().getData().getSaleOrders().getSaleOrder();
		if (data != null && data.size() > 0) {
			List<ProductConfirmDTO> lstProductConfirm = new ArrayList<>();
			for (Order item : data) {
				// Convert to object
				if (StringUtils.equals(item.getOrderStatus(), "WAITING_FOR_MC_TO_CONFIRM")) {
					// add item result
					resultData.add(item);
				}
			}
		}
		
		log.debug("getBvpProduct  data {} : " + resultData.size());
		return resultData;
	}

	private boolean validateData (ProductBvpVM bvp) {
		// check các case nếu tuổi trên 18 và các câu trả lời là 0 thì thêm mới, thành công luôn
		int tuoiNguoidbh = 0;
		if (bvp.getNguoidbhNgaysinh() != null) {
			tuoiNguoidbh = DateUtils.countYears(bvp.getNguoidbhNgaysinh(), new Date());
		}
		if (tuoiNguoidbh < 18 ) {
			return false;
		}
		if (StringUtils.equals(bvp.getQ1(), "1")) {
			return false;
		}
		if (StringUtils.equals(bvp.getQ2(), "1")) {
			return false;
		}
		if (StringUtils.equals(bvp.getQ3(), "1")) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public List<Order> createBvpProduct(List<Order> data, AgencyDTO currentAgency, String accessToken)
			throws URISyntaxException, AgencyBusinessException, JsonParseException, JsonMappingException, IOException {
		List<Order> resultData = new ArrayList<>();
		
		List<ProductConfirmDTO> lstProductConfirm = new ArrayList<>();
		for (Order item : data) {
			// Convert to object
			ObjectJsonBVP result = objectMapper.readValue(item.getJsonToMC(), new TypeReference<ObjectJsonBVP>() {});

			ProductBvpVM bvp = convertBvpToVM(result, item);

			// Đơn sạch : 0
			if (validateData(bvp)) {
				// tạo đơn hàng
				productBVPService.createOrUpdatePolicyAdayroi(bvp, currentAgency, "0", item.getOrderCode());

				// Confirm xác nhận với Adayroi
				List<PurchaseOrder> purchaseOrder = item.getPurchaseOrders().getPurchaseOrder();
				if (purchaseOrder != null && purchaseOrder.size() > 0) {
					for (PurchaseOrder itemPurchase : purchaseOrder) {
						List<Product> lstProduct = itemPurchase.getProducts().getProduct();
						if (lstProduct != null && lstProduct.size() > 0) {
							for (Product product : lstProduct) {
								List<QtyWarehouseConfirmDTO> lstWarehouse = new ArrayList<>();
								ProductConfirmDTO productConfirm = new ProductConfirmDTO();
								if (itemPurchase.getPoNumber() != null) {
									productConfirm.setPoCode(itemPurchase.getPoNumber());	
								}
								if (product.getOfferId() != null) {
									productConfirm.setProductCode(product.getOfferId());	
								}
								if (itemPurchase.getWarehouseId() != null) {
									productConfirm.setOriginalWarehouseID(itemPurchase.getWarehouseId());	
								}
								productConfirm.setOriginalQuantity("1");
								
								QtyWarehouseConfirmDTO qtyWarehouseConfirms = new QtyWarehouseConfirmDTO();
							
								if (product.getQuantityConfirm() != null) {
									qtyWarehouseConfirms.setQuantityConfirm(product.getQuantityConfirm().toString());
								}
								if (productConfirm.getOriginalWarehouseID() != null) {
									qtyWarehouseConfirms.setWarehouseIdConfirm(productConfirm.getOriginalWarehouseID());
								}
								lstWarehouse.add(qtyWarehouseConfirms);
								productConfirm.setQtyWarehouseConfirms(lstWarehouse);
								
								lstProductConfirm.add(productConfirm);
							}
						}
					}
				}
			} else {
				// cần giám định: loại đơn: 1
				// tạo đơn hàng
				productBVPService.createOrUpdatePolicyAdayroi(bvp, currentAgency, "1", item.getOrderCode());
			}
			
			// add item result
			resultData.add(item);
		}

		// gọi API confirm
		if (lstProductConfirm != null && lstProductConfirm.size() > 0) {
			ConfirmOrder confirmOrder = new ConfirmOrder();
			confirmOrder.setProductConfirms(lstProductConfirm);
			confirmPurchaseOrder(accessToken, confirmOrder);
		}

		log.debug("getBvpProduct  data {} : " + resultData.size());
		return resultData;
	}
	
	
	private ResponseConfirmDTO confirmPurchaseOrder(String accessToken, ProductConfirmDTO itemOrder) {
		// get access token
		try {
			RestTemplate restTemplate = new RestTemplate();
			// Object to JSON in String
			String jsonInString = "";

			List<ProductConfirmDTO> lstItem = new ArrayList<>();
			lstItem.add(itemOrder);
			ConfirmOrder order = new ConfirmOrder();
			order.setProductConfirms(lstItem);

			jsonInString = objectMapper.writeValueAsString(order);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "Bearer " + accessToken);

			HttpEntity<String> request = new HttpEntity<>(jsonInString, headers);

			String resource = url + "/merchant/orders/confirmPurchaseOrder";

			ResponseEntity<ResponseConfirmDTO> response = null;
			response = restTemplate.exchange(resource, HttpMethod.PUT, request, ResponseConfirmDTO.class);
			log.debug("confirmPurchaseOrder  response {} : " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	// thieu nguoithNgaysinh - để mặc định 01/01/0001
	// thiếu thông tin số tài khoản khi có GTGT
	// nguoi nhan
	private ProductBvpVM convertBvpToVM(ObjectJsonBVP obj, Order order)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("Request to convertBvpToVM, ObjectJsonBVP{} : ", obj);
		ProductBvpVM bvp = new ProductBvpVM();

		// thông tin nhận đơn bảo hiểm
		if (order.getDeliveryAddress() != null) {
			DeliveryAddress deliveryAddress = order.getDeliveryAddress();
			ReceiverUserInfoDTO receiverUser = new ReceiverUserInfoDTO();
			if (!StringUtils.isEmpty(deliveryAddress.getCustomerName())) {
				receiverUser.setName(deliveryAddress.getCustomerName());
			}
			if (!StringUtils.isEmpty(deliveryAddress.getCustomerEmail())) {
				receiverUser.setEmail(deliveryAddress.getCustomerEmail());
			}
			if (!StringUtils.isEmpty(deliveryAddress.getCustomerPhone())) {
				receiverUser.setMobile(deliveryAddress.getCustomerPhone());
			}
			String address = "";
			if (!StringUtils.isEmpty(deliveryAddress.getAddress().getWardName())) {
				address += deliveryAddress.getAddress().getWardName();
			}
			if (!StringUtils.isEmpty(deliveryAddress.getAddress().getDistrictName())) {
				address += ", " + deliveryAddress.getAddress().getDistrictName();
			}
			if (!StringUtils.isEmpty(deliveryAddress.getAddress().getProvinceName())) {
				address += ", " + deliveryAddress.getAddress().getProvinceName();
			}
			receiverUser.setAddress(address);
			bvp.setReceiverUser(receiverUser);

			// thông tin hóa đơn GTGT
			if (deliveryAddress != null && order.getCustomerInvoiceDetail() != null) {
				CustomerInvoiceDetail invoiceDetail = order.getCustomerInvoiceDetail();
				InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
				if (!StringUtils.isEmpty(deliveryAddress.getCustomerName())) {
					invoiceInfo.setName(deliveryAddress.getCustomerName());
				}
				if (!StringUtils.isEmpty(invoiceDetail.getCompanyName())) {
					invoiceInfo.setCompany(invoiceDetail.getCompanyName());
				}
				if (!StringUtils.isEmpty(invoiceDetail.getTaxCode())) {
					invoiceInfo.setTaxNo(invoiceDetail.getTaxCode());
					invoiceInfo.setCheck("1");
				} else {
					invoiceInfo.setCheck("0");
				}
				if (!StringUtils.isEmpty(invoiceDetail.getCompanyAddress())) {
					invoiceInfo.setAddress(invoiceDetail.getCompanyAddress());
				}

				bvp.setInvoiceInfo(invoiceInfo);
			}
		}
		// từ ngày, đến ngày
		bvp.setInceptionDate(DateUtils.str2Date(obj.getPolicy().getSectionDetail().getStartDate().getValue()));
		bvp.setExpiredDate(DateUtils.str2Date(obj.getPolicy().getSectionDetail().getEndDate().getValue()));

		CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("BVP");
		String gycbhNumber = codeManagementDTO.getIssueNumber();
		bvp.setGycbhNumber(gycbhNumber);
		bvp.setPolicyNumber(gycbhNumber);

		// check tạo contact
		if (!StringUtils.isEmpty(obj.getPolicyHolder().getSectionDetail().getPhCellPhone().getValue())
				|| !StringUtils.isEmpty(obj.getPolicyHolder().getSectionDetail().getPhHomePhone().getValue())) {
			ContactDTO contactDTO = contactService.findOneByPhoneAndType(
					obj.getPolicyHolder().getSectionDetail().getPhCellPhone().getValue(), "ADAYROI");
			if (contactDTO == null) {
				ContactDTO contactHomePhone = contactService.findOneByPhoneAndType(
						obj.getPolicyHolder().getSectionDetail().getPhHomePhone().getValue(), "ADAYROI");
				if (contactHomePhone != null) {
					bvp.setContactCode(contactHomePhone.getContactCode());
				} else {
					ContactDTO objContact = new ContactDTO();
					ContactCreateVM contactCreateVM = new ContactCreateVM();
					if (!StringUtils.isEmpty(obj.getPolicyHolder().getSectionDetail().getPhName().getValue())) {
						objContact.setContactName(obj.getPolicyHolder().getSectionDetail().getPhName().getValue());
						contactCreateVM.setContactName(obj.getPolicyHolder().getSectionDetail().getPhName().getValue());
					}
					if (!StringUtils.isEmpty(obj.getPolicyHolder().getSectionDetail().getPhEmail().getValue())) {
						objContact.setEmail(obj.getPolicyHolder().getSectionDetail().getPhEmail().getValue());
						contactCreateVM.setEmail(obj.getPolicyHolder().getSectionDetail().getPhEmail().getValue());
					}
					if (!StringUtils
							.isEmpty(obj.getPolicyHolder().getSectionDetail().getPhMailingAddress().getValue())) {
						objContact.setHomeAddress(
								obj.getPolicyHolder().getSectionDetail().getPhMailingAddress().getValue());
						contactCreateVM.setHomeAddress(
								obj.getPolicyHolder().getSectionDetail().getPhMailingAddress().getValue());
					}
					if (!StringUtils.isEmpty(obj.getPolicyHolder().getSectionDetail().getPhCellPhone().getValue())) {
						objContact.setPhone(obj.getPolicyHolder().getSectionDetail().getPhCellPhone().getValue());
						contactCreateVM.setPhone(obj.getPolicyHolder().getSectionDetail().getPhCellPhone().getValue());
					} else {
						objContact.setPhone(obj.getPolicyHolder().getSectionDetail().getPhHomePhone().getValue());
						contactCreateVM.setPhone(obj.getPolicyHolder().getSectionDetail().getPhHomePhone().getValue());
					}
					String contactCode = contactService.generateContactCode("ADAYROI");
					objContact.setContactCode(contactCode);
					objContact.setType("ADAYROI");
					ContactDTO data = contactService.create(objContact, contactCreateVM);
					if (data != null) {
						bvp.setContactCode(data.getContactCode());
					}
				}
			} else {
				bvp.setContactCode(contactDTO.getContactCode());
			}
		}

		// phí bảo hiểm
		bvp.setChuongtrinhPhi(order.getOrderPrice());
		bvp.setTanggiamPhi(0d);
		bvp.setTanggiamPhiNoidung("");
		bvp.setDiscount(0d);
		
		// set giá trị mặc định
		bvp.setHasExtracare(false); // quyền lợi bổ sung
		bvp.setNgoaitru("0");
		bvp.setNgoaitruPhi(0d);
		bvp.setTncn("0");
		bvp.setTncnPhi(0d);
		bvp.setTncnSotienbh(0d);
		bvp.setSinhmang("0");
		bvp.setSinhmangPhi(0d);
		bvp.setSinhmangSotienbh(0d);
		bvp.setNhakhoa("0");
		bvp.setNhakhoaPhi(0d);
		bvp.setThaisan("0");
		bvp.setThaisanPhi(0d);
		
		// Phí/ Số tiền quyền lợi bổ sung
		List<PurchaseOrder> lstPurchaseOrder = order.getPurchaseOrders().getPurchaseOrder();
		if (lstPurchaseOrder != null && lstPurchaseOrder.size() > 0) {
			for (PurchaseOrder purchaseOrder : lstPurchaseOrder) {
				// ds các sản phẩm
				List<Product> lstProduct = purchaseOrder.getProducts().getProduct();
				if (lstProduct != null && lstProduct.size() > 0) {
					for (Product product : lstProduct) {
						// kiểm tra từng sp xem ứng với quyền lợi gì
						if (!StringUtils.isEmpty(product.getOfferId())) {
							String offerId = product.getOfferId();
							String type = offerId.substring(6, 10);
							if (type.equals("OUTP")) {
								bvp.setHasExtracare(true); // quyền lợi bổ sung
								// Ngoai tru
								bvp.setNgoaitru("1");
								bvp.setNgoaitruPhi(product.getTotalPrice());
							}
							if (type.equals("E201")) {
								bvp.setHasExtracare(true);
								// BH sinh mang ca nhan
								List<McVariantInfoDTO> lstVariantInfo = product.getVariantInfos().getVariantInfo();
								if (lstVariantInfo != null && lstVariantInfo.size() > 0) {
									for (McVariantInfoDTO mcVariantInfoDTO : lstVariantInfo) {
										String strVariantCategoryID = mcVariantInfoDTO.getVariantCategoryID();
										if (StringUtils.equals(strVariantCategoryID, "Insurance_Max_Liability")) {
											// lấy số tiền tham gia bảo hiểm
											String variantValueCategoryName = mcVariantInfoDTO.getVariantValueCategoryName();
											if (!StringUtils.isEmpty(variantValueCategoryName)) {
												String sotien = variantValueCategoryName.substring(0, variantValueCategoryName.length() - 1).replace(".", "");
												bvp.setSinhmangSotienbh(Double.parseDouble(sotien));
											}
											
										}
									}
								}
								bvp.setSinhmang("1");
								bvp.setSinhmangPhi(product.getTotalPrice());
							}
							if (type.equals("E101")) {
								bvp.setHasExtracare(true);
								// BH tai nan ca nhan
								List<McVariantInfoDTO> lstVariantInfo = product.getVariantInfos().getVariantInfo();
								if (lstVariantInfo != null && lstVariantInfo.size() > 0) {
									for (McVariantInfoDTO mcVariantInfoDTO : lstVariantInfo) {
										String strVariantCategoryID = mcVariantInfoDTO.getVariantCategoryID();
										if (StringUtils.equals(strVariantCategoryID, "Insurance_Max_Liability")) {
											// lấy số tiền tham gia bảo hiểm
											String variantValueCategoryName = mcVariantInfoDTO.getVariantValueCategoryName();
											if (!StringUtils.isEmpty(variantValueCategoryName)) {
												String sotien = variantValueCategoryName.substring(0, variantValueCategoryName.length() - 1).replace(".", "");
												bvp.setTncnSotienbh(Double.parseDouble(sotien));
											}
											
										}
									}
								}
								bvp.setTncn("1");
								bvp.setTncnPhi(product.getTotalPrice());
							}
							if (type.equals("DENT")) {
								bvp.setHasExtracare(true);
								// Nha khoa
								bvp.setNhakhoa("1");
								bvp.setNhakhoaPhi(product.getTotalPrice());
							}
							if (type.equals("MATE")) {
								bvp.setHasExtracare(true);
								// Thai san
								bvp.setThaisan("1");
								bvp.setThaisanPhi(product.getTotalPrice());
							}
							if (type.equals("MAIN")) {
								// Quyen loi chinh
								String offerName = product.getOfferName();
								for (String name : offerName.split("-")) {
									if (name.trim().equals("Bạch Kim")) {
										bvp.setChuongtrinhBh("5");
										break;
									}
									if (name.trim().equals("Kim Cương")) {
										bvp.setChuongtrinhBh("4");
										break;
									}
									if (name.trim().equals("Vàng")) {
										bvp.setChuongtrinhBh("3");
										break;
									}
									if (name.trim().equals("Bạc")) {
										bvp.setChuongtrinhBh("2");
										break;
									}
									if (name.trim().equals("Đồng")) {
										bvp.setChuongtrinhBh("1");
										break;
									}
						        }
								bvp.setChuongtrinhPhi(product.getTotalPrice());
							}
						}
					}
				}
			}
		}
		
		bvp.setHasNguoinhantien(false);
		bvp.setReceiveMethod("2"); // 2: bản cứng 1: bản mềm

		// nguoi yeu cau bao hiem
		if (!StringUtils.isEmpty(obj.getPolicyHolder().getSectionDetail().getPhName().getValue())) {
			bvp.setNguoiycName(obj.getPolicyHolder().getSectionDetail().getPhName().getValue());
		}
		if (!StringUtils.isEmpty(obj.getPolicyHolder().getSectionDetail().getPhDOB().getValue()) && DateUtils
				.isValidDate(obj.getPolicyHolder().getSectionDetail().getPhDOB().getValue(), "dd/MM/yyyy")) {
			bvp.setNguoiycNgaysinh(DateUtils.str2Date(obj.getPolicyHolder().getSectionDetail().getPhDOB().getValue()));
		}

		// nguoi duoc bao hiem
		if (!StringUtils.isEmpty(obj.getInsuredPerson().getSectionDetail().getIpName().getValue())) {
			bvp.setNguoidbhName(obj.getInsuredPerson().getSectionDetail().getIpName().getValue());
		}
		if (!StringUtils.isEmpty(obj.getInsuredPerson().getSectionDetail().getIpId().getValue())) {
			bvp.setNguoidbhCmnd(obj.getInsuredPerson().getSectionDetail().getIpId().getValue());
		}
		if (!StringUtils.isEmpty(obj.getInsuredPerson().getSectionDetail().getIpRelationWithPh().getValue())) {
			bvp.setNguoidbhQuanhe(obj.getInsuredPerson().getSectionDetail().getIpRelationWithPh().getValue());
		}
		if (!StringUtils.isEmpty(obj.getInsuredPerson().getSectionDetail().getIpDOB().getValue()) && DateUtils
				.isValidDate(obj.getInsuredPerson().getSectionDetail().getIpDOB().getValue(), "dd/MM/yyyy")) {
			bvp.setNguoidbhNgaysinh(
					DateUtils.str2Date(obj.getInsuredPerson().getSectionDetail().getIpDOB().getValue()));
		}

		// nguoi thu huong : Thiếu ngày sinh
		if (!StringUtils.isEmpty(obj.getBeneficiary().getSectionDetail().getBnName().getValue())) {
			bvp.setNguoithName(obj.getBeneficiary().getSectionDetail().getBnName().getValue());
		}
		if (!StringUtils.isEmpty(obj.getBeneficiary().getSectionDetail().getBnId().getValue())) {
			bvp.setNguoithCmnd(obj.getBeneficiary().getSectionDetail().getBnId().getValue());
			bvp.setHasNguoithuhuong(true);
		} else {
			bvp.setHasNguoithuhuong(false);
		}
		if (!StringUtils.isEmpty(obj.getBeneficiary().getSectionDetail().getBnRelationWithIp().getValue())) {
			bvp.setNguoithQuanhe(obj.getBeneficiary().getSectionDetail().getBnRelationWithIp().getValue());
		}
		bvp.setNguoithNgaysinh(DateUtils.str2Date("01/01/0001"));	// để tạm giá trị mặc định

		// câu hỏi
		String q1 = obj.getQuestions().getSectionDetail().get(0).getQuestionText();
		String resultQ1 = obj.getQuestions().getSectionDetail().get(0).getResponse();
		if (StringUtils.equals(q1, Question_1)) {
			if (StringUtils.equals(resultQ1, "Có")) {
				bvp.setQ1("1");
			} else {
				bvp.setQ1("0");
			}
		}

		String q2 = obj.getQuestions().getSectionDetail().get(2).getQuestionText();
		String resultQ2 = obj.getQuestions().getSectionDetail().get(2).getResponse();
		if (StringUtils.equals(q2, Question_2)) {
			if (StringUtils.equals(resultQ2, "Có")) {
				bvp.setQ2("1");
			} else {
				bvp.setQ2("0");
			}
		}

		String q3 = obj.getQuestions().getSectionDetail().get(3).getQuestionText();
		String resultQ3 = obj.getQuestions().getSectionDetail().get(3).getResponse();
		if (StringUtils.equals(q3, Question_3)) {
			if (StringUtils.equals(resultQ3, "Có")) {
				bvp.setQ3("1");
				List<ObjMemoResponses> lstMemoResponses = obj.getQuestions().getSectionDetail().get(3)
						.getMemoResponses();
				if (lstMemoResponses != null && lstMemoResponses.size() > 0) {
					List<SKTDAddVM> lstSKTD = new ArrayList<>();
					for (ObjMemoResponses item : lstMemoResponses) {
						SKTDAddVM addVM = new SKTDAddVM();
						addVM.setBenhvienorbacsy(item.getDoctor().getValue());
						addVM.setChuandoan(item.getDiagnosis().getValue());
						addVM.setChitietdieutri(item.getTreatment().getValue());
						addVM.setKetqua(item.getResult().getValue());
						if (DateUtils.isValidDate(item.getDateoftreatment().getValue(), "dd/MM/yyyy")) {
							addVM.setNgaydieutri(DateUtils.str2Date(item.getDateoftreatment().getValue()));
						} else {
							addVM.setNgaydieutri(DateUtils.str2Date("01/01/0001"));
						}
						lstSKTD.add(addVM);
					}
					bvp.setLstAdd(lstSKTD);
				}
			} else {
				bvp.setQ3("0");
			}
		}

		return bvp;
	}

}
