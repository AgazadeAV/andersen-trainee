package com.andersenhotels;

import com.andersenhotels.model.storage.LiquibaseRunner;
import com.andersenhotels.view.View;
import com.andersenhotels.view.console_ui.ConsoleUI;

public class AndersenHotelsApplication {
    public static void main(String[] args) {
        LiquibaseRunner.runLiquibaseMigrations();

        View view = new ConsoleUI();
        view.initialize();
    }
}
