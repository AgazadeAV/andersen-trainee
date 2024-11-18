package com.andersenhotels.model.storage.db_storage;

import com.andersenhotels.model.config.ConfigManager;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public class LiquibaseRunner {

    public static void runLiquibaseMigrations() {
        try (Connection connection = DriverManager.getConnection(
                ConfigManager.getDatabaseUrl(),
                ConfigManager.getDatabaseUsername(),
                ConfigManager.getDatabasePassword())) {

            Database database = new liquibase.database.core.MySQLDatabase();
            database.setConnection(new JdbcConnection(connection));

            try (Liquibase liquibase = new Liquibase(ConfigManager.getLiquibaseChangeLogFile(), new ClassLoaderResourceAccessor(), database)) {
                liquibase.update(new Contexts());
            }
            System.out.println("Liquibase migrations executed successfully.");

        } catch (Exception e) {
            throw new RuntimeException("Error running Liquibase migrations: " + e.getMessage(), e);
        }
    }
}
