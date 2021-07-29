package com.dongwon.dearfood.contents.service;


import com.dongwon.dearfood.contents.dto.ProductSearchResponse;

import java.io.IOException;

public interface ProductSearchService {


    ProductSearchResponse getProductSearchApi(String keyword, String apiCode,  String pageNum, String pageSize, String sortCd, String option, String targetSearchPrd) throws IOException;
}

