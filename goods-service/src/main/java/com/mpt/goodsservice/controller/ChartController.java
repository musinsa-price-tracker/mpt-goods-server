package com.mpt.goodsservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpt.goodsservice.config.UrlConfig;
import com.mpt.goodsservice.dao.ChartDao;
import com.mpt.goodsservice.domain.Chart;
import com.mpt.goodsservice.dto.ChartDto;
import com.mpt.goodsservice.dto.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Controller
public class ChartController {
    private final ChartDao chartDao;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${mpt.server.suffix.access}")
    private String ACCESS;

    public ChartController(ChartDao chartDao, ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.chartDao = chartDao;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/api/goods/chart/{id}")
    public ResponseEntity<List<List<Long>>> getChartData(@PathVariable("id") int id, @RequestHeader HttpHeaders httpHeaders) {
        UserResponse userResponse;
        List<List<Long>> list = new LinkedList<>();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", httpHeaders.getFirst("Authorization"));
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
            String url = UrlConfig.AUTH_SERVER.getUrl() + ACCESS;

            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            userResponse = getUserResponse(result);
            List<Chart> chart = chartDao.getChartData(id);

            if (userResponse.getStatus().equals("Verify Success :Token Refreshed")) {
                for (Chart ch : chart) {
                    list.add(ChartDto.jsonList(ch));
                }
            } else {
                System.out.println("Verify Failed");
                return (ResponseEntity<List<List<Long>>>) ResponseEntity.status(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return (ResponseEntity<List<List<Long>>>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(list);
    }

    public UserResponse getUserResponse(ResponseEntity<String> response) throws JsonProcessingException {
        return objectMapper.readValue(response.getBody(),UserResponse.class);
    }
}
