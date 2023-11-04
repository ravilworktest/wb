package org.example.database;

import org.example.models.catalog.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBConnection {
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String USER = "sqluser";
    static final String PASS = "12345";
    static final String DB = "test";
    private Connection connection = null;

    public void dbConnect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test", "sqluser", "12345");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        createTable();
        createTableSell();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS card " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " seller INTEGER, " +
                " article INTEGER, " +
                " price INTEGER, " +
                " label VARCHAR(255), " +
                " priceHistory VARCHAR(255), " +
                " PRIMARY KEY (id))";

        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTableSell() {
        String sql = "CREATE TABLE IF NOT EXISTS sell " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                " seller INTEGER, " +
                " article INTEGER, " +
                " price INTEGER, " +
                " label VARCHAR(255), " +
                " priceHistory VARCHAR(255), " +
                " discount DOUBLE, " +
                " avgDiscount DOUBLE, " +
                " stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " + // YYYY-MM-DD HH:MM:SS
                " PRIMARY KEY (id))";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(List<Product> products) {
        String sql = "UPDATE card SET seller=?, article=?, price=?, label=?, priceHistory=? " +
                "WHERE seller = ? and article = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            int count = 0;

            for (Product product : products) {
                stmt.setInt(1, product.getSupplierId());
                stmt.setInt(2, product.getId());
                stmt.setInt(3, product.getSalePriceU());
                stmt.setString(4, product.getName());
                stmt.setString(5, product.getPriceHistory());
                stmt.setInt(6, product.getSupplierId());
                stmt.setInt(7, product.getId());
                stmt.addBatch();
                count++;

                if (count % 100 == 0 || count == products.size()) {
                    stmt.executeBatch();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertProducts(List<Product> products) {
        String sql = "INSERT INTO card (seller, article, price, label, priceHistory) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            int count = 0;

                for (Product product : products) {
                    stmt.setInt(1, product.getSupplierId());
                    stmt.setInt(2, product.getId());
                    stmt.setInt(3, product.getSalePriceU());
                    stmt.setString(4, product.getName());
                    stmt.setString(5, product.getPriceHistory());
                    stmt.addBatch();
                    count++;

                    if (count % 100 == 0 || count == products.size()) {
                        stmt.executeBatch();
                    }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertDiscountProducts(List<Product> products) {
        String sql = "INSERT INTO sell (seller, article, price, label, priceHistory, discount, avgDiscount) " +
                "VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            int count = 0;

            for (Product product : products) {
                stmt.setInt(1, product.getSupplierId());
                stmt.setInt(2, product.getId());
                stmt.setInt(3, product.getSalePriceUInRub());
                stmt.setString(4, product.getName());
                stmt.setString(5, product.getPriceHistoryInRub());
                stmt.setDouble(6, product.getDiscount());
                stmt.setDouble(7, product.getAvgDiscount());
                stmt.addBatch();
                count++;

                if (count % 100 == 0 || count == products.size()) {
                    stmt.executeBatch();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> readProduct() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT seller, article, price, label, priceHistory FROM card";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("seller"),
                        rs.getInt("article"),
                        rs.getInt("price"),
                        rs.getString("label"),
                        rs.getString("priceHistory")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

       return products;
    }
    public List<Product> selectProductBySeller(List<String> sellerId) {
        List<Product> products = new ArrayList<>();
        String query = String.format(
                "SELECT seller, article, price, label, priceHistory " +
                        "FROM card " +
                        "WHERE seller IN (%s)", String.join(",", sellerId));

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("seller"),
                        rs.getInt("article"),
                        rs.getInt("price"),
                        rs.getString("label"),
                        rs.getString("priceHistory")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

}
