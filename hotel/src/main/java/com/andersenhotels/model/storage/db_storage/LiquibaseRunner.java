package com.andersenhotels.model.storage.db_storage;

import com.andersenhotels.model.config.ConfigManager;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class LiquibaseRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiquibaseRunner.class);

    public static void runLiquibaseMigrations() {
        LOGGER.info("Starting Liquibase migrations...");

        try (Connection connection = DriverManager.getConnection(
                ConfigManager.getDatabaseUrl(),
                ConfigManager.getDatabaseUsername(),
                ConfigManager.getDatabasePassword())) {

            LOGGER.info("Database connection established successfully.");

            Database database = new liquibase.database.core.MySQLDatabase();
            database.setConnection(new JdbcConnection(connection));

            try (Liquibase liquibase = new Liquibase(
                    ConfigManager.getLiquibaseChangeLogFile(),
                    new ClassLoaderResourceAccessor(),
                    database)) {

                LOGGER.info("Executing Liquibase migrations using changeLog file: {}", ConfigManager.getLiquibaseChangeLogFile());
                liquibase.update(new Contexts());
                LOGGER.info("Liquibase migrations executed successfully.");

            } catch (Exception e) {
                LOGGER.error("Error during Liquibase migrations: {}", e.getMessage(), e);
                throw new RuntimeException("Error running Liquibase migrations", e);
            }

        } catch (Exception e) {
            LOGGER.error("Failed to establish database connection or run migrations: {}", e.getMessage(), e);
            throw new RuntimeException("Error running Liquibase migrations", e);
        }
    }
}
