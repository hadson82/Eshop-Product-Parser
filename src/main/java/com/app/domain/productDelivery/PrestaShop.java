package com.app.domain.productDelivery;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "prestashop")
public class PrestaShop {

    private Product product;

}
