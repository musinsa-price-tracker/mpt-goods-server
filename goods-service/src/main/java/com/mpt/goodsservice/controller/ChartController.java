package com.mpt.goodsservice.controller;

import com.mpt.goodsservice.dao.ChartDao;
import com.mpt.goodsservice.dao.GoodsDao;
import com.mpt.goodsservice.domain.Chart;
import com.mpt.goodsservice.domain.Goods;
import com.mpt.goodsservice.dto.ChartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedList;
import java.util.List;

@Controller
public class ChartController {
    @Autowired
    private ChartDao chartDao;
    @Autowired
    private GoodsDao goodsDao;

    @GetMapping("/db/goods/{id}")
    public ResponseEntity<List<List<Long>>> getChartData(@PathVariable("id") int id) {
        List<Chart> chart = chartDao.getChartData(id);
        List<List<Long>> list = new LinkedList<>();

        for (Chart ch : chart) {
            list.add(ChartDto.jsonList(ch));
        }

        return ResponseEntity.ok()
                .body(list);
    }

    @GetMapping("/goods/{id}")
    public String getGoodsDetail(@PathVariable("id") int id, Model model) {
        Goods goods = goodsDao.getGoodsById(id);

        model.addAttribute("goods", goods);

        return "chart";
    }
}
