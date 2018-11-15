package com.baoviet.agency.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class AgencyResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
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
}