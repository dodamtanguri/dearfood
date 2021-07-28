package com.dongwon.dearfood.contents.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "상품 등록 Request")
public class AddProductReq {
    private AddProduct product;
    private MultipartFile productImage;
}
