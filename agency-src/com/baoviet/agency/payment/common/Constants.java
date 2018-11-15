package com.baoviet.agency.payment.common;

public interface Constants {
	static final String PAY = "PAY";
	static final String TRANS_INQUIRY = "TRANS_INQUIRY";
	static final String PAYMENT_VIETTEL_STATUS_SUCCESS = "1";
	static final String PAYMENT_VNPAY_STATUS_SUCCESS = "00";
	static final String PAYMENT_MOMO_STATUS_SUCCESS = "0";
	
	static final String MOMO_PARAM_PARTNER_CODE = "partnerCode";
	static final String MOMO_PARAM_ACCESS_KEY = "accessKey";
	static final String MOMO_PARAM_REQUEST_ID = "requestId";
	static final String MOMO_PARAM_AMOUNT = "amount";
	static final String MOMO_PARAM_ORDER_ID = "orderId";
	static final String MOMO_PARAM_ORDER_INFO = "orderInfo";
	static final String MOMO_PARAM_ORDER_TYPE = "orderType";
	static final String MOMO_PARAM_TRANS_ID = "transId";
	static final String MOMO_PARAM_MESSAGE = "message";
	static final String MOMO_PARAM_LOCAL_MESSAGE = "localMessage";
	static final String MOMO_PARAM_RESPONSE_TIME = "responseTime";
	static final String MOMO_PARAM_ERROR_CODE = "errorCode";
	static final String MOMO_PARAM_PAY_TYPE = "payType";
	static final String MOMO_PARAM_EXTRA_DATA = "extraData";
	static final String MOMO_PARAM_SIGNATURE = "signature";
	
	static final String VIETTEL_PAY_PARAM_AMOUNT = "amount";
	static final String VIETTEL_PAY_PARAM_COMMAND = "command";
	static final String VIETTEL_PAY_PARAM_MERCHANT_CODE = "merchantCode";
	static final String VIETTEL_PAY_PARAM_MERCHANT_TRANS_ID = "merchantTransId";
	static final String VIETTEL_PAY_PARAM_RESPONSE_CODE = "responseCode";
	static final String VIETTEL_PAY_PARAM_VERSION = "version";
	static final String VIETTEL_PAY_PARAM_SECURE_HASH = "secureHash";
	
	static final String VNPAY_PARAM_AMOUNT = "vnp_Amount";
	static final String VNPAY_PARAM_BANK_CODE = "vnp_BankCode";
	static final String VNPAY_PARAM_BANK_TRAN_NO = "vnp_BankTranNo";
	static final String VNPAY_PARAM_CARD_TYPE = "vnp_CardType";
	static final String VNPAY_PARAM_ORDER_INFO = "vnp_OrderInfo";
	static final String VNPAY_PARAM_PAY_DATE = "vnp_PayDate";
	static final String VNPAY_PARAM_RESPONSE_CODE = "vnp_ResponseCode";
	static final String VNPAY_PARAM_TMN_CODE = "vnp_TmnCode";
	static final String VNPAY_PARAM_TRANSACTION_NO = "vnp_TransactionNo";
	static final String VNPAY_PARAM_TXN_REF = "vnp_TxnRef";
	static final String VNPAY_PARAM_SECURE_HASH = "vnp_SecureHash";
	static final String VNPAY_PARAM_VERSION = "vnp_Version";
	static final String VNPAY_PARAM_COMMAND = "vnp_Command";
	static final String VNPAY_PARAM_MERCHANT = "vnp_Merchant";
	static final String VNPAY_PARAM_TRANS_DATE = "vnp_TransDate";
	static final String VNPAY_PARAM_CREATE_DATE = "vnp_CreateDate";
	static final String VNPAY_PARAM_IP_ADDR = "vnp_IpAddr";
	static final String VNPAY_PARAM_TRANSACTION_STATUS = "vnp_TransactionStatus";
}
