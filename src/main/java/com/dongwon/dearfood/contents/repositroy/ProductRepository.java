package com.dongwon.dearfood.contents.repositroy;

import com.dongwon.dearfood.contents.domain.Product;
import com.dongwon.dearfood.contents.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class ProductRepository {
    private ProductMapper mapper;

    @Autowired
    public ProductRepository(ProductMapper mapper) {
        this.mapper = mapper;
    }

    public List<Product> getProductDetail() throws Exception {
        return mapper.getProductDetail();
    }

}
