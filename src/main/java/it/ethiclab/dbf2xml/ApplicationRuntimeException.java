package it.ethiclab.dbf2xml;

public class ApplicationRuntimeException extends RuntimeException {
    public static final long serialVersionUID = 1L;

    public ApplicationRuntimeException(Throwable cause) {
        super(cause);
    }

    public ApplicationRuntimeException(String message) {
        super(message);
    }
}
