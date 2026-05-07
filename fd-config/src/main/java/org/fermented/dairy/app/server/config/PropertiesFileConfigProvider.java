package org.fermented.dairy.app.server.config;

import org.fermented.dairy.app.server.config.exceptions.ConfigurationRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static org.fermented.dairy.app.server.config.logging.LOGGING.LOGGER;

/// A configuration provider that loads properties from a file.
///
/// This provider reads configuration from a file named `configuration.properties`
/// located in the classpath.
///
/// @since 1.0.0
public class PropertiesFileConfigProvider implements ConfigProvider {

    private static final String CONFIG_FILE = "configuration.properties";
    private final Properties properties = new Properties();

    /// Initializes the provider by loading properties from the classpath.
    ///
    /// @since 1.0.0
    public PropertiesFileConfigProvider() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            } else {
                LOGGER.warn("Unable to find configuration file: {}", CONFIG_FILE);
            }
        } catch (IOException e) {
            LOGGER.error("Exception caught while reading configuration file: ", e);
        }
    }

    /// Retrieves a value from the loaded properties.
    ///
    /// @param key The configuration key to look up.
    /// @return An [Optional] containing the configuration value if found, otherwise an empty [Optional].
    @Override
    public Optional<String> getValue(String key) {
        return Optional.ofNullable(properties.getProperty(key));
    }

    /// Gets the priority of this provider.
    ///
    /// @return A lower priority value representing the default priority.
    @Override
    public int getPriority() {
        return Integer.MAX_VALUE - 1; // Default priority
    }

    public static class ConfigFileReadException extends ConfigurationRuntimeException {
        public ConfigFileReadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
