package com.dongwon.dearfood.contents.service;

import com.dongwon.dearfood.contents.domain.Category;
import com.dongwon.dearfood.contents.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    public List<Category> getCategoryList() throws Exception {
        return repository.getCategoryList();
    }
}
