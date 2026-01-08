package com.wise.app.dao;

import com.wise.app.DB;
import com.wise.app.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static User login(String username, String password) throws SQLException {
        String sql = "SELECT user_id, username, role FROM users WHERE username=? AND password=?";
        try (Connection c = DB.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new User(rs.getInt(1), rs.getString(2), rs.getString(3));
            return null;
        }
    }

    public static void register(String username, String password) throws SQLException {
        String sql = "INSERT INTO users(username,password,role) VALUES(?,?, 'USER')";
        try (Connection c = DB.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        }
    }

    public static List<User> getAll() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection c = DB.getConn(); Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT user_id, username, role FROM users ORDER BY user_id DESC");
            while (rs.next()) list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        return list;
    }

    public static void delete(int userId) throws SQLException {
        try (Connection c = DB.getConn(); PreparedStatement ps = c.prepareStatement("DELETE FROM users WHERE user_id=?")) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
}
