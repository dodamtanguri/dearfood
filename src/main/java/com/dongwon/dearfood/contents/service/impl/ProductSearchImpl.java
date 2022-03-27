package com.dongwon.dearfood.contents.service.impl;

import com.dongwon.dearfood.contents.dto.ProductSearchResponse;

import com.dongwon.dearfood.contents.service.ProductSearchService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;
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


    private final Environment environment;

    /**
     * 11번가 상품 조회 API
     *
     * @param keyword         검색 키워드
     * @param type            productSearch
     * @param pageNum         페이지 숫자
     * @param pageSize        페이지 사이즈
     * @param sortCd          정렬 코드
     * @param option          옵션
     * @param targetSearchPrd
     * @return ProductSearchResponse
     * @throws IOException
     */
    @Override
    public ProductSearchResponse getProductSearchApi(String keyword, String type, String pageNum, String pageSize, String sortCd, String option, String targetSearchPrd) throws IOException {
        String OPEN_API_URL = environment.getProperty("11st.URL");
        String url = OPEN_API_URL + environment.getProperty("11st.API_KEY") + "&apiCode=" + type + "&keyword=" + keyword;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "text/xml");

        HttpEntity<?> enti = new HttpEntity<>(headers);

        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, enti, String.class);

        return parser(res.getBody());
    }

    /**
     * XML 파싱
     *
     * @param body XML 데이터
     * @return ProductSearchResponse
     * @throws IOException
     */
    private ProductSearchResponse parser(String body) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(body, ProductSearchResponse.class);
    }
}
