package br.com.ecommerce.infrastructure.exception.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("error")
@JsonInclude(Include.NON_EMPTY)
public class MessageDto implements Serializable {

	private static final long serialVersionUID = -2243742480047312690L;

	private String message;
	private String details;
	private List<MessageDto> additionalMessages;

	public MessageDto() {
		super();
		this.additionalMessages = new ArrayList<>();

	}

	public MessageDto(final String mensagem) {
		super();
		this.message = mensagem;
		this.additionalMessages = new ArrayList<>();

	}

	public MessageDto(String message, String details) {
		super();
		this.message = message;
		this.details = details;
		this.additionalMessages = new ArrayList<>();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public List<MessageDto> getErrors() {		
		return additionalMessages;
	}

	public void setErrors(List<MessageDto> errors) {
		this.additionalMessages = errors;
	}

	public void addAdditionalMessages(String addtionalMessage) {
		if (this.additionalMessages == null) {
			this.additionalMessages = new ArrayList<>();
		}

		this.additionalMessages.add(new MessageDto(addtionalMessage));
	}

	public boolean containsErrors() {
		return this.additionalMessages!=null &&   !this.additionalMessages.isEmpty();
	}

}
