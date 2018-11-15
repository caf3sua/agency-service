package com.baoviet.agency.service;

import java.net.URISyntaxException;
import java.util.List;

import com.baoviet.agency.bean.DashboardDTO;
import com.baoviet.agency.dto.PayActionDTO;
import com.baoviet.agency.dto.eclaim.EclaimDTO;
import com.baoviet.agency.dto.report.ReportDataDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.web.rest.vm.ReportSearchCriterialVM;

/**
 * Service Interface for managing Report.
 */
public interface ReportService {
	ReportDataDTO getBaoCaoHoaHong(ReportSearchCriterialVM obj, String agentId);
	
	ReportDataDTO getBaoCaoDoanhThu(ReportSearchCriterialVM obj, String agentId);
	
	DashboardDTO getBaoCaoForDashboard(ReportSearchCriterialVM obj, String agentId);
	
	List<PayActionDTO> getBaoCaoTransfer(ReportSearchCriterialVM obj, String agentId);
	
	List<PayActionDTO> getBaoCaoHistoryPurchase(String contactId, String agentId);
	
	List<EclaimDTO> getBaoCaoHistoryLoss(String contactId, String agentId) throws URISyntaxException, AgencyBusinessException;
	
	
	DashboardDTO getBaoCaoForDashboardAdmin(ReportSearchCriterialVM obj, String adminId);
	
	ReportDataDTO getBaoCaoHoaHongAdmin(ReportSearchCriterialVM obj, String adminId);
	
	ReportDataDTO getBaoCaoDoanhThuAdmin(ReportSearchCriterialVM obj, String adminId);
	
	List<PayActionDTO> getBaoCaoTransferAdmin(ReportSearchCriterialVM obj, String agentId);
	
}

