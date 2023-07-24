package com.mpt.goodsservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.sql.*;

@Component
public class JdbcUtil {

    @Value("${spring.datasource.driver-class-name}")
    private  String dbDriverName;

    @Value("${spring.datasource.url}")
    private  String dbUrl;

    @Value("${spring.datasource.username}")
    private  String dbUsername;

    @Value("${spring.datasource.password}")
    private  String dbPassword;

    @Value("${mpt.database-port}")
    private  String dbPort;

    @Value("${mpt.database-name}")
    private  String dbName;

    public Connection getConnection() {
        Connection conn = null;
        String jdbcUrl =
                dbUrl + ":" + dbPort + "/" + dbName;
        try {
            Class.forName(dbDriverName);
            conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            if (conn != null) {
                System.out.println("DB Connection Success");
            }
            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("Driver Load Error");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DB Connection Failed");
            e.printStackTrace();
        }
        return null;
    }
    public static void close(PreparedStatement stmt, Connection conn) {
        close(null,stmt,conn);
    }
    public static void close(ResultSet rs, PreparedStatement stmt, Connection conn)
    {      try {if(rs != null)rs.close();
    } catch (Exception e) {e.printStackTrace();
    } finally { rs = null; }
        try {if(stmt != null) stmt.close();
        } catch (Exception e) {e.printStackTrace();
        } finally {stmt = null; }
        try {if(conn != null)conn.close();
        } catch (Exception e) {e.printStackTrace();
        } finally {conn = null; }
    }

//    public static void main(String[] args) {
//        getConnection();
//    }
}