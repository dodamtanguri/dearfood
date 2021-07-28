package com.dongwon.dearfood.contents.domain;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductApiDomain {
    private String status;
    private List<ProductDomain> productList;

}
