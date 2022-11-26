package com.novik.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.beans.Introspector;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/test_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String SELECT_ALL_PRODUCTS_QUERY = "select * from product";
private String method;
private String id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         method = req.getParameter("method");
         id = req.getParameter("id");
        //if we delete object
        if ("delete".equals(method)) {
            //if we don't now exist object or not, this word in brackets
            System.out.println("here: " + id);
            ProductService.deleteById(id);
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Connection connection1 = null;
            try {
                connection1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Statement statement = connection1.createStatement();
                statement.execute("delete from product where id="+id);
                }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            req.getRequestDispatcher("main").forward(req, resp);
        }
        //if we create project
        else if ("create".equals(method) || "change".equals(method)) {
            req.getRequestDispatcher("product.jsp").forward(req, resp);
        }

        //if we update product
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("here ");
        String id1=req.getParameter("id");
        String name=req.getParameter("name");
        String price=req.getParameter("price");
        String information=req.getParameter("information");
        Map<String,String> errors=new HashMap<>();
        try{
            Integer.parseInt(id1);
        } catch (Exception e){
            errors.put("id","id is not Integer");
        }
        try{
            Integer.parseInt(price);
        } catch (Exception e) {
            errors.put("price","price is not Integer");
        }
        if (errors.size()==0) { //means no exception
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement();
                if("create".equals(method)) {
            Product product=new Product(Integer.parseInt(id1),name,Integer.parseInt(price),information);
            ProductService.addProduct(product);
            statement.execute("insert into product (name,price,information) values ('" + name + "'," + price+ ",'"+information+"'" + ")");
                }
                else if("change".equals(method)){
                    Product product=new Product(Integer.parseInt(id),name,Integer.parseInt(price),information);
                    ProductService.changeById(id,product);
                    statement.execute("update product set price="+price+", information='"+information+"', name='"+name+"' where id="+id);
                }
            req.getRequestDispatcher("main").forward(req,resp);}
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            req.setAttribute("errors",errors);
            req.getRequestDispatcher("product.jsp").forward(req,resp);
        }

    }

}
