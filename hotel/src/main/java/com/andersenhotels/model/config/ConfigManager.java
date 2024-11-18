package com.andersenhotels.model.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class ConfigManager {

    private static final String CONFIG_FILE = "config.properties";
    private static final String LIQUIBASE_FILE = "liquibase.properties";

    private static final Properties properties = loadProperties(CONFIG_FILE);
    private static final Properties liquibaseProperties = loadProperties(LIQUIBASE_FILE);

    private ConfigManager() {
        // Private constructor to prevent instantiation
    }

    public static String getStateFilePath() {
        return getProperty(properties, "stateFilePath")
                .map(ConfigManager::resolveFilePath)
                .orElseThrow(() -> new IllegalStateException("stateFilePath is not configured in config.properties"));
    }

    public static String getDatabaseUrl() {
        return getRequiredProperty(liquibaseProperties, "url", "Database URL is not configured in liquibase.properties");
    }

    public static String getDatabaseUsername() {
        return getRequiredProperty(liquibaseProperties, "username", "Database username is not configured in liquibase.properties");
    }

    public static String getDatabasePassword() {
        return getRequiredProperty(liquibaseProperties, "password", "Database password is not configured in liquibase.properties");
    }

    public static String getLiquibaseChangeLogFile() {
        return getRequiredProperty(liquibaseProperties, "changeLogFile", "Liquibase changeLogFile is not configured in liquibase.properties");
    }

    public static boolean isAllowApartmentStatusChange() {
        return getProperty(properties, "allowApartmentStatusChange")
                .map(Boolean::parseBoolean)
                .orElse(false);
    }

    public static int getPageSizeForPagination() {
        return getProperty(properties, "pageSizeForPagination")
                .map(Integer::parseInt)
                .orElse(10);
    }

    private static Properties loadProperties(String fileName) {
        Properties props = new Properties();
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) {
                props.load(input);
                System.out.println(fileName + " loaded successfully.");
            } else {
                System.err.println(fileName + " not found.");
            }
        } catch (IOException e) {
            System.err.println("Error loading " + fileName + ": " + e.getMessage());
        }
        return props;
    }

    private static Optional<String> getProperty(Properties props, String key) {
        return Optional.ofNullable(props.getProperty(key));
    }

    private static String getRequiredProperty(Properties props, String key, String errorMessage) {
        return getProperty(props, key)
                .orElseThrow(() -> new IllegalStateException(errorMessage));
    }

    private static String resolveFilePath(String configuredPath) {
        File file;
        if (isWebVersion()) {
            String baseDir = System.getProperty("catalina.base", System.getProperty("user.dir"));
            File webappsDir = new File(baseDir, "state");
            file = new File(webappsDir, configuredPath);
        } else {
            file = new File(configuredPath);
            if (!file.isAbsolute()) {
                String baseDir = System.getProperty("user.dir");
                file = new File(baseDir, configuredPath);
            }
        }
        return file.getAbsolutePath();
    }

    private static boolean isWebVersion() {
        return getProperty(properties, "web.version")
                .map(Boolean::parseBoolean)
                .orElse(false);
    }
}
