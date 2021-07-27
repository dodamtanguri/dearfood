package com.dongwon.dearfood.contents.domain;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductApiDomain {
    private List<ProductDomain> productList;
}
