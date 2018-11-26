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
    
    public static final String DEFAULT_STRING_VALUE = " ";
    public static final double DEFAULT_DOUBLE_VALUE = 0d;
    public static final int DEFAULT_INTEGER_VALUE = 0;
    public static final boolean DEFAULT_BOOLEAN_VALUE = false;
    public static final Date DEFAULT_DATE_NOW_VALUE = new Date();
    public static final Date DEFAULT_DOB_VALUE = DateUtils.str2Date("01/01/0001");
    
    public static final String PAYMENT_METHOD = "PAYMENT_LATER";
    
    private AgencyConstants() {
    }
    
    public class AgreementStatus {
    	public static final String HUY_DON_BAO_HIEM = "89";
    	public static final String CHUA_THANH_TOAN = "90";
    	public static final String DA_THANH_TOAN = "91";
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
