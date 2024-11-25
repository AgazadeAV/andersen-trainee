package com.andersenhotels.model.config;

import com.andersenhotels.presenter.exceptions.MissingConfigurationException;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Setter
    private static boolean isTesting;

    static {
        validateConfiguration();
    }

    private ConfigManager() {
        // Private constructor to prevent instantiation
    }

    public static String getPersistenceUnitName() {
        if (isTesting) {
            return getProperty(PROPERTIES, "testPersistenceUnitName",
                    "testPersistenceUnitName is not configured in config.properties");
        } else {
            return getProperty(PROPERTIES, "persistenceUnitName",
                    "persistenceUnitName is not configured in config.properties");
        }
    }

    public static int getPageSizeForPagination() {
        String pageSize = getProperty(PROPERTIES, "pageSizeForPagination",
                "pageSizeForPagination is not configured in config.properties");
        try {
            return Integer.parseInt(pageSize);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid number format for 'pageSizeForPagination': {}", pageSize, e);
            throw new MissingConfigurationException("Invalid number format for 'pageSizeForPagination': " +
                    pageSize, e);
        }
    }

    public static String getDatabaseUrl() {
        if (isTesting) {
            return getProperty(LIQUIBASE_PROPERTIES, "testUrl",
                    "Database URL is not configured in liquibase.properties");
        } else {
            return getProperty(LIQUIBASE_PROPERTIES, "url",
                    "Test database URL is not configured in liquibase.properties");
        }
    }

    public static String getDatabaseUrlWithoutDb() {
        return getProperty(LIQUIBASE_PROPERTIES, "urlWithoutDb",
                "URL is not configured in liquibase.properties");
    }

    public static String getDatabaseUsername() {
        return getProperty(LIQUIBASE_PROPERTIES, "username",
                "Database username is not configured in liquibase.properties");
    }

    public static String getDatabasePassword() {
        return getProperty(LIQUIBASE_PROPERTIES, "password",
                "Database password is not configured in liquibase.properties");
    }

    public static String getLiquibaseChangeLogFile() {
        return getProperty(LIQUIBASE_PROPERTIES, "changeLogFile",
                "Liquibase changeLogFile is not configured in liquibase.properties");
    }

    static Properties loadProperties(String fileName) {
        Properties props = new Properties();
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) {
                props.load(input);
                LOGGER.info("{} loaded successfully.", fileName);
            } else {
                LOGGER.error("Configuration file '{}' not found.", fileName);
                throw new MissingConfigurationException("Configuration file '" + fileName + "' is missing.");
            }
        } catch (IOException e) {
            LOGGER.error("Error loading configuration file '{}': {}", fileName, e.getMessage(), e);
            throw new MissingConfigurationException("Error loading configuration file '" + fileName + "'", e);
        }
        return props;
    }

    static String getProperty(Properties props, String key, String errorMessage) {
        return Optional.ofNullable(props.getProperty(key))
                .orElseThrow(() -> {
                    LOGGER.error("Property '{}' is missing. Error: {}", key, errorMessage);
                    return new MissingConfigurationException(errorMessage);
                });
    }

    static void validateConfiguration() {
        try {
            getPersistenceUnitName();
            getPageSizeForPagination();
            getDatabaseUrl();
            getDatabaseUsername();
            getDatabasePassword();
            getLiquibaseChangeLogFile();
            LOGGER.info("Configuration validation completed successfully.");
        } catch (MissingConfigurationException e) {
            LOGGER.error("Configuration validation failed: {}", e.getMessage(), e);
            throw e;
        }
    }
}
