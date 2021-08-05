package com.dongwon.dearfood.contents.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;


import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@MybatisTest
class ProductMapperTest {

    @Autowired
    private ProductMapper mapper;

    @Test
    public void getProductList() {

    }
 }