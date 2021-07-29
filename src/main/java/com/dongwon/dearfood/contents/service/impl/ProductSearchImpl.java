package com.dongwon.dearfood.contents.service.impl;

import com.dongwon.dearfood.contents.dto.ProductSearchResponse;
import com.dongwon.dearfood.contents.dto.Products;
import com.dongwon.dearfood.contents.service.ProductSearchService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSearchImpl implements ProductSearchService {


    private final String OPENAPI_KEYS = "260524363cf2ded7844278c449b651f0";

    private final String OPENAPI_URL = "http://openapi.11st.co.kr/openapi/OpenApiService.tmall?key=";

    @Override
    public ProductSearchResponse getProductSearchApi(String keyword, String apiCode, String pageNum, String pageSize, String sortCd, String option, String targetSearchPrd) throws IOException {
        String url = OPENAPI_URL + OPENAPI_KEYS + "&apiCode=" + apiCode + "&keyword=" + keyword;


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "text/xml");

        HttpEntity<?> enti = new HttpEntity<>(headers);

        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, enti, String.class);

        ProductSearchResponse response = parser(res.getBody());
        return response;
    }

    private ProductSearchResponse parser(String body) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        ProductSearchResponse value = xmlMapper.readValue(body, ProductSearchResponse.class);
        return value;
    }
}
