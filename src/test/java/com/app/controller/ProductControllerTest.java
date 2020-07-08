package com.app.controller;

import com.app.services.old.ProductParse;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class ProductControllerTest {

    @Test
    void sent() {
    }

    @Test
    void product() {
        ProductParse productParse = new ProductParse();
        productParse.setUrl("https://www.ikea.com/ru/ru/p/plats-solonka-perechnica-2-shtuki-nerzhaveyushch-stal-70372469/");
        String url = "http://localhost:8250/product";
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
        driver.quit();
    }
}