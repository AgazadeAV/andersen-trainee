package com.andersenhotels.model.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class ConfigManager {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
                System.out.println("Configuration loaded successfully.");
            } else {
                System.err.println("Configuration file '" + CONFIG_FILE + "' not found.");
            }
        } catch (Exception e) { //TODO ИСПОЛЬЗОВАТЬ СПЕЦИФИЧНОЕ ИСКЛЮЧЕНИЕ
            System.err.println("Error loading configuration file: " + e.getMessage());
        }
    }

    public static String getStateFilePath() {
        String configuredPath = properties.getProperty("stateFilePath");
        if (configuredPath == null) {
            throw new IllegalStateException("stateFilePath is not configured in config.properties");
        }

        File file = new File(configuredPath);

        if (!file.isAbsolute()) {
            String baseDir = System.getProperty("catalina.base", System.getProperty("user.dir"));
            file = new File(baseDir, configuredPath);
        }

        return file.getAbsolutePath();
    }

    public static boolean isAllowApartmentStatusChange() {
        return Optional.ofNullable(properties.getProperty("allowApartmentStatusChange"))
                .map(Boolean::parseBoolean)
                .orElse(false);
    }

    public static int getPageSizeForPagination() {
        return Optional.ofNullable(properties.getProperty("pageSizeForPagination"))
                .map(Integer::parseInt)
                .orElse(10);
    }
}
