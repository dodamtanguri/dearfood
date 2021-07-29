package com.dongwon.dearfood.contents.mapper;

import com.dongwon.dearfood.contents.domain.*;
import com.dongwon.dearfood.contents.domain.request.AddProductReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT * FROM product")
    public List<Product> getProductDetail() throws Exception;

    @Select("SELECT p.id,\n" +
            "       p.product_name,\n" +
            "       p.category_id as categoryId,\n" +
            "       c.parent_id,\n" +
            "       c.name        as categoryName,\n" +
            "       p.description as description,\n" +
            "       p.content     as content,\n" +
            "       p.price       as price,\n" +
            "       fi.file_name  as fileName,\n" +
            "       pi.id         as productImageId,\n" +
            "       p.create_date as createDate,\n" +
            "       p.modify_date as modifyDate,\n" +
            "       p.delete_flag as deleteFlag\n" +
            "FROM product p\n" +
            "         INNER join category c on p.category_id = c.id\n" +
            "         INNER JOIN product_image pi on p.id = pi.product_id\n" +
            "         INNER JOIN file_info fi on pi.file_id = fi.id\n" +
            "WHERE p.category_id = #{keyword}")
    List<ProductDomain> getProductDetailList(@Param("keyword") int keyword);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Result(column = "category_id", property = "addReq.category_id")
    @Insert("INSERT INTO product (category_id, product_name, price, description, content) values (#{category_id},#{productName},#{price},#{description},#{content})")
    public void addProduct(AddProductReq addReq);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO product_image (product_id, file_id) values(#{productId},#{fileId})")
    public void addProductImage(AddProductFile file);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO file_info (file_name, save_file_name, content_type) values (#{fileName},#{saveFileName},#{contentType})")
    public void addProductFile(UploadImageDomain image);

    @Update("UPDATE product SET delete_flag = 'Y', modify_date = now() WHERE id = #{productId};")
    public boolean deleteProduct(@Param("productId") int productId);

    @Update("UPDATE product SET price = #{modifyPrice}, modify_date = now() WHERE id = #{productId}")
    boolean modifyPrice(@Param("productId") int productId, @Param("modifyPrice") String modifyPrice);
}
