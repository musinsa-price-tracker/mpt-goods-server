package com.mpt.goodsservice.goodsservice.common;

import java.sql.*;

public class JDBCUtil {
    public static Connection getConnection() {
        final String driver = "org.mariadb.jdbc.Driver";
        final String DB_IP = "kakao-cloud-database-11111.c0wawbyewxin.ap-northeast-2.rds.amazonaws.com";
        final String DB_PORT = "3306";
        final String DB_NAME = "mydb";
        final String DB_URL =
                "jdbc:mariadb://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);                
            conn = DriverManager.getConnection(DB_URL, "mpt", "1234"); 
                                          //아이디 패스워드 수정할 것 
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

    public static void main(String[] args) {
        getConnection();
    }
}