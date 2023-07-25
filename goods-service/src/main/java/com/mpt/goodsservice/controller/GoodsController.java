package com.mpt.goodsservice.controller;

import com.mpt.goodsservice.dao.GoodsDao;
import com.mpt.goodsservice.domain.Goods;
import com.mpt.goodsservice.dto.GoodsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class GoodsController {

    private final GoodsDao goodsDao;

    public GoodsController(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    @GetMapping("/api/goods/{id}")
    public ResponseEntity<GoodsDto> goodsDetail(@PathVariable("id") int id) {
        Goods goods =  goodsDao.getGoodsById(id);

        return ResponseEntity.ok()
                .body(new GoodsDto(goods));
    }

    @GetMapping("/api/goods")
    public ResponseEntity<List<GoodsDto>> goodsList(@RequestParam HashMap<String, String> param) {
        List<Goods> list = goodsDao.getGoods(param);
        List<GoodsDto> result = new ArrayList<>();

        for(Goods goods : list) {
            result.add(new GoodsDto(goods));
        }

        return ResponseEntity.ok().body(result);
    }
}