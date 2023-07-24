package com.mpt.goodsservice.controller;

import com.mpt.goodsservice.dao.GoodsDao;
import com.mpt.goodsservice.domain.Goods;
import com.mpt.goodsservice.dto.GoodsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GoodsApiController {
    @Autowired 
    private GoodsDao goodsDao;

    @GetMapping("/api/goods/{id}")
    public ResponseEntity<GoodsDto> findPost(@PathVariable("id") int id) {
        Goods goods =  goodsDao.getGoodsById(id);

        return ResponseEntity.ok()
                .body(new GoodsDto(goods));
    }

}