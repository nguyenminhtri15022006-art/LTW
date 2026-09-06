package com.example.webapp.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class LayoutFilter extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.setDecoratorPrefix("")
                .addDecoratorPath("/*", "/WEB-INF/decorators/main.jsp")
                .addExcludedPath("/images/*")
                .addExcludedPath("/css/*")
                .addExcludedPath("/WEB-INF/*");
    }
}
