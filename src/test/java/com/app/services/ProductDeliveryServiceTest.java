package com.app.services;

import com.app.domain.productIkea.ProductIkea;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ProductDeliveryServiceTest {

    @Test
    void createLinkRewrite() {
        ProductIkeaService productIkeaService = new ProductIkeaService();
        productIkeaService.setUrl("https://www.ikea.com/ru/ru/p/loberget-blisker-rabochiy-stul-belyy-s39331867/");
        ProductIkea productIkea = new ProductIkea();
        WebDriver driver = productIkeaService.getDriver();
        WebDriverWait wait = productIkeaService.getDriverWait(driver);
        productIkea.setReferense(productIkeaService.scrapeReference(driver, wait));
        productIkea.setName(productIkeaService.scrapeName(driver, wait));
        ProductDeliveryService productDeliveryService = new ProductDeliveryService();
        String linkRewrite = productDeliveryService.createLinkRewrite(productIkea);
        System.out.println(linkRewrite);
        assertNotNull(linkRewrite);
        driver.quit();
    }

    @Test
    void createImageNameTest() {
        ProductIkea productIkea = new ProductIkea();
        productIkea.setName("ЛОБЕРГЕТ БЛИСКЭР");
        productIkea.setName2("стул рабочий");

        ProductDeliveryService productDeliveryService = new ProductDeliveryService();

        String result = productDeliveryService.createImageName(productIkea);
        System.out.println(result);
    }

    @Test
    void renameImagesTest() {
        ProductDeliveryService productDeliveryService = new ProductDeliveryService();
        ProductIkea productIkea = new ProductIkea();
        productIkea.setName("ЛОБЕРГЕТ БЛИСКЭР");
        productIkea.setName2("стул рабочий");
        productDeliveryService.renameImages(productIkea);
    }


    @Test
    void sentImagesByPostTest() throws IOException {
        ProductDeliveryService productDeliveryService = new ProductDeliveryService();
        productDeliveryService.sentImagesByPost(11099);
    }

}