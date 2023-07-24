package com.mpt.goodsservice.dto;

import com.mpt.goodsservice.domain.Goods;
import lombok.Getter;

@Getter
public class GoodsDto {
    private int id;
    private String name;
    private int price;

    public GoodsDto(Goods goods) {
        this.id = goods.getId();
        this.name = goods.getName();
        this.price = goods.getPrice();
    }
}
