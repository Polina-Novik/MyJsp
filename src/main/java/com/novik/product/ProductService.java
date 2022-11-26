package com.novik.product;

import jakarta.servlet.annotation.WebServlet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final String URL = "jdbc:mysql://localhost:3306/test_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Stalker261986";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String SELECT_ALL_QUERY = "select * from product";


    private static List<Product> products=new ArrayList<>();
    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            while (resultSet.next()) {
                products.add(new Product(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getInt("price"),resultSet.getString("information")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public static List<Product> getProducts() {
        return products;
    }

    public static void changeById(String id,Product product) {
        int currentId=Integer.parseInt(id);
        Product product1=products.stream().filter(prod -> prod.getId()==currentId).findAny().orElseThrow();
        int i=products.indexOf(product1);
        products.set(i,product);
    }
    public static void deleteById(String id) {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = null;
try {
    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    Statement statement = connection.createStatement();
    int currentId=Integer.parseInt(id);
    Product product=products.stream().filter(prod -> prod.getId()==currentId).findAny().orElseThrow();;
    products.remove(product);
    statement.execute("delete from products where id=" + currentId);
} catch (Exception e){
    System.out.println("wrong id");
}
    }
    public static void addProduct(Product product) {
        products.add(product);
    }


}
