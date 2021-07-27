package com.dongwon.dearfood.contents.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDomain {
    private String productName;
    private int id;
    private int categoryId;
    private String categoryName;
    private String description;
    private String content;
    private String price;
    private String fileName;
    private int productImageId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyDate;
}