package com.wise.app;

import java.sql.*;

public class DB {
    private static final String URL = "jdbc:sqlite:app.db";

    // مهم: تفعيل Foreign Keys بكل اتصال
    public static Connection getConn() throws SQLException {
        Connection c = DriverManager.getConnection(URL);
        try (Statement st = c.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON;");
        }
        return c;
    }

    public static void init() {
        String users = """
            CREATE TABLE IF NOT EXISTS users(
              user_id INTEGER PRIMARY KEY AUTOINCREMENT,
              username TEXT UNIQUE NOT NULL,
              password TEXT NOT NULL,
              role TEXT NOT NULL DEFAULT 'USER'
            );
        """;

        // مهم: بدون AUTOINCREMENT عشان يرجع يبدأ من 1 إذا الجدول فاضي
        String items = """
            CREATE TABLE IF NOT EXISTS items(
              item_id INTEGER PRIMARY KEY,
              name TEXT NOT NULL,
              quantity INTEGER NOT NULL DEFAULT 0,
              price REAL NOT NULL DEFAULT 0
            );
        """;

        // مهم: ON UPDATE CASCADE عشان لما نغير item_id تتحدث transactions تلقائياً
        String tx = """
            CREATE TABLE IF NOT EXISTS transactions(
              transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,
              user_id INTEGER NOT NULL,
              item_id INTEGER NOT NULL,
              qty INTEGER NOT NULL,
              total_amount REAL NOT NULL,
              date TEXT NOT NULL,
              FOREIGN KEY(user_id) REFERENCES users(user_id)
                ON UPDATE CASCADE ON DELETE CASCADE,
              FOREIGN KEY(item_id) REFERENCES items(item_id)
                ON UPDATE CASCADE ON DELETE CASCADE
            );
        """;

        try (Connection c = getConn(); Statement st = c.createStatement()) {
            st.execute(users);
            st.execute(items);
            st.execute(tx);

            st.execute("""
                INSERT OR IGNORE INTO users(username,password,role)
                VALUES('admin','admin','ADMIN');
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
