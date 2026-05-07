package org.fermented.dairy.app.server.config.exceptions;

public class ConfigurationRuntimeException extends RuntimeException {
    public ConfigurationRuntimeException(String message) {
        super(message);
    }

    public ConfigurationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
