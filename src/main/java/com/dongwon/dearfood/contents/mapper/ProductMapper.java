package com.dongwon.dearfood.contents.mapper;

import com.dongwon.dearfood.contents.domain.Product;
import com.dongwon.dearfood.contents.domain.ProductApiDomain;
import com.dongwon.dearfood.contents.domain.ProductDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT * FROM product")
    public List<Product> getProductDetail() throws Exception;

    @Select("SELECT p.id,\n" +
            "       p.product_name as productName,\n" +
            "       p.category_id as categoryId,\n" +
            "       c.name as categoryName,\n" +
            "       p.description as description,\n" +
            "       p.content as content,\n" +
            "       p.price as price,\n" +
            "       fi.file_name as fileName,\n" +
            "       pi.id as productImageId,\n" +
            "       p.create_date as createDate,\n" +
            "       p.modify_date as modifyDate\n" +
            "FROM product p\n" +
            "         INNER join category c on p.category_id = c.id\n" +
            "         INNER JOIN product_image pi on p.id = pi.product_id\n" +
            "         INNER JOIN file_info fi on pi.file_id = fi.id\n" +
            "where p.category_id like CONCAT('%', #{keyword}, '%')")
    List<ProductDomain> getProductDetailList(@Param("keyword") int keyword);
}
