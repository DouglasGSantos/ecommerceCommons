package br.com.ecommerce.infrastructure.exception.types;

import org.springframework.http.HttpStatus;

public class NoContentException extends DefaultException {

    private static final long serialVersionUID = 7111383351898153557L;

    public NoContentException() {
        super();
    }

    public NoContentException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NO_CONTENT;
    }
}
