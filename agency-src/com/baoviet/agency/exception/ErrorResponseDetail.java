package com.baoviet.agency.exception;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class ErrorResponseDetail {
	private Date timestamp;
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String message;
	private String details;
	private String stackTrace;
	private String errorCode;
	private String fieldName;
	private Exception exception;

	public ErrorResponseDetail(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
	public ErrorResponseDetail(Date timestamp, String message, String details, Exception exception) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.exception = exception;
	}
	
	public ErrorResponseDetail(Date timestamp, String message, String details, AgencyBusinessException exception) {
		super();
		this.timestamp = timestamp;
		this.message = message;

		this.stackTrace = exception.getStackTrace()[0].toString();
		this.errorCode = exception.getErrorCode().toString();
		this.fieldName = exception.getFieldName();
	}
}
