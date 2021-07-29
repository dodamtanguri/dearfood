package com.dongwon.dearfood.contents.controller;

import com.dongwon.dearfood.commons.exception.CustomRequestException;
import com.dongwon.dearfood.contents.domain.AddProductApiDomain;
import com.dongwon.dearfood.contents.domain.ClientMessage;
import com.dongwon.dearfood.contents.domain.Product;
import com.dongwon.dearfood.contents.domain.ProductApiDomain;
import com.dongwon.dearfood.contents.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Api(tags = {"PRODUCT API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService service;

    @ApiOperation(value = "상품조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping
    public List<Product> productList() throws Exception {
        return service.getProductDetail();
    }

    @ApiOperation(value = "하위카테고리 상품 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomRequestException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CustomRequestException.class)
    })
    @GetMapping(value = "/subcategory")
    public ProductApiDomain getProductDetailList(
            @RequestParam(name = "keyword", required = true, defaultValue = "8") int keyword) throws Exception {
        return service.getProductDetailList(keyword);
    }

    @ApiOperation(value = "상품 등록")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomRequestException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CustomRequestException.class)
    })
    @PostMapping
    public AddProductApiDomain addProduct(
            @RequestParam(name = "categoryId", defaultValue = "8") int categoryId,
            @RequestParam(name = "productName", defaultValue = "test") String productName,
            @RequestParam(name = "price", defaultValue = "10000") String price,
            @RequestParam(name = "description", defaultValue = "test") String description,
            @RequestParam(name = "content", defaultValue = "test") String content,
            @RequestPart(name = "productImage") MultipartFile productImage) throws IOException {
        return service.addProduct(categoryId, productName, price, description, content, productImage);
    }

    @ApiOperation(value = "상품 판매중지")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomRequestException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CustomRequestException.class)
    })
    @PatchMapping(value = "/{productId}/suspend")
    public ClientMessage deleteProduct(
            @PathVariable(name = "productId") int productId) throws Exception {

        return service.deleteProduct(productId);
    }

    @ApiOperation(value = "상품 가격 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = CustomRequestException.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = CustomRequestException.class)
    })
    @PatchMapping(value = "/{productId}/price")
    public ClientMessage modifyPrice(
            @PathVariable(name = "productId") int productId,
            @RequestParam(name = "modifyPrice", defaultValue = "20000") String modifyPrice) throws Exception {
        return service.modifyPrice(productId, modifyPrice);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public String exception(NoSuchElementException e) {
        return e.getMessage();
    }

}

