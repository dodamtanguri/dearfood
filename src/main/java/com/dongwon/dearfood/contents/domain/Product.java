package com.dongwon.dearfood.contents.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    private int id;
    private int category_id;
    private String product_name;
    private String price;
    private String status;
    private String description;
    private String content;
    private LocalDateTime create_date;
    private LocalDateTime modify_date;
}
