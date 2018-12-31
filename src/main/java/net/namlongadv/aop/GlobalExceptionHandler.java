package net.namlongadv.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.namlongadv.constant.Constants;
import net.namlongadv.dto.GenericResponse;
import net.namlongadv.exceptions.AuthenticationException;
import net.namlongadv.exceptions.AuthorizationException;
import net.namlongadv.exceptions.BadRequestException;
import net.namlongadv.exceptions.BusinessException;
import net.namlongadv.exceptions.CustomError;

@ControllerAdvice(basePackages = {"net.namlongadv"})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	private static final String ERROR_MGS = "GlobalExceptionHandler {}";

	@ResponseBody
	@ExceptionHandler(value = { Exception.class, BusinessException.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<GenericResponse> exception(Exception ex) {
		LOG.error(ERROR_MGS, ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new GenericResponse(ERROR_MGS + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
	}

	@ResponseBody
	@ExceptionHandler(value = { BadRequestException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<GenericResponse> badRequestException(BadRequestException ex) {
		LOG.error(ERROR_MGS, ex.getMessage(), ex);
		CustomError customError = ex.getCustomError();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new GenericResponse(null != customError ? customError : Constants.EMPTY));
	}

	@ResponseBody
	@ExceptionHandler(value = { AuthorizationException.class, AuthenticationException.class })
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ResponseEntity<GenericResponse> authorizationException(AuthorizationException ex) {
		LOG.error(ERROR_MGS, ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new GenericResponse(HttpStatus.FORBIDDEN));
	}

}
