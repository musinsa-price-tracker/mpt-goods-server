package com.mpt.goodsservice.goodsservice.dao;

import com.mpt.goodsservice.common.JDBCUtil;
import com.mpt.goodsservice.domain.Goods;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class GoodsDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public Goods getGoodsById(int id) {
        Goods goods = null;

        try { conn = JDBCUtil.getConnection();
            String sql = " select * from goods where id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if(rs.next()){
                goods = new Goods(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("del_price"),
                        rs.getInt("price"),
                        rs.getDouble("rating"),
                        rs.getInt("rating_count"),
                        rs.getString("img"),
                        rs.getString("url"));
            }
        } catch (Exception e) {   e.printStackTrace();
        } finally {  JDBCUtil.close(rs, stmt, conn);
        }
        return goods;
    }
}