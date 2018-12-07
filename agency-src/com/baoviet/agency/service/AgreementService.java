package com.baoviet.agency.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.baoviet.agency.bean.AgreementNophiDTO;
import com.baoviet.agency.bean.DashboardDTO;
import com.baoviet.agency.bean.QueryResultDTO;
import com.baoviet.agency.domain.Agreement;
import com.baoviet.agency.domain.AgreementHis;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.AgreementStatusDTO;
import com.baoviet.agency.dto.BvpDTO;
import com.baoviet.agency.dto.CarDTO;
import com.baoviet.agency.dto.CountOrderDTO;
import com.baoviet.agency.dto.GoodsDTO;
import com.baoviet.agency.dto.HomeDTO;
import com.baoviet.agency.dto.KcareDTO;
import com.baoviet.agency.dto.MotoDTO;
import com.baoviet.agency.dto.OrderHistoryDTO;
import com.baoviet.agency.dto.TinhtrangSkDTO;
import com.baoviet.agency.dto.TlAddDTO;
import com.baoviet.agency.dto.TlDTO;
import com.baoviet.agency.dto.TravelCareAddDTO;
import com.baoviet.agency.dto.TravelcareDTO;
import com.baoviet.agency.dto.TviCareAddDTO;
import com.baoviet.agency.dto.TvicareDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.AgreementAnchiVM;
import com.baoviet.agency.web.rest.vm.AgreementNoPhiVM;
import com.baoviet.agency.web.rest.vm.AgreementYcbhOfflineVM;
import com.baoviet.agency.web.rest.vm.HastableTNC;
import com.baoviet.agency.web.rest.vm.OrderInfoItemVM;
import com.baoviet.agency.web.rest.vm.OrderInfoVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementVM;
import com.baoviet.agency.web.rest.vm.SearchAgreementWaitVM;


/**
 * Service Interface for managing Contact.
 */
public interface AgreementService {
	
	AgreementDTO save(AgreementDTO agreementDTO);
	
	AgreementDTO findById(String agreementId);
	
	Page<AgreementDTO> findAllByContactId(String contactId, String type, Pageable pageable);
	
	void delete(String agreementId);
	
	boolean updateAgreementHome(AgreementDTO agreementDTO, HomeDTO homeDTO);
	
	boolean updateAgreementCar(AgreementDTO agreementDTO, CarDTO carDTO);
	
	boolean updateAgreementKcare(AgreementDTO agreementDTO, KcareDTO kcareDTO, List<TinhtrangSkDTO> lstTinhtrangSKs);
	
	boolean updateAgreementMoto(AgreementDTO agreementDTO, MotoDTO motoDTO);

	boolean updateAgreementBVP(AgreementDTO agreementDTO, BvpDTO bvpDTO);
	
	boolean updateAgreementTNC(HastableTNC has);
	
	boolean updateAgreementKhc(AgreementDTO agreementDTO, TlDTO tlDTO, List<TlAddDTO> lstTlAdd);
	
	boolean updateAgreementHHVC(AgreementDTO agreementDTO, GoodsDTO goodsDTO);
	
	boolean updateAgreementTvicare(AgreementDTO agreementDTO, TvicareDTO tvicare, List<TviCareAddDTO> lstTviCareAdd);
	
	boolean updateAgreementTvcTravelCare(AgreementDTO agreementDTO, TravelcareDTO travelcare, List<TravelCareAddDTO> lstTravelCareAdd);
	
	List<AgreementStatusDTO> getOrderInfo(OrderInfoVM param);
	
	AgreementStatusDTO getOrderInfoItem(OrderInfoItemVM param);
	
	List<Agreement> findAllByAgentId(String agentId);
	
	List<Agreement> findAllByAgreementIds(List<String> agreementIds);
	
	AgreementDTO findById(String agreementId, String type);
	
	Page<AgreementDTO> findAllByAgentIdAndStatus(String agentId, List<String> status, Pageable pageable);
	
	Page<AgreementDTO> searchCart(String agentId, List<String> status, Pageable pageable);

	AgreementDTO findByGycbhNumberAndAgentId(String gycbhNumber, String agentId);
	
	Page<AgreementDTO> search(SearchAgreementWaitVM obj, String type);
	
	Page<AgreementNophiDTO> searchNophi(SearchAgreementVM obj, String type);
	
	QueryResultDTO count(SearchAgreementVM obj, String type);
	
	AgreementYcbhOfflineVM createOrUpdateYcbhOffline(AgreementYcbhOfflineVM obj, AgencyDTO currentAgency) throws AgencyBusinessException;
	
	AgreementAnchiVM createOrUpdateYcbhAnchi(AgreementAnchiVM obj, AgencyDTO currentAgency) throws AgencyBusinessException;
	
	AgreementAnchiVM getAnchiByGycbhNumber(AgreementDTO obj, AgencyDTO currentAgency) throws AgencyBusinessException;
	
	AgreementYcbhOfflineVM getYcbhOfflineByGycbhNumber(AgreementDTO obj, AgencyDTO currentAgency) throws AgencyBusinessException;
	
	DashboardDTO getDashboardInfo(SearchAgreementVM param, String type);
	
	AgreementNoPhiVM createOrUpdateNoPhi(AgreementNoPhiVM obj, AgencyDTO currentAgency) throws AgencyBusinessException;
	
	AgreementDTO findByGycbhNumberAndAgentIdAndOTP(String gycbhNumber, String agentId, String otp);
	
	Page<AgreementDTO> searchOrderWait(SearchAgreementWaitVM obj, String type, String caseWait); // caseWait:  0: chờ bảo việt, 1: chờ đại lý, 2: Khác
	
	AgreementDTO updateOTP(AgreementDTO data) throws AgencyBusinessException;
	
	Page<AgreementDTO> searchCart(SearchAgreementWaitVM obj, String type);
	
	List<OrderHistoryDTO> getOrderHistoryByGycbhNumber(List<AgreementHis> lstAgreementHis);
	
	Page<AgreementDTO> searchAdmin(SearchAgreementWaitVM obj, String departmentId);
	
	Page<AgreementDTO> searchCartAdmin(SearchAgreementWaitVM obj, String departmentId);
	
	Page<AgreementDTO> searchOrderBVWait(SearchAgreementWaitVM obj, String departmentId);
	
	Page<AgreementDTO> searchOrderTransport(SearchAgreementWaitVM obj, String departmentId);
	
	List<AgreementDTO> updateOrderTransport(OrderInfoVM obj, String departmentId) throws AgencyBusinessException;
	
	AgreementDTO findByGycbhNumberAndDepartmentId(String gycbhNumber, String departmentId);
	
	
	CountOrderDTO getCountAllOrder(String agentId);
	
	CountOrderDTO getAdmCountAllOrder(String departmentId);
	
	DashboardDTO getDashboardInfoAdmin(SearchAgreementVM param, String adminId);
	
	QueryResultDTO countAdmin(SearchAgreementVM obj, String adminId);
	
	Page<AgreementDTO> getWaitAgreementAdmin(String agentId, Pageable pageable);
	
	Page<AgreementDTO> getWaitAgencyAdmin(String agentId, Pageable pageable);
	
}

