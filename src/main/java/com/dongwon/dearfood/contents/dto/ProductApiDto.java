package com.dongwon.dearfood.contents.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductApiDto {
    private List<ProductDto> productList;
}
