package com.dongwon.dearfood.contents.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {
    private int id;
    private String deleteFlag;
    private FileInfo fileInfo;
}
