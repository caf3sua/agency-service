package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.domain.Agency;
import com.baoviet.agency.domain.AgencyRelation;
import com.baoviet.agency.domain.ProductGenInfo;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.AgreementStatusDTO;
import com.baoviet.agency.dto.CodeManagementDTO;
import com.baoviet.agency.dto.ConversationDTO;
import com.baoviet.agency.dto.MenuDTO;
import com.baoviet.agency.dto.RePostcodeVnDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AgencyRepository;
import com.baoviet.agency.repository.MenuRepository;
import com.baoviet.agency.security.SecurityUtils;
import com.baoviet.agency.service.AgencyRelationService;
import com.baoviet.agency.service.AgencyService;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.CodeManagementService;
import com.baoviet.agency.service.ConversationService;
import com.baoviet.agency.service.ProductGenService;
import com.baoviet.agency.service.RePostcodeVnService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.AgencyByParentVM;
import com.baoviet.agency.web.rest.vm.ChiNhanhOrPGDVM;
import com.baoviet.agency.web.rest.vm.ConversationVM;
import com.baoviet.agency.web.rest.vm.OrderInfoItemVM;
import com.baoviet.agency.web.rest.vm.OrderInfoVM;
import com.baoviet.agency.web.rest.vm.ProductInfoVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Gnoc CR resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "common")
public class CommonResource extends AbstractAgencyResource{

	private final Logger log = LoggerFactory.getLogger(CommonResource.class);

	private static final String ENTITY_NAME = "agency";

	@Autowired
	private AgencyService agencyService;

	@Autowired
	private AgencyRepository agencyRepository;

	@Autowired
	private MenuRepository menuRepository;

	@Autowired
	private AgencyRelationService agencyRelationService;

	@Autowired
	private AgreementService agreementService;

	@Autowired
	private ProductGenService productGenService;
	
	@Autowired
	private CodeManagementService codeManagementService;
	
	@Autowired
	private RePostcodeVnService rePostcodeVnService;
	
	@Autowired
	private ConversationService conversationService;
	
	@GetMapping("/getMenu")
	@Timed
	public ResponseEntity<List<MenuDTO>> getMenu() throws URISyntaxException {
		log.debug("REST request to getMenu");

		final String email = SecurityUtils.getCurrentUserLogin();

		Agency existingAgency = agencyRepository.findOneByEmailIgnoreCase(email);
		if (existingAgency == null) {
			return new ResponseEntity<List<MenuDTO>>(HttpStatus.NOT_FOUND);
		}

		// Call service to get menu
		List<MenuDTO> data0 = menuRepository.getRootMenuCollectionAgency(0, existingAgency.getAgencyP6Name(),
				existingAgency.getKenhPhanPhoi());

		List<MenuDTO> data1 = menuRepository.getRootMenuCollectionAgency(1, existingAgency.getAgencyP6Name(),
				existingAgency.getKenhPhanPhoi());

		List<MenuDTO> data = new ArrayList<>();

		if (data0 != null) {
			data.addAll(data0);
		}
		if (data1 != null) {
			data.addAll(data1);
		}
		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getPolicyNumber")
	@Timed
	@ApiOperation(value="getPolicyNumber"
		, notes="Lấy số gycbh mới (chưa có trong hệ thống - phục vụ việc tạo đơn mới). "
				+ "Dữ liệu truyền lên: mã sản phẩm (lineId: TVI, CAR, MOT, TVC, HOM, KHC, KCR, BVP, PAS, HHV, GFI, TNC). "
				+ "Dữ liệu trả về số gycbh nếu chưa tồn tại hoặc thông báo lỗi.")
	public ResponseEntity<String> getPolicyNumber(@RequestParam String lineId) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPolicyNumber, lineId: {}", lineId);

		// call service
		CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement(lineId);
		if (null == codeManagementDTO) {
			throw new AgencyBusinessException("policyNumber", ErrorCode.GET_GYCBH_NUMBER_ERROR , "Hệ thống chưa cấp được GYCBH_NUMBER mới. Vui lòng thử lại sau.");
		}
		
		// Return data
		return new ResponseEntity<>(codeManagementDTO.getIssueNumber(), HttpStatus.OK);
	}

	
	// {
	// "pgdId": "139"
	// }
	@PostMapping("/getListAgencyByPGD")
	@Timed
	public ResponseEntity<List<Agency>> getListAgencyByPGD(@Valid @RequestBody AgencyByParentVM param)
			throws URISyntaxException {
		log.debug("REST request to getListAgencyByPGD : {}", param.getPgdId());

		// Call service
		List<Agency> data = agencyService.getAgencyByParent(param.getPgdId());

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	// {
	// "parentId": "137",
	// "type": "2"
	// }
	@PostMapping("/getListChiNhanhOrPGD")
	@Timed
	public ResponseEntity<List<AgencyRelation>> getListChiNhanhOrPGD(@Valid @RequestBody ChiNhanhOrPGDVM param)
			throws URISyntaxException {
		log.debug("REST request to getListChiNhanhOrPGD : {}", param);

		// Call service
		List<AgencyRelation> data = agencyRelationService.getListChiNhanhOrPGD(param.getParentId(), param.getType());

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	// {
	// "listGYCBH": [
	// "BVGI.CAR.17.213",
	// "BVGI.KHC.18.64"
	// ]
	// }
	// Lấy thông tin trạng thái các đơn hàng, dữ liệu truyền lên là danh sách các
	// GYCBH cần kiểm tra trạng thái
	@PostMapping("/getOrderInfo")
	@Timed
	public ResponseEntity<List<AgreementStatusDTO>> getOrderInfo(@Valid @RequestBody OrderInfoVM param)
			throws URISyntaxException {
		log.debug("REST request to getOrderInfo : {}", param);

		// Call service
		List<AgreementStatusDTO> data = agreementService.getOrderInfo(param);

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	// {
	// "gycbh": "BVGI.KHC.18.64"
	// }
	// Lấy thông tin trạng thái đơn hàng, dữ liệu truyền lên là GYCBH_NUMBER cần
	// kiểm tra trạng thái
	@PostMapping("/getOrderInfoItem")
	@Timed
	public ResponseEntity<AgreementStatusDTO> getOrderInfoItem(@Valid @RequestBody OrderInfoItemVM param)
			throws URISyntaxException {
		log.debug("REST request to getOrderInfo : {}", param);

		// Call service
		AgreementStatusDTO data = agreementService.getOrderInfoItem(param);

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	// {
	// "productCode": "CAR"
	// }
	// Hàm lấy thông tin sản phẩm. ProductType là của CAR là CAR, MOTOR là 40, FLEXI
	// là 41, BH nhà tư nhân là 42, Tai nạn Con người là 60, Kết hợp CN là 61, Bảo
	// hiểm ung thư là 69, Sức khỏe toàn diện là 70. Json kết quả là Object
	// PRODUCT_GEN_INFOCollection
	@PostMapping("/getProductInfo")
	@Timed
	public ResponseEntity<List<ProductGenInfo>> getProductInfo(@Valid @RequestBody ProductInfoVM param)
			throws URISyntaxException {
		log.debug("REST request to getProductInfo : {}", param);

		// Call service
		List<ProductGenInfo> data = productGenService.getProductInfo(param.getProductCode());

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/getAddress")
	@ApiOperation(value="getAddress", notes="Lấy tất cả địa chỉ cả nước")
	@Timed
	public ResponseEntity<List<RePostcodeVnDTO>> getAddress() throws URISyntaxException {
		log.debug("REST request to getAddress");

		List<RePostcodeVnDTO> data = rePostcodeVnService.getAll();
		for (RePostcodeVnDTO item : data) {
			String formatDistrict = item.getPkDistrict() + ", " + item.getPkProvince();
			item.setPkDistrict(formatDistrict);
		}

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/getAddressByCode")
	@ApiOperation(value="getAddressByCode", notes="Lấy tất cả địa chỉ theo mã")
	@Timed
	public ResponseEntity<RePostcodeVnDTO> getAddressByCode(@RequestParam String code) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAddressByCode");

		RePostcodeVnDTO data = rePostcodeVnService.getAddressByCode(code);
		if (data == null) {
			throw new AgencyBusinessException("PkPostcode", ErrorCode.INVALID, "Không có dữ liệu với mã " + code);
		}

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
    @PostMapping("/createCommunication")
    @Timed
    @ApiOperation(value="createCommunication", notes="Tạo hội thoại trao đổi thông tin")
    public ResponseEntity<ConversationDTO> createCommunication(@Valid @RequestBody ConversationVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to createCommunication : {}", param);
		
		// get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		AgreementDTO agreement = agreementService.findByGycbhNumberAndAgentId(param.getGycbhNumber(), currentAgency.getMa());
		if (agreement == null) {
			throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không có dữ liệu với đơn hàng " + param.getGycbhNumber());
		}
		// Call service
		ConversationDTO data = conversationService.save(param, currentAgency, agreement);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
