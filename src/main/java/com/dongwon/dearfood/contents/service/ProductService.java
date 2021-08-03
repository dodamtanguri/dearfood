package com.dongwon.dearfood.contents.service;


import com.dongwon.dearfood.commons.enmuns.ErrorCode;
import com.dongwon.dearfood.commons.exception.SuspendAlreadyExistException;
import com.dongwon.dearfood.contents.domain.*;
import com.dongwon.dearfood.contents.domain.request.AddProductReq;
import com.dongwon.dearfood.contents.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static com.dongwon.dearfood.commons.enmuns.ErrorCode.NOT_EXISTS_PRODUCT_ID;
import static com.dongwon.dearfood.commons.enmuns.ErrorCode.SUCCESS;


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
        AddProductApiDomain addProduct = productRepository.createProduct(req);

        int productId = addProduct.getProductId();

        String saveName = UUID.randomUUID().toString() + "_" + productImage.getOriginalFilename();

        UploadImageDomain image = UploadImageDomain.builder()
                .contentType(productImage.getContentType())
                .fileName(productImage.getOriginalFilename())
                .saveFileName(saveName)
                .build();

        File target = new File(environment.getProperty("static.resource.location.img"), saveName);
        FileCopyUtils.copy(productImage.getBytes(), target);

        int fileId = productRepository.createProductFile(image);
        int productImageId = productRepository.createProductImage(fileId, productId);

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
    public ClientMessage deleteProduct(int productId) throws Exception, SuspendAlreadyExistException {
        int checkDeleteFlag = productRepository.checkDeleteFlag(productId);
        log.info(String.valueOf(checkDeleteFlag));
        if (checkDeleteFlag == 1) {
            throw new SuspendAlreadyExistException();
        } else {
            int delete = productRepository.deleteProduct(productId);
            if (delete == 0) throw new RuntimeException();
            return ClientMessage.builder()
                    .status("상품번호 : " + productId + "의 상태가 [판매중지]로 변경되었습니다.")
                    .productId(productId)
                    .build();
        }

    }

    /**
     * modifyPrice 상품 가격 수정
     *
     * @param productId   상품 아이디
     * @param modifyPrice 수정할 상품 가격
     * @return ClientMessage
     */
    @Transactional
    public ClientMessage modifyPrice(int productId, String modifyPrice) throws Exception {
        int modify = productRepository.modifyPrice(productId, modifyPrice);
        if (modify == 0) {
            log.info(String.valueOf(modify));
            throw new Exception();
        } else {
            return ClientMessage.builder()
                    .productId(productId)
                    .status("[상품번호 :"+productId+"의 가격이 업데이트 되었습니다.")
                    .build();

        }
    }
}
