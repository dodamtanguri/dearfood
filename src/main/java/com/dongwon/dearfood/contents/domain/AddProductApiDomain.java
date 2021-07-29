package com.dongwon.dearfood.contents.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductApiDomain {

    private int productId;
    private String productName;
    private String status;
    private int fileId;
    private int productImageId;
}
