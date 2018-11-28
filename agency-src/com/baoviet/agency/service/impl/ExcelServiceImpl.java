package com.baoviet.agency.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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

import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.Agency;
import com.baoviet.agency.domain.Permission;
import com.baoviet.agency.domain.Role;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.excel.ProductImportDTO;
import com.baoviet.agency.dto.excel.ProductTvcImportResponseDTO;
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
    private static final String CELL_TYPE_CHECK_DATA = "CHECK_DATA";
    private static final String CELL_TYPE_CHECK_STRING = "CHECK_STRING";

    @Value("${spring.upload.folder-upload}")
	private String folderUpload;
    
	@Override
	public ProductTvcImportResponseDTO processImportTVC(ProductImportDTO obj) throws AgencyBusinessException {
		// TODO Auto-generated method stub
    	ProductTvcImportResponseDTO dataImportDTO = new ProductTvcImportResponseDTO();
    	TvcAddBaseVM itemDTO = null;
		List<String> lstErrorMessage = null;
		
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
					lstErrorMessage = getValueImportTVC(rowTitle, row, itemDTO);
					
					if(lstErrorMessage != null && lstErrorMessage.size() > 0){
						String errorMessageFormat = StringUtils.join(lstErrorMessage, ", ");
						Cell cell=row.createCell(5);
						cell.setCellValue(errorMessageFormat);
						cell.setCellStyle(this.createErrorCellStyle(workbook));
						check=true;
					} else {
						data.add(itemDTO);
					}
				}
			}
			
			if (check) {
				String path = AgencyCommonUtil.customUploadFix(workbook, "Imp_TVC_Data_Error.xls", folderUpload);
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


    /*
	 * -------------------------------------------------
	 * ---------------- Private method -----------------
	 * -------------------------------------------------
	 */
  private List<String> getValueImportTVC(Row rowTitle, Row row, TvcAddBaseVM resultDTO) {
		
		// Declare variable
		DataFormatter formatter = new DataFormatter();
		List<String> lstErrorMessage = new ArrayList<>();
		String errorMessage = "";
		
		String projectName = formatter.formatCellValue(row.getCell(3));
		if (StringUtils.isEmpty(projectName)) {
			errorMessage = getRowTitle(rowTitle, 3) + " " + AgencyCommonUtil.ERROR_NULL;
			lstErrorMessage.add(errorMessage);
		}
		
		/*0. STT */
		processCellDataTVC(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_NUMBER, 0);
		
		/*1. Họ và tên*/
		processCellDataTVC(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_STRING, 1);
		
		/*6. Số CMND/Hộ chiếu*/
		processCellDataTVC(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_STRING, 2);
		
		/*8. Ngày sinh*/
		processCellDataTVC(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_STRING, 3);
		/*9. Quan hệ*/
		processCellDataTVC(rowTitle, row, resultDTO, lstErrorMessage, CELL_TYPE_CHECK_STRING, 4);
		
		return lstErrorMessage;
	}
	
	private String getRowTitle(Row rowTitle, int index) {
		DataFormatter formatter = new DataFormatter();
		Cell cellTitle = rowTitle.getCell(index);
		String title = formatter.formatCellValue(cellTitle);
		title = StringUtils.trim(title.replaceAll("\\*", ""));
		return title;
	}
	
	private String validateImport(String typeValidate, String data) {
		if (typeValidate.equals(CELL_TYPE_CHECK_STRING) && AgencyCommonUtil.isNullImport(data)) {
			return "Chưa nhập dữ liệu";
		}
		
		if (typeValidate.equals(CELL_TYPE_CHECK_NUMBER) && AgencyCommonUtil.isNotNumberImport(data)) {
			return "Sai định dạng";
		}
		
		return StringUtils.EMPTY;
	}
	
	private void processCellDataTVC(Row rowTitle, Row row, TvcAddBaseVM resultDTO, List<String> lstErrorMessage, String typeCheck, int index) {
		DataFormatter formatter = new DataFormatter();
		String itemValue = formatter.formatCellValue(row.getCell(index));
		String errorMessage = validateImport(typeCheck, itemValue);
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
	            	resultDTO.setRelationship((String)data);
	            	break;
	            default:
	            	break;
	        }
		}
	}
	
	private CellStyle createErrorCellStyle(Workbook workbook) {
		Font fontBold = workbook.createFont();
		fontBold.setFontHeightInPoints((short) 12);  
		fontBold.setFontName("Times New Roman");  
		fontBold.setColor(IndexedColors.RED.getIndex());  
		fontBold.setBold(true);  
		fontBold.setItalic(false);  
		CellStyle errCellStyle = workbook.createCellStyle();  
		errCellStyle.setFont(fontBold);  
		errCellStyle.setAlignment(CellStyle.ALIGN_CENTER);  
		errCellStyle.setWrapText(true);    
		return errCellStyle;
	}
//	
//	public String downloadTemplate() throws BusinessException {
//		String path = null;
//		try {
//			InputStream excelFileToRead;
//			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//			String filePath = classloader.getResource("../").getPath();
//			excelFileToRead = new FileInputStream(filePath + "/doc-template" + File.separatorChar
//					+ "IMS_PURCHASE_INVEST_PROPOSAL_ITEM_HMDT.xlsx");
//			XSSFWorkbook wb = new XSSFWorkbook(excelFileToRead);
//			XSSFSheet sheet = wb.getSheetAt(0);
//
//			List<String> currencyList = appParamsDAO.getParamListByType("CURRENCY_TYPE");
//			List<String> unitTypeList = appParamsDAO.getParamListByType("UNIT_TYPE");
//
//			bindComboboxData(wb, sheet, currencyList, 10, "CurrencyData");
//			bindComboboxData(wb, sheet, unitTypeList, 7, "UnitData");
//
//			path = AgencyCommonUtil.customUploadFix(wb, "IMS_PURCHASE_INVEST_PROPOSAL_ITEM_HMDT.xlsx", "default",
//					folderUpload);
//			path = UEncrypt.encryptFileUploadPath(path);
//		} catch (Exception e) {
//			log.error(e.getCause(), e);
//			throw new BusinessException("Lỗi khi tải biểu mẫu");
//		}
//		return path;
//	}
}
