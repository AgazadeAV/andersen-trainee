package com.andersenhotels.model.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class ConfigManager {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.err.println("Configuration file '" + CONFIG_FILE + "' not found in 'src/main/resources'. "
                        + "Please ensure the file exists and is placed correctly.");
            } else {
                properties.load(input);
                System.out.println("Configuration loaded successfully.");
            }
        } catch (IOException e) {
            System.err.println("Error occurred while loading configuration file '" + CONFIG_FILE + "'.");
        }
    }

    public static String getStateFilePath() {
        return Optional.ofNullable(properties.getProperty("stateFilePath"))
                .orElse("");
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
