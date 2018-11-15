package com.baoviet.agency.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * This exception is thrown in case of occur exception in application.
 */
@Getter
@Setter
public class AgencyBusinessException extends Exception {

    private static final long serialVersionUID = 1L;
    
    private String fieldName;
    
    private ErrorCode errorCode;

    public AgencyBusinessException(ErrorCode errorCode) {
    	this.errorCode = errorCode;
    }
    
    public AgencyBusinessException(String fieldName, ErrorCode errorCode) {
    	this.fieldName = fieldName;
    	this.errorCode = errorCode;
    }
    
    public AgencyBusinessException(ErrorCode errorCode, String message) {
    	super(message);
    	this.errorCode = errorCode;
    }
    
    public AgencyBusinessException(String fieldName, ErrorCode errorCode, String message) {
    	super(message);
    	this.fieldName = fieldName;
    	this.errorCode = errorCode;
    }
    
    public AgencyBusinessException(String message) {
        
    }

    public AgencyBusinessException(String message, Throwable t) {
        super(message, t);
    }
}
