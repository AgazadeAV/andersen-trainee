package com.andersenhotels.model.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Configuration file 'config.properties' not found in 'src/main/resources'. "
                        + "Please ensure the file exists and is placed correctly.");
            } else {
                properties.load(input);
                System.out.println("Configuration loaded successfully.");
            }
        } catch (IOException e) {
            System.err.println("Error occurred while loading configuration file 'config.properties'.");
        }
    }


    public static String getStateFilePath() {
        return properties.getProperty("stateFilePath");
    }

    public static boolean isAllowApartmentStatusChange() {
        return Boolean.parseBoolean(properties.getProperty("allowApartmentStatusChange"));
    }

    public static int getPageSizeForPagination() {
        return Integer.parseInt(properties.getProperty("pageSizeForPagination"));
    }
}
