package com.example.coffee.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton holder for properties.
 */
public class PropertiesUtils {

    private static Properties properties;

    public static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try (InputStream fis = PropertiesUtils.class.getClassLoader().getResourceAsStream("config.properties")) {
                properties.load(fis);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return properties;
    }
}
