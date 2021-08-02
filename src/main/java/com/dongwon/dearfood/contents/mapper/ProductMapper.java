package com.dongwon.dearfood.contents.mapper;

import com.dongwon.dearfood.contents.domain.*;
import com.dongwon.dearfood.contents.domain.request.AddProductReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    public List<Product> getProductDetail() throws Exception;

    List<ProductDomain> getProductDetailList(int keyword);

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
