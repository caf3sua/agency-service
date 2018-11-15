package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.bean.DashboardDTO;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.PayActionDTO;
import com.baoviet.agency.dto.eclaim.EclaimDTO;
import com.baoviet.agency.dto.report.ReportDataDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.service.ReportService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.web.rest.vm.ReportSearchCriterialVM;
import com.codahale.metrics.annotation.Timed;

import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency report resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "agency-report")
public class ReportResource extends AbstractAgencyResource{

	private final Logger log = LoggerFactory.getLogger(ReportResource.class);

	private static final String ENTITY_NAME = "agency-report";

	@Autowired
	private ReportService reportService;

	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_REPORT_INCOME_VIEW')")
	@PostMapping("/report-income")
	@ApiOperation(value="reportIncome", notes="Báo cáo Doanh thu")
	@Timed
	public ResponseEntity<ReportDataDTO> reportIncome(@Valid @RequestBody ReportSearchCriterialVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to reportIncome : {}", param);

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		buildDateReport(param);
		ReportDataDTO result = reportService.getBaoCaoDoanhThu(param, currentAgency.getMa());

		// Return data
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_REPORT_COMMISSION_VIEW')")
	@PostMapping("/report-commission")
	@ApiOperation(value="reportCommission", notes="Báo cáo Hoa hồng")
	@Timed
	public ResponseEntity<ReportDataDTO> reportCommission(@Valid @RequestBody ReportSearchCriterialVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to reportCommission : {}", param);

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		buildDateReport(param);
		ReportDataDTO result = reportService.getBaoCaoHoaHong(param, currentAgency.getMa());
		
		// Return data
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/report-dashboard")
	@ApiOperation(value="reportDashboard", notes="Báo cáo cho màn hình Dashboard")
	@Timed
	public ResponseEntity<DashboardDTO> reportDashboard(@Valid @RequestBody ReportSearchCriterialVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to reportDashboard : {}", param);

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		buildDateReport(param);
		DashboardDTO data = reportService.getBaoCaoForDashboard(param, currentAgency.getMa());

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping("/adm-report-dashboard")
	@ApiOperation(value="admReportDashboard", notes="Báo cáo cho màn hình Dashboard Admin")
	@Timed
	public ResponseEntity<DashboardDTO> admReportDashboard(@Valid @RequestBody ReportSearchCriterialVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to reportDashboard : {}", param);

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		buildDateReport(param);
		DashboardDTO data = reportService.getBaoCaoForDashboardAdmin(param, currentAgency.getMa());

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping("/adm-report-income")
	@ApiOperation(value="admReportIncome", notes="Báo cáo Doanh thu Admin")
	@Timed
	public ResponseEntity<ReportDataDTO> admReportIncome(@Valid @RequestBody ReportSearchCriterialVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to reportIncome : {}", param);

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		buildDateReport(param);
		ReportDataDTO result = reportService.getBaoCaoHoaHongAdmin(param, currentAgency.getMa());

		// Return data
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/adm-report-commission")
	@ApiOperation(value="admReportCommission", notes="Báo cáo Hoa hồng Admin")
	@Timed
	public ResponseEntity<ReportDataDTO> admReportCommission(@Valid @RequestBody ReportSearchCriterialVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to admReportCommission : {}", param);

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		buildDateReport(param);
		ReportDataDTO result = reportService.getBaoCaoHoaHongAdmin(param, currentAgency.getMa());
		
		// Return data
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_REPORT_TRANSFER_VIEW')")
	@PostMapping("/report-transfer")
	@ApiOperation(value="reportTransfer", notes="Báo cáo chuyển tiền")
	@Timed
	public ResponseEntity<List<PayActionDTO>> reportTransfer(@Valid @RequestBody ReportSearchCriterialVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to reportTransfer : {}", param);

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		buildDateReport(param);
		List<PayActionDTO> data = reportService.getBaoCaoTransfer(param, currentAgency.getMa());

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_REPORT_TRANSFER_VIEW')")
	@PostMapping("/adm-report-transfer")
	@ApiOperation(value="admReportTransfer", notes="Báo cáo chuyển tiền cho chức năng admin")
	@Timed
	public ResponseEntity<List<PayActionDTO>> admReportTransfer(@Valid @RequestBody ReportSearchCriterialVM param)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to reportTransfer : {}", param);

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		buildDateReport(param);
		List<PayActionDTO> data = reportService.getBaoCaoTransferAdmin(param, currentAgency.getMa());

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_REPORT_TRANSFER_VIEW')")
	@GetMapping("/report-history-purchase/{contactId}")
	@ApiOperation(value="reportHistoryPurchaseOfContact", notes="Báo cáo lịch sử mua hàng của contact")
	@Timed
	public ResponseEntity<List<PayActionDTO>> reportHistoryPurchaseOfContact(@PathVariable String contactId)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to reportHistoryPurchaseOfContact : {}", contactId);

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		List<PayActionDTO> data = reportService.getBaoCaoHistoryPurchase(contactId, currentAgency.getMa());

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// Tổn thất
	@GetMapping("/report-history-loss/{contactId}")
	@ApiOperation(value="reportHistoryLossOfContact", notes="Báo cáo lịch sử tổn thất của contact")
	@Timed
	public ResponseEntity<List<EclaimDTO>> reportHistoryLossOfContact(@PathVariable String contactId)
			throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to reportHistoryLossOfContact : {}", contactId);

		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		List<EclaimDTO> data = reportService.getBaoCaoHistoryLoss(contactId, currentAgency.getMa());

		// Return data
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	/*********************************
	 *     Private method 
	 *********************************/
	private void buildDateReport(ReportSearchCriterialVM param) {
		if (StringUtils.isEmpty(param.getPeriodTime())) {
			return;
		}
		switch (param.getPeriodTime()) {
		case "WEEK":
			param.setFromDate(DateUtils.date2Str(DateUtils.getMondayOfCurrentWeek()));
			param.setToDate(DateUtils.date2Str(DateUtils.getSundayOfCurrentWeek()));
			break;
		case "MONTH":
			param.setFromDate(DateUtils.date2Str(DateUtils.getFirstDateOfCurrentMonth()));
			param.setToDate(DateUtils.date2Str(DateUtils.getLastDateOfCurrentMonth()));
			break;
		case "YEAR":
			param.setFromDate(DateUtils.date2Str(DateUtils.getFirstDateOfCurrentYear()));
			param.setToDate(DateUtils.date2Str(DateUtils.getLastDateOfCurrentYear()));
			break;
		default:
			break;
		}
	}
}
