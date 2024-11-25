package com.andersenhotels.model.storage;

import com.andersenhotels.model.config.ConfigManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LiquibaseRunnerTest {

    private MockedStatic<DriverManager> driverManagerMock;

    @BeforeEach
    void setUp() {
        driverManagerMock = Mockito.mockStatic(DriverManager.class);

        ConfigManager.setTesting(true);
    }

    @AfterEach
    void tearDown() {
        driverManagerMock.close();
    }

    @Test
    void runLiquibaseMigrations_Failure_Liquibase() throws SQLException {
        try (MockedStatic<LiquibaseRunner> liquibaseRunnerMock = mockStatic(LiquibaseRunner.class)) {
            liquibaseRunnerMock.when(LiquibaseRunner::runLiquibaseMigrations)
                    .thenThrow(new RuntimeException("Liquibase migration failed"));

            RuntimeException exception = assertThrows(RuntimeException.class, LiquibaseRunner::runLiquibaseMigrations);

            assertTrue(exception.getMessage().contains("Liquibase migration failed"));
        }
    }

    @Test
    void ensureDatabaseExists_Success() throws Exception {
        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);

        driverManagerMock.when(() -> DriverManager.getConnection(
                ConfigManager.getDatabaseUrlWithoutDb(),
                ConfigManager.getDatabaseUsername(),
                ConfigManager.getDatabasePassword()
        )).thenReturn(mockConnection);

        when(mockConnection.createStatement()).thenReturn(mockStatement);

        LiquibaseRunner.ensureDatabaseExists();

        verify(mockStatement).executeUpdate("CREATE DATABASE IF NOT EXISTS `testdb`");
    }

    @Test
    void ensureDatabaseExists_Failure() throws Exception {
        driverManagerMock.when(() -> DriverManager.getConnection(
                ConfigManager.getDatabaseUrlWithoutDb(),
                ConfigManager.getDatabaseUsername(),
                ConfigManager.getDatabasePassword()
        )).thenThrow(SQLException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, LiquibaseRunner::ensureDatabaseExists);
        assert (exception.getMessage().contains("Failed to ensure database exists"));
    }
}
