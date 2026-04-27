package org.fermented.dairy.app.server.concurrency.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// A utility class for logging within the threading module.
///
/// This class provides a centralized logger for threading-related events.
///
/// @since 1.0.0
public final class LOGGING {

    /// The topic name used for threading-related logs.
    /// @since 1.0.0
    public static final String THREADS_LOGGING_TOPIC = "fd.app.server.threading";

    /// The topic name used for threading futures-loggin related logs.
    /// @since 1.0.0
    public static final String THREADS_FUTURES_LOGGING_TOPIC = "fd.app.server.threading.futures";

    /// The logger instance for threading-related events.
    /// @since 1.0.0
    public static final Logger THREADS_LOGGER = LoggerFactory.getLogger(THREADS_LOGGING_TOPIC);

    /// The logger instance for threading futures-related events.
    /// @since 1.0.0
    public static final Logger THREADS_FUTURES_LOGGER = LoggerFactory.getLogger(THREADS_FUTURES_LOGGING_TOPIC);

    private LOGGING(){}

}
