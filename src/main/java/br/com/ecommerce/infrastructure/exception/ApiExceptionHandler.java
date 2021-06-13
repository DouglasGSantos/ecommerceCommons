package br.com.ecommerce.infrastructure.exception;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.ecommerce.infrastructure.exception.dto.MessageDto;
import br.com.ecommerce.infrastructure.exception.types.AuthorizationException;
import br.com.ecommerce.infrastructure.exception.types.BusinessException;
import br.com.ecommerce.infrastructure.exception.types.DefaultException;
import br.com.ecommerce.infrastructure.exception.types.InfrastructureException;
import br.com.ecommerce.infrastructure.exception.types.NoContentException;

@RestControllerAdvice
public class ApiExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<MessageDto> businessError(BusinessException ex) {
		LOGGER.info(ex.getMessage(), ex);
		return ResponseEntity.status(ex.getStatusCode()).body(ex.buildResponse());
	}

	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<MessageDto> authorizationException(AuthorizationException ex) {
		LOGGER.info(ex.getMessage(), ex);
		return ResponseEntity.status(ex.getStatusCode()).body(ex.buildResponse());
	}

	@ExceptionHandler(NoContentException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<MessageDto> semResultadoException(NoContentException ex) {
		LOGGER.info(ex.getMessage(), ex);
		return ResponseEntity.status(ex.getStatusCode()).body(ex.buildResponse());
	}

	@ExceptionHandler(InfrastructureException.class)
	public ResponseEntity<MessageDto> infraestruturException(InfrastructureException ex) {
		LOGGER.error(ex.getMessage(), ex);
		return ResponseEntity.status(ex.getStatusCode()).body(ex.buildResponse());
	}

	@ExceptionHandler(DefaultException.class)
	public ResponseEntity<MessageDto> higiaError(DefaultException ex) {
		LOGGER.error(ex.getMessage(), ex);
		return ResponseEntity.status(ex.getStatusCode()).body(ex.buildResponse());
	}


	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<MessageDto> genericExcpetion(Exception ex) {
		LOGGER.error(ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDto(ex.getMessage()));
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_GATEWAY)
	public ResponseEntity<MessageDto> illegalArgumentException(BindException ex) {
		String formatarMensagem = formatarMensagem(ex);
		LOGGER.error(formatarMensagem, ex);
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new MessageDto(formatarMensagem));
	}

	private String formatarMensagem(Throwable ex) {
		BindingResult result = BindException.class.cast(ex).getBindingResult();
		final List<String> erros = result.getFieldErrors().stream().map(FieldError::getField)
				.collect(Collectors.toList());
		return MessageFormat.format("Verfique se os seguintes parâmetros foram devidamente preenchidos: {0}.",
				erros.stream().collect(Collectors.joining(", ")));
	}

}