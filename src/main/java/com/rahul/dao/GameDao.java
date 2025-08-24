package com.rahul.dao;

import java.sql.*;

public class GameDao {

    // TODO: move these to a properties file or JNDI DataSource in production
	 private static final String DB_URL = "jdbc:mysql://localhost/collage";
	    private static final String DB_USER = "root";
	    private static final String DB_PASS = "Rahul";

    public int fetchValueOrCreate(int defaultValue) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            // Ensure single-row table exists
            try (Statement st = c.createStatement()) {
                st.executeUpdate("CREATE TABLE IF NOT EXISTS game_state (id TINYINT PRIMARY KEY, counter INT NOT NULL)");
            }
            // Try to read row id=1
            try (PreparedStatement ps = c.prepareStatement("SELECT counter FROM game_state WHERE id=1");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
            // Insert default row
            try (PreparedStatement ins = c.prepareStatement("INSERT INTO game_state (id, counter) VALUES (1, ?)")) {
                ins.setInt(1, defaultValue);
                ins.executeUpdate();
            }
            return defaultValue;
        } catch (SQLException e) {
            throw new RuntimeException("DB init/read failed: " + e.getMessage(), e);
        }
    }

    public void saveValue(int value) {
        try (Connection c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = c.prepareStatement("UPDATE game_state SET counter=? WHERE id=1")) {
            ps.setInt(1, value);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB write failed: " + e.getMessage(), e);
        }
    }
}

