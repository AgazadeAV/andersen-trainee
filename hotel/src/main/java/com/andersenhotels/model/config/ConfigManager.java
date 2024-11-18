package com.andersenhotels.model.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class ConfigManager {

    private static final String CONFIG_FILE = "config.properties";
    private static final String LIQUIBASE_FILE = "liquibase.properties";
    private static final Properties properties = new Properties();
    private static final Properties liquibaseProperties = new Properties();

    static {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
                System.out.println("Configuration loaded successfully.");
            } else {
                System.err.println("Configuration file '" + CONFIG_FILE + "' not found.");
            }
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
        }

        try (InputStream liquibaseInput = ConfigManager.class.getClassLoader().getResourceAsStream(LIQUIBASE_FILE)) {
            if (liquibaseInput != null) {
                liquibaseProperties.load(liquibaseInput);
                System.out.println("Liquibase configuration loaded successfully.");
            } else {
                System.err.println("Liquibase configuration file '" + LIQUIBASE_FILE + "' not found.");
            }
        } catch (IOException e) {
            System.err.println("Error loading Liquibase configuration file: " + e.getMessage());
        }
    }

    public static String getStateFilePath() {
        return Optional.ofNullable(properties.getProperty("stateFilePath"))
                .map(ConfigManager::resolveFilePath)
                .orElseThrow(() -> new IllegalStateException("stateFilePath is not configured in config.properties"));
    }

    public static String getDatabaseUrl() {
        return Optional.ofNullable(liquibaseProperties.getProperty("url"))
                .orElseThrow(() -> new IllegalStateException("Database URL is not configured in liquibase.properties"));
    }

    public static String getDatabaseUsername() {
        return Optional.ofNullable(liquibaseProperties.getProperty("username"))
                .orElseThrow(() -> new IllegalStateException("Database username is not configured in liquibase.properties"));
    }

    public static String getDatabasePassword() {
        return Optional.ofNullable(liquibaseProperties.getProperty("password"))
                .orElseThrow(() -> new IllegalStateException("Database password is not configured in liquibase.properties"));
    }

    public static String getLiquibaseChangeLogFile() {
        return Optional.ofNullable(liquibaseProperties.getProperty("changeLogFile"))
                .orElseThrow(() -> new IllegalStateException("Liquibase changeLogFile is not configured in liquibase.properties"));
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
        return Optional.ofNullable(System.getProperty("web.version"))
                .map(Boolean::parseBoolean)
                .orElse(false);
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
