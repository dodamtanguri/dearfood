package com.dongwon.dearfood.contents.controller;

import com.dongwon.dearfood.commons.exception.CustomRequestException;
import com.dongwon.dearfood.contents.service.ProductSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"11st API"})
@Slf4j
@RestController
@RequestMapping("/api/v1/11stapi")
@RequiredArgsConstructor
public class ApiController {
    private final ProductSearchService productSearchService;

    @ApiOperation(value = "11번가 상품 조회하기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomRequestException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CustomRequestException.class)
    })

    @GetMapping
    //(value = "/productSearch", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> productSearch(
            @RequestParam(name = "keyword", defaultValue = "open") String keyword,
            @RequestParam(name = "type", defaultValue = "ProductSearch") String type,
            @RequestParam(name = "pageNum", defaultValue = "2") String pageNum,
            @RequestParam(name = "pageSize", required = false) String pageSize,
            @RequestParam(name = "sortCd", required = false) String sortCd,
            @RequestParam(name = "option", required = false) String option,
            @RequestParam(name = "targetSearchPrd", required = false) String targetSearchPrd) throws Exception {
        return ResponseEntity.ok().body(productSearchService.getProductSearchApi(keyword, type, pageNum, pageSize, sortCd, option, targetSearchPrd));

    }
}
