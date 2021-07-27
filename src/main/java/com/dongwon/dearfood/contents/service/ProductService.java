package com.dongwon.dearfood.contents.service;


import com.dongwon.dearfood.contents.domain.Product;
import com.dongwon.dearfood.contents.repositroy.ProductRepository;
import lombok.RequiredArgsConstructor;
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

}
