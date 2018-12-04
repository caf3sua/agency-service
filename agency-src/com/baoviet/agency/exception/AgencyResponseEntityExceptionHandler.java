package com.baoviet.agency.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.thymeleaf.context.Context;

@ControllerAdvice
@RestController
public class AgencyResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		getMeaningfulMessageArgumentNotValid(ex, headers, status, request);
		ErrorResponseDetail errorDetails = new ErrorResponseDetail(new Date(), "Dữ liệu không hợp lệ", ex.getBindingResult().toString());
		// Set field name
		errorDetails.setFieldName(ex.getBindingResult().getFieldError().getField());
		return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AgencyBusinessException.class)
	public final ResponseEntity<ErrorResponseDetail> handleAgencyException(AgencyBusinessException ex,
			WebRequest request) {
		ErrorResponseDetail errorDetails = new ErrorResponseDetail(new Date(), ex.getMessage(),
				request.getDescription(false), ex);
		if (ex.getErrorCode().equals(ErrorCode.AGENCY_NOT_FOUND)) {
			return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponseDetail> handleAllExceptions(Exception ex, WebRequest request) {
		ErrorResponseDetail errorDetails = new ErrorResponseDetail(new Date(), ex.getMessage(),
				request.getDescription(false), ex);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<ErrorResponseDetail> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		ErrorResponseDetail errorDetails = new ErrorResponseDetail(new Date(), "Bạn không có quyền truy cập",
				request.getDescription(false), ex);
		return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
	}
	
	private String getMeaningfulMessageArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ex.getBindingResult();
		List<String> errors = new ArrayList<String>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        errors.add(error.getField() + ": " + error.getDefaultMessage());
	    }
	    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	        errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	    }
	    
        String subject = messageSource.getMessage("tvc.param.expiredDate.empty", null, new Locale("vn"));
		
		return "Dữ liệu không hợp lệ";
	}
}