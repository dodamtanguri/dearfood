package com.dongwon.dearfood.contents.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
