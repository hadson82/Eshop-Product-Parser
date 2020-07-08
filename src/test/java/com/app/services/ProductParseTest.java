package com.app.services;

import com.app.services.old.ProductParse;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

class ProductParseTest {



    @Test
    void parseReference() {
        ProductParse productParse = new ProductParse();
        WebDriver driver = productParse.getDriver();
        WebDriverWait wait = productParse.getDriverWait(driver);
        String text = productParse.parseReference(driver, wait);
        System.out.println(text);
        assertNotNull(text);
        driver.quit();
    }

    @Test
    void productBuild() {
    }

    @Test
    void parseName() {
        ProductParse productParse = new ProductParse();
        WebDriver driver = productParse.getDriver();
        WebDriverWait wait = productParse.getDriverWait(driver);
        String text = productParse.parseName(driver, wait);
        System.out.println(text);
        assertNotNull(text);
        driver.quit();
    }

    @Test
    void createMetaTitle() {
    }

    @Test
    void createLinkRewrite() {
    }

    @Test
    void createMetaDescription() {
    }

    @Test
    void parsePrice() {
        ProductParse productParse = new ProductParse();
        WebDriver driver = productParse.getDriver();
        WebDriverWait wait = productParse.getDriverWait(driver);
        int price = productParse.parsePrice(driver, wait);
        System.out.println(price);
        assertNotNull(price);
        driver.quit();
    }

    @Test
    void parseDescription() {
    }

    @Test
    void parseDescriptionShort() {
        ProductParse productParse = new ProductParse();
        WebDriver driver = productParse.getDriver();
        WebDriverWait wait = productParse.getDriverWait(driver);
        String text = productParse.parseDescriptionShort(driver, wait);
        System.out.println(text);
        assertNotNull(text);
        driver.quit();
    }

    @Test
    void productBuildInString() {
    }

    @Test
    void getDriver() {
    }

    @Test
    void getDriverWait() {
    }

    @Test
    void getUrl() {
    }

    @Test
    void setUrl() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void canEqual() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}