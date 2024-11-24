package com.andersenhotels.model.config;

import com.andersenhotels.presenter.exceptions.MissingConfigurationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ConfigManagerTest {

    @BeforeAll
    static void setupTestingMode() {
        ConfigManager.setTesting(true);
    }

    @Test
    void getPersistenceUnitName_Success() {
        assertEquals("testdb", ConfigManager.getPersistenceUnitName());
    }

    @Test
    void getPageSizeForPagination_Success() {
        assertEquals(5, ConfigManager.getPageSizeForPagination());
    }

    @Test
    void getDatabaseUrl_Success() {
        assertEquals("jdbc:mysql://localhost:3306/testdb", ConfigManager.getDatabaseUrl());
    }

    @Test
    void getDatabaseUrlWithoutDb_Success() {
        assertEquals("jdbc:mysql://localhost:3306/", ConfigManager.getDatabaseUrlWithoutDb());
    }

    @Test
    void getDatabaseUsername_Success() {
        assertEquals("root", ConfigManager.getDatabaseUsername());
    }

    @Test
    void getDatabasePassword_Success() {
        assertEquals("05031995", ConfigManager.getDatabasePassword());
    }

    @Test
    void getLiquibaseChangeLogFile_Success() {
        assertEquals("db/changelog/db.changelog-master.xml", ConfigManager.getLiquibaseChangeLogFile());
    }

    @Test
    void missingPropertyThrowsException() {
        MissingConfigurationException exception = assertThrows(
                MissingConfigurationException.class,
                () -> ConfigManager.getProperty(new Properties(), "nonExistentKey", "Property is missing")
        );
        assertTrue(exception.getMessage().contains("Property is missing"));
    }
}
