package com.baoviet.agency.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.baoviet.agency.bean.AgreementNophiDTO;
import com.baoviet.agency.bean.DashboardDTO;
import com.baoviet.agency.bean.FileContentDTO;
import com.baoviet.agency.bean.QueryResultDTO;
import com.baoviet.agency.domain.AdminUserBu;
import com.baoviet.agency.domain.AgencyRelation;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.AgreementHis;
import com.baoviet.agency.domain.Anchi;
import com.baoviet.agency.domain.Attachment;
import com.baoviet.agency.domain.Bvp;
import com.baoviet.agency.domain.Car;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.Kcare;
import com.baoviet.agency.domain.Moto;
import com.baoviet.agency.domain.MvAgentAgreement;
import com.baoviet.agency.domain.MvClaOutletLocation;
import com.baoviet.agency.domain.TlAdd;
import com.baoviet.agency.domain.TravelCareAdd;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.AgreementNoPhiDTO;
import com.baoviet.agency.dto.AgreementStatusDTO;
import com.baoviet.agency.dto.AnchiDTO;
import com.baoviet.agency.dto.AttachmentDTO;
import com.baoviet.agency.dto.BvpDTO;
import com.baoviet.agency.dto.CarDTO;
import com.baoviet.agency.dto.ConversationDTO;
import com.baoviet.agency.dto.GoodsDTO;
import com.baoviet.agency.dto.HomeDTO;
import com.baoviet.agency.dto.KcareDTO;
import com.baoviet.agency.dto.MotoDTO;
import com.baoviet.agency.dto.OrderHistoryDTO;
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
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AdminUserBuRepository;
import com.baoviet.agency.repository.AgreementRepository;
import com.baoviet.agency.repository.AnchiRepository;
import com.baoviet.agency.repository.AttachmentRepository;
import com.baoviet.agency.repository.BVPRepository;
import com.baoviet.agency.repository.CarRepository;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.ConversationRepository;
import com.baoviet.agency.repository.KcareRepository;
import com.baoviet.agency.repository.MotoRepository;
import com.baoviet.agency.repository.MvAgentAgreementRepository;
import com.baoviet.agency.repository.MvClaOutletLocationRepository;
import com.baoviet.agency.repository.TlAddRepository;
import com.baoviet.agency.repository.TravelCareAddRepository;
import com.baoviet.agency.service.AgencyRelationService;
import com.baoviet.agency.service.AgreementNoPhiService;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.AnchiService;
import com.baoviet.agency.service.AttachmentService;
import com.baoviet.agency.service.BVPService;
import com.baoviet.agency.service.CarService;
import com.baoviet.agency.service.CodeManagementService;
import com.baoviet.agency.service.ConversationService;
import com.baoviet.agency.service.GoodsService;
import com.baoviet.agency.service.HomeService;
import com.baoviet.agency.service.KcareService;
import com.baoviet.agency.service.MotoService;
import com.baoviet.agency.service.PaAddService;
import com.baoviet.agency.service.PaService;
import com.baoviet.agency.service.TinhtrangSkService;
import com.baoviet.agency.service.TlService;
import com.baoviet.agency.service.TravelcareService;
import com.baoviet.agency.service.TviCareAddService;
import com.baoviet.agency.service.TvicareService;
import com.baoviet.agency.service.mapper.AgreementMapper;
import com.baoviet.agency.service.mapper.TlAddMapper;
import com.baoviet.agency.service.mapper.TravelCareAddMapper;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.AgreementAnchiVM;
import com.baoviet.agency.web.rest.vm.AgreementNoPhiVM;
import com.baoviet.agency.web.rest.vm.AgreementYcbhOfflineVM;
import com.baoviet.agency.web.rest.vm.HastableTNC;
import com.baoviet.agency.web.rest.vm.OrderInfoItemVM;
import com.baoviet.agency.web.rest.vm.OrderInfoVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementWaitVM;

import sun.misc.BASE64Encoder;

/**
 * Service Implementation for managing Agreement.
 * 
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class AgreementServiceImpl extends AbstractProductService implements AgreementService {

	private final Logger log = LoggerFactory.getLogger(AgreementServiceImpl.class);

	@Autowired
	private AgreementRepository agreementRepository;

	@Autowired
	private TlAddRepository tlAddRepository;

	@Autowired
	private TinhtrangSkService tinhtrangSkService;

	@Autowired
	private HomeService homeService;

	@Autowired
	private KcareService kcareService;

	@Autowired
	private AgreementMapper agreementMapper;

	@Autowired
	private TlAddMapper tlAddMapper;

	@Autowired
	private MotoService motoService;
	@Autowired
	private CarService carService;
	@Autowired
	private BVPService bVPService;
	@Autowired
	private PaService paService;
	@Autowired
	private PaAddService paAddService;

	@Autowired
	private TlService tlService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private TvicareService tvicareService;

	@Autowired
	private TviCareAddService tviCareAddService;

	@Autowired
	private TravelcareService travelcareService;

	@Autowired
	private TravelCareAddRepository travelCareAddRepository;

	@Autowired
	private TravelCareAddMapper travelCareAddMapper;

	@Autowired
	private AgencyRelationService agencyRelationService;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private ConversationService conversationService;

	@Autowired
	private AnchiService anchiService;
	
	@Autowired
	private AnchiRepository anchiRepository;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private AgreementNoPhiService agreementNoPhiService;

	@Autowired
	private ConversationRepository conversationRepository;
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Autowired
	private CodeManagementService codeManagementService;
	
	@Autowired
	private MotoRepository motoRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private BVPRepository bvpRepository;
	
	@Autowired
	private KcareRepository kcareRepository;
	
	@Autowired
	private MvClaOutletLocationRepository mvClaOutletLocationRepository;
	
	@Autowired
	private MvAgentAgreementRepository mvAgentAgreementRepository;
	
	@Autowired
	private AdminUserBuRepository adminUserBuRepository;
	
	@Override
	public AgreementDTO save(AgreementDTO agreementDTO) {
		log.debug("Request to save agreement, {}", agreementDTO);
		// Convert to Entity
		Agreement entity = agreementMapper.toEntity(agreementDTO);
		Agreement result = agreementRepository.save(entity);
		return agreementMapper.toDto(result);
	}

	@Override
	public AgreementDTO findById(String agreementId) {
		log.debug("Request to find agreement by id, {}", agreementId);
		return agreementMapper.toDto(agreementRepository.findOne(agreementId));
	}

	@Override
	public Page<AgreementDTO> findAllByContactId(String contactId, String type, Pageable pageable) {
		log.debug("Request to find all agreement by contactId, {}", contactId);
		// return
		// agreementMapper.toDto(agreementRepository.findByContactIdAndAgentIdOrderByAgreementIdDesc(contactId,
		// type, pageable));
		return agreementRepository.findByContactIdAndAgentIdOrderByAgreementIdDesc(contactId, type, pageable)
				.map(agreementMapper::toDto);
	}
	
	@Override
	public List<OrderHistoryDTO> getOrderHistoryByGycbhNumber(List<AgreementHis> lstAgreementHis) {
		log.debug("Request to find getOrderHistoryByGycbhNumber, List<AgreementHis>{}, ", lstAgreementHis);
		return agreementRepository.getOrderHistoryByGycbhNumber(lstAgreementHis);
	}
	
	@Override
	public void delete(String agreementId) {
		log.debug("Request to delete agreement by agreementId, {}", agreementId);
		agreementRepository.delete(agreementId);
	}

	@Override
	public boolean updateAgreementHome(AgreementDTO agreementDTO, HomeDTO homeDTO) {
		log.debug("Request to updateAgreementHome, agreementDTO: {}, homeDTO: {}", agreementDTO, homeDTO);
		try {
			if (!agreementDTO.getStatusPolicyId().equals("92")) {
				agreementDTO.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
				agreementDTO.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
			}
			// Save agreement
			save(agreementDTO);

			// Save home
			homeService.save(homeDTO);
		} catch (Exception e) {
			log.debug("updateAgreementHome error: " + e.getMessage(), e);
			return false;
		}

		return true;
	}

	@Override
	public boolean updateAgreementCar(AgreementDTO agreementDTO, CarDTO carDTO) {
		log.debug("Request to updateAgreementCar, agreementDTO: {}, carDTO: {}", agreementDTO, carDTO);
		try {
			if (!agreementDTO.getStatusPolicyId().equals("92")) {
				agreementDTO.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
				agreementDTO.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
			}
			// Save agreement
			save(agreementDTO);

			// Save home
			carService.InsertCar(carDTO);
		} catch (Exception e) {
			log.debug("updateAgreementCar error: " + e.getMessage(), e);
			return false;
		}

		return true;
	}

	@Override
	public boolean updateAgreementKcare(AgreementDTO agreementDTO, KcareDTO kcareDTO,
			List<TinhtrangSkDTO> lstTinhtrangSKs) {
		log.debug("Request to updateAgreementKcare, agreementDTO: {}, kcareDTO: {}, lstTinhtrangSKs: {}", agreementDTO,
				kcareDTO, lstTinhtrangSKs);
		try {
			if (!agreementDTO.getStatusPolicyId().equals("92")) {
				agreementDTO.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
				agreementDTO.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
			}
			// Save agreement
			save(agreementDTO);

			// Save home
			kcareService.save(kcareDTO);

			for (TinhtrangSkDTO tinhtrangSkDTO : lstTinhtrangSKs) {
				tinhtrangSkDTO.setIdThamchieu(kcareDTO.getKId());
				tinhtrangSkDTO.setMasanpham("KCR");
				tinhtrangSkService.save(tinhtrangSkDTO);
			}
		} catch (Exception e) {
			log.debug("updateAgreementKcare error: " + e.getMessage(), e);
			return false;
		}

		return true;
	}

	@Override
	public boolean updateAgreementMoto(AgreementDTO agreementDTO, MotoDTO motoDTO) {
		log.debug("Request to updateAgreementMoto, agreementDTO: {}, motoDTO: {}", agreementDTO, motoDTO);
		try {
			if (!agreementDTO.getStatusPolicyId().equals("92")) {
				agreementDTO.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
				agreementDTO.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
			}
			// Save agreement
			save(agreementDTO);

			// Save home
			motoService.save(motoDTO);
		} catch (Exception e) {
			log.debug("updateAgreementMoto error: " + e.getMessage(), e);
			return false;
		}

		return true;
	}

	@Override
	public boolean updateAgreementKhc(AgreementDTO agreementDTO, TlDTO tlDTO, List<TlAddDTO> lstTlAdd) {
		log.debug("Request to updateAgreementKhc, agreementDTO: {}, tlDTO: {}, lstTlAdd: {}", agreementDTO, tlDTO,
				lstTlAdd);
		try {
			if (!agreementDTO.getStatusPolicyId().equals("92")) {
				agreementDTO.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
				agreementDTO.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
			}
			Agreement agreement = agreementRepository.findOne(agreementDTO.getGycbhId());

			if (agreement == null) {
				throw new AgencyBusinessException(agreementDTO.getGycbhId(), ErrorCode.INVALID,
						"Không tồn tại dữ liệu");
			}

			agreementDTO.setAgreementId(agreement.getAgreementId());

			// Save agreement
			save(agreementDTO);

			// Save Tl
			tlService.save(tlDTO);

			List<TlAdd> lstTlAddOld = new ArrayList<TlAdd>();
			lstTlAddOld = tlAddRepository.findByTlId(tlDTO.getTlId());

			for (TlAdd tlAdd : lstTlAddOld) {
				tlAddRepository.delete(tlAdd.getTlAddId());
			}

			for (TlAddDTO tlAddDto : lstTlAdd) {
				TlAdd entity = tlAddMapper.toEntity(tlAddDto);
				tlAddRepository.save(entity);
			}

		} catch (Exception e) {
			log.debug("updateAgreementKhc error: " + e.getMessage(), e);
			return false;
		}

		return true;
	}

	@Override
	public boolean updateAgreementBVP(AgreementDTO agreementDTO, BvpDTO bvpDTO) {
		log.debug("Request to updateAgreementBVP, agreementDTO: {}, bvpDTO: {}", agreementDTO, bvpDTO);
		try {
			if (!agreementDTO.getStatusPolicyId().equals("92")) {
				agreementDTO.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
				agreementDTO.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
			}
			// Save agreement
			save(agreementDTO);

			// Save home
			bVPService.Insert(bvpDTO);
		} catch (Exception e) {
			log.debug("updateAgreementBVP error: " + e.getMessage(), e);
			return false;
		}

		return true;
	}

	@Override
	public boolean updateAgreementTNC(HastableTNC has) {
		log.debug("Request to updateAgreementTNC, has: {}", has);
		try {
			AgreementDTO agreementDTO = has.getAgreement();
			PaDTO paDto = has.getPa();
			List<PaAddDTO> lstAdd = has.getLstPaAdd();

			if (!agreementDTO.getStatusPolicyId().equals("92")) {
				agreementDTO.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
				agreementDTO.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
			}
			// Save agreement
			save(agreementDTO);
			// Save PA
			String Id = paService.Insert(paDto);
			// Save PaAdd
			for (PaAddDTO paAddDTO : lstAdd) {
				paAddService.Delete(paAddDTO.getPaAddId());
			}
			for (PaAddDTO paAddDTO : lstAdd) {
				paAddDTO.setPaId(Id);
				paAddService.Insert(paAddDTO);
			}
		} catch (Exception e) {
			log.debug("updateAgreementTNC error: " + e.getMessage(), e);
			return false;
		}

		return true;
	}

	@Override
	public boolean updateAgreementHHVC(AgreementDTO agreementDTO, GoodsDTO goodsDTO) {
		log.debug("Request to updateAgreementHHVC, agreementDTO: {}, goodsDTO: {}", agreementDTO, goodsDTO);
		try {
			if (!agreementDTO.getStatusPolicyId().equals("92")) {
				agreementDTO.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
				agreementDTO.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
			}
			// Save agreement
			agreementDTO.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_HUYDON);
			agreementDTO.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_HUYDON);
			save(agreementDTO);

			// Save home
			goodsService.save(goodsDTO);
		} catch (Exception e) {
			log.debug("updateAgreementHHVC error: " + e.getMessage(), e);
			return false;
		}

		return true;
	}

	@Override
	public boolean updateAgreementTvicare(AgreementDTO agreementDTO, TvicareDTO tvicare,
			List<TviCareAddDTO> lstTviCareAdd) {
		log.debug("Request to updateAgreementTvicare, agreementDTO: {}, tvicare: {}, lstTviCareAdd: {}", agreementDTO,
				tvicare, lstTviCareAdd);
		try {
			if (!agreementDTO.getStatusPolicyId().equals("92")) {
				agreementDTO.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
				agreementDTO.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
			}
			// Save agreement
			save(agreementDTO);

			// Save tvicare
			tvicareService.save(tvicare);

			for (TviCareAddDTO tviCareAddDTO : lstTviCareAdd) {
				tviCareAddDTO.setTviCareId(tvicare.getTvicareId());
				tviCareAddService.save(tviCareAddDTO);
			}
		} catch (Exception e) {
			log.debug("updateAgreementTvicare error: " + e.getMessage(), e);
			return false;
		}

		return true;
	}

	@Override
	public boolean updateAgreementTvcTravelCare(AgreementDTO agreementDTO, TravelcareDTO travelcare,
			List<TravelCareAddDTO> lstTravelCareAdd) {
		log.debug("Request to updateAgreementTvcTravelCare, agreementDTO: {}, travelcare: {}, lstTravelCareAdd: {}",
				agreementDTO, travelcare, lstTravelCareAdd);
		try {
			if (!agreementDTO.getStatusPolicyId().equals("92")) {
				agreementDTO.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
				agreementDTO.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
			}
			Agreement agreement = agreementRepository.findOne(agreementDTO.getGycbhId());

			if (agreement == null) {
				throw new AgencyBusinessException(agreementDTO.getGycbhId(), ErrorCode.INVALID,
						"Không tồn tại dữ liệu");
			}

			agreementDTO.setAgreementId(agreement.getAgreementId());

			// Save agreement
			save(agreementDTO);

			// Save travelcare
			travelcareService.save(travelcare);

			List<TravelCareAdd> lstTlAddOld = new ArrayList<TravelCareAdd>();
			lstTlAddOld = travelCareAddRepository.findByTravaelcareId(travelcare.getTravelcareId());

			// Xóa travelcare add cũ
			for (TravelCareAdd item : lstTlAddOld) {
				travelCareAddRepository.delete(item.getTvcAddId());
			}

			// Thêm lại danh sách vào database
			for (TravelCareAddDTO item : lstTravelCareAdd) {
				TravelCareAdd entity = travelCareAddMapper.toEntity(item);
				travelCareAddRepository.save(entity);
			}

		} catch (Exception e) {
			log.debug("updateAgreementTvcTravelCare error: " + e.getMessage(), e);
			return false;
		}

		return true;
	}

	@Override
	public List<AgreementStatusDTO> getOrderInfo(OrderInfoVM param) {
		log.debug("Request to getOrderInfo OrderInfoVM: {}", param);
		List<AgreementStatusDTO> dataAgreementStatusDTO = new ArrayList<>();
		String strListGycbh = "";
		StringBuilder sb = new StringBuilder();
		for (String item : param.getListGYCBH()) {
			sb.append("'").append(item).append("',");
		}
		strListGycbh = sb.toString().substring(0, sb.length() - 1);
		List<Agreement> data = agreementRepository.getByGycbhNumber(strListGycbh, "2");
		if (data != null) {
			for (Agreement item : data) {
				AgreementStatusDTO ag = new AgreementStatusDTO();
				ag.setAgreementid(item.getAgreementId());
				ag.setStatusPolicyId(item.getStatusPolicyId());
				ag.setStatusPolicyName(item.getStatusPolicyName());
				ag.setGycbhNumber(item.getGycbhNumber());
				ag.setGycbhId(item.getGycbhId());
				ag.setStatusRenewalsId1(item.getStatusRenewalsId1());
				ag.setResponseDate(DateUtils.date2Str(item.getResponseDate()));
				dataAgreementStatusDTO.add(ag);
			}
		}
		return dataAgreementStatusDTO;
	}

	@Override
	public AgreementStatusDTO getOrderInfoItem(OrderInfoItemVM param) {
		log.debug("Request to getOrderInfo OrderInfoVM: {}", param);

		List<Agreement> data = agreementRepository.getByGycbhNumber(param.getGycbh(), "1");
		AgreementStatusDTO ag = new AgreementStatusDTO();
		if (data != null) {
			Agreement item = data.get(0);

			ag.setAgreementid(item.getAgreementId());
			ag.setStatusPolicyId(item.getStatusPolicyId());
			ag.setStatusPolicyName(item.getStatusPolicyName());
			ag.setGycbhNumber(item.getGycbhNumber());
			ag.setGycbhId(item.getGycbhId());
			ag.setStatusRenewalsId1(item.getStatusRenewalsId1());
			ag.setResponseDate(DateUtils.date2Str(item.getResponseDate()));
		}
		return ag;
	}

	@Override
	public List<Agreement> findAllByAgentId(String agentId) {
		List<Agreement> data = agreementRepository.findByAgentIdOrderByAgreementIdDesc(agentId);
		return data;
	}

	@Override
	public AgreementDTO findById(String agreementId, String type) {
		log.debug("Request to find agreement by id, type {} {}", agreementId, type);
		return agreementMapper.toDto(agreementRepository.findByAgreementIdAndAgentId(agreementId, type));
	}

	@Override
	public List<Agreement> findAllByAgreementIds(List<String> agreementIds) {
		List<Agreement> data = agreementRepository.findAll(agreementIds);
		return data;
	}

	@Override
	public Page<AgreementDTO> findAllByAgentIdAndStatus(String agentId, List<String> status, Pageable pageable) {
		// List<AgreementDTO> data =
		// agreementMapper.toDto(agreementRepository.findByAgentIdAndStatusPolicyIdInOrderByAgreementIdDesc(agentId,
		// status, pageable));
		// return data;
		return agreementRepository.findByAgentIdAndStatusPolicyIdInOrderByAgreementIdDesc(agentId, status, pageable)
				.map(agreementMapper::toDto);
	}
	
	@Override
	public Page<AgreementDTO> searchCart(String agentId, List<String> status, Pageable pageable) {
		return agreementRepository.searchCart(agentId, status, pageable)
				.map(agreementMapper::toDto);
	}
	
	@Override
	public Page<AgreementDTO> getWaitAgency(String agentId, Pageable pageable) {
		return agreementRepository.getWaitAgency(agentId, pageable).map(agreementMapper::toDto);
	}
	
	@Override
	public Page<AgreementDTO> getWaitAgencyAdmin(String agentId, Pageable pageable) {
		return agreementRepository.getWaitAgencyAdmin(agentId, pageable).map(agreementMapper::toDto);
	}
	
	@Override
	public Page<AgreementDTO> getWaitAgreement(String agentId, Pageable pageable) {
		return agreementRepository.getWaitAgreement(agentId, pageable).map(agreementMapper::toDto);
	}
	
	@Override
	public Page<AgreementDTO> getWaitAgreementAdmin(String adminId, Pageable pageable) {
		return agreementRepository.getWaitAgreementAdmin(adminId, pageable).map(agreementMapper::toDto);
	}

	@Override
	public AgreementDTO findByGycbhNumberAndAgentId(String gycbhNumber, String agentId) {
		Agreement data = agreementRepository.findByGycbhNumberAndAgentId(gycbhNumber, agentId);
		return agreementMapper.toDto(data);
	}
	
	@Override
	public AgreementDTO findByGycbhNumberAndAgentIdAndOTP(String gycbhNumber, String agentId, String otp) {
		Agreement data = agreementRepository.findByGycbhNumberAndAgentIdAndOtp(gycbhNumber, agentId, otp);
		return agreementMapper.toDto(data);
	}

	@Override
	public Page<AgreementDTO> search(SearchAgreementVM obj, String type) {
		Page<AgreementDTO> page = agreementRepository.search(obj, type).map(agreementMapper::toDto);

		// Calulate isCanTaituc
		calculateIsCanTaituc(page.getContent());
		return page;
	}
	
	@Override
	public Page<AgreementDTO> searchAdmin(SearchAgreementWaitVM obj, String departmentId) {
		Page<AgreementDTO> page = agreementRepository.searchAdmin(obj, departmentId).map(agreementMapper::toDto);
		return page;
	}
	
	@Override
	public Page<AgreementDTO> searchCartAdmin(SearchAgreementWaitVM obj, String departmentId) {
		Page<AgreementDTO> page = agreementRepository.searchCartAdmin(obj, departmentId).map(agreementMapper::toDto);
		return page;
	}
	
	@Override
	public Page<AgreementDTO> searchOrderBVWait(SearchAgreementWaitVM obj, String departmentId) {
		Page<AgreementDTO> page = agreementRepository.searchAdminBVWait(obj, departmentId).map(agreementMapper::toDto);
		return page;
	}
	
	@Override
	public Page<AgreementDTO> searchOrderTransport(SearchAgreementWaitVM obj, String departmentId) {
		Page<AgreementDTO> page = agreementRepository.searchOrderTransport(obj, departmentId).map(agreementMapper::toDto);
		return page;
	}
	
	@Override
	public List<AgreementDTO> updateOrderTransport(OrderInfoVM obj, String departmentId) throws AgencyBusinessException {
		if (obj.getListGYCBH() != null && obj.getListGYCBH().size() > 0) {
			List<AgreementDTO> result = new ArrayList<>();
			// lấy phòng ban
			List<AdminUserBu> lstadminUserBu = adminUserBuRepository.findByAdminId(departmentId);
    		if (lstadminUserBu != null && lstadminUserBu.size() > 0) {
    			for (AdminUserBu item : lstadminUserBu) {
    				for (String gycbh : obj.getListGYCBH()) {
    					Agreement data = agreementRepository.findByGycbhNumberAndBaovietDepartmentId(gycbh, item.getBuId());
    					if (data == null) {
    						throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không tồn tại đơn hàng " + gycbh);
    					}
    					// update
    					data.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_HOANTHANH);
    					data.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_HOANTHANH);
    					Agreement dataSave = agreementRepository.save(data);
    					result.add(agreementMapper.toDto(dataSave));
    				}
    			}
    		}
			
			if (result != null && result.size() > 0) {
				return result;	
			}
		}
		return null;
	}
	
	@Override
	public AgreementDTO findByGycbhNumberAndDepartmentId(String gycbhNumber, String departmentId) {
		Agreement data = agreementRepository.findByGycbhNumberAndBaovietDepartmentId(gycbhNumber, departmentId);
		return agreementMapper.toDto(data);
	}
	
	@Override
	public Page<AgreementDTO> searchOrderWait(SearchAgreementWaitVM obj, String type, String caseWait) {
		Page<AgreementDTO> page = agreementRepository.searchOrderWait(obj, type, caseWait).map(agreementMapper::toDto);
		return page;
	}
	
	@Override
	public Page<AgreementDTO> searchCart(SearchAgreementWaitVM obj, String type) {
		Page<AgreementDTO> page = agreementRepository.searchCart(obj, type).map(agreementMapper::toDto);
		return page;
	}
	
	@Override
	public Page<AgreementNophiDTO> searchNophi(SearchAgreementVM obj, String type) {
		Page<AgreementNophiDTO> page = agreementRepository.searchNophi(obj, type);
		return page;
	}


	@Override
	public AgreementAnchiVM createOrUpdateYcbhAnchi(AgreementAnchiVM obj, AgencyDTO currentAgency)
			throws AgencyBusinessException {
		log.debug("Request to createOrUpdateYcbhAnchi, AgreementAnchiVM {}, currentAgency {}", obj, currentAgency);
		Contact co = contactRepository.findOneByContactCodeAndType(obj.getContactCode(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Mã khách hàng không tồn tại");
		}

		// 1. Save into Anchi table
		AnchiDTO anchi = getAnchi(obj, currentAgency, co);

		// 2. Save agreement
		AgreementDTO agrement = getAgrementAnchi(obj, currentAgency, co);
		
		anchi.setMciAddId(agrement.getMciAddId());
		String anchiId = anchiService.save(anchi);
		agrement.setGycbhId(anchiId);
		AgreementDTO agrementSave = save(agrement);
		obj.setAgreementId(agrementSave.getAgreementId());
		
		// 3. save file
		// xóa trước khi update file
		if (!StringUtils.isEmpty(obj.getAgreementId())) {
			attachmentRepository.deleteByParrentId(anchiId);
		}
		if (obj.getImgGcns() != null && obj.getImgGcns().size() > 0) {
			saveFileContent(obj.getImgGcns(), currentAgency, anchiId, "ACGCN");
		}
		
		if (obj.getImgGycbhs() != null && obj.getImgGycbhs().size() > 0) {
			saveFileContent(obj.getImgGycbhs(), currentAgency, anchiId, "ACDOC");
		}

		// pay_action
		sendSmsAndSavePayActionInfo(co, agrementSave);
		
		// 3. Call service
//		anchiService.wsUpdateAnChi(obj);	//07.08.2018 comment

		return obj;
	}
	

	@Override
	public DashboardDTO getDashboardInfo(SearchAgreementVM param, String type) {
		log.debug("Request to getDashboardInfo, SearchAgreementVM {}, type {}", param, type);
		DashboardDTO result = new DashboardDTO();

		// Total
		QueryResultDTO total = count(param, type);

		// Total policyStatus = 91
		List<String> lstStatus = new ArrayList<>();
		lstStatus.add("91");

		param.setLstStatusPolicy(lstStatus);
		QueryResultDTO payment = count(param, type);

		// Total
		lstStatus.clear();
		lstStatus.add("90");

		param.setLstStatusPolicy(lstStatus);
		QueryResultDTO notPayment = count(param, type);

		result.setTotalOrder(total.getCount());
		result.setNumberOrderPaid(payment.getCount());
		result.setNumberOrderNotPaid(notPayment.getCount());

		if (total.getPremium() < 0) {
			result.setTotalPremmium(0);
		} else {
			result.setTotalPremmium(total.getPremium());			
		}
		result.setPremiumPaid(payment.getPremium());
		if (notPayment.getPremium() < 0) {
			result.setPremiumNotPaid(0);
		} else {
			result.setPremiumNotPaid(notPayment.getPremium());	
		}

		return result;
	}
	
	@Override
	public DashboardDTO getDashboardInfoAdmin(SearchAgreementVM param, String adminId) {
		log.debug("Request to getDashboardInfo, SearchAgreementVM {}, adminId {}", param, adminId);
		DashboardDTO result = new DashboardDTO();

		// Total
		QueryResultDTO total = countAdmin(param, adminId);

		// Total policyStatus = 91
		List<String> lstStatus = new ArrayList<>();
		lstStatus.add("91");

		param.setLstStatusPolicy(lstStatus);
		QueryResultDTO payment = countAdmin(param, adminId);

		// Total
		lstStatus.clear();
		lstStatus.add("90");

		param.setLstStatusPolicy(lstStatus);
		QueryResultDTO notPayment = countAdmin(param, adminId);

		result.setTotalOrder(total.getCount());
		result.setNumberOrderPaid(payment.getCount());
		result.setNumberOrderNotPaid(notPayment.getCount());

		if (total.getPremium() < 0) {
			result.setTotalPremmium(0);
		} else {
			result.setTotalPremmium(total.getPremium());			
		}
		result.setPremiumPaid(payment.getPremium());
		if (notPayment.getPremium() < 0) {
			result.setPremiumNotPaid(0);
		} else {
			result.setPremiumNotPaid(notPayment.getPremium());	
		}

		return result;
	}

	@Override
	public QueryResultDTO count(SearchAgreementVM obj, String type) {
		log.debug("Request to count QueryResultDTO, SearchAgreementVM {}, type {}", obj, type);
		return agreementRepository.count(obj, type);
	}
	
	@Override
	public QueryResultDTO countAdmin(SearchAgreementVM obj, String adminId) {
		log.debug("Request to count QueryResultDTO, SearchAgreementVM {}, adminId {}", obj, adminId);
		return agreementRepository.countAdmin(obj, adminId);
	}

	@Override
	public AgreementNoPhiVM createOrUpdateNoPhi(AgreementNoPhiVM obj, AgencyDTO currentAgency)
			throws AgencyBusinessException {
		log.debug("Request to createOrUpdateNoPhi, AgreementNoPhiVM {}, currentAgency {}", obj, currentAgency);
		Contact co = contactRepository.findByContactIdAndType(obj.getContactId(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactId", ErrorCode.INVALID, "Mã khách hàng không tồn tại");
		}

		AgreementNoPhiDTO agreementNoPhi = new AgreementNoPhiDTO();
		if (!StringUtils.isEmpty(obj.getId())) {
			agreementNoPhi.setId(obj.getId()); // update
		}
		agreementNoPhi.setAgreementId(obj.getAgreementId());
		agreementNoPhi.setContactId(obj.getContactId());
		agreementNoPhi.setNote(obj.getNote());
		agreementNoPhi.setCreateDate(new Date());
		agreementNoPhi.setSotien(obj.getSotien());

		agreementNoPhiService.save(agreementNoPhi);
		obj.setResult(true);
		return obj;
	}
	
	@Override
	public AgreementAnchiVM getAnchiByGycbhNumber(AgreementDTO obj, AgencyDTO currentAgency)
			throws AgencyBusinessException {
		log.debug("Request to getAnchiByGycbhNumber, AgreementDTO {}, currentAgency {}", obj, currentAgency);

		if (!StringUtils.isEmpty(obj.getGycbhId())) {
			Anchi anchi = anchiRepository.findOne(obj.getGycbhId());
			if (anchi != null) {
				AgreementAnchiVM anchiVM = new AgreementAnchiVM();
				
				if (!StringUtils.isEmpty(anchi.getAnchiId())) {
					anchiVM.setAnchiId(anchi.getAnchiId());
				}
				if (!StringUtils.isEmpty(anchi.getAchiSoAnchi())) {
					anchiVM.setSoAnchi(anchi.getAchiSoAnchi());
				}
				if (!StringUtils.isEmpty(anchi.getLineId())) {
					anchiVM.setMaSanPham(anchi.getLineId());
				}
				if (!StringUtils.isEmpty(anchi.getAchiTenAnchi())) {
					anchiVM.setTenAnchi(anchi.getAchiTenAnchi());
				}
				if (!StringUtils.isEmpty(anchi.getContactId())) {
					Contact co = contactRepository.findOne(anchi.getContactId());
					if (co != null && StringUtils.isNotEmpty(co.getContactCode())) {
						anchiVM.setContactCode(co.getContactCode());
					}
				}
				if (anchi.getAchiTungay() != null) {
					anchiVM.setNgayHieulucTu(DateUtils.date2Str(anchi.getAchiTungay()));
				}
				if (anchi.getAchiDenngay() != null) {
					anchiVM.setNgayHieulucDen(DateUtils.date2Str(anchi.getAchiDenngay()));
				}
				if (!StringUtils.isEmpty(anchi.getAchiTinhtrangCap())) {
					anchiVM.setTinhTrangCap(anchi.getAchiTinhtrangCap());
				}
				if (anchi.getAchiNgaycap() != null) {
					anchiVM.setNgayCap(DateUtils.date2Str(anchi.getAchiNgaycap()));
				}
				anchiVM.setPhiBaoHiem(anchi.getAchiPhibh());
				anchiVM.setTongTienTT(anchi.getAchiStienvn());
				anchiVM.setGycbhNumber(obj.getGycbhNumber());
				anchiVM.setAgreementId(obj.getAgreementId());
				anchiVM.setDepartmentId(obj.getBaovietDepartmentId());
				
				BASE64Encoder encoder = new BASE64Encoder();
				List<Attachment> att = attachmentService.getByParrentId(obj.getGycbhId());
				List<FileContentDTO> imgGcns = new ArrayList<>();
				List<FileContentDTO> imgGycbhs = new ArrayList<>();
				
				for (Attachment item : att) {
					FileContentDTO file = new FileContentDTO();
					if (StringUtils.equals(item.getAttachmentName(), "ACGCN")) {
						String imageString = encoder.encode(item.getContent());
						file.setContent(imageString);
						imgGcns.add(file);
					}
					if (StringUtils.equals(item.getAttachmentName(), "ACDOC")) {
						String imageString = encoder.encode(item.getContent());
						file.setContent(imageString);
						imgGycbhs.add(file);
					}
				}
				anchiVM.setImgGcns(imgGcns);
				anchiVM.setImgGycbhs(imgGycbhs);
				return anchiVM;
			}
		}
		
		return null;
	}
	
	@Override
	public AgreementYcbhOfflineVM createOrUpdateYcbhOffline(AgreementYcbhOfflineVM obj, AgencyDTO currentAgency)
			throws AgencyBusinessException {

		Contact co = contactRepository.findOneByContactCodeAndType(obj.getContactCode(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Mã khách hàng không tồn tại");
		}

//		AgencyRelation agencyRelation = agencyRelationService.getById(currentAgency.getMaDonVi());
//		if (agencyRelation == null) {
//			throw new AgencyBusinessException(currentAgency.getMaDonVi(), ErrorCode.INVALID, "Mã đơn vị không tồn tại");
//		}

		AgreementDTO agreement = new AgreementDTO();
		if (co.getContactName() != null) {
			agreement.setContactName(co.getContactName().toUpperCase());
		}
		agreement.setContactId(co.getContactId());
		agreement.setContactPhone(co.getPhone());

		// TODO: không có gycbhNumber sẽ upload từ file cần hỏi lại
		// call service
		// CodeManagementDTO codeManagementDTO =
		// codeManagementService.getCodeManagement(obj.getMaSanPham());
		// if (null == codeManagementDTO) {
		// throw new AgencyBusinessException(obj.getMaSanPham(),
		// ErrorCode.GET_GYCBH_NUMBER_ERROR , "Hệ thống chưa cấp được mã sản phẩm mới.
		// Vui lòng thử lại sau.");
		// }
		agreement.setTotalPremium((double)obj.getTotalPremium());
		// update thì ko cập nhật gycbhNumber
		if (StringUtils.isEmpty(obj.getAgreementId())) {
			agreement.setGycbhNumber(obj.getGycbhNumber());
			agreement.setPolicyNumber(obj.getGycbhNumber());			
		} else {
			AgreementDTO data = agreementService.findById(obj.getAgreementId());
			if (data != null) {
				agreement.setGycbhNumber(data.getGycbhNumber());
				agreement.setPolicyNumber(data.getGycbhNumber());
				agreement.setAgreementId(obj.getAgreementId());
			}
		}
		
		agreement.setUserId(currentAgency.getId());
		agreement.setUserName(currentAgency.getEmail());
		if (!StringUtils.isEmpty(co.getContactName())) {
			agreement.setReceiverName(co.getContactName());	
		}
		if (!StringUtils.isEmpty(co.getPhone())) {
			agreement.setReceiverMoible(co.getPhone());	
		}
		if (!StringUtils.isEmpty(obj.getMaSanPham())) {
			agreement.setLineId(obj.getMaSanPham());
		}
		if (!StringUtils.isEmpty(obj.getNgayHieulucTu())) {
			agreement.setInceptionDate(DateUtils.str2Date(obj.getNgayHieulucTu()));
		}
		if (!StringUtils.isEmpty(obj.getNgayHieulucDen())) {
			agreement.setExpiredDate(DateUtils.str2Date(obj.getNgayHieulucDen()));
		}
		
		// phong ban
		if (StringUtils.isEmpty(obj.getDepartmentId())) {
			throw new AgencyBusinessException("departmentId", ErrorCode.INVALID, "Cần lựa chọn phòng ban");
		}
		
		List<MvClaOutletLocation> lstmvClaOutletLocation = mvClaOutletLocationRepository.findByOutletAmsIdAndPrOutletAmsId(currentAgency.getMa(), obj.getDepartmentId());
		List<MvAgentAgreement> listMvAgentAgreement = mvAgentAgreementRepository.findByAgentCodeAndDepartmentCode(currentAgency.getMa(), obj.getDepartmentId());
		if(lstmvClaOutletLocation == null && listMvAgentAgreement == null) {
			throw new AgencyBusinessException("departmentId", ErrorCode.INVALID, "Không tồn tại Id phòng ban: " + obj.getDepartmentId());
		} else {
			if (lstmvClaOutletLocation != null && lstmvClaOutletLocation.size() > 0) {
				agreement.setBaovietDepartmentId(obj.getDepartmentId());
				agreement.setBaovietDepartmentName(lstmvClaOutletLocation.get(0).getPrOutletName());
			} else if (listMvAgentAgreement != null && listMvAgentAgreement.size() > 0) {
				agreement.setBaovietDepartmentId(obj.getDepartmentId());
				agreement.setBaovietDepartmentName(listMvAgentAgreement.get(0).getDepartmentName());	
			} else {
			}
		}
		
//		if (!StringUtils.isEmpty(agencyRelation.getFs4())) {
//			agreement.setBaovietCompanyId(agencyRelation.getFs4());
//		}
//		if (!StringUtils.isEmpty(agencyRelation.getFs5())) {
//			agreement.setBaovietCompanyName(agencyRelation.getFs5()); // luau cong ty cua bao viet
//		}
//		if (!StringUtils.isEmpty(agencyRelation.getId())) {
//			agreement.setBankId(agencyRelation.getId());
//		}
//		if (!StringUtils.isEmpty(agencyRelation.getName())) {
//			agreement.setBankName(agencyRelation.getName());
//		}
		agreement.setAgentId(currentAgency.getMa());
		agreement.setAgentName(currentAgency.getTen());

		Date dateNow = new Date();
		agreement.setSendDate(dateNow);
		agreement.setResponseDate(dateNow);
		agreement.setDateOfRequirement(dateNow);
		agreement.setCreateType(1);

		// Luu phong ban cua cua sale
//		if (!StringUtils.isEmpty(agencyRelation.getId())) {
//			agreement.setAgencyP1Id(agencyRelation.getId());
//		}
//		if (!StringUtils.isEmpty(agencyRelation.getName())) {
//			agreement.setAgencyP1Name(agencyRelation.getName());
//		}
		agreement.setAgencyP6Id("agency");
		
		// Kiểm tra xem lưu tạm hay gửi BV giám định
		if (StringUtils.equals(obj.getStatusPolicy(), AppConstants.STATUS_POLICY_ID_DANGSOAN)) {
			agreement.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_DANGSOAN);
			agreement.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_DANGSOAN);
			agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_DANGSOAN);
			agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_DANGSOAN);	
		} else {
//			agreement.setStatusGycbhId("50");	// 16.10.2018 THEO code .Net thì là 2 dòng dưới
//			agreement.setStatusGycbhName("Đã gửi GYCBH");
			agreement.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_CHO_BV_GIAMDINH);	// 16.10.2018 THEO tài liệu BA thì
			agreement.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_CHO_BV_GIAMDINH);
			agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_BV_GIAMDINH);
			agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_BV_GIAMDINH);	
		}

		// Luu chi nhanh cua sale
//		if (agencyRelation.getParrenetId() != null) {
//			AgencyRelation archinhanh = agencyRelationService.getById(agencyRelation.getParrenetId());
//
//			if (archinhanh == null) {
//				throw new AgencyBusinessException(agencyRelation.getParrenetId(), ErrorCode.INVALID,
//						"ParrenetId chi nhánh không tồn tại");
//			}
//			if (!StringUtils.isEmpty(archinhanh.getId())) {
//				agreement.setAgencyP2Id(archinhanh.getId());
//			}
//			if (!StringUtils.isEmpty(archinhanh.getName())) {
//				agreement.setAgencyP2Name(archinhanh.getName());
//			}
//
//			AgencyRelation arhoiso = agencyRelationService.getById(archinhanh.getParrenetId());
//			if (arhoiso == null) {
//				throw new AgencyBusinessException(agencyRelation.getParrenetId(), ErrorCode.INVALID,
//						"ParrenetId hội sở không tồn tại");
//			}
//			if (!StringUtils.isEmpty(arhoiso.getId())) {
//				agreement.setAgencyP3Id(arhoiso.getId());
//			}
//			if (!StringUtils.isEmpty(arhoiso.getName())) {
//				agreement.setAgencyP3Name(arhoiso.getName());
//			}
//		}

		// Lưu Agrement
		Agreement result = agreementRepository.save(agreementMapper.toEntity(agreement));
		log.debug("Request to save agreement, AgreementId{}", result.getAgreementId());
		// CONVERSATION
		// xóa trước khi update
		if (!StringUtils.isEmpty(obj.getAgreementId())) {
			List<ConversationDTO> lstconversation = conversationService.getByParrentId(obj.getAgreementId());
			String conversationId = lstconversation.get(0).getConversationId();
			attachmentRepository.deleteByParrentId(conversationId);
			conversationRepository.deleteByParrentId(obj.getAgreementId());
		}
		ConversationDTO conversation = new ConversationDTO();
		conversation.setUserId(currentAgency.getId());
		if (!StringUtils.isEmpty(currentAgency.getEmail())) {
			conversation.setUserName(currentAgency.getEmail());
		}
		conversation.setParrentId(result.getAgreementId());
		conversation.setSendDate(dateNow);
		conversation.setResponseDate(dateNow);
		if (!StringUtils.isEmpty(co.getContactName())) {
			conversation.setConversationContent("Yêu cầu cấp đơn Bảo hiểm cho khách hàng " + co.getContactName());
		} else {
			conversation.setConversationContent("Yêu cầu cấp đơn Bảo hiểm cho khách hàng ");
		}
		if (!StringUtils.isEmpty(obj.getMaSanPham())) {
			conversation.setLineId(obj.getMaSanPham().toUpperCase());
		}
		conversation.setRole("agency");
		String conversationId = conversationService.insertConversation(conversation);

		// Lưu file
		// data content của gycbh
		if (obj.getImgGycbhContents() != null && obj.getImgGycbhContents().size() > 0) {
			saveFileContent(obj.getImgGycbhContents(), currentAgency, conversationId, "GYCBH");
		}
		// data content của giấy khai sinh
		if (obj.getImgKhaisinhContents() != null && obj.getImgKhaisinhContents().size() > 0) {
			saveFileContent(obj.getImgKhaisinhContents(), currentAgency, conversationId, "GKS");
		}
		// data content của tài liệu khác
		if (obj.getImgDocumentContents() != null && obj.getImgDocumentContents().size() > 0) {
			saveFileContent(obj.getImgDocumentContents(), currentAgency, conversationId, "DOC");
		}
		
		obj.setAgreementId(result.getAgreementId());
		return obj;
	}
	
	@Override
	public AgreementYcbhOfflineVM getYcbhOfflineByGycbhNumber(AgreementDTO obj, AgencyDTO currentAgency)
			throws AgencyBusinessException {
		log.debug("Request to getYcbhOfflineByGycbhNumber, AgreementDTO {}, currentAgency {}", obj, currentAgency);
		
		Contact co = contactRepository.findOne(obj.getContactId());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Mã khách hàng không tồn tại");
		}
		
		AgreementYcbhOfflineVM offline = new AgreementYcbhOfflineVM();
		if (!StringUtils.isEmpty(obj.getLineId())) {
			offline.setMaSanPham(obj.getLineId());
		}
		offline.setAgreementId(obj.getAgreementId());
		offline.setContactCode(co.getContactCode());
		offline.setGycbhNumber(obj.getGycbhNumber());
		offline.setTotalPremium(obj.getTotalPremium().longValue());
		offline.setDepartmentId(obj.getBaovietDepartmentId());
		if (obj.getInceptionDate() != null) {
			offline.setNgayHieulucTu(DateUtils.date2Str(obj.getInceptionDate()));	
		}
		if (obj.getExpiredDate() != null) {
			offline.setNgayHieulucDen(DateUtils.date2Str(obj.getExpiredDate()));	
		}
		
		List<ConversationDTO> lstconversation = conversationService.getByParrentId(obj.getAgreementId());
		if (lstconversation != null && lstconversation.size() > 0) {
			BASE64Encoder encoder = new BASE64Encoder();
			List<Attachment> att = attachmentService.getByParrentId(lstconversation.get(0).getConversationId());
			List<FileContentDTO> gycbhContents = new ArrayList<>();
			List<FileContentDTO> imgKhaisinhContents = new ArrayList<>();
			List<FileContentDTO> documentContents = new ArrayList<>();
			
			for (Attachment item : att) {
				FileContentDTO file = new FileContentDTO();
				if (StringUtils.equals(item.getAttachmentName(), "GYCBH")) {
					String imageString = encoder.encode(item.getContent());
					file.setContent(imageString);
					gycbhContents.add(file);
				}
				if (StringUtils.equals(item.getAttachmentName(), "GKS")) {
					String imageString = encoder.encode(item.getContent());
					file.setContent(imageString);
					imgKhaisinhContents.add(file);
				}
				if (StringUtils.equals(item.getAttachmentName(), "DOC")) {
					String imageString = encoder.encode(item.getContent());
					file.setContent(imageString);
					documentContents.add(file);
				}
			}
			offline.setImgGycbhContents(gycbhContents);
			offline.setImgDocumentContents(documentContents);
			offline.setImgKhaisinhContents(imgKhaisinhContents);
		}
		
		return offline;
	}
	
	@Override
	public AgreementDTO updateOTP(AgreementDTO data) throws AgencyBusinessException {
		log.debug("Request to updateOTP, AgreementDTO {}", data);

		data.setOtpStatus(AppConstants.STATUS_OTP_1);	// Đã sử dụng
		
		// TH cần giám định thì update policy
		if (StringUtils.equals(data.getLineId(), "MOT")) {
			// kiểm tra xem đơn hàng có trong TH cần giám định không
			Moto moto = motoRepository.findOne(data.getGycbhId());
			if (moto == null) {
				throw new AgencyBusinessException("gycbhId", ErrorCode.INVALID, "Không tồn tại đơn hàng xe máy có mã " + data.getGycbhId());
			}
			if (moto.getChaynoPhi() != null && moto.getChaynoStbh() != null && moto.getChaynoPhi() > 0 && moto.getChaynoStbh() > 0) {
				insertStatusPolicy(data, "1");	
			} else {
				insertStatusPolicy(data, "0");
			}
		} else if (StringUtils.equals(data.getLineId(), "CAR")) {
			Car car = carRepository.findOne(data.getGycbhId());
			if (car == null) {
				throw new AgencyBusinessException("gycbhId", ErrorCode.INVALID, "Không tồn tại đơn hàng xe máy có mã " + data.getGycbhId());
			}
			if (car.getPhysicalDamagePremium() != null && car.getPhysicalDamageSi() != null && car.getPhysicalDamagePremium() > 0 && car.getPhysicalDamageSi() > 0) {
				insertStatusPolicy(data, "1");
			} else {
				insertStatusPolicy(data, "0");
			}
		} else if (StringUtils.equals(data.getLineId(), "BVP")) {
			Bvp bvp = bvpRepository.findOne(data.getGycbhId());
			if (bvp == null) {
				throw new AgencyBusinessException("gycbhId", ErrorCode.INVALID, "Không tồn tại đơn hàng xe máy có mã " + data.getGycbhId());
			}
			if (bvp.getNguoidbhNgaysinh() != null) {
				int tuoi = DateUtils.countYears(bvp.getNguoidbhNgaysinh(), new Date());
				if (tuoi < 18) {
					insertStatusPolicy(data, "1");
				}
			}
			if (StringUtils.equals(bvp.getQ1(), "1") || StringUtils.equals(bvp.getQ2(), "1") || StringUtils.equals(bvp.getQ3(), "1")) {
				insertStatusPolicy(data, "1");
			} else {
				insertStatusPolicy(data, "0");
			}			
		} else if (StringUtils.equals(data.getLineId(), "KCR")) {
			Kcare kcare = kcareRepository.findOne(data.getGycbhId());
			if (kcare == null) {
				throw new AgencyBusinessException("gycbhId", ErrorCode.INVALID, "Không tồn tại đơn hàng xe máy có mã " + data.getGycbhId());
			}
			if (kcare.getInsuredDob() != null) {
				int tuoi = DateUtils.countYears(DateUtils.str2Date(kcare.getInsuredDob()), new Date());
				if (tuoi < 18) {
					insertStatusPolicy(data, "1");
				}
			}
			if (StringUtils.equals(kcare.getQ1(), "1") || StringUtils.equals(kcare.getQ2(), "1") || StringUtils.equals(kcare.getQ3(), "1") || StringUtils.equals(kcare.getQ4(), "1") || StringUtils.equals(kcare.getQ5(), "1")) {
				insertStatusPolicy(data, "1");
			} else {
				insertStatusPolicy(data, "0");
			}			
		} else {
			insertStatusPolicy(data, "0");
		}
		
		AgreementDTO dataUpdate = agreementService.save(data);

		return dataUpdate;
	}

	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	// 0. Chờ thanh toán
	private void insertStatusPolicy (AgreementDTO data, String type) {
		if (type.equals("0")) {
			data.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
			data.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
			data.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
			data.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);	
		} else {
			data.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_BV_GIAMDINH);
			data.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_BV_GIAMDINH);
			data.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_CHO_BV_GIAMDINH);
			data.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_CHO_BV_GIAMDINH);			
		}
	}
	
	private void saveFileContent(List<FileContentDTO> data, AgencyDTO currentAgency, String parrentId, String type) {
		for (FileContentDTO item : data) {
			AttachmentDTO attachmenInfo = new AttachmentDTO();
			attachmenInfo.setAttachmentName(type);
			if (!StringUtils.isEmpty(item.getContent())) {
				attachmenInfo.setContentFile(item.getContent());
			}
			attachmenInfo.setModifyDate(new Date());
			attachmenInfo.setTradeolSysdate(new Date());
			attachmenInfo.setUserId(currentAgency.getId());
			attachmenInfo.setParrentId(parrentId);
			attachmenInfo.setIstransferred(0);
			String attachmentId = attachmentService.save(attachmenInfo);
			log.debug("Request to save Attachment, attachmentId{}", attachmentId);
		}
	}
	
	private AnchiDTO getAnchi(AgreementAnchiVM obj, AgencyDTO currentAgency, Contact co) {
		AnchiDTO anchi = new AnchiDTO();
		if (!StringUtils.isEmpty(obj.getAgreementId())) {
			AgreementDTO data = agreementService.findById(obj.getAgreementId());
			if (data != null) {
				anchi.setAnchiId(data.getGycbhId());
			}
		}
		
		if (!StringUtils.isEmpty(obj.getSoAnchi())) {
			anchi.setAchiSoAnchi(obj.getSoAnchi());
		}
		if (!StringUtils.isEmpty(obj.getMaSanPham())) {
			anchi.setLineId(obj.getMaSanPham());
		}
		if (!StringUtils.isEmpty(obj.getTenAnchi())) {
			anchi.setAchiTenAnchi(obj.getTenAnchi());
		}
		anchi.setContactId(co.getContactId());
		if (DateUtils.isValidDate(obj.getNgayHieulucTu(), "dd/MM/yyyy")) {
			anchi.setAchiTungay(DateUtils.str2Date(obj.getNgayHieulucTu()));
		}
		if (DateUtils.isValidDate(obj.getNgayHieulucDen(), "dd/MM/yyyy")) {
			anchi.setAchiDenngay(DateUtils.str2Date(obj.getNgayHieulucDen()));
		}
		if (!StringUtils.isEmpty(obj.getTinhTrangCap())) {
			anchi.setAchiTinhtrangCap(obj.getTinhTrangCap());
		}
		if (DateUtils.isValidDate(obj.getNgayCap(), "dd/MM/yyyy")) {
			anchi.setAchiNgaycap(DateUtils.str2Date(obj.getNgayCap()));
		}
		anchi.setAchiPhibh(obj.getPhiBaoHiem());
		anchi.setAchiStienvn(obj.getTongTienTT());

//		if (obj.getImgGcn() != null) {
//			anchi.setImgGcnContent(obj.getImgGcn().getContent());
//		}
//		if (obj.getImgGycbh() != null) {
//			anchi.setImgGycbhContent(obj.getImgGycbh().getContent());
//		}
//		if (obj.getImgHd() != null) {
//			anchi.setImgHdContent(obj.getImgHd().getContent());
//		}
		if (!StringUtils.isEmpty(obj.getGycbhNumber())) {
			anchi.setPolicyNumber(obj.getGycbhNumber());
		}
		anchi.setCreateUser(currentAgency.getMa());
		anchi.setAchiDonvi(currentAgency.getMa());
		anchi.setStatus(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
		log.debug("Request to getAnchi, return AnchiDTO {}", anchi);
		return anchi;
	}

	private AgreementDTO getAgrementAnchi(AgreementAnchiVM obj, AgencyDTO currentAgency, Contact contact) throws AgencyBusinessException{
		AgreementDTO agrement = new AgreementDTO();
		
		// update thì ko cập nhật gucbhNumber
		if (StringUtils.isEmpty(obj.getAgreementId())) {
			agrement.setGycbhNumber(obj.getGycbhNumber());
			agrement.setPolicyNumber(obj.getGycbhNumber());			
		} else {
			AgreementDTO data = agreementService.findById(obj.getAgreementId());
			if (data != null) {
				agrement.setGycbhNumber(data.getGycbhNumber());
				agrement.setPolicyNumber(data.getGycbhNumber());
				agrement.setAgreementId(obj.getAgreementId());
			}
		}
		
		insertAgreementAnchi(agrement, contact, currentAgency, obj);
		
		setPolicyStatus(obj.getMaSanPham(), agrement);
		
		agrement.setInceptionDate(DateUtils.str2Date(obj.getNgayHieulucTu()));
		agrement.setExpiredDate(DateUtils.str2Date(obj.getNgayHieulucDen()));
		agrement.setNetPremium(obj.getPhiBaoHiem());
		agrement.setTotalPremium(obj.getTongTienTT());

		agrement.setStandardPremium(obj.getTongTienTT());
		agrement.setChangePremium(0d);
		agrement.setTotalVat(0d);
		log.debug("Request to getAgrementAnchi, return AgreementDTO {}", agrement);
		return agrement;
	}
	
	private void insertAgreementAnchi(AgreementDTO voAg, Contact contact, AgencyDTO currentAgency, AgreementAnchiVM obj) throws AgencyBusinessException{
		log.debug("Request to insertAgreementAnchi : AgreementDTO {}, Contact {}, AgencyDTO {},",  voAg, contact, currentAgency);
		
		voAg.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
		voAg.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		voAg.setCreateType(2);
		
		// Insert invoiceInfo & receiver
		voAg.setReceiverName(contact.getContactName());
		voAg.setReceiverAddress(contact.getHomeAddress());
		voAg.setReceiverEmail(contact.getEmail());
		voAg.setReceiverMoible(contact.getPhone());

		// Insert contact info
		voAg.setContactId(contact.getContactId());
		voAg.setContactName(contact.getContactName());
		voAg.setTaxIdNumber(contact.getIdNumber());
		voAg.setContactAddress(contact.getHomeAddress());
		voAg.setContactPhone(contact.getPhone());
		
		if (contact.getDateOfBirth() != null) {
			voAg.setContactDob(contact.getDateOfBirth());	
		} else {
			voAg.setContactDob(DateUtils.str2Date("01/01/0001"));
		}
		voAg.setContactUsername(contact.getEmail());
		voAg.setContactAddresstt(contact.getHomeAddress());
		
		// Insert extra
		voAg.setAgentId(currentAgency.getMa());
		voAg.setAgentName(currentAgency.getTen());// agency.TEN;
		
		voAg.setAgencyId("");
		voAg.setAgencyName("");
		voAg.setCommision(0.0);
		voAg.setCommisionSupport(0.0);
		voAg.setCancelPolicyPremium(0.0);
		voAg.setCancelPolicyCommision(0.0);
		voAg.setCancelPolicySupport(0.0);
		voAg.setCancelPolicyPremium2(0.0);
		voAg.setCancelPolicyCommision2(0.0);
		voAg.setCancelPolicySupport2(0.0);
		voAg.setCancelPolicyPremium3(0.0);
		voAg.setCancelPolicyCommision3(0.0);
		voAg.setCancelPolicySupport3(0.0);
		voAg.setCouponsCode("");
		voAg.setCouponsValue(0.0);
		voAg.setFeeReceive(0.0);
		voAg.setRenewalsReason("");
        voAg.setRenewalsRate(0.0);
        voAg.setRenewalsPremium(0.0);
        voAg.setRenewalsChoice("");
        voAg.setRenewalsSi(0.0);
        voAg.setRenewalsRate1(0.0);
        voAg.setRenewalsRate2(0.0);
        voAg.setRenewalsPremium1(0.0);
        voAg.setRenewalsPremium2(0.0);
        voAg.setRenewalsDes2("");
        voAg.setRenewalsDes1("");
        voAg.setClaimRate(0.0);
		voAg.setClaimRate1(0.0);
		voAg.setClaimRate2(0.0);
		voAg.setStatusRenewalsId("");
        voAg.setStatusRenewalsName(contact.getContactName());
        voAg.setStatusRenewalsId1("");
        voAg.setStatusRenewalsName1("");
        voAg.setStatusRenewalsId2("");
        voAg.setStatusRenewalsName2("");
		voAg.setOldPolicyStatusId("");
		voAg.setOldPolicyStatusName("");
		voAg.setBaovietCompanyId("");
		voAg.setBaovietCompanyName("");
		
		// phong ban
		if (StringUtils.isEmpty(obj.getDepartmentId())) {
			throw new AgencyBusinessException("departmentId", ErrorCode.INVALID, "Cần lựa chọn phòng ban");
		}
		
		List<MvClaOutletLocation> lstmvClaOutletLocation = mvClaOutletLocationRepository.findByOutletAmsIdAndPrOutletAmsId(currentAgency.getMa(), obj.getDepartmentId());
		List<MvAgentAgreement> listMvAgentAgreement = mvAgentAgreementRepository.findByAgentCodeAndDepartmentCode(currentAgency.getMa(), obj.getDepartmentId());
		if(lstmvClaOutletLocation == null && listMvAgentAgreement == null) {
			throw new AgencyBusinessException("departmentId", ErrorCode.INVALID, "Không tồn tại Id phòng ban: " + obj.getDepartmentId());
		} else {
			if (lstmvClaOutletLocation != null && lstmvClaOutletLocation.size() > 0) {
				voAg.setBaovietDepartmentId(obj.getDepartmentId());
				voAg.setBaovietDepartmentName(lstmvClaOutletLocation.get(0).getPrOutletName());
			} else if (listMvAgentAgreement != null && listMvAgentAgreement.size() > 0) {
				voAg.setBaovietDepartmentId(obj.getDepartmentId());
				voAg.setBaovietDepartmentName(listMvAgentAgreement.get(0).getDepartmentName());	
			} else {
				voAg.setBaovietDepartmentId("");
				voAg.setBaovietDepartmentName("");
			}
		}
		
		voAg.setBaovietDepartmentId("");
		voAg.setBaovietDepartmentName("");
		voAg.setTeamId("");
		voAg.setTeamName("");
		voAg.setBankId("");
		voAg.setBankName("");
		voAg.setUserId("");
		voAg.setUserName("");
		voAg.setOldGycbhId("");
		
		String mciAddId = codeManagementService.getIssueNumber("PAY", "PAY");
		voAg.setMciAddId(mciAddId);
		voAg.setIsPolicy(0);
		voAg.setReasonCancel("");
		Date dateNow = new Date();
		voAg.setSendDate(dateNow);
		voAg.setResponseDate(dateNow);
        voAg.setAgreementSysdate(dateNow);
        voAg.setCancelPolicyDate(dateNow);
        voAg.setDateOfRequirement(dateNow);
        voAg.setDateOfPayment(dateNow);
        voAg.setTypeOfPrint("");
		
		// LOẠI THANH TOÁN
		voAg.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
		voAg.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		voAg.setPaymentMethod("");
		voAg.setReceiveMethod("1");
	}

	private void calculateIsCanTaituc(List<AgreementDTO> data) {
		for (AgreementDTO agreementDTO : data) {
			// Check can taituc
			if (StringUtils.equals(agreementDTO.getStatusPolicyId(), "91")
					|| StringUtils.equals(agreementDTO.getStatusPolicyId(), "100")) {
				if (agreementDTO.getExpiredDate() != null) {
					Date now = new Date();
					int sn = DateUtils.getNumberDaysBetween2Date(now, agreementDTO.getExpiredDate());
					if (0 < sn && sn <= 30) {
						agreementDTO.setCanTaituc(true);
					}
				}
			}
		}
	}

	private void setPolicyStatus(String lineId, AgreementDTO voAg) {
		voAg.setLineId(lineId);
		switch (lineId) {
		case "BVP":
			voAg.setLineName("Bảo Việt An Gia");
			break;
		case "CAR":
			voAg.setLineName("Bảo hiểm ô tô");
			break;
		case "HHV":
			voAg.setLineName("Bảo hiểm Hàng hóa vận chuyện nội địa");
			break;
		case "HOM":
			voAg.setLineName("Bảo hiểm nhà tư nhân");
			break;
		case "KCR":
			voAg.setLineName("Bảo hiểm bệnh ung thư");
			break;
		case "KHC":
			voAg.setLineName("Bảo hiểm kết hợp con người");
			break;
		case "MOT":
			voAg.setLineName("Bảo hiểm xe máy");
			break;
		case "TNC":
			voAg.setLineName("Bảo hiểm tai nạn con người");
			break;
		case "TVI":
			voAg.setLineName("Bảo hiểm du lịch Việt Nam");
			break;
		case "TVC":
			voAg.setLineName("Bảo hiểm du lịch Quốc Tế");
			break;
		default:
			break;
		}
	}
}
