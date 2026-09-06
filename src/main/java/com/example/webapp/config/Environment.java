package com.example.webapp.config;

public final class Environment {
    private Environment() {}

    public static String get(String name, String fallback) {
        String value = System.getProperty(name);
        if (value == null || value.isBlank()) {
            value = System.getenv(name);
        }
        return value == null || value.isBlank() ? fallback : value;
    }

    public static String required(String name) {
        String value = get(name, null);
        if (value == null) throw new IllegalStateException("Chưa cấu hình " + name);
        return value;
    }
}
