package com.neosoft.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.neosoft.exception.EmployeeNotFoundException;
import com.neosoft.model.ExceptionType;

@RestControllerAdvice
public class EmployeeExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ExceptionType> employeeNotFound(EmployeeNotFoundException excep) {

		return new ResponseEntity<>(
				new ExceptionType("404", new Date(System.currentTimeMillis()).toString(), excep.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> customeValidationHandler(MethodArgumentNotValidException excep) {

		ExceptionType exceptionType = new ExceptionType("400", new Date(System.currentTimeMillis()).toString(),
				excep.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<>(exceptionType, HttpStatus.BAD_REQUEST);

	}

}
