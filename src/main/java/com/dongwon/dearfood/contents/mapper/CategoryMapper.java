package com.dongwon.dearfood.contents.mapper;

import com.dongwon.dearfood.contents.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    public List<Category> getCategoryList() throws Exception;
}
