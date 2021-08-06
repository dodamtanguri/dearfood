package com.dongwon.dearfood.contents.controller;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ProductControllerTest {
    private MockMvc mockMvc;
    private final String URI = "/api/v1/product";
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("하위카테고리아이디로 상품목록 조회하기 🛠")
    public void shouldFindProductById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(URI + "/subcategory?keyword=8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success")).andReturn();

    }

    @Test
    @DisplayName("유효하지 않은 카테고리 아이디")
    public void shouldVerifyInvalidCategoryId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(URI + "/subcategory?keyword=100")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage")
                        .value("존재하지 않는 아이디 입니다."))
                .andExpect((jsonPath("$.errorCode")
                        .value("NO_EXIST_ID")))
                .andReturn();

    }

    @Test
    @DisplayName("상품 가격 수정하기 💰")
    public void shouldUpdatePrice() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.patch(URI + "/1/price?modifyPrice=20000")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId")
                        .value(1))
                .andExpect(jsonPath("$.status")
                        .value("[상품번호 :1] 의 가격이 업데이트 되었습니다.")).andReturn();

    }

    @Test
    @DisplayName("유효하지 않은 상품 아이디❌")
    public void shouldVerifyInvalidUpdate() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.patch(URI + "/100/price?modifyPrice=20000")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode")
                        .value("NO_EXIST_ID"))
                .andExpect(jsonPath("$.errorMessage")
                        .value("존재하지 않는 아이디 입니다.")).andReturn();
    }


}