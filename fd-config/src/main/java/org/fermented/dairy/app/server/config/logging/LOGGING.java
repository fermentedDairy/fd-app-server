package org.fermented.dairy.app.server.config.logging;

import org.slf4j.LoggerFactory;

public final class LOGGING {

    public static final String FD_APP_SERVER_CONFIG_TOPIC = "fd.app.server.config";
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FD_APP_SERVER_CONFIG_TOPIC);

    private LOGGING(){}
}
