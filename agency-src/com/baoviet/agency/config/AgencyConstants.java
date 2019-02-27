package com.baoviet.agency.config;

import java.util.Date;

import com.baoviet.agency.utils.DateUtils;

/**
 * Application constants.
 */
public final class AgencyConstants {

    //Regex for acceptable logins
//    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";
//
//    public static final String SYSTEM_ACCOUNT = "system";
//    public static final String ANONYMOUS_USER = "anonymoususer";
//
//    public static final String formatterDateText = "dd/MM/yyyy";
//    public static final String formatterDateTimeText = "dd/MM/yyyy HH:mm:ss";
//    
//    public static final String SUCCESS = "Success";
    
	public class TVC {
		public static final String PACKAGE_CA_NHAN = "1";
		public static final String PACKAGE_GIA_DINH = "2";
		public static final String PACKAGE_KHACH_DOAN = "3";
	}
	
	public class RELATIONSHIP {
		public static final String BAN_THAN = "30";
		public static final String VO_CHONG = "31";
		public static final String CON = "32";
		public static final String BO_ME = "33";
		
		public static final String BO_ME_CUA_VO_CHONG = "34";
		public static final String ANH_CHI_EM_RUOT = "35";
		public static final String ANH_CHI_EM_RUOT_CUA_VO_CHONG = "36";
		public static final String ONG_BA = "37";
		public static final String CHAU = "38";
		public static final String KHACH_DOAN = "39";
		public static final String GIOI_THIEU = "41";
		public static final String KHAC = "99";
		public static final String KHONG_XAC_DINH = "00";
	}
	
	public class EXCEL {
		// POTENTIAL, FAMILIAR, VIP, ORGANIZATION
		public static final String TEMPLATE_NAME_TVC = "TVC_Template.xls";
		public static final String TEMPLATE_NAME_MOTO = "MOTO_Template.xls";
		public static final String IMPORT_NAME_TVC_ERROR = "Imp_TVC_Data_Error.xls";
		public static final String EXPORT_NAME_TVC = "Export_TVC_Data.xls";
		public static final String EXPORT_NAME_MOTO = "Export_Moto_Data.xls";
	}
	
	public class CONTACT_GROUP_TYPE {
		// POTENTIAL, FAMILIAR, VIP, ORGANIZATION
		public static final String POTENTIAL = "POTENTIAL";
		public static final String FAMILIAR = "FAMILIAR";
		public static final String VIP = "VIP";
		public static final String ORGANIZATION = "ORGANIZATION";
	}
	
	public class CONTACT_CATEGORY_TYPE {
		// POTENTIAL, FAMILIAR, VIP, ORGANIZATION
		public static final String PERSON = "PERSON";
		public static final String ORGANIZATION = "ORGANIZATION";
	}
	
	public class ATTACHMENT_GROUP_TYPE {
		public static final String OFFLINE_GYCBH = "OFFLINE_GYCBH";
		public static final String OFFLINE_GIAY_KHAI_SINH = "OFFLINE_GIAY_KHAI_SINH";
		public static final String OFFLINE_TAI_LIEU_KHAC = "OFFLINE_TAI_LIEU_KHAC";
		
		public static final String ANCHI_GCN = "ANCHI_GCN";
		public static final String ANCHI_TAI_LIEU_KHAC = "ANCHI_TAI_LIEU_KHAC";
		
		public static final String ONLLINE_BVP = "ONLLINE_BVP";
	}
	
	public class OrderHistory {
		public static final String VIEW_HISTORY = "0";
		public static final String VIEW_CONVERSATION = "1";
	}
	
    public static final String ROLE_AGENCY = "ROLE_AGENCY";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_BAOVIET = "ROLE_BAOVIET";
    
    public static final String KENH_PHAN_PHOI_AGENCY = "AGENCY";
    
    public static final String DEFAULT_STRING_VALUE = " ";
    public static final double DEFAULT_DOUBLE_VALUE = 0d;
    public static final int DEFAULT_INTEGER_VALUE = 0;
    public static final boolean DEFAULT_BOOLEAN_VALUE = false;
    public static final Date DEFAULT_DATE_NOW_VALUE = new Date();
    public static final Date DEFAULT_DOB_VALUE = DateUtils.str2Date("01/01/0001");
    
    public static final String PAYMENT_METHOD_LATER = "PAYMENT_LATER";
    
    private AgencyConstants() {
    }
    
    public class AgreementStatus {
    	public static final String HUY_DON_BAO_HIEM = "89";
    	public static final String CHUA_THANH_TOAN = "90";
    	public static final String DA_THANH_TOAN = "91";
    	public static final String THANH_TOAN_SAU = "91_1";
    	public static final String CAN_GIAM_DINH = "92";
    	public static final String YEU_CAU_GIAM_DINH = "93";
    	public static final String YEU_CAU_THANH_TOAN_TIEN_MAT = "94";
    	public static final String YEU_CAU_GAP_KHACH_HANG = "95";
    	public static final String CHAP_NHAN = "97";
    	public static final String DA_CHUYEN_BAN_CUNG = "98";
    	public static final String TU_CHOI_BAO_HIEM = "99";
    	public static final String HOAN_THANH = "100";    	
    }
}
