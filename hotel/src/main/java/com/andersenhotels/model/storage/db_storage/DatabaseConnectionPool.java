package com.andersenhotels.model.storage.db_storage;

import com.andersenhotels.model.config.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class DatabaseConnectionPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnectionPool.class);
    private static HikariDataSource dataSource;

    static {
        try {
            LOGGER.info("Initializing database connection pool...");
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(ConfigManager.getDatabaseUrl());
            config.setUsername(ConfigManager.getDatabaseUsername());
            config.setPassword(ConfigManager.getDatabasePassword());
            config.setMaximumPoolSize(10);

            dataSource = new HikariDataSource(config);
            LOGGER.info("Database connection pool initialized successfully.");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize database connection pool: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize database connection pool", e);
        }
    }

    public static DataSource getDataSource() {
        LOGGER.debug("Getting data source from connection pool.");
        return dataSource;
    }
}
