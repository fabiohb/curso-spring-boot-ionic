package com.fabiohb.cursos.cursomc.resources.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.fabiohb.cursos.cursomc.services.exceptions.AuthorizationException;
import com.fabiohb.cursos.cursomc.services.exceptions.DataIntegrityException;
import com.fabiohb.cursos.cursomc.services.exceptions.FileException;
import com.fabiohb.cursos.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotfound(ObjectNotFoundException e, HttpServletRequest request) {
		return toResponse(HttpStatus.NOT_FOUND, e.getMessage());
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request) {
		return toResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	}
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.valueOf(e.getErrorCode());
		return toResponse(status, e.getMessage());
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request) {
		return toResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest request) {
		return toResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> objectNotfound(DataIntegrityException e, HttpServletRequest request) {
		return toResponse(HttpStatus.BAD_REQUEST, e.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		return toResponse(
			HttpStatus.BAD_REQUEST, 
			"Erro de validação",
			e.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> new FieldMessage(error.getField(), error.getDefaultMessage()))
				.collect(Collectors.toList())
		);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
		return toResponse(HttpStatus.FORBIDDEN, e.getMessage());
	}
	
	private ResponseEntity<StandardError> toResponse(HttpStatus status, String message) {
		return toResponse(
					status,
					new StandardError(
						status.value(), 
						message, 
						System.currentTimeMillis()
					)
				);
	}
	
	private ResponseEntity<StandardError> toResponse(HttpStatus status, String message, List<FieldMessage> fieldMessages) {
		return toResponse(
				status,
				new ValidationError(
					status.value(), 
					message, 
					System.currentTimeMillis(),
					fieldMessages
				)
			);
	}
	
	private ResponseEntity<StandardError> toResponse(HttpStatus status, StandardError standardError) {
		return ResponseEntity
				.status(status.value())
				.body(standardError);
	}
	
}
