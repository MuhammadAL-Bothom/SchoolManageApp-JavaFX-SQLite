package com.wise.app.dao;

import com.wise.app.DB;
import com.wise.app.model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public static List<Item> getAll() throws SQLException {
        List<Item> list = new ArrayList<>();
        try (Connection c = DB.getConn(); Statement st = c.createStatement()) {
            // خليها ASC عشان يظهر 1..N طبيعي بعد إعادة الترقيم
            ResultSet rs = st.executeQuery("SELECT item_id,name,quantity,price FROM items ORDER BY item_id ASC");
            while (rs.next()) {
                list.add(new Item(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4)));
            }
        }
        return list;
    }

    public static void add(String name, int qty, double price) throws SQLException {
        String sql = "INSERT INTO items(name,quantity,price) VALUES(?,?,?)";
        try (Connection c = DB.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, qty);
            ps.setDouble(3, price);
            ps.executeUpdate();
        }
    }

    public static void update(int id, String name, int qty, double price) throws SQLException {
        String sql = "UPDATE items SET name=?, quantity=?, price=? WHERE item_id=?";
        try (Connection c = DB.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, qty);
            ps.setDouble(3, price);
            ps.setInt(4, id);
            ps.executeUpdate();
        }
    }

    // ✅ Delete + Resequence (سد الفجوات + يبدأ من 1 إذا فاضي)
    public static void delete(int id) throws SQLException {
        try (Connection c = DB.getConn()) {
            c.setAutoCommit(false);

            // 1) حذف العنصر (transactions المرتبطة به تنحذف تلقائياً بسبب ON DELETE CASCADE)
            try (PreparedStatement ps = c.prepareStatement("DELETE FROM items WHERE item_id=?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            // 2) إعادة ترقيم كل items إلى 1..N بدون فجوات
            resequenceItems(c);

            c.commit();
            c.setAutoCommit(true);
        }
    }

    // يعمل mapping old_id -> new_id ثم تحديث IDs بطريقة آمنة بدون تعارض
    private static void resequenceItems(Connection c) throws SQLException {
        try (Statement st = c.createStatement()) {

            // لو الجدول فاضي، لا تعمل شيء (الـ next insert رح يرجع يبدأ من 1)
            ResultSet rsCount = st.executeQuery("SELECT COUNT(*) FROM items");
            int count = rsCount.next() ? rsCount.getInt(1) : 0;
            if (count == 0) return;

            // جدول مؤقت لخريطة الترقيم
            st.execute("DROP TABLE IF EXISTS temp_id_map;");

            // ROW_NUMBER() يعطي أرقام 1..N حسب ترتيب item_id الحالي
            st.execute("""
                CREATE TEMP TABLE temp_id_map AS
                SELECT item_id AS old_id,
                       ROW_NUMBER() OVER (ORDER BY item_id) AS new_id
                FROM items;
            """);

            // Step 1: نحول IDs إلى سالبة لتجنب تضارب PRIMARY KEY
            st.execute("""
                UPDATE items
                SET item_id = -(
                    SELECT new_id FROM temp_id_map WHERE old_id = items.item_id
                );
            """);

            // Step 2: نرجعها موجبة (تصير 1..N)
            st.execute("UPDATE items SET item_id = -item_id;");

            st.execute("DROP TABLE IF EXISTS temp_id_map;");
        }
    }
}
