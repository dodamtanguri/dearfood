package com.dongwon.dearfood.contents.service;


import com.dongwon.dearfood.contents.domain.*;
import com.dongwon.dearfood.contents.domain.request.AddProductReq;
import com.dongwon.dearfood.contents.repositroy.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    public AddProductApiDomain addProduct(AddProductReq addReq) throws IOException {
        AddProductApiDomain addProduct = productRepository.addProduct(addReq);
        if (addProduct.getProductId() != 0) {
            addProduct.setStatus("success");
        } else {
            addProduct.setStatus("fail");
        }
        return addProduct;
    }


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

    public ClientMessage deleteProduct(int productId) {
        ClientMessage clientMessage = new ClientMessage();
        boolean delete = productRepository.deleteProduct(productId);
        clientMessage.setProductId(productId);
        if (!delete) {
            clientMessage.setStatus("fail");
        } else {
            clientMessage.setStatus("success!");
        }


        return clientMessage;
    }

    public ClientMessage modifyPrice(int productId, String modifyPrice) {
        //판매중인지, productId가 존재하는지 확인해야하는데
        ClientMessage clientMessage = new ClientMessage();
        boolean modify = productRepository.modifyPrice(productId, modifyPrice);
        clientMessage.setProductId(productId);
        if (!modify) {
            clientMessage.setStatus("판매가격 수정에 실패했습니다. 상품번호를 다시 한 번 확인해 주세요.");
        } else {
            clientMessage.setStatus("success!");
        }
        return clientMessage;
    }
}
