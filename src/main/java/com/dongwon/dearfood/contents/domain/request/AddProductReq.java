package com.dongwon.dearfood.contents.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ApiModel(description = "상품 등록 Request")

public class AddProductReq {
    private int id;
    @ApiModelProperty(name = "하위카테고리 번호", example = "8")
    private int category_id;
    @ApiModelProperty(name = "상품명", example = "test")
    private String productName;
    @ApiModelProperty(name = "상품가격", example = "10000")
    private String price;
    @ApiModelProperty(name = "상품설명", example = "test 상품 살명")
    private String description;
    @ApiModelProperty(name = "상품상세설명", example = "test 상품 상세 설명")
    private String content;
}

