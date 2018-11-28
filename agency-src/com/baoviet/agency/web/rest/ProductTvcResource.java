package com.baoviet.agency.web.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import com.baoviet.agency.domain.AgentDiscount;
import com.baoviet.agency.domain.BenifitTravel;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.excel.ProductTvcImportDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.repository.AgentDiscountRepository;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.ProductTvcService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.web.rest.vm.PremiumTvcVM;
import com.baoviet.agency.web.rest.vm.ProductTvcVM;
import com.baoviet.agency.web.rest.vm.UpdateAgreementTvcVM;
import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;
import com.viettel.ims.common.ImsCommonUtil;
import com.viettel.ims.dto.AppParamsDTO;
import com.viettel.ims.dto.IProjInvestProjectDTO;
import com.viettel.ims.dto.IProjInvestProjectItemsDTO;
import com.viettel.ims.dto.PurchaseInvestProposalImportDTO;
import com.viettel.ims.dto.service.PurchaseInvestProposalItemsDTO;
import com.viettel.ktts2.common.BusinessException;
import com.viettel.ktts2.common.UEncrypt;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for Agency TVC resource.
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/tvc")
@Api(value = AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "product/tvc", description = "<a href=\"/content/extra_doc/tvc.html\" target=\"_blank\">External document</a>")
public class ProductTvcResource extends AbstractAgencyResource{

    private final Logger log = LoggerFactory.getLogger(ProductTvcResource.class);

    private static final String ENTITY_NAME = "tvc";

    @Autowired
    private ProductTvcService productTvcService;
    
    @Autowired
    private AgreementService agreementService;
    
    @Autowired
    private AgentDiscountRepository agentDiscountRepository;
    
    @GetMapping("/get-benefit-areaId/{areaId}")
    @Timed
    @ApiOperation(value="getAllBenefit", notes="Lấy thông tin bảng quyền lợi bảo hiểm, areaId='2,3,4' 2: ASEAN, 3: Châu Á - TBD, 4: Toàn cầu ")
    public ResponseEntity<List<BenifitTravel>> getAllBenefit(@PathVariable String areaId) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getAllBenefit");
	
		// Call service
		List<BenifitTravel> data = productTvcService.getBenefitByAreaId(areaId);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
//    {
//    	  "destination": "3",	// địa điểm đến
//    	  "ngayDi": "13/04/2018",
//    	  "ngayVe": "20/04/2018",
//    	  "numberOfPerson": 1,	// gói bảo hiểm : 1 cá nhân: 
//    	  "planId": "2",
//    	  "premiumDiscount": 0,
//    	  "premiumNet": 0,
//    	  "premiumPackage": "1",
//    	  "premiumTvc": 0,
//    	  "songay": 0
//    }    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TVC_VIEW')")
    @PostMapping("/premium")
    @Timed
    @ApiOperation(value="getPremium", notes="Hàm tính tổng phí bảo hiểm du lịch quốc tế/International travel insurance premiums")
    public ResponseEntity<PremiumTvcVM> getPremium(@Valid @RequestBody PremiumTvcVM param) throws URISyntaxException, AgencyBusinessException {
		log.debug("REST request to getPremium : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		AgentDiscount agentDiscount = agentDiscountRepository.findByAgencyIdAndLineId(currentAgency.getId(), "TVC");
		if (agentDiscount != null) {
			if (agentDiscount.getDiscount() != null && agentDiscount.getDiscount() > 0) {
				param.setPremiumDiscount(agentDiscount.getDiscount());
			}
		}

		// Call service
		PremiumTvcVM data = productTvcService.calculatePremium(param);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TVC_CREATE')")
    @PostMapping("/createPolicy")
    @Timed
    @ApiOperation(value="createPolicy", notes="Tạo yêu cầu bảo hiểm du lịch quốc tế/Create international travel insurance claim")
    public ResponseEntity<ProductTvcVM> createPolicy(@Valid @RequestBody ProductTvcVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to createPolicy : {}", param);
		
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		AgentDiscount agentDiscount = agentDiscountRepository.findByAgencyIdAndLineId(currentAgency.getId(), "TVC");
		if (agentDiscount != null) {
			if (agentDiscount.getDiscount() != null && agentDiscount.getDiscount() > 0) {
				param.setChangePremium(agentDiscount.getDiscount());
			}
		}
		
		// Call service
		ProductTvcVM data = productTvcService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TVC_EDIT')")
    @PostMapping("/update")
    @Timed
    @ApiOperation(value="update", notes="Cập nhật yêu cầu bảo hiểm du lịch quốc tế/Create international travel insurance claim")
    public ResponseEntity<ProductTvcVM> update(@Valid @RequestBody ProductTvcVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to update : {}", param);
		
		// validate 
		validateUpdateProduct(param);
				
		// Get current agency
		AgencyDTO currentAgency = getCurrentAccount();
		
		// Call service
		ProductTvcVM data = productTvcService.createOrUpdatePolicy(param, currentAgency);
		
		// Return data
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PRODUCT_TVC_EDIT')")
    @PostMapping("/updatePolicy")
    @Timed
    @ApiOperation(value="updatePolicy", notes="Cập nhật yêu cầu bảo hiểm du lịch quốc tế/Update international travel insurance claim")
    @Deprecated
    public ResponseEntity<UpdateAgreementTvcVM> updatePolicy(@Valid @RequestBody UpdateAgreementTvcVM param) throws URISyntaxException, AgencyBusinessException{
		log.debug("REST request to updatePolicy : {}", param);
		
		// Call service
		//TvicareBaseVM data = productTvcService.updatePolicy(param);
		Boolean result = agreementService.updateAgreementTvcTravelCare(param.getAgreement(), param.getTravelcare(), param.getLstTravelCareAdd());
		param.setResult(result);
		
		// Return data
        return new ResponseEntity<>(param, HttpStatus.OK);
    }
    
    // Import file excel
    @POST
    @Path("/import-excel")
    public ResponseEntity<UpdateAgreementTvcVM> importExcel(@Valid @RequestBody ProductTvcImportDTO param) throws Exception {
    	PurchaseInvestProposalImportDTO dataImportDTO = new PurchaseInvestProposalImportDTO();
		PurchaseInvestProposalItemsDTO itemDTO = null;
		List<String> lstErrorMessage = null;
		
		try {
			String fileName = folderUpload + File.separatorChar+ UEncrypt.decryptFileUploadPath(obj.getPath());
			List<PurchaseInvestProposalItemsDTO> data = Lists.newArrayList(); 
			FileInputStream file = new FileInputStream(new File(fileName));
			
			
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			int count = 0;
			Row rowTitle= sheet.getRow(ROW_TITLE_INDEX);
			boolean check = false;
			
			// Validate proposal code
//			check = validateProposalCode(obj, workbook, sheet);
			
			for (Row row : sheet) {
				itemDTO = new PurchaseInvestProposalItemsDTO();
				if (count > ROW_TITLE_INDEX && !ImsCommonUtil.isRowEmpty(row)) {
					lstErrorMessage = getValueImport(rowTitle, row, obj, itemDTO);
					
//					validateProjInvestProjectData(obj
//							, itemDTO, lstErrorMessage);
					
					if(lstErrorMessage != null && lstErrorMessage.size() > 0){
						String errorMessageFormat = StringUtils.join(lstErrorMessage, ", ");
						Cell cell=row.createCell(15);
						cell.setCellValue(errorMessageFormat);
						cell.setCellStyle(this.createErrorCellStyle(workbook));
						check=true;
					} else {
						data.add(itemDTO);
					}
				}
				count++;
			}
			
			if (check) {
				String path = ImsCommonUtil.customUploadFix(workbook, "Imp_HMTT_ERROR.xlsx", "default", folderUpload);
				dataImportDTO.setPath(UEncrypt.encryptFileUploadPath(path));
			} else {
				dataImportDTO.setData(data);
				// Load invest project 
				List<IProjInvestProjectDTO> lstProjectInvest = getProjInvestProjects(data);
				dataImportDTO.setLstProjInvestProject(lstProjectInvest);
			}
			
			dataImportDTO.setError(check);
			workbook.close();
			file.close();
			return dataImportDTO;
		} catch (NullPointerException pointerException) {
			log.error(pointerException.getMessage(), pointerException);
		} catch (OLE2NotOfficeXmlFileException ole) {
			log.error(ole.getMessage(), ole);
			throw new BusinessException("Sai định dạng biểu mẫu !");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(e.getMessage());
		}
		return new PurchaseInvestProposalImportDTO();
    }
    
    private List<String> getValueImport(Row rowTitle, Row row
			, PurchaseInvestProposalImportDTO obj,PurchaseInvestProposalItemsDTO resultDTO) {
		
		// Declare variable
		DataFormatter formatter = new DataFormatter();
		List<String> lstErrorMessage = new ArrayList<>();
		String errorMessage = "";
		
		String projectName = formatter.formatCellValue(row.getCell(3));
		if (StringUtils.isEmpty(projectName)) {
			errorMessage = getRowTitle(rowTitle, 3) + " " + ImsCommonUtil.ERROR_NULL;
			lstErrorMessage.add(errorMessage);
		}
		
		/*1.  Nhóm hạng mục  */
//		resultDTO.setCostType(obj.getItemType());
		resultDTO.setItemType(obj.getItemType());
		
		/*2. Hạng mục dự án* */
		String proInvestItemCode = formatter.formatCellValue(row.getCell(2));
		if (StringUtils.isEmpty(proInvestItemCode)) {
			errorMessage = getRowTitle(rowTitle, 2) + " " + ImsCommonUtil.ERROR_NULL;
			lstErrorMessage.add(errorMessage);
		}
		
		if (!StringUtils.isEmpty(projectName) && !StringUtils.isEmpty(proInvestItemCode)) {
			IProjInvestProjectItemsDTO rowInvestItemObj = getProjInvestProjectItemByPrjectNameAndItemName(
					projectName, 
					formatter.formatCellValue(row.getCell(2)));
			errorMessage = validateImport(CELL_TYPE_CHECK_DATA, formatter.formatCellValue(row.getCell(2)), rowInvestItemObj);
			if(StringUtils.isNotEmpty(errorMessage)){
				errorMessage = getRowTitle(rowTitle, 2) + " " + errorMessage;
				lstErrorMessage.add(errorMessage);
			} else{
				resultDTO.setProjInvestProjectObj(rowInvestItemObj);
				resultDTO.setProjInvestProjectName(rowInvestItemObj.getProjInvestProjectName());
			}
		}
		
		/*4. Mã hạng mục tờ trình*/
		processCellData(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_STRING, 4);
		
		/*5. Tên hạng mục tờ trình*/
		processCellData(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_STRING, 5);
		
		/*6. Mô tả*/
		processCellData(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_STRING, 6);
		
		/*7. Đơn vị tính*/
		List<AppParamsDTO> paramsDTO=appParamsDAO.getByParTypeAndParName("UNIT_TYPE", formatter.formatCellValue(row.getCell(7)));
		errorMessage=validateImport(CELL_TYPE_CHECK_DATA, paramsDTO);
		if(StringUtils.isNotEmpty(errorMessage)){
			errorMessage = getRowTitle(rowTitle, 7) + " " + errorMessage;
			lstErrorMessage.add(errorMessage);
		} else{
			resultDTO.setObjUnit(paramsDTO.get(0));
		}
		/*8. Số lượng*/
		processCellData(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_NUMBER, 8);
		/*9. Đơn giá*/
		processCellData(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_NUMBER, 9);
		/*10.Loại tiền */
		List<AppParamsDTO> paramsDTO2=appParamsDAO.getByParTypeAndParName("CURRENCY_TYPE", formatter.formatCellValue(row.getCell(10)));
		errorMessage = validateImport(CELL_TYPE_CHECK_DATA, paramsDTO2);
		if(StringUtils.isNotEmpty(errorMessage)){
			errorMessage = getRowTitle(rowTitle, 10) + " " + errorMessage;
			lstErrorMessage.add(errorMessage);
		} else{
			resultDTO.setCurrencyObj(paramsDTO2.get(0));
		}
		/*11. Tỷ giá */
		processCellData(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_NUMBER, 11);
		
		/*12. Thành tiền trước thuế*/
//		processCellData(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_NUMBER, 12);
		/*13. Thuế*/
		processCellData(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_NUMBER, 13);
		/*14. Tổng tiền sau thuế*/
//		processCellData(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_NUMBER, 14);
		
		return lstErrorMessage;
	}
	
	private String getRowTitle(Row rowTitle, int index) {
		DataFormatter formatter = new DataFormatter();
		Cell cellTitle = rowTitle.getCell(index);
		String title = formatter.formatCellValue(cellTitle);
		title = StringUtils.trim(title.replaceAll("\\*", ""));
		return title;
	}
	
	private void processCellData(Row rowTitle, Row row
			, PurchaseInvestProposalItemsDTO resultDTO, List<String> lstErrorMessage, String typeCheck, int index) {
		DataFormatter formatter = new DataFormatter();
		String itemValue = formatter.formatCellValue(row.getCell(index));
		String errorMessage = validateImport(typeCheck, itemValue);
		if(StringUtils.isNotEmpty(errorMessage)){
			errorMessage = getRowTitle(rowTitle, index) + " " + errorMessage;
			lstErrorMessage.add(errorMessage);
		} else {
			Object data = 0;
			if (StringUtils.equals(CELL_TYPE_CHECK_STRING, typeCheck)) {
				data = itemValue;//resultDTO.setItemCode(itemValue);
			} else if (StringUtils.equals(CELL_TYPE_CHECK_NUMBER, typeCheck)) {
				data = Double.parseDouble(StringUtils.isNotEmpty(itemValue) ? itemValue : "0");
			}
			
			// Set
			switch (index) {
	            case 4:
	            	resultDTO.setItemCode((String)data);
	            	break;
	            case 5:  
	            	resultDTO.setItemName((String)data);
	            	break;
	            case 6:  
	            	resultDTO.setComments((String)data);
	            	break;
	            case 8:  
	            	resultDTO.setCount((Double)data);
	            	break;
	            case 9:  
	            	resultDTO.setUnitPrice((Double)data);
	            	break;
	            case 11:  
	            	resultDTO.setTransRate((Double)data);
	            	break;
	            case 12:  
	            	resultDTO.setValueBfVat((Double)data);
	            	break;
	            case 13:  
	            	resultDTO.setVat((Double)data);
	            	break;
	            case 14:  
	            	resultDTO.setTotalValue((Double)data);
	            	break;
	            default:
	            	break;
	        }
		}
	}
	
	public String downloadTemplate() throws BusinessException {
		String path = null;
		try {
			InputStream excelFileToRead;
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			String filePath = classloader.getResource("../").getPath();
			excelFileToRead = new FileInputStream(filePath + "/doc-template" + File.separatorChar
					+ "IMS_PURCHASE_INVEST_PROPOSAL_ITEM_HMDT.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(excelFileToRead);
			XSSFSheet sheet = wb.getSheetAt(0);

			List<String> currencyList = appParamsDAO.getParamListByType("CURRENCY_TYPE");
			List<String> unitTypeList = appParamsDAO.getParamListByType("UNIT_TYPE");

			bindComboboxData(wb, sheet, currencyList, 10, "CurrencyData");
			bindComboboxData(wb, sheet, unitTypeList, 7, "UnitData");

			path = ImsCommonUtil.customUploadFix(wb, "IMS_PURCHASE_INVEST_PROPOSAL_ITEM_HMDT.xlsx", "default",
					folderUpload);
			path = UEncrypt.encryptFileUploadPath(path);
		} catch (Exception e) {
			log.error(e.getCause(), e);
			throw new BusinessException("Lỗi khi tải biểu mẫu");
		}
		return path;
	}
}
