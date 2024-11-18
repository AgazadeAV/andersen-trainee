package com.andersenhotels.model.storage.db_storage;

import com.andersenhotels.model.config.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseConnectionPool {

    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(ConfigManager.getDatabaseUrl());
        config.setUsername(ConfigManager.getDatabaseUsername());
        config.setPassword(ConfigManager.getDatabasePassword());
        config.setMaximumPoolSize(10);

        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
