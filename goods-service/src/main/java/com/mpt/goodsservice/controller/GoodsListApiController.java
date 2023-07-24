package com.mpt.goodsservice.controller;

import com.mpt.goodsservice.dao.GoodsListDAO;
import com.mpt.goodsservice.domain.Goods;
import com.mpt.goodsservice.dto.GoodsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GoodsListApiController {
    @Autowired 
    private GoodsListDAO goodsListDAO;

    @GetMapping("/api/goods")
    public ResponseEntity<List<GoodsDto>> findPost() {
        List<Goods> list = goodsListDAO.getGoodsList();
        List<GoodsDto> result = new ArrayList<>();

        for(Goods goods : list) {
            result.add(new GoodsDto(goods));
        }

        return ResponseEntity.ok().body(result);
    }

}