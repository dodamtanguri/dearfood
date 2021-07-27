package com.dongwon.dearfood.contents.controller;

import com.dongwon.dearfood.contents.domain.Product;
import com.dongwon.dearfood.contents.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"PRODUCT API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductService service;

    @ApiOperation(value = "상품조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Erro")
    })
    @GetMapping(value = "/products")
    public List<Product> productList(
            //@RequestParam(value = "keyword",required = false) String keyword
    ) throws Exception {
        return service.getProductDetail();
    }
}

