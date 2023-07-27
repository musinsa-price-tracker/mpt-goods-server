package com.mpt.goodsservice.goodsservice.controller;

import com.mpt.goodsservice.common.MptServerInfo;
import com.mpt.goodsservice.dao.ChartDao;
import com.mpt.goodsservice.dao.UserResponse;
import com.mpt.goodsservice.domain.Chart;
import com.mpt.goodsservice.dto.ChartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChartController {
    @Autowired
    private final ChartDao chartDao;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/api/goods/chart/{id}")
    public ResponseEntity<List<List<Long>>> getChartData(@PathVariable("id") int id, @RequestHeader HttpHeaders httpHeaders) {
        UserResponse userResponse = null;

        try {
            System.out.println("httpHeaders Authorization: " + httpHeaders.getFirst("Authorization"));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",httpHeaders.getFirst("Authorization"));
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
            String url = MptServerInfo.AUTH_SERVER.getUrl() + "/api/access_token_info";

            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET,request,String.class);
        } catch (Exception e) {
            // TODO: handle exception
            userResponse = new UserResponse();
        }

        List<Chart> chart = chartDao.getChartData(id);
        List<List<Long>> list = new LinkedList<>();

        if(userResponse.getStatus().equals("Login-Success")){
            for (Chart ch : chart) {
                list.add(ChartDto.jsonList(ch));
            }
        }

        return ResponseEntity.ok()
                .body(list);
    }
}
