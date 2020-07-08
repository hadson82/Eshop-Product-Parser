package com.app.domain.productDelivery;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "product")
public class Product {

    private int id_category_default;
//    private int id_shop_default;
    private String reference;
    private int price;
    private int active;
    private int available_for_order;
    private MetaDescription meta_description;
    private LinkRewrite link_rewrite;
    private MetaTitle meta_title;
    private Name name;
    private Description description;
    private DescriptionShort description_short;




}
