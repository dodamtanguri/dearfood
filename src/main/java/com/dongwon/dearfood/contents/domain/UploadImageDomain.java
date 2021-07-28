package com.dongwon.dearfood.contents.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadImageDomain {
    private int id;
    private String fileName;
    private String saveFileName;
    private String contentType;
}
