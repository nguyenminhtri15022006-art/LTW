package com.example.webapp.config;

import jakarta.persistence.*;

import java.util.HashMap;

public final class JpaConfig {
    private JpaConfig() {}

    private static EntityManagerFactory factory;

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            var p = new HashMap<String, Object>();
            String defaultUrl =
                    "jdbc:sqlserver://localhost:1433;databaseName=jakartaJPA;encrypt=true;trustServerCertificate=true";
            p.put("jakarta.persistence.jdbc.url", Environment.get("DB_URL", defaultUrl));
            p.put("jakarta.persistence.jdbc.user", Environment.get("DB_USERNAME", "sa"));
            p.put("jakarta.persistence.jdbc.password", Environment.get("DB_PASSWORD", "1234"));
            EntityManagerFactory candidate =
                    Persistence.createEntityManagerFactory("jakartaJpaPU", p);
            // Filtered index permits multiple legacy accounts without an email on SQL Server.
            try (EntityManager em = candidate.createEntityManager()) {
                var tx = em.getTransaction();
                tx.begin();
                // Nullable columns added to existing SQL Server rows can remain NULL despite
                // DEFAULT.
                em.createNativeQuery("UPDATE users SET version = 0 WHERE version IS NULL")
                        .executeUpdate();
                em.createNativeQuery("UPDATE users SET active = 1 WHERE active IS NULL")
                        .executeUpdate();
                em.createNativeQuery(
                                "IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name ="
                                    + " 'UX_users_email' AND object_id = OBJECT_ID('users')) CREATE"
                                    + " UNIQUE INDEX UX_users_email ON users(email) WHERE email IS"
                                    + " NOT NULL")
                        .executeUpdate();
                tx.commit();
            } catch (RuntimeException e) {
                candidate.close();
                throw e;
            }
            factory = candidate;
        }
        return factory;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static synchronized void shutdown() {
        if (factory != null && factory.isOpen()) factory.close();
        factory = null;
    }
}
