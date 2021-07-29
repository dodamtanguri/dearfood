package com.dongwon.dearfood.contents.service;


import com.dongwon.dearfood.contents.domain.*;
import com.dongwon.dearfood.contents.domain.request.AddProductReq;
import com.dongwon.dearfood.contents.repositroy.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.dongwon.dearfood.commons.enmuns.ErrorCode.NOT_EXISTS_PRODUCT_ID;

@Slf4j
@Service
public class ProductService {
    @Autowired
    Environment environment;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductDetail() throws Exception {
        return productRepository.getProductDetail();
    }

    @Transactional
    public ProductApiDomain getProductDetailList(int keyword) {
        List<ProductDomain> productDomains = productRepository.getProductDetailList(keyword);
         return  ProductApiDomain.builder()
                .productList(productDomains)
                .status(productDomains.isEmpty() ? "fail" : "success")
                .build();


    }

    @Transactional
    public AddProductApiDomain addProduct(AddProductReq addReq) throws IOException {
        AddProductApiDomain addProduct = productRepository.addProduct(addReq);
        if (addProduct.getProductId() != 0) {
            addProduct.setStatus("success");
        } else {
            addProduct.setStatus("fail");
        }
        return addProduct;
    }

    @Transactional
    public AddProductApiDomain addProduct(int categoryId, String productName, String price, String description, String content, MultipartFile productImage) throws IOException {

        AddProductReq req = AddProductReq.builder()
                .category_id(categoryId)
                .price(price)
                .productName(productName)
                .description(description)
                .content(content)
                .build();
        AddProductApiDomain addProduct = productRepository.addProduct(req);

        int productId = addProduct.getProductId();

        String saveName = UUID.randomUUID().toString() + "_" + productImage.getOriginalFilename();

        UploadImageDomain image = UploadImageDomain.builder()
                .contentType(productImage.getContentType())
                .fileName(productImage.getOriginalFilename())
                .saveFileName(saveName)
                .build();

        File target = new File(environment.getProperty("static.resource.location.img"), saveName);
        FileCopyUtils.copy(productImage.getBytes(), target);
        int fileId = productRepository.addProductFile(image);
        System.out.println("fileId:" + fileId);

        int productImageId = productRepository.addProductImage(fileId, productId);

        addProduct.setFileId(fileId);
        addProduct.setProductImageId(productImageId);
        return addProduct;
    }

    @Transactional
    public ClientMessage deleteProduct(int productId) {
        boolean delete = productRepository.deleteProduct(productId);

        return ClientMessage.builder()
                .productId(productId)
                .status(delete ? "success" : "fail")
                .build();
    }

    @Transactional
    public ClientMessage modifyPrice(int productId, String modifyPrice) {
        boolean modify = productRepository.modifyPrice(productId, modifyPrice);
        return ClientMessage.builder()
                .productId(productId)
                .status(modify ? "success" : "fail")
                .build();
    }
}
