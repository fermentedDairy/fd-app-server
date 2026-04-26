package org.fermented.dairy.app.server.bootstrap.server.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// A utility class for logging within the bootstrapping module.
///
/// This class provides a centralized logger for bootstrapping events.
///
/// @since 1.0.0
public final class LOGGING {

    /// The topic name used for bootstrapping-related logs.
    /// @since 1.0.0
    public static final String BOOTSTRAPPING_LOGGER_TOPIC = "fd.app.server.bootstrap";

    /// The logger instance for bootstrapping-related events.
    /// @since 1.0.0
    public static final Logger BOOTSTRAPPING_LOGGER = LoggerFactory.getLogger(BOOTSTRAPPING_LOGGER_TOPIC);

    private LOGGING(){}
}
