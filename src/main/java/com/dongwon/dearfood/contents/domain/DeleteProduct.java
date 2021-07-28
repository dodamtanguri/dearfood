package com.dongwon.dearfood.contents.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProduct {
    private int id;
    private int categoryId;
    private String price;

}
