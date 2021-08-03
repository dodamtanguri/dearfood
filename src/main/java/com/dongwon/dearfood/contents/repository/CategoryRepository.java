package com.dongwon.dearfood.contents.repository;

import com.dongwon.dearfood.contents.domain.Category;
import com.dongwon.dearfood.contents.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {
    private final CategoryMapper mapper;

    public List<Category> getCategoryList() throws Exception {
        return mapper.getCategoryList();
    }
}
