package com.baoviet.agency.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class AgencyCommonUtil {
	public static final Integer NOT_CHECK = -1;
	public static final Integer CHECK_NULL = 0;
	public static final Integer CHECK_NUMBER = 1;
	public static final String ERROR_NULL = "Chưa nhập dữ liệu"; 
	public static final String ERROR_NOT_FEILD = "Sai định dạng"; 
	public static final String ERROR_NOT_FOUND = "Không tồn tại";
	public static final String ERROR_DUBLICATE = "Đã tồn tại";
	
	public static final String ERROR_MISMATCH = "Không trùng khớp";
	public static final String ERROR_IS_ZERO = "Phải lớn hơn 0"; 
	
	protected final static Logger LOGGER = Logger.getLogger(AgencyCommonUtil.class);
	static public String customFormat(String pattern, double value) {
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return  myFormatter.format(value);
	}
	
	
	private static String [] mangso = { "không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};

	private static String dochangchuc(Double so, boolean daydu) {
	    String chuoi = "";
	    Double chuc = Math.floor(so / 10);
	    Double donvi = so % 10;
	    if (chuc > 1) {
	        chuoi = " " + mangso[chuc.intValue()] + " mươi";
	        if (donvi == 1) {
	            chuoi += " mốt";
	        }
	    } else if (chuc == 1) {
	        chuoi = " mười";
	        if (donvi == 1) {
	            chuoi += " một";
	        }
	    } else if (daydu && donvi > 0) {
	        chuoi = " lẻ";
	    }
	    if (donvi == 5 && chuc > 1) {
	        chuoi += " lăm";
	    } else if (donvi > 1 || (donvi == 1 && chuc == 0)) {
	        chuoi += " " + mangso[donvi.intValue()];
	    }
	    return chuoi;
	}

	private static String docblock(Double so, boolean daydu) {
	    String chuoi;
	    Double tram = Math.floor(so / 100);
	    so = so % 100;
	    if (daydu || tram > 0) {
	        chuoi = " " + mangso[tram.intValue()] + " trăm";
	        chuoi += dochangchuc(so, true);
	    } else {
	        chuoi = dochangchuc(so, false);
	    }
	    return chuoi;
	}

	private static String dochangtrieu(Double so, boolean daydu) {
	    String chuoi ="";
	    Double trieu = Math.floor(so / 1000000);
	    so = so % 1000000;
	    if (trieu > 0) {
	        chuoi = docblock(trieu, daydu) + " triệu";
	        daydu = true;
	    }
	    Double nghin = Math.floor(so / 1000);
	    so = so % 1000;
	    if (nghin > 0) {
	        chuoi += docblock(nghin, daydu) + " nghìn";
	        daydu = true;
	    }
	    if (so > 0) {
	        chuoi += docblock(so, daydu);
	    }
	    return chuoi;
	}

	public static String docso(Double so) {
	    if (so == 0) return mangso[0];
	    String chuoi = "";
		String hauto = "";
	    do {
	    	Double ty = so % 1000000000;
	        so = Math.floor(so / 1000000000);
	        if (so > 0) {
	            chuoi = dochangtrieu(ty, true) + hauto + chuoi;
	        } else {
	            chuoi = dochangtrieu(ty, false) + hauto + chuoi;
	        }
	        hauto = " tỷ";
	    } while (so > 0);
	    return chuoi;
	}
	public static Date convertString2Date(String strDate, String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		try {
		    return df.parse(strDate);
		} catch (Exception e) {
		    LOGGER.error("error", e);
		}
		return null;
	}
	
	public static String convertDate2String(Date date, String dateFormat) {
		SimpleDateFormat sdfr = new SimpleDateFormat(dateFormat);
		String dateString = "";
		try {
			dateString = sdfr.format(date);
		} catch (Exception e) {
			LOGGER.error("error", e);
		}
		return dateString;
	}
	
	public static Date convertToDate(String date) {
		return AgencyCommonUtil.convertString2Date(date, "dd/MM/yyyy HH:mm");
	}
	
	
	public static Integer convertString2Int(String param) {
		Integer intParam =Integer.parseInt(param);
		return intParam;
	}
	public static boolean isNullOrBlank(String param) {
		if (param == null || "".equals(param.trim())   || "null".equals(param.trim()) || "undefined".equals(param.trim())) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static String convertDateToString(Date date, String format) {
		SimpleDateFormat datetemp = new SimpleDateFormat(format);
		return datetemp.format(date);
	}
	
	static public String doubleFormat(Double value ) {
		if (null == value) {
			return "0";
		}
		DecimalFormat myFormatter = new DecimalFormat("###,###.###");
		return myFormatter.format(value);
	}
	
	static public String doubleFormat(Long value ) {
		if (null == value) {
			return "0";
		}
		DecimalFormat myFormatter = new DecimalFormat("###,###.###");
		return myFormatter.format(value);
	}
	static public String doubleFormat(Double value, Long param2) {
		if (null == value || null == param2) {
			return "0";
		}
		DecimalFormat myFormatter = new DecimalFormat("###,###.###");
		return myFormatter.format(value * param2);
	}
	static public String doubleFormat(Long value, Long param2) {
		if (null == value || null == param2) {
			return "0";
		}
		DecimalFormat myFormatter = new DecimalFormat("###,###.###");
		return myFormatter.format(value * param2);
	}
	
	static public String doubleFormat(String value ) {
		Double result = 0d;
		try{
			result = Double.parseDouble(value);
		}catch(Exception ex) {
			LOGGER.error("error", ex);
		}
		DecimalFormat myFormatter = new DecimalFormat("###,###.###");
		return myFormatter.format(result);
	}
	
	
	static public String customUpload(HSSFWorkbook wb, String fileName, String subFolder, String folder) throws Exception {

		String safeFileName = UString.getSafeFileName(fileName);
		Calendar cal = Calendar.getInstance();
		String uploadPath = folder + File.separator + UFile.getSafeFileName(subFolder) + File.separator
				+ cal.get(Calendar.YEAR) + File.separator + (cal.get(Calendar.MONTH) + 1) + File.separator
				+ cal.get(Calendar.DATE) + File.separator + cal.get(Calendar.MILLISECOND);
		String uploadPathReturn = File.separator + UFile.getSafeFileName(subFolder) + File.separator
				+ cal.get(Calendar.YEAR) + File.separator + (cal.get(Calendar.MONTH) + 1) + File.separator
				+ cal.get(Calendar.DATE) + File.separator + cal.get(Calendar.MILLISECOND);
		File udir = new File(uploadPath);
		if (!udir.exists()) {
			udir.mkdirs();
		}
		try (OutputStream out = new FileOutputStream(udir.getAbsolutePath() + File.separator + safeFileName)) {
			wb.write(out);
			out.close();
		}
		return uploadPathReturn + File.separator + safeFileName;

	}
	
	static public String customUploadFix(Workbook wb, String fileName, String subFolder, String folder) throws Exception {

		String safeFileName = UString.getSafeFileName(fileName);
		Calendar cal = Calendar.getInstance();
		String uploadPath = folder + File.separator + UFile.getSafeFileName(subFolder) + File.separator
				+ cal.get(Calendar.YEAR) + File.separator + (cal.get(Calendar.MONTH) + 1) + File.separator
				+ cal.get(Calendar.DATE) + File.separator + cal.get(Calendar.MILLISECOND);
		String uploadPathReturn = File.separator + UFile.getSafeFileName(subFolder) + File.separator
				+ cal.get(Calendar.YEAR) + File.separator + (cal.get(Calendar.MONTH) + 1) + File.separator
				+ cal.get(Calendar.DATE) + File.separator + cal.get(Calendar.MILLISECOND);
		File udir = new File(uploadPath);
		if (!udir.exists()) {
			udir.mkdirs();
		}
		try (OutputStream out = new FileOutputStream(udir.getAbsolutePath() + File.separator + safeFileName)) {
			wb.write(out);
			out.close();
		}
		return uploadPathReturn + File.separator + safeFileName;

	}
	
	static public String customUploadFix(Workbook wb, String fileName, String folder) throws Exception {
		long currentTime = System.currentTimeMillis();
		
		String safeFileName = UString.getSafeFileName(fileName);
		String uploadPath = folder + currentTime + "_" + safeFileName;
		File udir = new File(uploadPath);
		try (OutputStream out = new FileOutputStream(udir)) {
			wb.write(out);
			out.close();
		}
		return uploadPath;
	}
	
	public static HSSFCellStyle createStyleForParentTitle(HSSFSheet sheet) {
		HSSFFont font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		font.setColor((short) HSSFColor.RED.index);
		HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setFont(font);
		style.setWrapText(true);

		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}

	public static HSSFCellStyle createStyleForParent2Title(HSSFSheet sheet) {
		HSSFFont font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}


	public static HSSFCellStyle createStyle(HSSFSheet sheet) {
		HSSFFont font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}
	/**
	 * create style all sheet
	 * @param sheet
	 * @return
	 */
	public static CellStyle createStyleFix(Sheet sheet) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		CellStyle style = sheet.getWorkbook().createCellStyle();
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}
	
	public static HSSFCellStyle createStyleNumber(HSSFSheet sheet) {
		HSSFFont font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HorizontalAlignment.RIGHT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}
	

	public static HSSFCellStyle createStyleDate(HSSFSheet sheet) {
		HSSFFont font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}
	public static HSSFCellStyle createStyleSTT(HSSFSheet sheet) {
		HSSFFont font = sheet.getWorkbook().createFont();
		font.setBold(true);
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		font.setColor((short) HSSFColor.BLACK.index);
		HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);

		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}
	
	public static HSSFCellStyle createStyleError(HSSFSheet sheet) {
		HSSFFont font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		font.setColor((short) HSSFColor.RED.index);
		HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}
	
	public static String validData(String param) {
		if (isNullOrBlank(param)) {
			return "";
		}
		return param;
	}
	
	public static Date validDateData(Date param) {
		if (param != null) {
			return param;
		}
		return new Date();
	}
	
	public static Long validNumberData(Long param) {
		if (param != null) {
			return param;
		}
		return 0L;
	}

	public static Double validDoubleData(Double param) {
		if (param != null) {
			return param;
		}
		return 0d;
	}
	
	public static String getCode(String input, int length) {
		String format = "%0" + length + "d";
		try{
			return String.format(format, Integer.parseInt(input) + 1);
		}catch(Exception e){
			LOGGER.error(e.getCause(),e);
			return String.format(format, 1);
		}
	}
	
	public static String convertLongToString(Long input) {
		try{
			if(input == null){
				return "";
			}
			return input.toString();
		}catch(Exception e){
			LOGGER.error(e.getCause(),e);
			return "";
		}
	}
	/**
	 * check number import
	 * @param param
	 * @return
	 */
	public static boolean isNotNumberImport(String param) {
		if(!StringUtils.isNotEmpty(param)) return true;
		try {
			Double.parseDouble(param);
			return false;
		} catch (Exception e) {
			return true;
		}
	}
	
	public static boolean isNotDateImport(String param) {
		if(!StringUtils.isNotEmpty(param)) return true;
		try {
			return !DateUtils.validDateFormat(param);
		} catch (Exception e) {
			LOGGER.error(e.getCause(),e);
			return true;
		}
	}
	
	/**
	 * check null
	 * @param param
	 * @return
	 */
	public static boolean isNullImport(String param) {
		if (AgencyCommonUtil.isNullOrBlank(param)) {
			return true;
		}
		return false;
	}
	/**
	 * creat combobox in file excel
	 * @param wb
	 * @param sheet
	 * @param nameSheet
	 * @param index
	 * @param projectList
	 * @param addressList
	 */
	public static void bindComboboxXFFS(Workbook wb, Sheet sheet,String nameSheet, int index ,ArrayList<String> projectList ,CellRangeAddressList addressList) {
		if(projectList !=null && projectList.size() > 0){
			DataValidationHelper helper = new XSSFDataValidationHelper((XSSFSheet) sheet);
			String[] listTring = convertToArray(projectList, String.class);
			DataValidationConstraint constraint = helper.createExplicitListConstraint(listTring);
			DataValidation validation = helper.createValidation(constraint, addressList);
			validation.setSuppressDropDownArrow(true); 
			sheet.addValidationData(validation);
		}
	}
	
	public static void bindComboboxHFFS(Workbook wb, Sheet sheet,String nameSheet, int index ,List<String> projectList ,CellRangeAddressList addressList) {
		HSSFSheet hidden = (HSSFSheet) wb.createSheet(nameSheet);
		for (int i = 0, length = projectList.size(); i < length; i++) {
			String name = projectList.get(i);
			Row row = hidden.createRow(i);
			Cell cell = row.createCell(0);
			cell.setCellValue(name);
		}
		Name namedCell = wb.createName();
		namedCell.setNameName(nameSheet);
		namedCell.setRefersToFormula("hidden!$A$1:$A$" + projectList.size());
		DVConstraint constraint = DVConstraint.createFormulaListConstraint("hidden");
		HSSFDataValidation validation = new HSSFDataValidation(addressList, constraint);
		wb.setSheetHidden(index, true);
		sheet.addValidationData(validation);
	}
	public static Double convertStringToDouble(String input){
		try{
			return Double.parseDouble(input);
		}catch(Exception e){
			LOGGER.error(e.getCause(),e);
			return 0d;
		}
	}
	/**
	 * du lieu lay ve khong du o text
	 * @param index
	 * @param data
	 * @return
	 */
	public static List<String> convertNullListDataExcel(int index , String[] data){
		ArrayList<String> listData = new ArrayList<String>();
		String value;
		int length = data.length;
		for( int i = 0 ; i < index ; i++){
			value ="";
			if(i< length){
				value = data[i];
			}
			listData.add(value);
		}
		return listData;
	}
	/**
	 * co
	 * @param al
	 * @param clazz
	 * @return
	 */
	public static <T> T[] convertToArray(ArrayList<T> al, Class clazz) {
	    return (T[]) al.toArray((T[])Array.newInstance(clazz, al.size()));
	}
	/**
	 * check empty for list
	 * @param list
	 * @return
	 */
	public static <T> boolean isListEmpty(List<T> list){
		boolean result = false;
		if(list == null || list.size() == 0){
			result = true;
		}
		return result;
	}
	/**
	 * 
	 * @param input
	 * @return
	 */ 
	public static String convertStringExcel(String input){
		char oldChar = '\"';
		return input.replace(oldChar, '\'');
	}
	public static boolean checkAndSetCell(Row row,  int index ,String data ,HSSFCellStyle style ,HSSFCellStyle styleError ,Integer typeCheck){
		Cell cell;
		HSSFCellStyle styleValue = style;
		String value = data;
		boolean result = false;
		if(typeCheck.equals(AgencyCommonUtil.CHECK_NULL) && AgencyCommonUtil.isNullImport(data)){
			value = AgencyCommonUtil.ERROR_NULL;
			result = true;
			styleValue = styleError ;
		}
		if(typeCheck.equals(AgencyCommonUtil.CHECK_NUMBER) && AgencyCommonUtil.isNotNumberImport(data)){
			value = AgencyCommonUtil.ERROR_NOT_FEILD;
			result = true;
			styleValue = styleError ;
		}
		cell = row.createCell(index, CellType.STRING);
        cell.setCellValue(value);
        cell.setCellStyle(styleValue);
        return result;
	}
	
	public static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}
	
	
	public static boolean validDoubleDataNotEmty(Double param) {
		if (param != null && param>0) {
			return true;
		}
		return false;
	}
	
	public static boolean isPdf(byte[] data) {
	    if (data != null && data.length > 4
	            && data[0] == 0x25 && // %
	            data[1] == 0x50 && // P
	            data[2] == 0x44 && // D
	            data[3] == 0x46 && // F
	            data[4] == 0x2D) { // -

	        // version 1.3 file terminator
	        if (//data[5] == 0x31 && data[6] == 0x2E && data[7] == 0x33 &&
	                data[data.length - 7] == 0x25 && // %
	                data[data.length - 6] == 0x25 && // %
	                data[data.length - 5] == 0x45 && // E
	                data[data.length - 4] == 0x4F && // O
	                data[data.length - 3] == 0x46 && // F
	                data[data.length - 2] == 0x20 // SPACE
	                //&& data[data.length - 1] == 0x0A// EOL
	                ) {
	            return true;
	        }

	        // version 1.3 file terminator
	        if (//data[5] == 0x31 && data[6] == 0x2E && data[7] == 0x34 &&
	                data[data.length - 6] == 0x25 && // %
	                data[data.length - 5] == 0x25 && // %
	                data[data.length - 4] == 0x45 && // E
	                data[data.length - 3] == 0x4F && // O
	                data[data.length - 2] == 0x46 // F
	                //&& data[data.length - 1] == 0x0A // EOL
	                ) {
	            return true;
	        }
	    }
	    return false;
	}

	//TungBT Custom Style XSSF Mode
	public static XSSFCellStyle styleTextXSSF(XSSFSheet sheet){
		XSSFFont font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		XSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}

	//TungBT Custom Style XSSF Mode
	public static CellStyle fontBoldRedXSSF(XSSFSheet sheet){
		XSSFFont fontBold = sheet.getWorkbook().createFont();
		fontBold.setFontHeightInPoints((short) 12);
		fontBold.setFontName("Times New Roman");
		fontBold.setColor(IndexedColors.RED.getIndex());
		fontBold.setBold(true);
		fontBold.setItalic(false);
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(fontBold);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setWrapText(true);
		return cellStyle;
	}

	public static CellStyle fontBoldBlackXSSF(XSSFSheet sheet){
		XSSFFont fontBold = sheet.getWorkbook().createFont();
		fontBold.setFontHeightInPoints((short) 12);
		fontBold.setFontName("Times New Roman");
		fontBold.setColor(IndexedColors.BLACK.getIndex());
		fontBold.setBold(true);
		fontBold.setItalic(false);
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(fontBold);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setWrapText(true);
		return cellStyle;
	}
	
	//TungBT Custom Style XSSF Mode
	public static XSSFCellStyle styleTextTitleXSSF(XSSFSheet sheet){
		XSSFFont font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		XSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}
	
	//TungBT Custom Style XSSF Mode
	public static XSSFCellStyle styleNumberXSSF(XSSFSheet sheet) {
		XSSFFont font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		XSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HorizontalAlignment.RIGHT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}

	//TungBT Custom Style XSSF Mode
	public static XSSFCellStyle styleDateXSSF(XSSFSheet sheet) {
		XSSFFont font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		XSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}
	
	//TungBT Custom Style XSSF Mode
	public static XSSFCellStyle stypeTitle(XSSFSheet sheet) {
		XSSFFont font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setBold(true);
		font.setFontHeight((short) 240);
		XSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		return style;
	}

	//TungBT Custom Style XSSF Mode
	public static XSSFCellStyle stypeHeader(XSSFSheet sheet) {
		XSSFFont font = sheet.getWorkbook().createFont();
		font.setFontName("Calibri");
		font.setFontHeight((short) 220);
		XSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		return style;
	}
	
	public static CellStyle createErrorCellStyle(Workbook workbook) {
		Font fontBold = workbook.createFont();
		fontBold.setFontHeightInPoints((short) 12);  
		fontBold.setFontName("Times New Roman");  
		fontBold.setColor(IndexedColors.RED.getIndex());  
		fontBold.setBold(true);  
		fontBold.setItalic(false);  
		CellStyle errCellStyle = workbook.createCellStyle();  
		errCellStyle.setFont(fontBold);  
		errCellStyle.setAlignment(HorizontalAlignment.LEFT);  
		errCellStyle.setWrapText(true);
		return errCellStyle;
	}
	
	public static CellStyle styleNumber(Sheet sheet) {
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		CellStyle style = sheet.getWorkbook().createCellStyle();
		style.setAlignment(HorizontalAlignment.RIGHT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}
	
	public static CellStyle styleText(Sheet sheet){
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setFontHeight((short) 240);
		CellStyle style = sheet.getWorkbook().createCellStyle();
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(font);
		style.setWrapText(true);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		return style;
	}
}



