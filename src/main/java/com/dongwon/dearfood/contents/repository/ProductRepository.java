package com.dongwon.dearfood.contents.repository;

import com.dongwon.dearfood.contents.domain.*;
import com.dongwon.dearfood.contents.domain.request.AddProductReq;
import com.dongwon.dearfood.contents.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    private final ProductMapper mapper;

    /**
     * 상품 조회
     * @return
     * @throws Exception
     */
    public List<Product> getProductDetail() throws Exception {
        return mapper.getProductDetail();
    }

    /**
     * 하위카테고리 상품 조회
     * @param keyword 카테고리 아이디
     * @return list
     */
    public List<ProductDomain> getProductDetailList(int keyword) {

        return mapper.getProductDetailList(keyword);
    }

    /**
     * 상품 등록
     * @param addReq request body
     * @return AddProductApiDomain
     */
    public AddProductApiDomain createProduct(AddProductReq addReq) {
        AddProductApiDomain createProduct = new AddProductApiDomain();
        mapper.addProduct(addReq);
        createProduct.setProductId(addReq.getId());
        createProduct.setProductName(addReq.getProductName());
        return createProduct;
    }

    /**
     * 상품이미지 정보 등록
     * @param fileId 상품 이미지 아이디
     * @param productId 상품 아이디
     * @return int
     */
    public int createProductImage(int fileId, int productId) {
        AddProductFile file = AddProductFile.builder()
                .fileId(fileId)
                .productId(productId)
                .build();
        mapper.addProductImage(file);
        return file.getId();
    }

    /**
     * 상품 이미지 업로드
     * @param image 이미지
     * @return int
     */

    public int createProductFile(UploadImageDomain image) {
        mapper.addProductFile(image);
        return image.getId();
    }

    /**
     * 상품 판매 중지
     * @param productId 상품 아이디
     * @return boolean
     */
    public int deleteProduct(int productId) {
            return mapper.deleteProduct(productId);
    }

    /**
     * 상품 가격 수정
     * @param productId 상품 아이디
     * @param modifyPrice 수정할 상품 아이디
     * @return boolean
     */
    public int modifyPrice(int productId, String modifyPrice) {
        return mapper.modifyPrice(productId, modifyPrice);
    }

    public int checkDeleteFlag(int productId) {
        return mapper.checkDeleteFlag(productId);
    }
}
