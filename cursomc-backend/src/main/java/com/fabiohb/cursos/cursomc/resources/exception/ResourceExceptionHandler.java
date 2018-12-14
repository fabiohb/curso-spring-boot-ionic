package com.fabiohb.cursos.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fabiohb.cursos.cursomc.services.exceptions.DataIntegrityException;
import com.fabiohb.cursos.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotfound(ObjectNotFoundException e, HttpServletRequest request) {
		return toResponse(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> objectNotfound(DataIntegrityException e, HttpServletRequest request) {
		return toResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	}
	
	private ResponseEntity<StandardError> toResponse(HttpStatus status, String message) {
		return ResponseEntity
				.status(status.value())
				.body(
					new StandardError(
						status.value(), 
						message, 
						System.currentTimeMillis()
					)
				);
	}
	
}
