package com.dongwon.dearfood.contents.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProductFile {
    private int id;
    private int productId;
    private int fileId;

}
