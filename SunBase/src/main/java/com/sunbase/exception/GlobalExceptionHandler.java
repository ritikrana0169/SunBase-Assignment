package com.sunbase.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetail> handleBadCredentialsException(BadCredentialsException ex, WebRequest wr) {
		ErrorDetail ed = new ErrorDetail( wr.getDescription(false), ex.getMessage());
		return new ResponseEntity<ErrorDetail>(ed, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetail> handleException(Exception ex, WebRequest wr) {
		ErrorDetail ed = new ErrorDetail( wr.getDescription(false), ex.getMessage());
		return new ResponseEntity<ErrorDetail>(ed, HttpStatus.BAD_REQUEST);
	}

}