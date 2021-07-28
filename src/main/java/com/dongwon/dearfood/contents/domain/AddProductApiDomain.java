package com.dongwon.dearfood.contents.domain;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductApiDomain {

    private int productId;
    private String productName;
    private String status;
}
