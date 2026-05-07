package org.fermented.dairy.app.server.config;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class AppConfigTest {

    @Test
    public void testGetStringFromPropertiesFile() {
        AppConfig config = new AppConfig();
        Optional<String> appName = config.getString("app.name");
        assertTrue(appName.isPresent());
        assertEquals("FD App Server", appName.get());
    }

    @Test
    public void testGetStringFromSystemProperties() {
        System.setProperty("test.property", "testValue");
        AppConfig config = new AppConfig();
        Optional<String> value = config.getString("test.property");
        assertTrue(value.isPresent());
        assertEquals("testValue", value.get());
        System.clearProperty("test.property");
    }

    @Test
    public void testGetStringWithDefaultValue() {
        System.setProperty("test.property.with.default", "actualValue");
        AppConfig config = new AppConfig();

        //config exists
        String value = config.getString("test.property.with.default", "defaultValue");
        assertEquals("actualValue", value);

        //config does not exist
        String missingValue = config.getString("non.existent.property", "defaultValue");
        assertEquals("defaultValue", missingValue);
        
        System.clearProperty("test.property.with.default");
    }
}
