package com.andersenhotels.view.web_ui;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class StateListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebUI webUI = new WebUI();

        try {
            webUI.initialize();
            System.out.println("State loaded successfully.");
        } catch (Exception e) {
            logError("Failed to load state.", e);
        }

        sce.getServletContext().setAttribute("webUI", webUI);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        WebUI webUI = (WebUI) sce.getServletContext().getAttribute("webUI");

        if (webUI != null) {
            try {
                webUI.complete();
                System.out.println("State saved successfully.");
            } catch (Exception e) {
                logError("Failed to save state.", e);
            }
        }
    }

    private void logError(String message, Exception e) {
        System.err.println(message);
        e.printStackTrace();
    }
}
