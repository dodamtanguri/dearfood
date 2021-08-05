package com.dongwon.dearfood.contents.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDomain {
    //카테고리
    private int parentId;
    private int categoryId;
    private String categoryName;
    //상품 정보
    private int id;
    private String productName;
    private String description;
    private String content;
    private String price;
    private String deleteFlag;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyDate;
    //상품 이미지 정보
    private List<ProductImage> productImage;
}
