package com.baoviet.agency.service.impl;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.bean.FileContentDTO;
import com.baoviet.agency.bean.InvoiceInfoDTO;
import com.baoviet.agency.bean.ReceiverUserInfoDTO;
import com.baoviet.agency.bean.TncAddDTO;
import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.Attachment;
import com.baoviet.agency.domain.Car;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.TvcPlane;
import com.baoviet.agency.domain.TvcPlaneAdd;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.AgreementSearchDTO;
import com.baoviet.agency.dto.BvpDTO;
import com.baoviet.agency.dto.CodeManagementDTO;
import com.baoviet.agency.dto.GoodsDTO;
import com.baoviet.agency.dto.HomeDTO;
import com.baoviet.agency.dto.KcareDTO;
import com.baoviet.agency.dto.MotoDTO;
import com.baoviet.agency.dto.PaAddDTO;
import com.baoviet.agency.dto.PaDTO;
import com.baoviet.agency.dto.TinhtrangSkDTO;
import com.baoviet.agency.dto.TlAddDTO;
import com.baoviet.agency.dto.TlDTO;
import com.baoviet.agency.dto.TravelCareAddDTO;
import com.baoviet.agency.dto.TravelcareDTO;
import com.baoviet.agency.dto.TviCareAddDTO;
import com.baoviet.agency.dto.TvicareDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.repository.AttachmentRepository;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.TvcPlaneAddRepository;
import com.baoviet.agency.repository.TvcPlaneRepository;
import com.baoviet.agency.service.BVPService;
import com.baoviet.agency.service.CarService;
import com.baoviet.agency.service.CodeManagementService;
import com.baoviet.agency.service.GoodsService;
import com.baoviet.agency.service.HomeService;
import com.baoviet.agency.service.KcareService;
import com.baoviet.agency.service.MotoService;
import com.baoviet.agency.service.PaAddService;
import com.baoviet.agency.service.PaService;
import com.baoviet.agency.service.ProductBVPService;
import com.baoviet.agency.service.ProductCARService;
import com.baoviet.agency.service.ProductCommonService;
import com.baoviet.agency.service.ProductHomeService;
import com.baoviet.agency.service.ProductKcareService;
import com.baoviet.agency.service.ProductKhcService;
import com.baoviet.agency.service.ProductMotoService;
import com.baoviet.agency.service.ProductTncService;
import com.baoviet.agency.service.ProductTvcService;
import com.baoviet.agency.service.ProductTviService;
import com.baoviet.agency.service.TinhtrangSkService;
import com.baoviet.agency.service.TlAddService;
import com.baoviet.agency.service.TlService;
import com.baoviet.agency.service.TravelCareAddService;
import com.baoviet.agency.service.TravelcareService;
import com.baoviet.agency.service.TviCareAddService;
import com.baoviet.agency.service.TvicareService;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.AbstractAgencyResource;
import com.baoviet.agency.web.rest.vm.HastableTNC;
import com.baoviet.agency.web.rest.vm.KhcResultVM;
import com.baoviet.agency.web.rest.vm.ProductBaseVM;
import com.baoviet.agency.web.rest.vm.ProductBvpVM;
import com.baoviet.agency.web.rest.vm.ProductCarVM;
import com.baoviet.agency.web.rest.vm.ProductHhvcVM;
import com.baoviet.agency.web.rest.vm.ProductHomeVM;
import com.baoviet.agency.web.rest.vm.ProductKcareVM;
import com.baoviet.agency.web.rest.vm.ProductKhcVM;
import com.baoviet.agency.web.rest.vm.ProductMotoVM;
import com.baoviet.agency.web.rest.vm.ProductTncVM;
import com.baoviet.agency.web.rest.vm.ProductTvcVM;
import com.baoviet.agency.web.rest.vm.ProductTviVM;
import com.baoviet.agency.web.rest.vm.SKTDAddVM;
import com.baoviet.agency.web.rest.vm.TvcAddBaseVM;

import sun.misc.BASE64Encoder;

/**
 * Service Implementation for managing TVI.
 * @author CuongTT
 */
@Service
@CacheConfig(cacheNames = "product")
@Transactional
public class ProductCommonServiceImpl extends AbstractAgencyResource implements ProductCommonService {

	private final Logger log = LoggerFactory.getLogger(ProductCARServiceImpl.class);
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private TvicareService tvicareService;
	
	@Autowired
	private TviCareAddService tviCareAddService;
	
	@Autowired
	private MotoService motoService;
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private TravelcareService travelcareService;
	
	@Autowired
	private TravelCareAddService travelCareAddService;
	
	@Autowired
	private TlService tlService;
	
	@Autowired
	private TlAddService tlAddService;
	
	@Autowired
	private KcareService kcareService;
	
	@Autowired
	private TinhtrangSkService tinhtrangSkService;
	
	@Autowired
	private BVPService bVPService;
	
	@Autowired
	private PaService paService;
	
	@Autowired
	private PaAddService paAddService;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Autowired
	private CodeManagementService codeManagementService;
	
	@Autowired
    private ProductBVPService productBVPService;
	
	@Autowired
    private ProductCARService productCARService;
	
	@Autowired
	private ProductTviService productTviService;
	
	@Autowired
    private ProductTvcService productTvcService;
	
	@Autowired
    private ProductMotoService productMotoService;
	
	@Autowired
    private ProductHomeService productHomeService;
	
	@Autowired
    private ProductKhcService productKhcService;
	
	@Autowired
    private ProductKcareService productKcareService;
	
	@Autowired
    private ProductTncService productTncService;
	
	@Autowired
	private TvcPlaneAddRepository tvcPlaneAddRepository;
	
	@Autowired
	private TvcPlaneRepository tvcPlaneRepository;
	
	@Override
	public String getProductIdByGycbhId(String lineId, String gycbhId) {
		log.debug("Request to getProductIdByGycbhId, lineId{}, gycbhId{}", lineId, gycbhId);
		//"TVI", "CAR", "MOT", "TVC", "HOM", "KHC", "KCR", "BVP", "PAS", "HHV", "GFI", "TNC"
		if (StringUtils.equals(lineId, "BVP")) {
			BvpDTO bvpProduct = bVPService.getById(gycbhId);
			return bvpProduct.getId();
		}
		if (StringUtils.equals(lineId, "CAR")) {
			Car carProduct = carService.getById(gycbhId);
			return carProduct.getCarId();
		}
		if (StringUtils.equals(lineId, "TVI")) {
			TvicareDTO tviProduct = tvicareService.getById(gycbhId);
			return tviProduct.getTvicareId();
		}
		if (StringUtils.equals(lineId, "TVC")) {
			TravelcareDTO tvcProduct = travelcareService.getById(gycbhId);
			return tvcProduct.getTravelcareId();
		}
		if (StringUtils.equals(lineId, "MOT")) {
			MotoDTO motoProduct = motoService.getById(gycbhId);
			return motoProduct.getId();
		}
		if (StringUtils.equals(lineId, "HOM")) {
			HomeDTO homeProduct = homeService.getById(gycbhId);
			return homeProduct.getHomeId();
		}
		if (StringUtils.equals(lineId, "KHC")) {
			TlDTO tlProduct = tlService.getById(gycbhId);
			return tlProduct.getTlId();
		}
		if (StringUtils.equals(lineId, "KCR")) {
			KcareDTO kcareProduct = kcareService.findById(gycbhId);
			return kcareProduct.getKId();
		}
		if (StringUtils.equals(lineId, "TNC")) {
			PaDTO tncProduct = paService.getById(gycbhId);
			return tncProduct.getPaId();
		}
		if (StringUtils.equals(lineId, "HHV")) {
			GoodsDTO hhvcProduct = goodsService.getById(gycbhId);
			return hhvcProduct.getHhId();
		}
		
		return null;
	}

	@Override
	public void addProductObject(String lineId, String gycbhId, AgreementSearchDTO result) {
		log.debug("Request to addProductObject, lineId{}, gycbhId{}, AgreementSearchDTO{} : ", lineId, gycbhId, result);
		//"TVI", "CAR", "MOT", "TVC", "HOM", "KHC", "KCR", "BVP", "PAS", "HHV", "GFI", "TNC"
		if (StringUtils.equals(lineId, "BVP")) {
			BvpDTO bvpProduct = bVPService.getById(gycbhId);
			List<TinhtrangSkDTO> lstAdd = tinhtrangSkService.findByIdThamchieu(gycbhId, "BVP");
			result.setLstTinhtrangSkAddProduct(lstAdd);
			result.setObjProduct(bvpProduct);
		}
		if (StringUtils.equals(lineId, "CAR")) {
			Car carProduct = carService.getById(gycbhId);
			result.setObjProduct(carProduct);
		}
		if (StringUtils.equals(lineId, "TVI")) {
			TvicareDTO tviProduct = tvicareService.getById(gycbhId);
			List<TviCareAddDTO> lstAdd = tviCareAddService.getByTvicareId(gycbhId);
			result.setObjProduct(tviProduct);
			result.setLstTviCareAddProduct(lstAdd);
		}
		if (StringUtils.equals(lineId, "TVC")) {
			TravelcareDTO tvcProduct = travelcareService.getById(gycbhId);
			List<TravelCareAddDTO> lstAdd = travelCareAddService.getByTravaelcareId(gycbhId);
			result.setObjProduct(tvcProduct);
			result.setLstTravelCareAddProduct(lstAdd);
		}
		if (StringUtils.equals(lineId, "MOT")) {
			MotoDTO motoProduct = motoService.getById(gycbhId);
			result.setObjProduct(motoProduct);
		}
		if (StringUtils.equals(lineId, "HOM")) {
			HomeDTO homeProduct = homeService.getById(gycbhId);
			result.setObjProduct(homeProduct);
		}
		if (StringUtils.equals(lineId, "KHC")) {
			TlDTO tlProduct = tlService.getById(gycbhId);
			List<TlAddDTO> lstAdd = tlAddService.getAllByTlId(gycbhId);
			result.setObjProduct(tlProduct);
			result.setLstTlAddProduct(lstAdd);
		}
		if (StringUtils.equals(lineId, "KCR")) {
			KcareDTO kcareProduct = kcareService.findById(gycbhId);
			List<TinhtrangSkDTO> lstAdd = tinhtrangSkService.findByIdThamchieu(gycbhId, "KCR");
			result.setObjProduct(kcareProduct);
			result.setLstTinhtrangSkAddProduct(lstAdd);
		}
		if (StringUtils.equals(lineId, "TNC")) {
			PaDTO tncProduct = paService.getById(gycbhId);
			List<PaAddDTO> lstAdd = paAddService.getByPaId(gycbhId);
			result.setObjProduct(tncProduct);
			result.setLstPaAddProduct(lstAdd);
		}
		if (StringUtils.equals(lineId, "HHV")) {
			GoodsDTO hhvcProduct = goodsService.getById(gycbhId);
			result.setObjProduct(hhvcProduct);
		}
	}

	@Override
	public <T extends ProductBaseVM> T convertProductObjectToVM(AgreementSearchDTO result, String taituc) throws URISyntaxException, AgencyBusinessException{
		log.debug("Request to convertProductObjectToVM, AgreementSearchDTO{}, taituc{} : ", result, taituc);
		//"TVI", "CAR", "MOT", "TVC", "HOM", "KHC", "KCR", "BVP", "PAS", "HHV", "GFI", "TNC"
		T object = null;
		String lineId = result.getObjAgreement().getLineId();
		if (StringUtils.equals(lineId, "BVP")) {
			object = (T) convertBvpToVM(result, taituc);
		}
		if (StringUtils.equals(lineId, "CAR")) {
			object = (T) convertCarToVM(result, taituc);
		}
		if (StringUtils.equals(lineId, "TVI")) {
			object = (T) convertTviToVM(result, taituc);
		}
		if (StringUtils.equals(lineId, "TVC")) {
			object = (T) convertTvcToVM(result, taituc);
		}
		if (StringUtils.equals(lineId, "MOT")) {
			object = (T) convertMotoToVM(result, taituc);
		}
		if (StringUtils.equals(lineId, "HOM")) {
			object = (T) convertHomeToVM(result, taituc);
		}
		if (StringUtils.equals(lineId, "KHC")) {
			object = (T) convertKhcToVM(result, taituc);
		}
		if (StringUtils.equals(lineId, "KCR")) {
			object = (T) convertKcareToVM(result, taituc);
		}
		if (StringUtils.equals(lineId, "TNC")) {
			object = (T) convertTncToVM(result, taituc);
		}
		if (StringUtils.equals(lineId, "HHV")) {
			object = (T) convertHHVToVM(result, taituc);
		}
		
		// Set lineId
		object.setLineId(lineId);
		
		// set department
		object.setDepartmentId(result.getObjAgreement().getBaovietDepartmentId());
		
		// set contactName
		object.setContactName(result.getObjAgreement().getContactName());
		object.setContactDob(DateUtils.date2Str(result.getObjAgreement().getContactDob()));
		object.setContactPhone(result.getObjAgreement().getContactPhone());
		object.setContactEmail(result.getObjAgreement().getContactUsername());
		object.setContactIdNumber(result.getObjAgreement().getTaxIdNumber());
		Contact co = contactRepository.findOneByContactCode(object.getContactCode());
		if (co != null) {
			object.setContactCategoryType(co.getCategoryType());
			object.setContactAddress(co.getHomeAddress());
//			object.setContactName(co.getContactName());
//			object.setContactDob(DateUtils.date2Str(co.getDateOfBirth()));
		}
		
		return object;
	}
	
	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	private ProductBvpVM convertBvpToVM(AgreementSearchDTO result, String taituc) throws URISyntaxException, AgencyBusinessException {
		log.debug("Request to convertBvpToVM, AgreementSearchDTO{}, taituc{} : ", result, taituc);
		ProductBvpVM bvp = new ProductBvpVM();
		// convert
		AgreementDTO dto = result.getObjAgreement();
		BvpDTO bvpObj = (BvpDTO) result.getObjProduct();
		
		List<TinhtrangSkDTO> lstSk = tinhtrangSkService.findByIdThamchieu(dto.getGycbhId(), "BVP");
		if (lstSk != null && lstSk.size() > 0) {
			List<SKTDAddVM> lstAdd = new ArrayList<>();
			for (TinhtrangSkDTO item : lstSk) {
				SKTDAddVM input = new SKTDAddVM();
				input.setChuandoan(item.getChuandoan());
				input.setBenhvienorbacsy(item.getBenhvienorbacsy());
				input.setChitietdieutri(item.getChitietdieutri());
				input.setKetqua(item.getKetqua());
				input.setNgaydieutri(item.getNgaydieutri());
				lstAdd.add(input);
			}
			bvp.setLstAdd(lstAdd);
		}
		// productBase
		bvp.setStatusPolicy(dto.getStatusPolicyId());
		if (taituc.equals("0")) {
			bvp.setAgreementId(dto.getAgreementId());
			bvp.setGycbhId(dto.getGycbhId());
			bvp.setGycbhNumber(dto.getGycbhNumber());
			bvp.setPolicyNumber(bvpObj.getPolicyNumber());
			if(dto.getOldGycbhNumber() != null) {
				bvp.setOldGycbhNumber(dto.getOldGycbhNumber());	
			}
			bvp.setInceptionDate(bvpObj.getInceptionDate());
			bvp.setExpiredDate(bvpObj.getExpiredDate());	
		} else {
			// TH tái tục
			bvp.setAgreementId("");
			bvp.setGycbhId("");
			// số Gycbh mới
			CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("BVP");
			bvp.setGycbhNumber(codeManagementDTO.getIssueNumber());
			bvp.setPolicyNumber(codeManagementDTO.getIssueNumber());
			bvp.setOldGycbhNumber(dto.getGycbhNumber());	
			bvp.setInceptionDate(bvpObj.getExpiredDate());
			bvp.setExpiredDate(DateUtils.getCurrentYearPrevious(bvpObj.getExpiredDate(), 1));
		}
		
		
		bvp.setContactCode(bvpObj.getContactCode());
		bvp.setReceiveMethod(dto.getReceiveMethod());
		// product
		
		bvp.setNguoidbhNgaysinh(bvpObj.getNguoidbhNgaysinh());
		bvp.setChuongtrinhBh(bvpObj.getChuongtrinhBh());
		bvp.setNgoaitru(bvpObj.getNgoaitru());
		bvp.setNgoaitruPhi(bvpObj.getNgoaitruPhi());
		bvp.setTncn(bvpObj.getTncn());
		bvp.setTncnPhi(bvpObj.getTncnPhi());
		bvp.setTncnPhiSi(bvpObj.getTncnSotienbh());
		bvp.setSinhmang(bvpObj.getSinhmang());
		bvp.setSinhmangPhi(bvpObj.getSinhmangPhi());
		bvp.setSinhmangPhiSi(bvpObj.getSinhmangSotienbh());
		bvp.setNhakhoa(bvpObj.getNhakhoa());
		bvp.setNhakhoaPhi(bvpObj.getNhakhoaPhi());
		bvp.setThaisan(bvpObj.getThaisan());
		bvp.setThaisanPhi(bvpObj.getThaisanPhi());
		if(dto.getDiscount() != null) {
			bvp.setDiscount(Double.parseDouble(dto.getDiscount()));	
		}
		if (bvpObj.getNgoaitru().equals("1") || bvpObj.getTncn().equals("1") || bvpObj.getSinhmang().equals("1") 
				|| bvpObj.getNhakhoa().equals("1") || bvpObj.getThaisan().equals("1")) {
			bvp.setHasExtracare(true); // quyen loi bo sung	
		} else {
			bvp.setHasExtracare(false); // quyen loi bo sung
		}
		bvp.setNguoiycName(bvpObj.getNguoiycName());
		bvp.setNguoiycNgaysinh(bvpObj.getNguoiycNgaysinh());
		bvp.setNguoidbhName(bvpObj.getNguoidbhName());
		bvp.setNguoidbhCmnd(bvpObj.getNguoidbhCmnd());
		bvp.setNguoidbhQuanhe(bvpObj.getNguoidbhQuanhe());
		bvp.setQ1(bvpObj.getQ1());
		bvp.setQ2(bvpObj.getQ2());
		bvp.setQ3(bvpObj.getQ3());
		if (!StringUtils.isEmpty(bvpObj.getNguoithName()) && !StringUtils.isEmpty(bvpObj.getNguoithCmnd()) && !StringUtils.isEmpty(bvpObj.getNguoithQuanhe())) {
			bvp.setHasNguoithuhuong(true);	
			bvp.setNguoithName(bvpObj.getNguoithName());
			bvp.setNguoithCmnd(bvpObj.getNguoithCmnd());
			bvp.setNguoithQuanhe(bvpObj.getNguoithQuanhe());
			bvp.setNguoithNgaysinh(bvpObj.getNguoithNgaysinh());
			
		} else {
			bvp.setHasNguoithuhuong(false);
		}
		if (!StringUtils.isEmpty(bvpObj.getNguoinhanName()) && !StringUtils.isEmpty(bvpObj.getNguoinhanCmnd()) && !StringUtils.isEmpty(bvpObj.getNguoinhanQuanhe())) {
			bvp.setHasNguoinhantien(true);	
			bvp.setNguoinhanName(bvpObj.getNguoinhanName());
			bvp.setNguoinhanCmnd(bvpObj.getNguoinhanCmnd());
			bvp.setNguoinhanQuanhe(bvpObj.getNguoinhanQuanhe());
			bvp.setNguointNgaysinh(bvpObj.getNguointNgaysinh());
		} else {
			bvp.setHasNguoinhantien(false);
		}
		InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
		if(dto.getInvoiceBuyer() != null) {
			invoiceInfo.setName(dto.getInvoiceBuyer());
			invoiceInfo.setCheck("1");
		} else {
			invoiceInfo.setCheck("0");
		}
		if(dto.getInvoiceCompany() != null) {
			invoiceInfo.setCompany(dto.getInvoiceCompany());
		} 
		if(dto.getInvoiceTaxNo() != null) {
			invoiceInfo.setTaxNo(dto.getInvoiceTaxNo());
		} 
		if(dto.getInvoiceAddress() != null) {
			invoiceInfo.setAddress(dto.getInvoiceAddress());
		}
		if(dto.getInvoiceAccountNo() != null) {
			invoiceInfo.setAccountNo(dto.getInvoiceAccountNo());
		} 
		bvp.setInvoiceInfo(invoiceInfo);
		
		ReceiverUserInfoDTO receiverUser = new ReceiverUserInfoDTO();
		receiverUser.setName(bvpObj.getReceiverName());
		receiverUser.setAddress(bvpObj.getReceiverAddress());
		receiverUser.setEmail(bvpObj.getReceiverEmail());
		receiverUser.setMobile(bvpObj.getReceiverMoible());
		bvp.setReceiverUser(receiverUser);
		
		bvp.setTncnSotienbh(bvpObj.getTncnSotienbh());
		bvp.setSinhmangSotienbh(bvpObj.getSinhmangSotienbh());
		bvp.setPolicyParent(bvpObj.getPolicyParent());
		bvp.setChuongtrinhPhi(bvpObj.getChuongtrinhPhi());
		bvp.setTanggiamPhi(bvpObj.getTanggiamPhi());
		bvp.setTanggiamPhiNoidung(bvpObj.getTanggiamPhiNoidung());
		
		// Check q1Id if not null -> get file
		if (StringUtils.isNotEmpty(StringUtils.trim(bvpObj.getQ1Id()))) {
			BASE64Encoder encoder = new BASE64Encoder();
			Attachment item = attachmentRepository.findOne(bvpObj.getQ1Id());
			if (item != null) {
				FileContentDTO file = new FileContentDTO();
				if (StringUtils.equals(item.getGroupType(), AgencyConstants.ATTACHMENT_GROUP_TYPE.ONLLINE_BVP)) {
					String imageString = encoder.encode(item.getContent());
					file.setContent(imageString);
					file.setFilename(item.getAttachmentName());
					file.setFileType(item.getAttachmentType());
					file.setAttachmentId(item.getAttachmentId());
					bvp.setImgGks(file);
				}
			}
		}
		
		// Tái tục thì insert
		if (taituc.equals("1")) {
			// Get current agency
			AgencyDTO currentAgency = getCurrentAccount();
			
			// Call service
			ProductBvpVM data = productBVPService.createOrUpdatePolicy(bvp, currentAgency);
		}
		
		return bvp;
	}

	private ProductCarVM convertCarToVM(AgreementSearchDTO result, String taituc) throws URISyntaxException, AgencyBusinessException{
		log.debug("Request to convertCarToVM, AgreementSearchDTO{}, taituc{} : ", result, taituc);
		ProductCarVM car = new ProductCarVM();
		// convert
		AgreementDTO dto = result.getObjAgreement();
		Car carObj = (Car) result.getObjProduct();
		
		// productBase
		car.setContactCode(carObj.getContactCode());
		car.setReceiveMethod(dto.getReceiveMethod());
		car.setStatusPolicy(dto.getStatusPolicyId());
		
		InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
		if(dto.getInvoiceBuyer() != null) {
			invoiceInfo.setName(dto.getInvoiceBuyer());
			invoiceInfo.setCheck("1");
		} else {
			invoiceInfo.setCheck("0");
		}
		if(dto.getInvoiceCompany() != null) {
			invoiceInfo.setCompany(dto.getInvoiceCompany());
		} 
		if(dto.getInvoiceTaxNo() != null) {
			invoiceInfo.setTaxNo(dto.getInvoiceTaxNo());
		} 
		if(dto.getInvoiceAddress() != null) {
			invoiceInfo.setAddress(dto.getInvoiceAddress());
		}
		if(dto.getInvoiceAccountNo() != null) {
			invoiceInfo.setAccountNo(dto.getInvoiceAccountNo());
		} 
		car.setInvoiceInfo(invoiceInfo);
		
		ReceiverUserInfoDTO receiverUser = new ReceiverUserInfoDTO();
		receiverUser.setName(carObj.getReceiverName());
		receiverUser.setAddress(carObj.getReceiverAddress());
		receiverUser.setEmail(carObj.getReceiverEmail());
		receiverUser.setMobile(carObj.getReceiverMoible());
		car.setReceiverUser(receiverUser);
		
		// product
		if (taituc.equals("0")) {
			car.setAgreementId(dto.getAgreementId());
			car.setGycbhId(dto.getGycbhId());
			car.setGycbhNumber(dto.getGycbhNumber());
			if(dto.getOldGycbhNumber() != null) {
				car.setOldGycbhNumber(dto.getOldGycbhNumber());	
			}
			car.setPolicyNumber(carObj.getPolicyNumber());
			car.setThoihanden(DateUtils.date2Str(carObj.getExpiredDate()));
			car.setThoihantu(DateUtils.date2Str(carObj.getInceptionDate()));
		} else {
			// TH tái tục
			car.setAgreementId("");
			car.setGycbhId("");
			// số Gycbh mới
			CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("CAR");
			car.setGycbhNumber(codeManagementDTO.getIssueNumber());
			car.setPolicyNumber(codeManagementDTO.getIssueNumber());
			car.setOldGycbhNumber(dto.getGycbhNumber());	
			car.setThoihantu(DateUtils.date2Str(carObj.getExpiredDate()));
			car.setThoihanden(DateUtils.date2Str(DateUtils.getCurrentYearPrevious(carObj.getExpiredDate(), 1)));
		}
		
		car.setChangePremium(carObj.getChangePremiumPremium());
		car.setChassisNumber(carObj.getChassisNumber());
		car.setEngineNumber(carObj.getEngineNumber());
		car.setInsuredAddress(carObj.getBankName());
		car.setInsuredName(carObj.getBankId());
		
		if(carObj.getPassengersAccidentSi() != null && carObj.getPassengersAccidentSi() > 0) {
			car.setNntxCheck(true);
			car.setPassengersAccidentNumber(carObj.getPassengersAccidentNumber());
			car.setPassengersAccidentPremium(carObj.getPassengersAccidentPremium());
			car.setPassengersAccidentSi(carObj.getPassengersAccidentSi());	
		} else {
			car.setNntxCheck(false);
		}
		
		if (!StringUtils.isEmpty(carObj.getMakeId())) {
			car.setMakeId(carObj.getMakeId());	
		}
		if (!StringUtils.isEmpty(carObj.getMakeName())) {
			car.setMakeName(carObj.getMakeName());
		}
		if (!StringUtils.isEmpty(carObj.getModelId())) {
			car.setModelId(carObj.getModelId());
			car.setVcxCheck(true);
		} else {
			car.setVcxCheck(false);
		}
		if (!StringUtils.isEmpty(carObj.getModelName())) {
			car.setModelName(carObj.getModelName());
		}
		if (carObj.getActualValue() != null && carObj.getActualValue() > 0) {
			car.setActualValue(carObj.getActualValue());
		}
		if (!StringUtils.isEmpty(carObj.getYearOfMake())) {
			car.setYearOfMake(carObj.getYearOfMake());
		} 
		if (carObj.getPhysicalDamagePremium() != null && carObj.getPhysicalDamagePremium() > 0) {
			car.setPhysicalDamagePremium(carObj.getPhysicalDamagePremium());	// phi bhvc
		}
		if (carObj.getPhysicalDamageSi() != null && carObj.getPhysicalDamageSi() > 0) {
			car.setPhysicalDamageSi(carObj.getPhysicalDamageSi().longValue());
		}
		if (car.getVcxCheck()) {
			if (carObj.getGarage() != null && carObj.getGarage() > 0) {
				car.setGarageCheck(true);
			} else {
				car.setGarageCheck(false);
			}
			if (carObj.getKhaoHao() != null && carObj.getKhaoHao() > 0) {
				car.setKhaoHaoCheck(true);
			} else {
				car.setKhaoHaoCheck(false);
			}
			if (carObj.getKhauTru() != null && carObj.getKhauTru() > 0) {
				car.setKhauTruCheck(true);
			} else {
				car.setKhauTruCheck(false);
			}
			if (carObj.getMatCap() != null && carObj.getMatCap() > 0) {
				car.setMatCapCheck(true);
			} else {
				car.setMatCapCheck(false);
			}
			if (carObj.getNgapNuoc() != null && carObj.getNgapNuoc() > 0) {
				car.setNgapNuocCheck(true);
			} else {
				car.setNgapNuocCheck(false);
			}
		} else {
			car.setGarageCheck(false);
			car.setKhaoHaoCheck(false);
			car.setKhauTruCheck(false);
			car.setMatCapCheck(false);
			car.setNgapNuocCheck(false);
		}
		car.setPremium(carObj.getPremiumVat());
		car.setPurposeOfUsageId(carObj.getPurposeOfUsageId());
		car.setRegistrationNumber(carObj.getRegistrationNumber());
		car.setTndsSoCho(carObj.getTypeOfCarid());
		
		if (carObj.getThirdPartySiCn() != null && carObj.getThirdPartySiCn() >0) {
			car.setTndsbbCheck(true);	
		} else {
			car.setTndsbbCheck(false);
		}
		
		if (carObj.getMatCapBoPhanPremium() != null && carObj.getMatCapBoPhanPremium() > 0) {
			car.setTndstnPhi(carObj.getMatCapBoPhanPremium());
		}
		if (carObj.getMatCapBoPhanRate() != null && carObj.getMatCapBoPhanRate() > 0) {
			car.setTndstnSotien(carObj.getMatCapBoPhanRate());
			car.setTndstnCheck(true);
		} else {
			car.setTndstnCheck(false);
		}
		
		
		car.setTotalPremium(carObj.getTotalPremium());
		car.setThirdPartyPremium(carObj.getThirdPartyPremium());
		
		// Tái tục thì insert
		if (taituc.equals("1")) {
			// Get current agency
			AgencyDTO currentAgency = getCurrentAccount();
			
			// Call service
			ProductCarVM data = productCARService.createOrUpdatePolicy(car, currentAgency);
		}
		
		return car;
	}

	private ProductHomeVM convertHomeToVM(AgreementSearchDTO result, String taituc) throws URISyntaxException, AgencyBusinessException{
		log.debug("Request to convertHomeToVM, AgreementSearchDTO{}, taituc{} : ", result, taituc);
		ProductHomeVM home = new ProductHomeVM();
		// convert
		AgreementDTO dto = result.getObjAgreement();
		HomeDTO homeObj = (HomeDTO) result.getObjProduct();
		
		// productBase
		home.setStatusPolicy(dto.getStatusPolicyId());
		home.setContactCode(homeObj.getContactCode());
		home.setReceiveMethod(dto.getReceiveMethod());
		
		InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
		if(dto.getInvoiceBuyer() != null) {
			invoiceInfo.setName(dto.getInvoiceBuyer());
			invoiceInfo.setCheck("1");
		} else {
			invoiceInfo.setCheck("0");
		}
		if(dto.getInvoiceCompany() != null) {
			invoiceInfo.setCompany(dto.getInvoiceCompany());
		} 
		if(dto.getInvoiceTaxNo() != null) {
			invoiceInfo.setTaxNo(dto.getInvoiceTaxNo());
		} 
		if(dto.getInvoiceAddress() != null) {
			invoiceInfo.setAddress(dto.getInvoiceAddress());
		}
		if(dto.getInvoiceAccountNo() != null) {
			invoiceInfo.setAccountNo(dto.getInvoiceAccountNo());
		} 
		home.setInvoiceInfo(invoiceInfo);
		
		ReceiverUserInfoDTO receiverUser = new ReceiverUserInfoDTO();
		receiverUser.setName(homeObj.getReceiverName());
		receiverUser.setAddress(homeObj.getReceiverAddress());
		receiverUser.setEmail(homeObj.getReceiverEmail());
		receiverUser.setMobile(homeObj.getReceiverMoible());
		home.setReceiverUser(receiverUser);
		
		// product
		// mới thêm trường 17/07/2018
		if(homeObj.getSi() != null && homeObj.getSi() > 0) {
			home.setSi((new BigDecimal(homeObj.getSi()).toPlainString()));
		}
		if(homeObj.getSiin() != null && homeObj.getSiin() > 0) {
			home.setSiin((new BigDecimal(homeObj.getSiin()).toPlainString()));
		}
		if(homeObj.getPremiumHome() != null && homeObj.getPremiumHome() > 0) {
			home.setPremiumHome(homeObj.getPremiumHome());
		}
		if(!StringUtils.isEmpty(homeObj.getYearBuildCode())) {
			home.setYearBuildCode(homeObj.getYearBuildCode());
		}
		
		if(homeObj.getConPremium() != null && homeObj.getConPremium() > 0) {
			home.setSiPremium(homeObj.getConPremium());
		}
		if(homeObj.getPlanPremium() != null && homeObj.getPlanPremium() > 0) {
			home.setSiinPremium(homeObj.getPlanPremium());
		}
		if(homeObj.getConSi() != null && homeObj.getConSi() > 0) {
			home.setPremiumsi(homeObj.getConSi());
		}
		if(homeObj.getLlimitPerItem() != null && homeObj.getLlimitPerItem() > 0) {
			home.setPremiumsiin(homeObj.getLlimitPerItem());
		}
		if(homeObj.getTotalBasicPremium() != null && homeObj.getTotalBasicPremium() > 0) {
			home.setPremiumNet(homeObj.getTotalBasicPremium());
		}
		if(homeObj.getChangePremiumRate() != null && homeObj.getChangePremiumRate() > 0) {
			home.setPremiumDiscount(homeObj.getChangePremiumRate());
		}
		if(!StringUtils.isEmpty(homeObj.getInvoiceCompany())) {
			home.setInsuranceName(homeObj.getInvoiceCompany());
		}
		if(!StringUtils.isEmpty(homeObj.getInvoceNumber())) {
			home.setInvoceNumber(homeObj.getInvoceNumber());
		}
		if(!StringUtils.isEmpty(homeObj.getInvoiceAddress())) {
			home.setInsuranceAddress(homeObj.getInvoiceAddress());
		}
		if(!StringUtils.isEmpty(homeObj.getByNight())) {
			home.setByNight(homeObj.getByNight());
		}
		if(!StringUtils.isEmpty(homeObj.getInsuredLocation())) {
			home.setInsuredLocation(homeObj.getInsuredLocation());
		}
		if (homeObj.getOwnership().equals("1000")) {
			home.setBankId("0");	
		}
		if (homeObj.getOwnership().equals("1001")) {
			home.setBankId("1");	
		}
		if (homeObj.getOwnership().equals("1002")) {
			home.setBankId("2");	
		}
		if(homeObj.getTotalUsedArea() != null && homeObj.getTotalUsedArea() > 0) {
			home.setTotalUsedArea(String.valueOf(homeObj.getTotalUsedArea()));
		}
		home.setLoaiHinh(String.valueOf(homeObj.getLoaiHinh()));
		
		home.setWindowLocks(String.valueOf(homeObj.getWindowLocks()));
		home.setBars(String.valueOf(homeObj.getBars()));
		
		if (taituc.equals("0")) {
			home.setAgreementId(dto.getAgreementId());
			home.setGycbhId(dto.getGycbhId());
			home.setGycbhNumber(dto.getGycbhNumber());
			if(dto.getOldGycbhNumber() != null) {
				home.setOldGycbhNumber(dto.getOldGycbhNumber());	
			}
			if(homeObj.getInceptionDate() != null) {
				home.setInceptionDate(DateUtils.date2Str(homeObj.getInceptionDate()));
			}
			if(homeObj.getExpiredDate() != null) {
				home.setExpiredDate(DateUtils.date2Str(homeObj.getExpiredDate()));
			}
			if(!StringUtils.isEmpty(homeObj.getPolicyNumber())) {
				home.setPolicyNumber(homeObj.getPolicyNumber());
			}	
		} else {
			// TH tái tục
			home.setAgreementId("");
			home.setGycbhId("");
			// số Gycbh mới
			CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("HOM");
			home.setGycbhNumber(codeManagementDTO.getIssueNumber());
			home.setPolicyNumber(codeManagementDTO.getIssueNumber());
			home.setOldGycbhNumber(dto.getGycbhNumber());	
			home.setInceptionDate(DateUtils.date2Str(homeObj.getExpiredDate()));
			home.setExpiredDate(DateUtils.date2Str(DateUtils.getCurrentYearPrevious(homeObj.getExpiredDate(), 1)));
		}
		
		// Tái tục thì insert
		if (taituc.equals("1")) {
			// Get current agency
			AgencyDTO currentAgency = getCurrentAccount();
			
			// Call service
			ProductHomeVM data = productHomeService.createOrUpdatePolicy(home, currentAgency);
		}
		
		return home;
	}

	private ProductKcareVM convertKcareToVM(AgreementSearchDTO result, String taituc) throws URISyntaxException, AgencyBusinessException{
		log.debug("Request to convertKcareToVM, AgreementSearchDTO{}, taituc{} : ", result, taituc);
		ProductKcareVM kcare = new ProductKcareVM();
		// convert
		AgreementDTO dto = result.getObjAgreement();
		KcareDTO kcareObj = (KcareDTO) result.getObjProduct();
		
		List<TinhtrangSkDTO> lstSk = tinhtrangSkService.findByIdThamchieu(dto.getGycbhId(), "KCR");
		if (lstSk != null && lstSk.size() > 0) {
			kcare.setLstTinhtrangSKs(lstSk);
		}
		
		// productBase
		kcare.setStatusPolicy(dto.getStatusPolicyId());
		kcare.setContactCode(kcareObj.getContactCode());
		kcare.setReceiveMethod(dto.getReceiveMethod());

		InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
		if(dto.getInvoiceBuyer() != null) {
			invoiceInfo.setName(dto.getInvoiceBuyer());
			invoiceInfo.setCheck("1");
		} else {
			invoiceInfo.setCheck("0");
		}
		if(dto.getInvoiceCompany() != null) {
			invoiceInfo.setCompany(dto.getInvoiceCompany());
		} 
		if(dto.getInvoiceTaxNo() != null) {
			invoiceInfo.setTaxNo(dto.getInvoiceTaxNo());
		} 
		if(dto.getInvoiceAddress() != null) {
			invoiceInfo.setAddress(dto.getInvoiceAddress());
		}
		if(dto.getInvoiceAccountNo() != null) {
			invoiceInfo.setAccountNo(dto.getInvoiceAccountNo());
		} 
		kcare.setInvoiceInfo(invoiceInfo);
		
		ReceiverUserInfoDTO receiverUser = new ReceiverUserInfoDTO();
		receiverUser.setName(kcareObj.getReceiverName());
		receiverUser.setAddress(kcareObj.getReceiverAddress());
		receiverUser.setEmail(kcareObj.getReceiverEmail());
		receiverUser.setMobile(kcareObj.getReceiverMoible());
		kcare.setReceiverUser(receiverUser);
		
		// product
		if (taituc.equals("0")) {
			kcare.setAgreementId(dto.getAgreementId());
			kcare.setGycbhId(dto.getGycbhId());
			kcare.setGycbhNumber(dto.getGycbhNumber());
			if(dto.getOldGycbhNumber() != null) {
				kcare.setOldGycbhNumber(dto.getOldGycbhNumber());	
			}
			if(!StringUtils.isEmpty(kcareObj.getPolicyNumber())) {
				kcare.setPolicyNumber(kcareObj.getPolicyNumber());
			}
			if(kcareObj.getInceptionDate() != null) {
				kcare.setThoihantu(DateUtils.date2Str(kcareObj.getInceptionDate()));
			}
			if(kcareObj.getExpiredDate() != null) {
				kcare.setThoihanden(DateUtils.date2Str(kcareObj.getExpiredDate()));
			}
		} else {
			// TH tái tục
			kcare.setAgreementId("");
			kcare.setGycbhId("");
			// số Gycbh mới
			CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("KCR");
			kcare.setGycbhNumber(codeManagementDTO.getIssueNumber());
			kcare.setPolicyNumber(codeManagementDTO.getIssueNumber());
			kcare.setOldGycbhNumber(dto.getGycbhNumber());	
			kcare.setThoihantu(DateUtils.date2Str(kcareObj.getExpiredDate()));
			kcare.setThoihanden(DateUtils.date2Str(DateUtils.getCurrentYearPrevious(kcareObj.getExpiredDate(), 1)));
		}
		
		
		if(!StringUtils.isEmpty(kcareObj.getInsuredName())) {
			kcare.setInsuredName(kcareObj.getInsuredName());
		}
		if(!StringUtils.isEmpty(kcareObj.getInsuredSex())) {
			kcare.setInsuredSex(kcareObj.getInsuredSex());
		}
		if(!StringUtils.isEmpty(kcareObj.getInsuredSex())) {
			kcare.setInsuredSex(kcareObj.getInsuredSex());
		}
		if(kcareObj.getInsuredDob() != null) {
			kcare.setInsuredNgaysinh(kcareObj.getInsuredDob());
		}
		if(!StringUtils.isEmpty(kcareObj.getInsuredIdNumber())) {
			kcare.setInsuredIdNumber(kcareObj.getInsuredIdNumber());
		}
		if(!StringUtils.isEmpty(kcareObj.getNote())) {
			kcare.setInsuredRelationship(kcareObj.getNote());
		}
		if(!StringUtils.isEmpty(kcareObj.getPlanId())) {
			kcare.setPlanId(kcareObj.getPlanId());
		}
		if(!StringUtils.isEmpty(kcareObj.getQ1())) {
			kcare.setQ1(kcareObj.getQ1());
		}
		if(!StringUtils.isEmpty(kcareObj.getQ2())) {
			kcare.setQ2(kcareObj.getQ2());
		}
		if(!StringUtils.isEmpty(kcareObj.getQ3())) {
			kcare.setQ3(kcareObj.getQ3());
		}
		if(!StringUtils.isEmpty(kcareObj.getQ4())) {
			kcare.setQ4(kcareObj.getQ4());
		}
		if(!StringUtils.isEmpty(kcareObj.getQ5())) {
			kcare.setQ5(kcareObj.getQ5());
		}
		if(!StringUtils.isEmpty(kcareObj.getQTreatment())) {
			kcare.setQtreatment(kcareObj.getQTreatment());
		}
		if(!StringUtils.isEmpty(kcareObj.getQResultTre())) {
			kcare.setQresultTre(kcareObj.getQResultTre());
		}
		if(!StringUtils.isEmpty(kcareObj.getQResultCan())) {
			kcare.setQresultCan(kcareObj.getQResultCan());
		}
		if(!StringUtils.isEmpty(kcareObj.getQTypeCancer())) {
			kcare.setQtypeCancer(kcareObj.getQTypeCancer());
		}
		if(!StringUtils.isEmpty(kcareObj.getBeneficiaryName())) {
			kcare.setBeneficiaryName(kcareObj.getBeneficiaryName());
		}
		if(kcareObj.getBeneficiaryDob() != null) {
			kcare.setBeneficiaryNgaysinh(DateUtils.date2Str(kcareObj.getBeneficiaryDob()));
		}
		if(!StringUtils.isEmpty(kcareObj.getBeneficiaryIdNumber())) {
			kcare.setBeneficiaryIdNumber(kcareObj.getBeneficiaryIdNumber());
		}
		if(!StringUtils.isEmpty(kcareObj.getBeneficiaryRelationship())) {
			kcare.setBeneficiaryRelationship(kcareObj.getBeneficiaryRelationship());
		}
		
		if(!StringUtils.isEmpty(kcareObj.getBeneficiaryNameD())) {
			kcare.setBeneficiaryNameD(kcareObj.getBeneficiaryNameD());
		}
		if(kcareObj.getBeneficiaryDobD() != null) {
			kcare.setBeneficiaryNgaysinhD(DateUtils.date2Str(kcareObj.getBeneficiaryDobD()));
		}
		if(!StringUtils.isEmpty(kcareObj.getBeneficiaryIdNumberD())) {
			kcare.setBeneficiaryIdNumberD(kcareObj.getBeneficiaryIdNumberD());
		}
		if(!StringUtils.isEmpty(kcareObj.getBeneficiaryRelationshipD())) {
			kcare.setBeneficiaryRelationshipD(kcareObj.getBeneficiaryRelationshipD());
		}
		if(kcareObj.getNetPremium() != null && kcareObj.getNetPremium() > 0) {
			kcare.setNetPremium(kcareObj.getNetPremium());
		}
		if(kcareObj.getChangePremium() != null && kcareObj.getChangePremium() > 0) {
			kcare.setChangePremium(kcareObj.getChangePremium());
		}
		if(kcareObj.getTotalPremium() != null && kcareObj.getTotalPremium() > 0) {
			kcare.setTotalPremium(kcareObj.getTotalPremium());
		}
		
		// Tái tục thì insert
		if (taituc.equals("1")) {
			// Get current agency
			AgencyDTO currentAgency = getCurrentAccount();
			
			// Call service
			ProductKcareVM data = productKcareService.createOrUpdatePolicy(currentAgency, kcare);
		}
		
		return kcare;
	}
	
	private ProductKhcVM convertKhcToVM(AgreementSearchDTO result, String taituc) throws URISyntaxException, AgencyBusinessException{
		log.debug("Request to convertKhcToVM, AgreementSearchDTO{}, taituc{} : ", result, taituc);
		ProductKhcVM khc = new ProductKhcVM();
		// convert
		AgreementDTO dto = result.getObjAgreement();
		TlDTO khcObj = (TlDTO) result.getObjProduct();
		
		List<TlAddDTO> tlAdd = tlAddService.getAllByTlId(dto.getGycbhId());
		if (tlAdd != null && tlAdd.size() > 0) {
			khc.setTlAddcollections(tlAdd);
		}
		
		// productBase
		khc.setStatusPolicy(dto.getStatusPolicyId());
		khc.setContactCode(khcObj.getContactCode());
		khc.setReceiveMethod(dto.getReceiveMethod());
		
		InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
		if(dto.getInvoiceBuyer() != null) {
			invoiceInfo.setName(dto.getInvoiceBuyer());
			invoiceInfo.setCheck("1");
		} else {
			invoiceInfo.setCheck("0");
		}
		if(dto.getInvoiceCompany() != null) {
			invoiceInfo.setCompany(dto.getInvoiceCompany());
		} 
		if(dto.getInvoiceTaxNo() != null) {
			invoiceInfo.setTaxNo(dto.getInvoiceTaxNo());
		} 
		if(dto.getInvoiceAddress() != null) {
			invoiceInfo.setAddress(dto.getInvoiceAddress());
		}
		if(dto.getInvoiceAccountNo() != null) {
			invoiceInfo.setAccountNo(dto.getInvoiceAccountNo());
		} 
		khc.setInvoiceInfo(invoiceInfo);
		
		ReceiverUserInfoDTO receiverUser = new ReceiverUserInfoDTO();
		receiverUser.setName(dto.getReceiverName());
		receiverUser.setAddress(dto.getReceiverAddress());
		receiverUser.setEmail(dto.getReceiverEmail());
		receiverUser.setMobile(dto.getReceiverMoible());
		khc.setReceiverUser(receiverUser);
		
		// product
		if (khcObj.getPermanentTotalDisablement() != null) {
			khc.setPermanentTotalDisablement(khcObj.getPermanentTotalDisablement());
		}
		if (khcObj.getPlan() != null) {
			int plan = Integer.parseInt(khcObj.getPlan()) / 10000000;
			khc.setPlan(plan);
		}
		
		if (taituc.equals("0")) {
			khc.setAgreementId(dto.getAgreementId());
			khc.setGycbhId(dto.getGycbhId());
			khc.setGycbhNumber(dto.getGycbhNumber());
			if(dto.getOldGycbhNumber() != null) {
				khc.setOldGycbhNumber(dto.getOldGycbhNumber());	
			}
			if (khcObj.getInceptionDate() != null) {
				khc.setInceptionDate(DateUtils.date2Str(khcObj.getInceptionDate()));
			}
		} else {
			// TH tái tục
			khc.setAgreementId("");
			khc.setGycbhId("");
			// số Gycbh mới
			CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("KHC");
			khc.setGycbhNumber(codeManagementDTO.getIssueNumber());
			khc.setOldGycbhNumber(dto.getGycbhNumber());	
			khc.setInceptionDate(DateUtils.date2Str(dto.getExpiredDate()));
		}
		
		// Tái tục thì insert
		if (taituc.equals("1")) {
			// Get current agency
			AgencyDTO currentAgency = getCurrentAccount();
			
			// Call service
			KhcResultVM data = productKhcService.createOrUpdatePolicy(khc, currentAgency);
		}
			
		return khc;
	}

	private ProductMotoVM convertMotoToVM(AgreementSearchDTO result, String taituc) throws URISyntaxException, AgencyBusinessException{
		log.debug("Request to convertKhcToVM, AgreementSearchDTO{}, taituc{} : ", result, taituc);
		ProductMotoVM moto = new ProductMotoVM();
		// convert
		AgreementDTO dto = result.getObjAgreement();
		MotoDTO motoObj = (MotoDTO) result.getObjProduct();
		
		// productBase
		moto.setStatusPolicy(dto.getStatusPolicyId());
		moto.setContactCode(motoObj.getContactCode());
		moto.setReceiveMethod(dto.getReceiveMethod());
		
		InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
		if (!StringUtils.isEmpty(dto.getInvoiceBuyer())) {
			invoiceInfo.setName(dto.getInvoiceBuyer());
			invoiceInfo.setCheck("1");
		} else {
			invoiceInfo.setCheck("0");
		}
		if (!StringUtils.isEmpty(dto.getInvoiceCompany())) {
			invoiceInfo.setCompany(dto.getInvoiceCompany());
		} 
		if (!StringUtils.isEmpty(dto.getInvoiceTaxNo())) {
			invoiceInfo.setTaxNo(dto.getInvoiceTaxNo());
		} 
		if (!StringUtils.isEmpty(dto.getInvoiceAddress())) {
			invoiceInfo.setAddress(dto.getInvoiceAddress());
		}
		if (!StringUtils.isEmpty(dto.getInvoiceAccountNo())) {
			invoiceInfo.setAccountNo(dto.getInvoiceAccountNo());
		} 
		moto.setInvoiceInfo(invoiceInfo);
		
		ReceiverUserInfoDTO receiverUser = new ReceiverUserInfoDTO();
		receiverUser.setName(dto.getReceiverName());
		receiverUser.setAddress(dto.getReceiverAddress());
		receiverUser.setEmail(dto.getReceiverEmail());
		receiverUser.setMobile(dto.getReceiverMoible());
		moto.setReceiverUser(receiverUser);
		
		// product
		if (taituc.equals("0")) {
			moto.setAgreementId(dto.getAgreementId());
			moto.setGycbhId(dto.getGycbhId());
			moto.setGycbhNumber(dto.getGycbhNumber());
			if(dto.getOldGycbhNumber() != null) {
				moto.setOldGycbhNumber(dto.getOldGycbhNumber());	
			}
//			if (!StringUtils.isEmpty(motoObj.getPolicyNumber())) {
//				moto.setPolicyNumber(motoObj.getPolicyNumber());
//			}
			if (motoObj.getInceptionDate() != null) {
				moto.setThoihantu(DateUtils.date2Str(motoObj.getInceptionDate()));
			}
			if (motoObj.getExpiredDate() != null) {
				moto.setThoihanden(DateUtils.date2Str(motoObj.getExpiredDate()));
			}
		} else {
			// TH tái tục
			moto.setAgreementId("");
			moto.setGycbhId("");
			// số Gycbh mới
			CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("MOT");
			moto.setGycbhNumber(codeManagementDTO.getIssueNumber());
//			moto.setPolicyNumber(codeManagementDTO.getIssueNumber());
			moto.setOldGycbhNumber(dto.getGycbhNumber());	
			moto.setThoihantu(DateUtils.date2Str(motoObj.getExpiredDate()));
			moto.setThoihanden(DateUtils.date2Str(DateUtils.getCurrentYearPrevious(motoObj.getExpiredDate(), 1)));
		}
		
		if (!StringUtils.isEmpty(motoObj.getInsuredName())) {
			moto.setInsuredName(motoObj.getInsuredName());
		}
		if (!StringUtils.isEmpty(motoObj.getInsuredAddress())) {
			moto.setInsuredAddress(motoObj.getInsuredAddress());
		}
		if (!StringUtils.isEmpty(motoObj.getRegistrationNumber())) {
			moto.setRegistrationNumber(motoObj.getRegistrationNumber());
		}
		if (!StringUtils.isEmpty(motoObj.getSokhung())) {
			moto.setSokhung(motoObj.getSokhung());
		}
		if (!StringUtils.isEmpty(motoObj.getSomay())) {
			moto.setSomay(motoObj.getSomay());
		}
		if (!StringUtils.isEmpty(motoObj.getGhichu())) {
			moto.setHieuxe(motoObj.getGhichu());
		}
		if (!StringUtils.isEmpty(motoObj.getTypeOfMotoId())) {
			moto.setTypeOfMoto(motoObj.getTypeOfMotoId());
		}
		if (motoObj.getTndsBbPhi() != null && motoObj.getTndsBbPhi() > 0) {
			moto.setTndsbbCheck(true);
		} else {
			moto.setTndsbbCheck(false);
		}
		if (motoObj.getTndsBbPhi() != null) {
			moto.setTndsbbPhi(motoObj.getTndsBbPhi());
		}
		if (motoObj.getTndsTnTs() != null && motoObj.getTndsTnTs() > 0) {
			moto.setTndstnCheck(true);
		} else {
			moto.setTndstnCheck(false);
		}
		if (motoObj.getTndsTnTs() != null && motoObj.getTndsTnTs() > 0) {
			moto.setTndstnSotien(motoObj.getTndsTnTs());;
		}
		if (motoObj.getTndsTnPhi() != null && motoObj.getTndsTnPhi() > 0) {
			moto.setTndstnPhi(motoObj.getTndsTnPhi());;
		}
		//nntx
		if (motoObj.getNntxStbh() != null && motoObj.getNntxStbh() > 0) {
			moto.setNntxCheck(true);
		} else {
			moto.setNntxCheck(false);
		}
		if (moto.getNntxCheck()) {
			if (motoObj.getNntxStbh() != null) {
				moto.setNntxStbh(motoObj.getNntxStbh());
			}
			if (motoObj.getNntxSoNguoi() != null) {
				moto.setNntxSoNguoi(motoObj.getNntxSoNguoi());
			}
			if (motoObj.getTndsTnNntxPhi() != null) {
				moto.setNntxPhi(motoObj.getTndsTnNntxPhi());
			}
		}
		// chay no
		if (motoObj.getChaynoStbh() != null && motoObj.getChaynoStbh() > 0) {
			moto.setChaynoCheck(true);
		} else {
			moto.setChaynoCheck(false);
		}
		if (moto.getChaynoCheck()) {
			if (motoObj.getChaynoStbh() != null && motoObj.getChaynoStbh() > 0) {
				moto.setChaynoStbh(motoObj.getChaynoStbh());
			}
			if (motoObj.getChaynoPhi() != null && motoObj.getChaynoPhi() > 0) {
				moto.setChaynoPhi(motoObj.getChaynoPhi());
			}
		}
		if (motoObj.getTongPhi() != null && motoObj.getTongPhi() > 0) {
			moto.setTongPhi(motoObj.getTongPhi());
		}
		
		// Tái tục thì insert
		if (taituc.equals("1")) {
			// Get current agency
			AgencyDTO currentAgency = getCurrentAccount();
			
			// Call service
			ProductMotoVM data = productMotoService.createOrUpdatePolicy(moto, currentAgency);
		}
			
		return moto;
	}

	private ProductTncVM convertTncToVM(AgreementSearchDTO result, String taituc) throws URISyntaxException, AgencyBusinessException{
		log.debug("Request to convertTncToVM, AgreementSearchDTO{}, taituc{} : ", result, taituc);
		ProductTncVM tnc = new ProductTncVM();
		// convert
		AgreementDTO dto = result.getObjAgreement();
		PaDTO tncObj = (PaDTO) result.getObjProduct();
		
		List<PaAddDTO> listPaAdd = paAddService.getByPaId(dto.getGycbhId());
		if (listPaAdd != null && listPaAdd.size() > 0) {
			List<TncAddDTO> lstTncAdd = new ArrayList<>();
			
			for (PaAddDTO item : listPaAdd) {
				TncAddDTO tncAdd = new TncAddDTO();
				tncAdd.setInsuredName(item.getInsuredName());
				tncAdd.setIdPasswport(item.getIdPasswport());
				tncAdd.setDob(item.getDob());
				tncAdd.setTitle(item.getTitle());
				lstTncAdd.add(tncAdd);
			}
			tnc.setListTncAdd(lstTncAdd);
		}
		
		// productBase
		tnc.setStatusPolicy(dto.getStatusPolicyId());
		tnc.setContactCode(tncObj.getContactCode());
		tnc.setReceiveMethod(dto.getReceiveMethod());
		
		InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
		if(dto.getInvoiceBuyer() != null) {
			invoiceInfo.setName(dto.getInvoiceBuyer());
			invoiceInfo.setCheck("1");
		} else {
			invoiceInfo.setCheck("0");
		}
		if(dto.getInvoiceCompany() != null) {
			invoiceInfo.setCompany(dto.getInvoiceCompany());
		} 
		if(dto.getInvoiceTaxNo() != null) {
			invoiceInfo.setTaxNo(dto.getInvoiceTaxNo());
		} 
		if(dto.getInvoiceAddress() != null) {
			invoiceInfo.setAddress(dto.getInvoiceAddress());
		}
		if(dto.getInvoiceAccountNo() != null) {
			invoiceInfo.setAccountNo(dto.getInvoiceAccountNo());
		} 
		tnc.setInvoiceInfo(invoiceInfo);
		
		ReceiverUserInfoDTO receiverUser = new ReceiverUserInfoDTO();
		receiverUser.setName(dto.getReceiverName());
		receiverUser.setAddress(dto.getReceiverAddress());
		receiverUser.setEmail(dto.getReceiverEmail());
		receiverUser.setMobile(dto.getReceiverMoible());
		tnc.setReceiverUser(receiverUser);
		
		// product
		if (tncObj.getPermanentTotalDisablement() != null && tncObj.getPermanentTotalDisablement() > 0) {
			tnc.setNumberperson(tncObj.getPermanentTotalDisablement());
		}
		if (tncObj.getPermanentPartDisablement() != null && tncObj.getPermanentPartDisablement() > 0) {
			tnc.setNumbermonth(tncObj.getPermanentPartDisablement());
		}
		if (tncObj.getDeath() != null && tncObj.getDeath() > 0) {
			tnc.setPremiumPackage(tncObj.getDeath().longValue());
		}
		if (!StringUtils.isEmpty(tncObj.getPlan())) { 
			tnc.setPremiumPackageplanid(Long.parseLong(tncObj.getPlan()));
		}
		if (tncObj.getTotalBasicPremium() != null && tncObj.getTotalBasicPremium() > 0) {
			tnc.setPremiumnet(tncObj.getTotalBasicPremium());
		}
		if (tncObj.getChangePremiumPremium() != null && tncObj.getChangePremiumPremium() > 0) {
			tnc.setPremiumdiscount(tncObj.getChangePremiumPremium());
		}
		if (tncObj.getTotalPremium() != null && tncObj.getTotalPremium() > 0) {
			tnc.setPremiumtnc(tncObj.getTotalPremium());
		}
		
		if (taituc.equals("0")) {
			tnc.setAgreementId(dto.getAgreementId());
			tnc.setGycbhId(dto.getGycbhId());
			tnc.setGycbhNumber(dto.getGycbhNumber());
			if(dto.getOldGycbhNumber() != null) {
				tnc.setOldGycbhNumber(dto.getOldGycbhNumber());	
			}
			if (tncObj.getInceptionDate() != null) {
				tnc.setInsurancestartdate(tncObj.getInceptionDate());
			}
			if (tncObj.getExpiredDate() != null) {
				tnc.setInsuranceexpireddate(tncObj.getExpiredDate());
			}
		} else {
			// TH tái tục
			tnc.setAgreementId("");
			tnc.setGycbhId("");
			// số Gycbh mới
			CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("TNC");
			tnc.setGycbhNumber(codeManagementDTO.getIssueNumber());
			tnc.setOldGycbhNumber(dto.getGycbhNumber());	
			tnc.setInsurancestartdate(tncObj.getExpiredDate());
			tnc.setInsuranceexpireddate(DateUtils.getCurrentYearPrevious(tncObj.getExpiredDate(), 1));
			
			// Tái tục thì insert
			// Get current agency
			AgencyDTO currentAgency = getCurrentAccount();
			
			// Call service
			HastableTNC data = productTncService.createOrUpdatePolicy(tnc, currentAgency);
		}
		
		return tnc;
	}

	private ProductTvcVM convertTvcToVM(AgreementSearchDTO result, String taituc) throws URISyntaxException, AgencyBusinessException{
		log.debug("Request to convertTvcToVM, AgreementSearchDTO{}, taituc{} : ", result, taituc);
		ProductTvcVM tvc = new ProductTvcVM();
		// convert
		AgreementDTO dto = result.getObjAgreement();
		TravelcareDTO tvcObj = (TravelcareDTO) result.getObjProduct();
		
		// productBase
		tvc.setStatusPolicy(dto.getStatusPolicyId());
		if (dto.getContactId() != null) {
			Contact co = contactRepository.findOne(dto.getContactId());
			tvc.setContactCode(co.getContactCode());
		}
		tvc.setReceiveMethod(dto.getReceiveMethod());
		
		InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
		if (!StringUtils.isEmpty(dto.getInvoiceBuyer())) {
			invoiceInfo.setName(dto.getInvoiceBuyer());
			invoiceInfo.setCheck("1");
		} else {
			invoiceInfo.setCheck("0");
		}
		if (!StringUtils.isEmpty(dto.getInvoiceCompany())) {
			invoiceInfo.setCompany(dto.getInvoiceCompany());
		} 
		if (!StringUtils.isEmpty(dto.getInvoiceTaxNo())) {
			invoiceInfo.setTaxNo(dto.getInvoiceTaxNo());
		} 
		if (!StringUtils.isEmpty(dto.getInvoiceAddress())) {
			invoiceInfo.setAddress(dto.getInvoiceAddress());
		}
		if (!StringUtils.isEmpty(dto.getInvoiceAccountNo())) {
			invoiceInfo.setAccountNo(dto.getInvoiceAccountNo());
		}
		if (!StringUtils.isEmpty(dto.getUrlPolicy())) {
			tvc.setUrlPolicy(dto.getUrlPolicy());
		}
		tvc.setInvoiceInfo(invoiceInfo);
		
		ReceiverUserInfoDTO receiverUser = new ReceiverUserInfoDTO();
		receiverUser.setName(dto.getReceiverName());
		receiverUser.setAddress(dto.getReceiverAddress());
		receiverUser.setEmail(dto.getReceiverEmail());
		receiverUser.setMobile(dto.getReceiverMoible());
		tvc.setReceiverUser(receiverUser);
		
		// product
		if (dto.getAgentId().equals("vietjet")) {
			List<TvcPlaneAdd> lstTviPlane = tvcPlaneAddRepository.findByTvcPlaneId(dto.getGycbhId());
			if (lstTviPlane != null && lstTviPlane.size() > 0) {
				List<TvcAddBaseVM> lstTvcAdd = new ArrayList<>();
				for (TvcPlaneAdd item : lstTviPlane) {
					TvcAddBaseVM tvcAdd = new TvcAddBaseVM();
					tvcAdd.setInsuredName(item.getInsuredName());
					tvcAdd.setDob(DateUtils.date2Str(item.getDob()));
					tvcAdd.setIdPasswport(item.getIdPasswport());
					tvcAdd.setRelationship("00");
					lstTvcAdd.add(tvcAdd);
				}
				tvc.setListTvcAddBaseVM(lstTvcAdd);	
			}
			
			TvcPlane entity = tvcPlaneRepository.findOne(result.getObjAgreement().getGycbhId());
			if (entity.getNetPremium() != null) {
				tvc.setNetPremium(entity.getNetPremium());
			}
			if (entity.getTotalPremium() != null) {
				tvc.setPremium(entity.getTotalPremium());
			}
			
		} else {
			List<TravelCareAddDTO> lstAdd = travelCareAddService.getByTravaelcareId(dto.getGycbhId());
			if (lstAdd != null && lstAdd.size() > 0) {
				tvc.setSoNguoiThamGia(lstAdd.size());		
				List<TvcAddBaseVM> lstTvcAdd = new ArrayList<>();
				for (TravelCareAddDTO item : lstAdd) {
					TvcAddBaseVM tvcAdd = new TvcAddBaseVM();
					tvcAdd.setInsuredName(item.getInsuredName());
					tvcAdd.setIdPasswport(item.getIdPasswport());
					tvcAdd.setDob(DateUtils.date2Str(item.getDob()));
					tvcAdd.setRelationship(item.getRelationship());
					lstTvcAdd.add(tvcAdd);
				}
				tvc.setListTvcAddBaseVM(lstTvcAdd);
			}
			if (tvcObj.getPremium() != null) {
				tvc.setPremium(tvcObj.getPremium());
			}
			if (tvcObj.getNetPremium() != null) {
				tvc.setNetPremium(tvcObj.getNetPremium());
			}
		}
		if (!StringUtils.isEmpty(tvcObj.getTravelWithId())) {
			tvc.setTravelWithId(tvcObj.getTravelWithId());
		}
		if (!StringUtils.isEmpty(tvcObj.getPlanId())) {
			tvc.setPlanId(tvcObj.getPlanId());
		}
		if (!StringUtils.isEmpty(tvcObj.getDestinationId())) {
			tvc.setDestinationId(tvcObj.getDestinationId());
		}
		if (!StringUtils.isEmpty(tvcObj.getPropserName())) {
			tvc.setPropserName(tvcObj.getPropserName());
		}
		if (!StringUtils.isEmpty(tvcObj.getPropserCellphone())) {
			tvc.setPropserCellphone(tvcObj.getPropserCellphone());
		}
		if (tvcObj.getPropserNgaysinh() != null) {
			tvc.setPropserNgaysinh(DateUtils.date2Str(tvcObj.getPropserNgaysinh()));
		}
		if (!StringUtils.isEmpty(tvcObj.getPaymentMethod())) {
			tvc.setPaymentMethod(tvcObj.getPaymentMethod());
		}
		if (tvcObj.getChangePremium() != null) {
			tvc.setChangePremium(tvcObj.getChangePremium());
		}
		if (!StringUtils.isEmpty(tvcObj.getBankId())) {
			tvc.setLoaitien(tvcObj.getBankId());
		}
		if (!StringUtils.isEmpty(tvcObj.getDestinationDetail())) {
			tvc.setDestinationDetail(tvcObj.getDestinationDetail());
		}
		// tvcPackage để = travelWithId
		if (!StringUtils.isEmpty(tvcObj.getTravelWithId())) {
			tvc.setTvcPackage(tvcObj.getTravelWithId());
		}
		
		if (taituc.equals("0")) {
			tvc.setAgreementId(dto.getAgreementId());
			tvc.setGycbhId(dto.getGycbhId());
			tvc.setGycbhNumber(dto.getGycbhNumber());
			if(dto.getOldGycbhNumber() != null) {
				tvc.setOldGycbhNumber(dto.getOldGycbhNumber());	
			}
			if (!StringUtils.isEmpty(tvcObj.getPolicyNumber())) {
				tvc.setPolicyNumber(tvcObj.getPolicyNumber());
			}
			if (tvcObj.getInceptionDate() != null) {
				tvc.setInceptionDate(DateUtils.date2Str(dto.getInceptionDate()));
			}
			if (tvcObj.getExpiredDate() != null) {
				tvc.setExpiredDate(DateUtils.date2Str(dto.getExpiredDate()));
			}
		} else {
			// TH tái tục
			tvc.setAgreementId("");
			tvc.setGycbhId("");
			// số Gycbh mới
			CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("TVC");
			tvc.setGycbhNumber(codeManagementDTO.getIssueNumber());
			tvc.setPolicyNumber(codeManagementDTO.getIssueNumber());
			tvc.setOldGycbhNumber(dto.getGycbhNumber());	
			
			Date inceptionDate = tvcObj.getInceptionDate();
			Date expiredDate = tvcObj.getExpiredDate();
			int sn = DateUtils.getNumberDaysBetween2Date(inceptionDate, expiredDate);
			int songay = sn + 1;
			tvc.setInceptionDate(DateUtils.date2Str(tvcObj.getExpiredDate()));
			tvc.setExpiredDate(DateUtils.date2Str(DateUtils.addDay(tvcObj.getExpiredDate(), songay)));
			
			// Tái tục thì insert
			// Get current agency
			AgencyDTO currentAgency = getCurrentAccount();
			
			// Call service
			ProductTvcVM data = productTvcService.createOrUpdatePolicy(tvc, currentAgency);
		}
		
		return tvc;
	}

	private ProductTviVM convertTviToVM(AgreementSearchDTO result, String taituc) throws URISyntaxException, AgencyBusinessException{
		log.debug("Request to convertTviToVM, AgreementSearchDTO{}, taituc{} : ", result, taituc);
		ProductTviVM tvi = new ProductTviVM();
		// convert
		AgreementDTO dto = result.getObjAgreement();
		TvicareDTO tviObj = (TvicareDTO) result.getObjProduct();
		
		// productBase
		tvi.setStatusPolicy(dto.getStatusPolicyId());
		if (dto.getContactId() != null) {
			Contact co = contactRepository.findOne(dto.getContactId());
			tvi.setContactCode(co.getContactCode());
		}
		tvi.setReceiveMethod(dto.getReceiveMethod());
		
		InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
		if (!StringUtils.isEmpty(dto.getInvoiceBuyer())) {
			invoiceInfo.setName(dto.getInvoiceBuyer());
			invoiceInfo.setCheck("1");
		} else {
			invoiceInfo.setCheck("0");
		}
		if (!StringUtils.isEmpty(dto.getInvoiceCompany())) {
			invoiceInfo.setCompany(dto.getInvoiceCompany());
		} 
		if (!StringUtils.isEmpty(dto.getInvoiceTaxNo())) {
			invoiceInfo.setTaxNo(dto.getInvoiceTaxNo());
		} 
		if (!StringUtils.isEmpty(dto.getInvoiceAddress())) {
			invoiceInfo.setAddress(dto.getInvoiceAddress());
		}
		if (!StringUtils.isEmpty(dto.getInvoiceAccountNo())) {
			invoiceInfo.setAccountNo(dto.getInvoiceAccountNo());
		} 
		tvi.setInvoiceInfo(invoiceInfo);
		
		ReceiverUserInfoDTO receiverUser = new ReceiverUserInfoDTO();
		receiverUser.setName(dto.getReceiverName());
		receiverUser.setAddress(dto.getReceiverAddress());
		receiverUser.setEmail(dto.getReceiverEmail());
		receiverUser.setMobile(dto.getReceiverMoible());
		tvi.setReceiverUser(receiverUser);
		
		// product
		if (dto.getAgentId().equals("vietjet")) {
			TvcPlane entity = tvcPlaneRepository.findOne(result.getObjAgreement().getGycbhId());
			if (entity.getNetPremium() != null) {
				tvi.setNetPremium(entity.getNetPremium());
			}
			if (entity.getTotalPremium() != null) {
				tvi.setPremium(entity.getTotalPremium());
			}
			
			List<TvcPlaneAdd> lstTviPlane = tvcPlaneAddRepository.findByTvcPlaneId(dto.getGycbhId());
			if (lstTviPlane != null && lstTviPlane.size() > 0) {
				List<TviCareAddDTO> lstTvi = new ArrayList<>();
				for (TvcPlaneAdd item : lstTviPlane) {
					TviCareAddDTO tviAdd = new TviCareAddDTO();
					tviAdd.setInsuredName(item.getInsuredName());
					tviAdd.setDob(item.getDob());
					tviAdd.setIdPasswport(item.getIdPasswport());
					tviAdd.setRelationshipId("00");
					tviAdd.setRelationship("00");
					lstTvi.add(tviAdd);
				}
				tvi.setListTviAdd(lstTvi);	
			}
		} else {
			List<TviCareAddDTO> lstTvi = tviCareAddService.getByTvicareId(dto.getGycbhId());
			if(lstTvi != null && lstTvi.size() > 0) {
				tvi.setListTviAdd(lstTvi);
			}
			if (tviObj.getNetPremium() != null) {
				tvi.setNetPremium(tviObj.getNetPremium());
			}
			if (tviObj.getPremium() != null) {
				tvi.setPremium(tviObj.getPremium());
			}
		}
		if (!StringUtils.isEmpty(tviObj.getDestinationId())) {
			tvi.setDestinationId(tviObj.getDestinationId());
		}
		if (!StringUtils.isEmpty(tviObj.getTravelWithId())) {
			tvi.setTravelWithId(tviObj.getTravelWithId());
		}
		if (!StringUtils.isEmpty(tviObj.getPlanId())) {
			tvi.setPlanId(tviObj.getPlanId());
		}
		if (!StringUtils.isEmpty(tviObj.getDepartureId())) {
			tvi.setDepartureId(tviObj.getDepartureId());
		}
		if (!StringUtils.isEmpty(tviObj.getBankId())) {
			tvi.setBankId(tviObj.getBankId());
		}
		if(tviObj.getDateOfPayment() != null) {
			tvi.setDateOfPayment(DateUtils.date2Str(tviObj.getDateOfPayment()));	
		}
		if (!StringUtils.isEmpty(tviObj.getPaymentMethod())) {
			tvi.setPaymentMethod(tviObj.getPaymentMethod());
		}
		if (!StringUtils.isEmpty(tviObj.getTeamId())) {
			tvi.setTeamId(tviObj.getTeamId());
		}
		if (tviObj.getFeeReceive() != null) {
			tvi.setFeeReceive(tviObj.getFeeReceive());
		}
		if (tviObj.getChangePremium() != null) {
			tvi.setChangePremium(tviObj.getChangePremium());
		}
		
		if (taituc.equals("0")) {
			tvi.setAgreementId(dto.getAgreementId());
			tvi.setGycbhId(dto.getGycbhId());
			tvi.setGycbhNumber(dto.getGycbhNumber());
			if (!StringUtils.isEmpty(tviObj.getSoGycbh())) {
				tvi.setSoGycbh(tviObj.getSoGycbh());
			}
			if(dto.getOldGycbhNumber() != null) {
				tvi.setOldGycbhNumber(dto.getOldGycbhNumber());	
			}
			if (tviObj.getInceptionDate() != null) {
				tvi.setInceptionDate(DateUtils.date2Str(dto.getInceptionDate()));
			}
			if (tviObj.getExpiredDate() != null) {
				tvi.setExpiredDate(DateUtils.date2Str(dto.getExpiredDate()));
			}
			if (!StringUtils.isEmpty(tviObj.getPolicyNumber())) {
				tvi.setPolicyNumber(tviObj.getPolicyNumber());
			}
			if (!StringUtils.isEmpty(tviObj.getStatusPolicyId())) {
				tvi.setStatusPolicyId(tviObj.getStatusPolicyId());
			}	
		} else {
			// TH tái tục
			tvi.setAgreementId("");
			tvi.setGycbhId("");
			// số Gycbh mới
			CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("TVI");
			tvi.setGycbhNumber(codeManagementDTO.getIssueNumber());
			tvi.setPolicyNumber(codeManagementDTO.getIssueNumber());
			tvi.setOldGycbhNumber(dto.getGycbhNumber());
			
			Date inceptionDate = tviObj.getInceptionDate();
			Date expiredDate = tviObj.getExpiredDate();
			int sn = DateUtils.getNumberDaysBetween2Date(inceptionDate, expiredDate);
			int songay = sn + 1;
			tvi.setInceptionDate(DateUtils.date2Str(tviObj.getExpiredDate()));
			tvi.setExpiredDate(DateUtils.date2Str(DateUtils.addDay(tviObj.getExpiredDate(), songay)));
			tvi.setStatusPolicyId("90");
			
			// Tái tục thì insert
			// Get current agency
			AgencyDTO currentAgency = getCurrentAccount();
			
			// Call service
			ProductTviVM data = productTviService.createOrUpdatePolicy(tvi, currentAgency);
		}
		
		return tvi;
	}

	private ProductHhvcVM convertHHVToVM(AgreementSearchDTO result, String taituc) throws URISyntaxException, AgencyBusinessException{
		log.debug("Request to convertHHVToVM, AgreementSearchDTO{}, taituc{} : ", result, taituc);
		ProductHhvcVM hhvc = new ProductHhvcVM();
		// convert
		AgreementDTO dto = result.getObjAgreement();
		GoodsDTO hhvcObj = (GoodsDTO) result.getObjProduct();
		hhvc.setStatusPolicy(dto.getStatusPolicyId());		
		//TODO: Chưa làm để lại
		return hhvc;
	}


}
