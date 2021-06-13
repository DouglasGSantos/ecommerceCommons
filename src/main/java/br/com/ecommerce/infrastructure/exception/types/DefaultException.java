package br.com.ecommerce.infrastructure.exception.types;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import br.com.ecommerce.infrastructure.exception.dto.MessageDto;

public abstract class DefaultException extends RuntimeException {

    private static final long serialVersionUID = 4220212442387105196L;

    protected DefaultException() {
        this(null, null);
    }

    protected DefaultException(final String message) {
        super(message);
    }

    protected DefaultException(final Throwable cause) {
        super(cause);
    }

    protected DefaultException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MessageDto buildResponse() {
        final MessageDto responseDefault = new MessageDto();        
        responseDefault.setMessage(getMensagem());
        responseDefault.setDetails(this.getLocalizedMessage());
        responseDefault.setErrors(getAdditionalMessages());
        return responseDefault;
    }

    public abstract HttpStatus getStatusCode();

    public List<MessageDto> getAdditionalMessages() {
        return new ArrayList<>();
    }

    protected Integer getCodigo() {
        return getStatusCode().value();
    }

    protected String getMensagem() {
        return getMessage();
    }
}
