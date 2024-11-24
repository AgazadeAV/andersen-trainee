package com.andersenhotels.model.config;

import com.andersenhotels.presenter.exceptions.MissingConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.*;

class ConfigManagerTest {

    @BeforeEach
    void setupProperties() {
        Properties configProperties = new Properties();
        configProperties.setProperty("persistenceUnitName", "TestPersistenceUnit");
        configProperties.setProperty("pageSizeForPagination", "10");

        Properties liquibaseProperties = new Properties();
        liquibaseProperties.setProperty("url", "jdbc:mysql://localhost:3306/testdb");
        liquibaseProperties.setProperty("urlWithoutDb", "jdbc:mysql://localhost:3306/");
        liquibaseProperties.setProperty("username", "testuser");
        liquibaseProperties.setProperty("password", "testpassword");
        liquibaseProperties.setProperty("changeLogFile", "db/changelog/db.changelog-master.xml");

        ConfigManager.setMockProperties(configProperties, liquibaseProperties);
    }

    @Test
    void getPersistenceUnitName_Success() {
        assertEquals("TestPersistenceUnit", ConfigManager.getPersistenceUnitName());
    }

    @Test
    void getPersistenceUnitName_Failure() {
        ConfigManager.setMockProperties(new Properties(), new Properties());

        MissingConfigurationException exception = assertThrows(
                MissingConfigurationException.class,
                ConfigManager::getPersistenceUnitName
        );
        assertEquals("persistenceUnitName is not configured in config.properties", exception.getMessage());
    }

    @Test
    void getPageSizeForPagination_Success() {
        assertEquals(10, ConfigManager.getPageSizeForPagination());
    }

    @Test
    void getPageSizeForPagination_Failure_MissingValue() {
        Properties configWithoutPageSize = new Properties();
        ConfigManager.setMockProperties(configWithoutPageSize, new Properties());

        MissingConfigurationException exception = assertThrows(
                MissingConfigurationException.class,
                ConfigManager::getPageSizeForPagination
        );
        assertEquals("pageSizeForPagination is not configured in config.properties", exception.getMessage());
    }

    @Test
    void getPageSizeForPagination_Failure_InvalidFormat() {
        Properties invalidConfig = new Properties();
        invalidConfig.setProperty("pageSizeForPagination", "not-a-number");
        ConfigManager.setMockProperties(invalidConfig, new Properties());

        MissingConfigurationException exception = assertThrows(
                MissingConfigurationException.class,
                ConfigManager::getPageSizeForPagination
        );
        assertTrue(exception.getMessage().contains("Invalid number format for 'pageSizeForPagination'"));
    }

    @Test
    void getDatabaseUrl_Success() {
        assertEquals("jdbc:mysql://localhost:3306/testdb", ConfigManager.getDatabaseUrl());
    }

    @Test
    void getDatabaseUrl_Failure() {
        ConfigManager.setMockProperties(new Properties(), new Properties());

        MissingConfigurationException exception = assertThrows(
                MissingConfigurationException.class,
                ConfigManager::getDatabaseUrl
        );
        assertEquals("Database URL is not configured in liquibase.properties", exception.getMessage());
    }

    @Test
    void getDatabaseUrlWithoutDb_Success() {
        assertEquals("jdbc:mysql://localhost:3306/", ConfigManager.getDatabaseUrlWithoutDb());
    }

    @Test
    void getDatabaseUrlWithoutDb_Failure() {
        ConfigManager.setMockProperties(new Properties(), new Properties());

        MissingConfigurationException exception = assertThrows(
                MissingConfigurationException.class,
                ConfigManager::getDatabaseUrlWithoutDb
        );
        assertEquals("URL is not configured in liquibase.properties", exception.getMessage());
    }

    @Test
    void getDatabaseUsername_Success() {
        assertEquals("testuser", ConfigManager.getDatabaseUsername());
    }

    @Test
    void getDatabaseUsername_Failure() {
        ConfigManager.setMockProperties(new Properties(), new Properties());

        MissingConfigurationException exception = assertThrows(
                MissingConfigurationException.class,
                ConfigManager::getDatabaseUsername
        );
        assertEquals("Database username is not configured in liquibase.properties", exception.getMessage());
    }

    @Test
    void getDatabasePassword_Success() {
        assertEquals("testpassword", ConfigManager.getDatabasePassword());
    }

    @Test
    void getDatabasePassword_Failure() {
        ConfigManager.setMockProperties(new Properties(), new Properties());

        MissingConfigurationException exception = assertThrows(
                MissingConfigurationException.class,
                ConfigManager::getDatabasePassword
        );
        assertEquals("Database password is not configured in liquibase.properties", exception.getMessage());
    }

    @Test
    void getLiquibaseChangeLogFile_Success() {
        assertEquals("db/changelog/db.changelog-master.xml", ConfigManager.getLiquibaseChangeLogFile());
    }

    @Test
    void getLiquibaseChangeLogFile_Failure() {
        ConfigManager.setMockProperties(new Properties(), new Properties());

        MissingConfigurationException exception = assertThrows(
                MissingConfigurationException.class,
                ConfigManager::getLiquibaseChangeLogFile
        );
        assertEquals("Liquibase changeLogFile is not configured in liquibase.properties", exception.getMessage());
    }
}
