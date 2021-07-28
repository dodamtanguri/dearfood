package com.dongwon.dearfood.contents.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    private int id;
    private int categoryId;
    private String productName;
    private String price;
    private String status;
    private String description;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String deleteFlag;
}
