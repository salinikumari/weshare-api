package com.weshare.exception;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.weshare.dto.ErrorDto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
   // @Validated For Validating Path Variables and Request Parameters (Used in Login validation)
   @ExceptionHandler(ConstraintViolationException.class)
   public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) 
   {
	   	String errors = ""; 
	    for (ConstraintViolation<?> violation : ex.getConstraintViolations())
	    {
	        errors += violation.getMessage() + " ";
	    }

	    return new ResponseEntity<>(new ErrorDto(HttpStatus.BAD_REQUEST.value(), errors), HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler(value = UserNotFoundException.class)
   public ResponseEntity<Object> handleException(UserNotFoundException exception) 
   {
      return new ResponseEntity<>(new ErrorDto(HttpStatus.NOT_FOUND.value(), "User id not found"), HttpStatus.NOT_FOUND);
   }

   //@Valid for validating RequestBody object (Used to post a new message)
   @Override
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                HttpHeaders headers,
                                HttpStatus status, WebRequest request) 
   {
       //Get all fields errors
       List<String> errors = ex.getBindingResult()
               .getFieldErrors()
               .stream()
               .map(x -> x.getDefaultMessage())
               .collect(Collectors.toList());

       return new ResponseEntity<>(new ErrorDto(status.value(), errors.toString()), status);
   }
}
