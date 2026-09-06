package com.example.webapp.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {
    public void contextDestroyed(ServletContextEvent event) {
        JpaConfig.shutdown();
    }
}
