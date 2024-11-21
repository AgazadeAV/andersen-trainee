package com.andersenhotels.model.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class ConfigManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);

    private static final String CONFIG_FILE = "config.properties";
    private static final String LIQUIBASE_FILE = "liquibase.properties";

    private static final Properties PROPERTIES = loadProperties(CONFIG_FILE);
    private static final Properties LIQUIBASE_PROPERTIES = loadProperties(LIQUIBASE_FILE);

    private ConfigManager() {
        // Private constructor to prevent instantiation
    }

    public static String getStateFilePath() {
        return getProperty(PROPERTIES, "stateFilePath")
                .map(ConfigManager::resolveFilePath)
                .orElseThrow(() -> new IllegalStateException("stateFilePath is not configured in config.properties"));
    }

    public static String getTestStateFilePath() {
        return getProperty(PROPERTIES, "testStateFilePath")
                .map(ConfigManager::resolveFilePath)
                .orElseThrow(() -> new IllegalStateException("testStateFilePath is not configured in config.properties"));
    }

    public static String getPersistenceUnitName() {
        return getProperty(PROPERTIES, "persistenceUnitName")
                .orElseThrow(() -> new IllegalStateException("persistenceUnitName is not configured in config.properties"));
    }

    public static String getDatabaseUrl() {
        return getRequiredProperty(LIQUIBASE_PROPERTIES, "url",
                "Database URL is not configured in liquibase.properties");
    }

    public static String getDatabaseUsername() {
        return getRequiredProperty(LIQUIBASE_PROPERTIES, "username",
                "Database username is not configured in liquibase.properties");
    }

    public static String getDatabasePassword() {
        return getRequiredProperty(LIQUIBASE_PROPERTIES, "password",
                "Database password is not configured in liquibase.properties");
    }

    public static String getLiquibaseChangeLogFile() {
        return getRequiredProperty(LIQUIBASE_PROPERTIES, "changeLogFile",
                "Liquibase changeLogFile is not configured in liquibase.properties");
    }

    public static boolean isAllowApartmentStatusChange() {
        return getProperty(PROPERTIES, "allowApartmentStatusChange")
                .map(Boolean::parseBoolean)
                .orElse(false);
    }

    public static int getPageSizeForPagination() {
        return getProperty(PROPERTIES, "pageSizeForPagination")
                .map(Integer::parseInt)
                .orElse(10);
    }

    private static Properties loadProperties(String fileName) {
        Properties props = new Properties();
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) {
                props.load(input);
                LOGGER.info("{} loaded successfully.", fileName);
            } else {
                LOGGER.warn("{} not found.", fileName);
            }
        } catch (IOException e) {
            LOGGER.error("Error loading {}: {}", fileName, e.getMessage(), e);
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
        LOGGER.debug("Resolved file path: {}", file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    private static boolean isWebVersion() {
        return getProperty(PROPERTIES, "web.version")
                .map(Boolean::parseBoolean)
                .orElse(false);
    }
}
