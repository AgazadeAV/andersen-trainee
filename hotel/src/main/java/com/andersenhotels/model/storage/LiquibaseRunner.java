package com.andersenhotels.model.storage;

import com.andersenhotels.model.config.ConfigManager;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LiquibaseRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiquibaseRunner.class);

    public static void runLiquibaseMigrations() {
        System.setProperty("liquibase.logging.level", "WARN");
        LOGGER.info("Starting Liquibase migrations...");

        ensureDatabaseExists();

        try (Connection connection = DriverManager.getConnection(
                ConfigManager.getDatabaseUrl(),
                ConfigManager.getDatabaseUsername(),
                ConfigManager.getDatabasePassword())) {

            LOGGER.info("Database connection established successfully: {}", ConfigManager.getDatabaseUrl());

            Database database = new MySQLDatabase();
            database.setConnection(new JdbcConnection(connection));

            try (Liquibase liquibase = new Liquibase(
                    ConfigManager.getLiquibaseChangeLogFile(),
                    new ClassLoaderResourceAccessor(),
                    database)) {

                LOGGER.info("Executing Liquibase migrations using changeLog file: {}", ConfigManager.getLiquibaseChangeLogFile());
                liquibase.update(new Contexts());
                LOGGER.info("Liquibase migrations executed successfully.");

            } catch (LiquibaseException e) {
                LOGGER.error("Liquibase migration error: {}", e.getMessage());
                throw new RuntimeException("Liquibase migration failed", e);
            }

        } catch (SQLException e) {
            LOGGER.error("Database connection error: {}", e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    private static void ensureDatabaseExists() {
        String databaseName = ConfigManager.getPersistenceUnitName();
        String databaseUrlWithoutDb = ConfigManager.getDatabaseUrlWithoutDb();

        LOGGER.info("Ensuring database exists: {}", databaseName);

        try (Connection connection = DriverManager.getConnection(
                databaseUrlWithoutDb,
                ConfigManager.getDatabaseUsername(),
                ConfigManager.getDatabasePassword());
             Statement statement = connection.createStatement()) {

            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS `" + databaseName + "`";
            statement.executeUpdate(createDatabaseQuery);

            LOGGER.info("Database checked/created: {}", databaseName);

        } catch (SQLException e) {
            LOGGER.error("Failed to check/create database: {}", e.getMessage());
            throw new RuntimeException("Failed to ensure database exists", e);
        }
    }
}
