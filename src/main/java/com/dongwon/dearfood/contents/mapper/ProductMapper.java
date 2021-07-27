package com.dongwon.dearfood.contents.mapper;

import com.dongwon.dearfood.contents.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface ProductMapper {
    @Select("SELECT * FROM product")
    public List<Product> getProductDetail() throws Exception;

}
