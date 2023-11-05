package org.example.database;

import org.example.models.catalog.Product;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class DBConnection {
    private static final String CONNECTION_STRING = "jdbc:mysql://%s:%s/%s";
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB = "test";
    private static final String USER = "sqluser";
    private static final String PASS = "12345";
    private static final String CARD_TABLE = "card";
    private static final String SELL_TABLE = "sell";
    private Connection connection = null;

    public void dbConnect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(String.format(CONNECTION_STRING, HOST, PORT, DB), USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        createTable();
        createTableSell();
    }

    public void dbClose(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + CARD_TABLE +
                " (id INTEGER not NULL AUTO_INCREMENT, " +
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
        String sql = "CREATE TABLE IF NOT EXISTS "+ SELL_TABLE +
                " (id INTEGER not NULL AUTO_INCREMENT, " +
                " seller INTEGER, " +
                " article INTEGER, " +
                " price INTEGER, " +
                " label VARCHAR(255), " +
                " priceHistory VARCHAR(255), " +
                " discount DOUBLE, " +
                " avgDiscount DOUBLE, " +
                " stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                " PRIMARY KEY (id))";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(List<Product> products) {
        String sql = "UPDATE "+ CARD_TABLE +" SET seller=?, article=?, price=?, label=?, priceHistory=? " +
                "WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            int count = 0;

            for (Product product : products) {
                stmt.setInt(1, product.getSupplierId());
                stmt.setInt(2, product.getId());
                stmt.setInt(3, product.getSalePriceU());
                stmt.setString(4, product.getName());
                stmt.setString(5, product.getPriceHistory());
                stmt.setLong(6, product.getDbRecordId());
                stmt.addBatch();
                count++;

                if (count % 100 == 0 || count == products.size()) {
                    stmt.executeBatch();
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertProducts(List<Product> products) {
        String sql = "INSERT INTO "+ CARD_TABLE +" (seller, article, price, label, priceHistory) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
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
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertDiscountProducts(List<Product> products) {
        String sql = "INSERT INTO "+ SELL_TABLE +" (seller, article, price, label, priceHistory, discount, avgDiscount) " +
                "VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
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
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Integer, Product> selectProductBySeller(List<String> sellerId) {
        Map<Integer, Product> productMap = new ConcurrentHashMap<>();
        String query = String.format(
                "SELECT id, seller, article, price, label, priceHistory" +
                        " FROM "+ CARD_TABLE +
                        " WHERE seller IN (%s)", String.join(",", sellerId));

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Product product = new Product(
                        rs.getLong("id"),
                        rs.getInt("seller"),
                        rs.getInt("article"),
                        rs.getInt("price"),
                        rs.getString("label"),
                        rs.getString("priceHistory"));
                productMap.put(product.getId(), product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productMap;
    }

}
