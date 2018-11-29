package com.baoviet.agency.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.Agency;
import com.baoviet.agency.domain.Permission;
import com.baoviet.agency.domain.Role;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.excel.BasePathInfoDTO;
import com.baoviet.agency.dto.excel.ProductImportDTO;
import com.baoviet.agency.dto.excel.ProductTvcExcelDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AgencyRepository;
import com.baoviet.agency.service.AgencyService;
import com.baoviet.agency.service.ExcelService;
import com.baoviet.agency.service.mapper.AgencyMapper;
import com.baoviet.agency.utils.AgencyCommonUtil;
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
			
			for (Row row : sheet) {
				itemDTO = new TvcAddBaseVM();
				if (row.getRowNum() > ROW_TITLE_INDEX && !AgencyCommonUtil.isRowEmpty(row)) {
					lstErrorMessage = getValueImportTVC(rowTitle, row, itemDTO, lstCmnd);
					
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
			
			if (check) {
				sheet.setColumnWidth(5, 40000);
				
				String path = AgencyCommonUtil.customUploadFix(workbook, AgencyConstants.EXCEL.IMPORT_NAME_TVC_ERROR, folderUpload);
				dataImportDTO.setPath(UEncrypt.encryptFileUploadPath(path));
			} else {
				dataImportDTO.setData(data);
			}
			
			dataImportDTO.setError(check);
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

	@Override
	public BasePathInfoDTO processExportTVC(ProductTvcExcelDTO obj) throws AgencyBusinessException {
		BasePathInfoDTO result = new BasePathInfoDTO();
		InputStream excelFileToRead = null;
		File file = null;
		try {
			// Load file into input stream
			file = ResourceUtils.getFile("src/main/resources/templates/" + AgencyConstants.EXCEL.TEMPLATE_NAME_TVC);
			if (!file.exists()) {
				throw new AgencyBusinessException(ErrorCode.INVALID, "Không tồn tại file");
			}
			excelFileToRead = new FileInputStream(file);
			
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
		createCellAndStyle(row, 4, "Khách đoàn", styleText);
	}
	
	private List<String> getValueImportTVC(Row rowTitle, Row row, TvcAddBaseVM resultDTO, List<String> lstCmnd) {
		
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
	            	resultDTO.setRelationship(AgencyConstants.RELATIONSHIP.KHACH_DOAN); // Khach doan
	            	break;
	            default:
	            	break;
	        }
		}
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
}