package com.dongwon.dearfood.contents.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ProductCode")
    private String productCode;
    @JacksonXmlProperty(localName = "ProductName")
    private String productName;
    @JacksonXmlProperty(localName = "ProductPrice")
    private String productPrice;
    @JacksonXmlProperty(localName = "ProductImage")
    private String imageUrl;
    @JacksonXmlProperty(localName = "SellerNick")
    private String sellerNick;
    @JacksonXmlProperty(localName = "DetailPageUrl")
    private String productDetailUrl;
    @JacksonXmlProperty(localName = "Seller")
    private String seller;
    @JacksonXmlProperty(localName = "Rating")
    private String rating;
    @JacksonXmlProperty(localName = "SalePrice")
    private String salePrice;
    @JacksonXmlProperty(localName = "Delivery")
    private String delivery;
    @JacksonXmlProperty(localName = "ReviewCount")
    private String reviewCount;
    @JacksonXmlProperty(localName = "BuySatisfy")
    private String buySatisfy;
    @JacksonXmlProperty(localName = "Benefit")
    private Benefit benefit;


}
