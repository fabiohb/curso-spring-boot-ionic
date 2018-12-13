package com.fabiohb.cursos.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fabiohb.cursos.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotfound(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError error = new StandardError(
			HttpStatus.NOT_FOUND.value(),
			e.getMessage(),
			System.currentTimeMillis()
		);
		
		return ResponseEntity
				.status(error.getStatus())
				.body(error);
	}
	
}
