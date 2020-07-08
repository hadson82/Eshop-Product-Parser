package com.app.controller;

import com.app.domain.productDelivery.PrestaShop;
import com.app.services.ProductDeliveryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

@RestController
public class ProductController {



    @GetMapping(value = "/sent")
    public void sent() throws XMLStreamException, IOException {

        ProductDeliveryService productDeliveryService = new ProductDeliveryService();
        PrestaShop prestaShop = productDeliveryService.createProductDelivery("https://www.ikea.com/ru/ru/p/torkel-rabochiy-stul-bumstad-chernyy-20459950/");


        String xml = productDeliveryService.productToXml(prestaShop);

        productDeliveryService.sentProductByPost(xml);


    }

    @GetMapping(value = "/product")
    public PrestaShop product() throws XMLStreamException, IOException {

        ProductDeliveryService productDeliveryService = new ProductDeliveryService();


        return productDeliveryService.createProductDelivery("https://www.ikea.com/ru/ru/p/torkel-rabochiy-stul-bumstad-chernyy-20459950/");

    }


}

