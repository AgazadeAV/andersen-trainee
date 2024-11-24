package com.andersenhotels.model.storage;

import com.andersenhotels.model.config.ConfigManager;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.Logger;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LiquibaseRunnerTest {

    private MockedStatic<DriverManager> driverManagerMock;
    private MockedStatic<ConfigManager> configManagerMock;

    @BeforeEach
    void setUp() {
        driverManagerMock = Mockito.mockStatic(DriverManager.class);
        configManagerMock = Mockito.mockStatic(ConfigManager.class);

        configManagerMock.when(ConfigManager::getDatabaseUrlWithoutDb).thenReturn("jdbc:mysql://localhost:3306/");
        configManagerMock.when(ConfigManager::getDatabaseUsername).thenReturn("user");
        configManagerMock.when(ConfigManager::getDatabasePassword).thenReturn("password");
        configManagerMock.when(ConfigManager::getPersistenceUnitName).thenReturn("testdb");
    }

    @AfterEach
    void tearDown() {
        driverManagerMock.close();
        configManagerMock.close();
    }

    @Test
    void ensureDatabaseExists_Success() throws Exception {
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);

        driverManagerMock.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                .thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        LiquibaseRunner.ensureDatabaseExists();

        verify(mockStatement).executeUpdate("CREATE DATABASE IF NOT EXISTS `testdb`");
    }

    @Test
    void ensureDatabaseExists_Failure() throws Exception {
        driverManagerMock.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                .thenThrow(SQLException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, LiquibaseRunner::ensureDatabaseExists);
        assert (exception.getMessage().contains("Failed to ensure database exists"));
    }
}
