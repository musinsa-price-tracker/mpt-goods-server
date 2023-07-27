package com.mpt.goodsservice.goodsservice.dao;

import com.mpt.goodsservice.common.JDBCUtil;
import com.mpt.goodsservice.domain.Chart;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ChartDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public List<Chart> getChartData(int id) {
        Chart chart;
        List<Chart> list = new LinkedList();

        try { conn = JDBCUtil.getConnection();
            String sql = "select * from price_chart where id=?";
            if (conn != null) {
                stmt = conn.prepareStatement(sql);
            }
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while(rs.next()) {
                chart = new Chart(rs.getInt("id"),
                        rs.getInt("price"),
                        rs.getInt("goods_rank"),
                        rs.getDate("chart_date"));

                list.add(chart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs, stmt, conn);
        }
        return list;
    }
}