package com.dongwon.dearfood.contents.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    private int id;
    private String deleteFlag;
    private FileInfo fileInfo;
}
