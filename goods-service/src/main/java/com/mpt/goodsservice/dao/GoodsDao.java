package com.mpt.goodsservice.dao;

import com.mpt.goodsservice.config.JdbcUtil;
import com.mpt.goodsservice.domain.Goods;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class GoodsDao {
    private final JdbcUtil jdbcUtil;
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public GoodsDao(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    public Goods getGoodsById(int id) {
        Goods goods = null;

        try {
            conn = jdbcUtil.getConnection();
            String sql = " select * from goods where id=?";
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, id);
            rs = psmt.executeQuery();
            if (rs.next()) {
                goods = new Goods(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("del_price"),
                        rs.getInt("price"),
                        rs.getDouble("rating"),
                        rs.getInt("rating_count"),
                        rs.getString("img"),
                        rs.getString("url"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close(rs, psmt, conn);
        }
        return goods;
    }

    public ArrayList<Goods> getGoods(HashMap<String, String> param) {
        Goods goods = null;
        ArrayList<Goods> list = new ArrayList<>();
        String sql = null;
        try {
            conn = jdbcUtil.getConnection();

            if (param.get("salemode").equals("priceComparison")){ // 정가 대비 할인율 순
                sql = "select * from goods order by ((del_price - price)/del_price) desc LIMIT ?, 50";
            } else{
                sql = "select g.*, price_information.drop_rate goods from goods g left join price_information on g.id = price_information.id order by price_information.drop_rate asc LIMIT ?, 50";
            }

            int pageNumber = Integer.parseInt(param.get("page"));
            int offset = (pageNumber-1)*50;

            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, offset);
            rs = psmt.executeQuery();
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
            jdbcUtil.close(rs, psmt, conn);
        }
        return list;
    }

}