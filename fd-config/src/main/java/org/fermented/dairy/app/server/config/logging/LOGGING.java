package org.fermented.dairy.app.server.config.logging;

import org.slf4j.LoggerFactory;

/// Configuration constants and logger instance for the application server logging.
///
/// @since 1.0.0
public final class LOGGING {

    /// The Kafka topic used for application server configuration.
    public static final String FD_APP_SERVER_CONFIG_TOPIC = "fd.app.server.config";

    /// The logger instance for logging configuration-related events.
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FD_APP_SERVER_CONFIG_TOPIC);

    private LOGGING(){}
}
