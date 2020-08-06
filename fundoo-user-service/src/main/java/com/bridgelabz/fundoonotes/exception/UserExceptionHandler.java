package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.bridgelabz.fundoonotes.response.ExceptionResponse;

public class UserExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(UserException.class)
	public final ResponseEntity<ExceptionResponse> userException(UserException ex) {
		ExceptionResponse exp = new ExceptionResponse(ex.getCode(), ex.getMessage());
		return ResponseEntity.status(exp.getCode()).body(exp);
	}
	
}