package com.dongwon.dearfood.contents.mapper;

import com.dongwon.dearfood.contents.domain.*;
import com.dongwon.dearfood.contents.domain.request.AddProductReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> getProductDetail() throws Exception;

    List<ProductDomain> getProductDetailList(@Param("keyword") int keyword);

    void addProduct(AddProductReq addReq);

    void addProductImage(AddProductFile file);

    void addProductFile(UploadImageDomain image);

    int deleteProduct(@Param("productId") int productId);

    int modifyPrice(@Param("productId") int productId, @Param("modifyPrice") String modifyPrice);

    int checkDeleteFlag(@Param("productId") int productId);
}
