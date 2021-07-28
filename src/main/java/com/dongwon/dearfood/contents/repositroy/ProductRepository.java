package com.dongwon.dearfood.contents.repositroy;

import com.dongwon.dearfood.contents.domain.*;
import com.dongwon.dearfood.contents.domain.request.AddProduct;
import com.dongwon.dearfood.contents.domain.request.AddProductReq;
import com.dongwon.dearfood.contents.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

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

    public List<ProductDomain> getProductDetailList(int keyword) {

        return mapper.getProductDetailList(keyword);
    }

    public AddProductApiDomain addProduct(AddProductReq addReq) {
        AddProductApiDomain addProduct = new AddProductApiDomain();
        mapper.addProduct(addReq);
        addProduct.setProductId(addReq.getId());
        addProduct.setProductName(addReq.getProductName());
        return addProduct;
    }

    public int addProductImage(int fileId, int productId) {
        AddProductFile file = AddProductFile.builder()
                .fileId(fileId)
                .productId(productId)
                .build();
        mapper.addProductImage(file);
        return file.getId();
    }

    public int addProductFile(UploadImageDomain image) {
        mapper.addProductFile(image);
        return image.getId();
    }
}
