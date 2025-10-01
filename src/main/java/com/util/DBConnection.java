package com.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnection {

    private static Connection connection;

    private DBConnection() {}

    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Properties props = new Properties();
                try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                    if (in == null) throw new RuntimeException("db.properties introuvable");
                    props.load(in);
                }
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");
                connection = DriverManager.getConnection(url, user, password);
            }
            return connection;
        } catch (SQLException | RuntimeException ex) {
            throw new RuntimeException("Impossible d'obtenir la connexion DB: " + ex.getMessage(), ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) connection.close();
                connection = null;
                System.out.println("Connexion DB fermée.");
            } catch (SQLException e) {
                System.err.println("Erreur fermeture DB: " + e.getMessage());
            }
        }
    }
}
