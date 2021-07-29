package com.dongwon.dearfood.contents.service;


import com.dongwon.dearfood.contents.domain.*;
import com.dongwon.dearfood.contents.domain.request.AddProductReq;
import com.dongwon.dearfood.contents.repositroy.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final Environment environment;
    private final ProductRepository productRepository;

    /**
     * getProductDetail 상품 조회
     *
     * @return List
     * @throws Exception
     */
    public List<Product> getProductDetail() throws Exception {
        return productRepository.getProductDetail();
    }

    /**
     * getProductDetailList 하위카테고리 상품조회
     *
     * @param keyword 하위카테고리아이디
     * @return ProductApiDomain
     */
    @Transactional
    public ProductApiDomain getProductDetailList(int keyword) {
        List<ProductDomain> productDomains = productRepository.getProductDetailList(keyword);
        return ProductApiDomain.builder()
                .productList(productDomains)
                .status(productDomains.isEmpty() ? "fail" : "success")
                .build();


    }

    /**
     * createProduct 상품등록
     *
     * @param categoryId   하위카테고리 아이디
     * @param productName  상품명
     * @param price        상품 가격
     * @param description  상품 설명
     * @param content      상품 상세 설명
     * @param productImage 상품 이미지
     * @return AddProductApiDomain
     * @throws IOException
     */
    @Transactional
    public AddProductApiDomain createProduct(int categoryId, String productName, String price, String description, String content, MultipartFile productImage) throws IOException {

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
        int productImageId = productRepository.addProductImage(fileId, productId);

        addProduct.setFileId(fileId);
        addProduct.setProductImageId(productImageId);

        return addProduct;
    }

    /**
     * deleteProduct 상품 판매 중지
     *
     * @param productId 상품 아이디
     * @return ClientMessage
     */
    @Transactional
    public ClientMessage deleteProduct(int productId) {
        boolean delete = productRepository.deleteProduct(productId);

        return ClientMessage.builder()
                .productId(productId)
                .status(delete ? "success" : "fail")
                .build();
    }

    /**
     * modifyPrice 상품 가격 수정
     *
     * @param productId   상품 아이디
     * @param modifyPrice 수정할 상품 가격
     * @return ClientMessage
     */
    @Transactional
    public ClientMessage modifyPrice(int productId, String modifyPrice) {
        boolean modify = productRepository.modifyPrice(productId, modifyPrice);
        return ClientMessage.builder()
                .productId(productId)
                .status(modify ? "success" : "fail")
                .build();
    }
}
