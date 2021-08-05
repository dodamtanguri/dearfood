package com.dongwon.dearfood.contents.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileInfo {
    private int id;
    private String fileName;
}
