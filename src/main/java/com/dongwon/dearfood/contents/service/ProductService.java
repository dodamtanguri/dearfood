package com.dongwon.dearfood.contents.service;


import com.dongwon.dearfood.contents.domain.AddProductApiDomain;
import com.dongwon.dearfood.contents.domain.Product;
import com.dongwon.dearfood.contents.domain.ProductApiDomain;
import com.dongwon.dearfood.contents.domain.ProductDomain;
import com.dongwon.dearfood.contents.domain.request.AddProductReq;
import com.dongwon.dearfood.contents.repositroy.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service

public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductDetail() throws Exception {
        return productRepository.getProductDetail();
    }

    public ProductApiDomain getProductDetailList(int keyword) {

        ProductApiDomain domain = new ProductApiDomain();
        domain.setProductList(productRepository.getProductDetailList(keyword));
        if (domain.getProductList().isEmpty()) {
            domain.setStatus("error");
        } else {
            domain.setStatus("success");
        }
        return domain;

    }

    public AddProductApiDomain addProduct(AddProductReq addReq) {
        return productRepository.addProduct(addReq);
    }
}
