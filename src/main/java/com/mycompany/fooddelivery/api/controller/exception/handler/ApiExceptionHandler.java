package com.mycompany.fooddelivery.api.controller.exception.handler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.mycompany.fooddelivery.api.controller.exception.handler.Problem.Field;
import com.mycompany.fooddelivery.core.validation.exception.ValidationException;
import com.mycompany.fooddelivery.domain.exception.BusinessException;
import com.mycompany.fooddelivery.domain.exception.EntityInUseException;
import com.mycompany.fooddelivery.domain.exception.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String END_USER_GENERIC_ERROR_MESSAGE = "An unexpected internal system error has occurred. "
			+ "Try again and if the problem persists, contact your system administrator.";

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = END_USER_GENERIC_ERROR_MESSAGE;

		e.printStackTrace();

		Problem problem = createProblemBuilder(status, problemType, detail, detail, null);

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException e, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.BUSINESS_ERROR;
		String detail = e.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail, detail, null);

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<?> handleEntityInUseException(EntityInUseException e, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTITY_IN_USE;
		String detail = e.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail, detail, null);

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}

		ProblemType problemType = ProblemType.INCOMPREENSIBLE_MESSAGE;
		String detail = "The request body is invalid. Please, check syntax error.";

		Problem problem = createProblemBuilder(status, problemType, detail, detail, null);
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		ProblemType problemType = ProblemType.INCOMPREENSIBLE_MESSAGE;
		String detail = String.format(
				"The property '%s' received the value '%s', "
						+ "which is of an invalid type. Correct and enter a value compatible with the type %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail, END_USER_GENERIC_ERROR_MESSAGE, null);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		ProblemType problemType = ProblemType.INCOMPREENSIBLE_MESSAGE;
		String detail = String
				.format("The property '%s' does not exist. " + "Correct or remove this property and try again.", path);

		Problem problem = createProblemBuilder(status, problemType, detail, END_USER_GENERIC_ERROR_MESSAGE, null);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status,
					request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.INVALID_PARAMETER;

		String detail = String.format(
				"The URL parameter '%s' received the value '%s', "
						+ "which is an invalid type. Correct and enter a value compatible with the type %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail, detail, null);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());

		Problem problem = createProblemBuilder(status, problemType, detail, detail, null);

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.SYSTEM_ERROR;
		String detail = END_USER_GENERIC_ERROR_MESSAGE;
		ex.printStackTrace();

		Problem problem = createProblemBuilder(status, problemType, detail, detail, null);

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}

	@ExceptionHandler({ ValidationException.class })
	public ResponseEntity<Object> handleValidacaoException(ValidationException ex, WebRequest request) {
	    return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), 
	            HttpStatus.BAD_REQUEST, request);
	}   
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		        
	    ProblemType problemType = ProblemType.INVALID_DATA;
	    String detail = "One or more fields are invalid. Fill in correctly and try again.";
	    
	    List<Problem.Field> problemObjects = bindingResult.getAllErrors().stream()
	            .map(objectError -> {
	                String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
	                
	                String name = objectError.getObjectName();
	                
	                if (objectError instanceof FieldError) {
	                    name = ((FieldError) objectError).getField();
	                }
	                
	                return Problem.Field.builder()
	                    .name(name)
	                    .userMessage(message)
	                    .build();
	            })
	            .collect(Collectors.toList());
	    
	    Problem problem = createProblemBuilder(status, problemType, detail, detail, problemObjects);
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	// ---------- Global exception
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problem.builder()
					.timestamp(OffsetDateTime.now())
					.title(status.getReasonPhrase())
					.status(status.value())
					.userMessage(END_USER_GENERIC_ERROR_MESSAGE).build();
		} else if (body instanceof String) {
			body = Problem.builder().timestamp(OffsetDateTime.now()).title((String) body).status(status.value())
					.userMessage(END_USER_GENERIC_ERROR_MESSAGE).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem createProblemBuilder(HttpStatus status, ProblemType problemType, String detail, String userMessage,
			List<Field> fields) {

		return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle())
				.detail(detail).userMessage(userMessage).timestamp(OffsetDateTime.now()).fields(fields).build();
	}

	private String joinPath(List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}

}
