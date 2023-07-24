package com.mpt.goodsservice.dao;

import java.sql.*;
import java.util.ArrayList;

import com.mpt.goodsservice.config.JdbcUtil;
import com.mpt.goodsservice.domain.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsListDAO {

    private final JdbcUtil jdbcUtil;
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public GoodsListDAO(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    public ArrayList<Goods> getGoodsList() {
        Goods goods = null;
        ArrayList<Goods> list = new ArrayList<>();

        try {
            conn = jdbcUtil.getConnection();
            String sql = " select * from goods";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                goods = new Goods(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("del_price"),
                        rs.getInt("price"),
                        rs.getDouble("rating"),
                        rs.getInt("rating_count"),
                        rs.getString("img"),
                        rs.getString("url"));
                list.add(goods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close(rs, stmt, conn);
        }
        return list;
    }
}