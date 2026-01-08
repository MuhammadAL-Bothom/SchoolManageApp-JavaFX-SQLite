package com.wise.app.dao;

import com.wise.app.DB;
import com.wise.app.model.Tx;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TxDAO {

    public static void createTx(int userId, int itemId, int qty) throws SQLException {
        try (Connection c = DB.getConn()) {
            c.setAutoCommit(false);

            int stock;
            double price;

            try (PreparedStatement ps = c.prepareStatement("SELECT quantity, price FROM items WHERE item_id=?")) {
                ps.setInt(1, itemId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) throw new SQLException("Item not found");
                stock = rs.getInt("quantity");
                price = rs.getDouble("price");
            }

            if (qty <= 0) throw new SQLException("Qty must be > 0");
            if (stock < qty) throw new SQLException("Not enough stock");

            double total = qty * price;

            try (PreparedStatement ps = c.prepareStatement("""
                INSERT INTO transactions(user_id,item_id,qty,total_amount,date)
                VALUES(?,?,?,?,?)
            """)) {
                ps.setInt(1, userId);
                ps.setInt(2, itemId);
                ps.setInt(3, qty);
                ps.setDouble(4, total);
                ps.setString(5, LocalDate.now().toString());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = c.prepareStatement("UPDATE items SET quantity = quantity - ? WHERE item_id=?")) {
                ps.setInt(1, qty);
                ps.setInt(2, itemId);
                ps.executeUpdate();
            }

            c.commit();
            c.setAutoCommit(true);
        }
    }

    public static List<Tx> getAll() throws SQLException {
        List<Tx> list = new ArrayList<>();
        String sql = """
            SELECT t.transaction_id, u.username, i.name, t.qty, t.total_amount, t.date
            FROM transactions t
            JOIN users u ON u.user_id=t.user_id
            JOIN items i ON i.item_id=t.item_id
            ORDER BY t.transaction_id DESC
        """;
        try (Connection c = DB.getConn(); Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new Tx(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDouble(5),
                        rs.getString(6)
                ));
            }
        }
        return list;
    }

    public static double totalSales() throws SQLException {
        try (Connection c = DB.getConn(); Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT COALESCE(SUM(total_amount),0) FROM transactions");
            return rs.next() ? rs.getDouble(1) : 0;
        }
    }

    public static int totalTransactions() throws SQLException {
        try (Connection c = DB.getConn(); Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM transactions");
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
