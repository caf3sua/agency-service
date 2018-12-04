package com.baoviet.agency.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.Agency;
import com.baoviet.agency.domain.Permission;
import com.baoviet.agency.domain.Relationship;
import com.baoviet.agency.domain.Role;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.excel.BasePathInfoDTO;
import com.baoviet.agency.dto.excel.ProductImportDTO;
import com.baoviet.agency.dto.excel.ProductTvcExcelDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AgencyRepository;
import com.baoviet.agency.repository.RelationshipRepository;
import com.baoviet.agency.service.AgencyService;
import com.baoviet.agency.service.ExcelService;
import com.baoviet.agency.service.mapper.AgencyMapper;
import com.baoviet.agency.utils.AgencyCommonUtil;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.UEncrypt;
import com.baoviet.agency.web.rest.vm.TvcAddBaseVM;


/**
 * Service Implementation for managing GnocCr.
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class ExcelServiceImpl implements ExcelService {

    private final Logger log = LoggerFactory.getLogger(ExcelServiceImpl.class);
    
    private static final int ROW_TITLE_INDEX = 3;
    
    private static final String CELL_TYPE_CHECK_NUMBER = "CHECK_NUMBER"; 
    private static final String CELL_TYPE_CHECK_DATE = "CHECK_DATE";
    private static final String CELL_TYPE_CHECK_DATA = "CHECK_DATA";
    private static final String CELL_TYPE_CHECK_STRING = "CHECK_STRING";
    private static final String CELL_TYPE_NOT_CHECK = "NOT_CHECK";

    @Value("${spring.upload.folder-upload}")
	private String folderUpload;
    
    @Autowired
	private ResourceLoader resourceLoader;
    
    @Autowired
	private RelationshipRepository relationshipRepository;
    
    
	@Override
	public ProductTvcExcelDTO processImportTVC(ProductImportDTO obj) throws AgencyBusinessException {
		// TODO Auto-generated method stub
    	ProductTvcExcelDTO dataImportDTO = new ProductTvcExcelDTO();
    	TvcAddBaseVM itemDTO = null;
		List<String> lstErrorMessage = null;
		
		List<String> lstCmnd = new ArrayList<>();
		
		try {
			String fileName = UEncrypt.decryptFileUploadPath(obj.getPath());
			List<TvcAddBaseVM> data = new ArrayList<>(); 
			FileInputStream file = new FileInputStream(new File(fileName));
			
			Workbook workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheetAt(0);
			
			Row rowTitle= sheet.getRow(ROW_TITLE_INDEX);
			boolean check = false;
			int index = 0;
			
			for (Row row : sheet) {
				itemDTO = new TvcAddBaseVM();
				if (row.getRowNum() > ROW_TITLE_INDEX && !AgencyCommonUtil.isRowEmpty(row)) {
					index++;
					lstErrorMessage = getValueImportTVC(obj, rowTitle, row, itemDTO, lstCmnd);
					
					if(lstErrorMessage != null && lstErrorMessage.size() > 0){
						String errorMessageFormat = StringUtils.join(lstErrorMessage, ", ");
						Cell cell=row.createCell(5);
						cell.setCellValue(errorMessageFormat);
						cell.setCellStyle(AgencyCommonUtil.createErrorCellStyle(workbook));
						check=true;
					} else {
						data.add(itemDTO);
						lstCmnd.add(itemDTO.getIdPasswport());
					}
				}
			}
			
			boolean extraCheck = validateNumberOfPerson(workbook, rowTitle, obj.getTravelWithId(), index);
			
			if (check || extraCheck) {
				dataImportDTO.setError(true);
				sheet.setColumnWidth(5, 40000);
				
				String path = AgencyCommonUtil.customUploadFix(workbook, AgencyConstants.EXCEL.IMPORT_NAME_TVC_ERROR, folderUpload);
				dataImportDTO.setPath(UEncrypt.encryptFileUploadPath(path));
			} else {
				dataImportDTO.setError(false);
				dataImportDTO.setData(data);
			}
			
			
			workbook.close();
			file.close();
			return dataImportDTO;
		} catch (NullPointerException pointerException) {
			log.error(pointerException.getMessage(), pointerException);
		} catch (OLE2NotOfficeXmlFileException ole) {
			log.error(ole.getMessage(), ole);
			throw new AgencyBusinessException("fileImport", ErrorCode.INVALID , "Sai định dạng biểu mẫu !");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new AgencyBusinessException("fileImport", ErrorCode.UNKNOW_ERROR , e.getMessage());
		}
		return dataImportDTO;
	}

	private boolean validateNumberOfPerson(Workbook workbook, Row rowTitle, String travelWithId, int total) {
		String message = null;
		if (StringUtils.equals(travelWithId, AgencyConstants.TVC.PACKAGE_CA_NHAN)) {
			if (total > 1) {
				message = "Số người đi du lịch cá nhân phải = 1";
			}
		}
		
		if (!StringUtils.equals(travelWithId, AgencyConstants.TVC.PACKAGE_CA_NHAN)) {
			if (total <= 1) {
				message = "Số người đi du lịch theo gia đình/đoàn phải > 1";
			}
		}
		
		if(StringUtils.isNotEmpty(message)){
			Cell cell= rowTitle.createCell(5);
			cell.setCellValue(message);
			cell.setCellStyle(AgencyCommonUtil.createErrorCellStyle(workbook));
			return true;
		}
		
		return false;
	}
	
	@Override
	public BasePathInfoDTO processExportTVC(ProductTvcExcelDTO obj) throws AgencyBusinessException {
		BasePathInfoDTO result = new BasePathInfoDTO();
		InputStream excelFileToRead = null;
		File file = null;
		try {
			// Load file into input stream
			ClassLoader classLoader = getClass().getClassLoader();
			file = new File(classLoader.getResource("templates/" + AgencyConstants.EXCEL.TEMPLATE_NAME_TVC).getFile());
			log.debug("Path to file template : {}", file.getAbsolutePath());
			
			if (!file.exists()) {
				Resource resource = resourceLoader.getResource("classpath:templates/" + AgencyConstants.EXCEL.TEMPLATE_NAME_TVC);
				excelFileToRead = resource.getInputStream(); // <-- this is the difference
			} else {
				excelFileToRead = new FileInputStream(file);
			}
			
			if (excelFileToRead == null) {
				// Load from template folder
				throw new AgencyBusinessException(ErrorCode.INVALID, "Không tồn tại file");
			}
			
			Workbook workbook = WorkbookFactory.create(excelFileToRead);
			Sheet sheet0 = workbook.getSheetAt(0); // Lay sheet dau tien
			
			bindingDataForExcelItemTvc(obj.getData(), sheet0);
			
			// close resource
			excelFileToRead.close();
			String path = AgencyCommonUtil.customUploadFix(workbook, AgencyConstants.EXCEL.EXPORT_NAME_TVC, folderUpload);
			result.setPath(UEncrypt.encryptFileUploadPath(path));
		} catch (FileNotFoundException e) {
			throw new AgencyBusinessException(ErrorCode.INVALID, "Không tồn tại file template: " + AgencyConstants.EXCEL.TEMPLATE_NAME_TVC);
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			e.printStackTrace();
			throw new AgencyBusinessException(ErrorCode.INVALID, "Lỗi khi tạo file workbook excel");
		} catch (Exception e) {
			e.printStackTrace();
			throw new AgencyBusinessException(ErrorCode.UNKNOW_ERROR, e.getMessage());
		}
				
		return result;
	}

	
	
    /*
	 * -------------------------------------------------
	 * ---------------- Private method -----------------
	 * -------------------------------------------------
	 */
	private void createCellAndStyle(Row row, int cellIndex, Object value, CellStyle style) {
		Cell cell = row.createCell(cellIndex, CellType.STRING);
		cell.setCellValue(String.valueOf(value));
		cell.setCellStyle(style);
	}
	
	private void bindingDataForExcelItemTvc(List<TvcAddBaseVM> lstObj, Sheet sheet) {
 		Row row = null;
		int rowNum = 4;
		int i = 1;
		
		for (TvcAddBaseVM obj : lstObj) {
			row = sheet.createRow(rowNum++);
			bindDataForOneRowTvc(obj, i, row, sheet);
			i++;
		}
	}
	
	private void bindDataForOneRowTvc(TvcAddBaseVM obj, int stt, Row row, Sheet sheet) {
		CellStyle styleText = AgencyCommonUtil.styleText(sheet);
		CellStyle styleNumber = AgencyCommonUtil.styleNumber(sheet);
		
		// STT
		createCellAndStyle(row, 0, stt, styleNumber);
		
		// Ho va ten
		createCellAndStyle(row, 1, obj.getInsuredName(), styleText);
		
		// CMND/Passport
		createCellAndStyle(row, 2, obj.getIdPasswport(), styleText);
		
		// Ngay sinh
		createCellAndStyle(row, 3, obj.getDob(), styleText);
		
		// Quan he	
		Relationship rEntity = relationshipRepository.getOne(obj.getRelationship());
		String relationshipName = "Khác";
		if (rEntity != null) {
			relationshipName = rEntity.getRelationshipName();
		}
		createCellAndStyle(row, 4, relationshipName, styleText);
	}
	
	private List<String> getValueImportTVC(ProductImportDTO obj, Row rowTitle, Row row, TvcAddBaseVM resultDTO, List<String> lstCmnd) {
		
		// Declare variable
		List<String> lstErrorMessage = new ArrayList<>();
		
		/*0. STT */
		processCellDataTVC(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_NUMBER, 0, true);
		
		/*1. Họ và tên*/
		processCellDataTVC(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_STRING, 1, true);
		
		/*6. Số CMND/Hộ chiếu*/
		processCellDataTVC(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_STRING, 2, false);
		
		/*8. Ngày sinh*/
		processCellDataTVC(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_STRING, 3, false);
		
		/*9. Quan hệ*/
		processCellDataTVC(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_STRING, 4, true);
		
		// Validate trung Cmnd/passport
		validateDuplicateCmnd(rowTitle, row, resultDTO, lstErrorMessage, lstCmnd);
		
		// Validate bat buoc nhap Cmnd hoac Ngay sinh
		validateRequiredCmndOrDob(rowTitle, row, resultDTO, lstErrorMessage);
		
		validateRelationship(rowTitle, row, resultDTO, lstErrorMessage, obj.getTravelWithId());
		
		validateExtraInfo(rowTitle, row, resultDTO, lstErrorMessage);
		
		return lstErrorMessage;
	}
	
	private String getRowTitle(Row rowTitle, int index) {
		DataFormatter formatter = new DataFormatter();
		Cell cellTitle = rowTitle.getCell(index);
		String title = formatter.formatCellValue(cellTitle);
		title = StringUtils.trim(title.replaceAll("\\*", ""));
		return title;
	}
	
	private String validateImport(String typeValidate, String data, boolean isRequired) {
		if (isRequired == false && AgencyCommonUtil.isNullImport(data)) {
			return StringUtils.EMPTY;
		}
		
		if (typeValidate.equals(CELL_TYPE_CHECK_STRING) && AgencyCommonUtil.isNullImport(data)) {
			return "Chưa nhập dữ liệu";
		}
		
		if (typeValidate.equals(CELL_TYPE_CHECK_NUMBER) && AgencyCommonUtil.isNotNumberImport(data)) {
			return "Sai định dạng số";
		}
		
		if (typeValidate.equals(CELL_TYPE_CHECK_DATE) && AgencyCommonUtil.isNotDateImport(data)) {
			return "Sai định dạng ngày tháng";
		}
		
		return StringUtils.EMPTY;
	}
	
	private void processCellDataTVC(Row rowTitle, Row row, TvcAddBaseVM resultDTO, List<String> lstErrorMessage, String typeCheck, int index, boolean isRequired) {
		DataFormatter formatter = new DataFormatter();
		String itemValue = formatter.formatCellValue(row.getCell(index));
		String errorMessage = validateImport(typeCheck, itemValue, isRequired);
		if(StringUtils.isNotEmpty(errorMessage)){
			errorMessage = getRowTitle(rowTitle, index) + " " + errorMessage;
			lstErrorMessage.add(errorMessage);
		} else {
			Object data = 0;
			if (StringUtils.equals(CELL_TYPE_CHECK_STRING, typeCheck)) {
				data = itemValue;
			} else if (StringUtils.equals(CELL_TYPE_CHECK_NUMBER, typeCheck)) {
				data = Integer.parseInt(StringUtils.isNotEmpty(itemValue) ? itemValue : "0");
			}
			
			// Set
			switch (index) {
	            case 0:
	            	resultDTO.setOrder((Integer)data);
	            	break;
	            case 1:  
	            	resultDTO.setInsuredName((String)data);
	            	break;
	            case 2:  
	            	resultDTO.setIdPasswport((String)data);
	            	break;
	            case 3:  
	            	resultDTO.setDob((String)data);
	            	break;
	            case 4:
	            	resultDTO.setRelationship(getRelationshipIdByName((String)data));
	            	resultDTO.setRelationshipName((String)data);
	            	break;
	            default:
	            	break;
	        }
		}
	}
	
	private String getRelationshipIdByName(String relationshipName) {
		String relationShipId = AgencyConstants.RELATIONSHIP.KHONG_XAC_DINH;
		
		Relationship rEntity = relationshipRepository.findTopByRelationshipName(relationshipName);
		
		if (rEntity == null) {
			return relationShipId;
		}
		
		relationShipId = rEntity.getRelationshipId();
		
		return relationShipId;
	}
	
	private void validateDuplicateCmnd(Row rowTitle, Row row, TvcAddBaseVM resultDTO, List<String> lstErrorMessage, List<String> lstCmnd) {
		if (StringUtils.isEmpty(resultDTO.getIdPasswport())) {
			return;
		}
		
		if(lstCmnd.contains(resultDTO.getIdPasswport())) {
			String errorMessage = "Số hộ chiếu/CMND đã tồn tại";
			lstErrorMessage.add(errorMessage);
		}
	}
	
	private void validateRequiredCmndOrDob(Row rowTitle, Row row, TvcAddBaseVM resultDTO, List<String> lstErrorMessage) {
		if (StringUtils.isEmpty(resultDTO.getIdPasswport()) && StringUtils.isEmpty(resultDTO.getDob())) {
			String errorMessage = "Cần nhập dữ liệu cho Số hộ chiếu/CMND hoặc Ngày sinh";
			lstErrorMessage.add(errorMessage);
		}
	}
	
	private void validateRelationship(Row rowTitle, Row row, TvcAddBaseVM resultDTO, List<String> lstErrorMessage, String travelWithId) {
		if (StringUtils.isEmpty(resultDTO.getRelationshipName())) {
			return;
		}
		
		if (StringUtils.equals(travelWithId, AgencyConstants.TVC.PACKAGE_KHACH_DOAN)) {
			if (!(StringUtils.equals(resultDTO.getRelationship(), AgencyConstants.RELATIONSHIP.BAN_THAN) 
					|| StringUtils.equals(resultDTO.getRelationship(), AgencyConstants.RELATIONSHIP.KHACH_DOAN))) {
				String errorMessage = "Du lịch theo đoàn thì quan hệ phải là: Thành viên đoàn hoặc Bản thân";
				lstErrorMessage.add(errorMessage);
			}
			
		} else {
			if (StringUtils.equals(resultDTO.getRelationship(), AgencyConstants.RELATIONSHIP.KHACH_DOAN)) {
				String errorMessage = "Quan hệ là: Thành viên đoàn chỉ áp dụng cho khách du lịch theo đoàn";
				lstErrorMessage.add(errorMessage);
			}
		}
	}
	
	private void validateExtraInfo(Row rowTitle, Row row, TvcAddBaseVM resultDTO, List<String> lstErrorMessage) {
		if (StringUtils.isEmpty(resultDTO.getDob())) {
			return;
		}
		
		if (!DateUtils.isValidDate(resultDTO.getDob(), "dd/MM/yyyy")) {
			String errorMessage = "Định dạng ngày sinh không đúng (dd/MM/yyyy)";
			lstErrorMessage.add(errorMessage);
			return;
		}

		int utageNDBHYear = DateUtils.countYears(DateUtils.str2Date(resultDTO.getDob()),
				new Date()); // DateUtils.str2Date(objTravel.getInceptionDate())
		int utageNDBHMonth = DateUtils.getNumberMonthsBetween2Date(DateUtils.str2Date(resultDTO.getDob()),
				new Date());

		if (utageNDBHYear == 0 && utageNDBHMonth < 6) {
			String errorMessage = "Người được bảo hiểm phải từ 6 tháng tuổi";
			lstErrorMessage.add(errorMessage);
			return;
		}
		if (utageNDBHYear > 85) {
			String errorMessage = "Người được bảo hiểm phải <= 85 tuổi";
			lstErrorMessage.add(errorMessage);
			return;
		}
	}
}
