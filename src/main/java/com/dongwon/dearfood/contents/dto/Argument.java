package com.dongwon.dearfood.contents.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class Argument {
    @JacksonXmlElementWrapper(localName = "Arguments")
    @JsonProperty(namespace = "key")
    @JacksonXmlProperty(localName = "key")
    private String key;

    @JsonProperty(namespace = "apiCode")
    @JacksonXmlProperty(localName = "apiCode")
    private String apiCode;

    @JsonProperty(namespace = "keyword")
    @JacksonXmlProperty(localName = "keyword")
    private String keyWord;

    @JsonProperty(namespace = "pageNum")
    @JacksonXmlProperty(localName = "pageNum")
    private String pageNum;

    @JsonProperty(namespace = "pageSize")
    @JacksonXmlProperty(localName = "pageSize")
    private String pageSize;

    @JsonProperty(namespace = "sortCd")
    @JacksonXmlProperty(localName = "sortCd")
    private String sortCd;

    @JsonProperty(namespace = "option")
    @JacksonXmlProperty(localName = "option")
    private String option;

    @JsonProperty(namespace = "targetSearchPrd")
    @JacksonXmlProperty(localName = "targetSearchPrd")
    private String targetSearchPrd;

}