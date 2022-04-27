package tech.theraven.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();
    static {
        loadProperties();
    }

    private PropertiesUtil() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (java.io.InputStream inputStream = new FileInputStream("./src/test/resources/application-test.properties")) {
            PROPERTIES.load(inputStream);
        } catch ( IOException e) {
            throw new RuntimeException(e);
        }
    }
}
