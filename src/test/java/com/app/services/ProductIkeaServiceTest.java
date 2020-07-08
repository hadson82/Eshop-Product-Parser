package com.app.services;

import com.app.domain.productIkea.ProductIkea;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductIkeaServiceTest {

    @Test
    void scrapeAssembeledDimensionsItemsTest(){

        ProductIkeaService productIkeaService = new ProductIkeaService();
        WebDriver driver = productIkeaService.getDriver();
        WebDriverWait wait = productIkeaService.getDriverWait(driver);

        productIkeaService.clickOK(driver, wait);
        
        List<String> p = productIkeaService.scrapeAssembeledDimensionsItems(driver, wait);
        System.out.println(p);
        assertNotNull(p);
        driver.quit();
    }

    @Test
    void scrapeDescriptionShortListPTest(){

        ProductIkeaService productIkeaService = new ProductIkeaService();
        WebDriver driver = productIkeaService.getDriver();
        WebDriverWait wait = productIkeaService.getDriverWait(driver);

        productIkeaService.clickOK(driver, wait);
        
        List<String> p = productIkeaService.scrapeDescriptionShortListP(driver, wait);
        System.out.println(p);
        assertNotNull(p);
        driver.quit();
        

    }

    @Test
    void scrapePriceTest() {
        ProductIkeaService productIkeaService = new ProductIkeaService();
        WebDriver driver = productIkeaService.getDriver();
        WebDriverWait wait = productIkeaService.getDriverWait(driver);
        int price = productIkeaService.scrapePrice(driver, wait);
        System.out.println(price);
        assertNotNull(price);
        driver.quit();
        
    }


    @Test
    void scrapreName2() {
        ProductIkeaService productIkeaService = new ProductIkeaService();
        WebDriver driver = productIkeaService.getDriver();
        WebDriverWait wait = productIkeaService.getDriverWait(driver);
        
        String name = productIkeaService.scrapeName2(driver, wait);
        System.out.println(name);
        assertNotNull(name);
        driver.quit();
    }

    @Test
    void scrapeName() {
        ProductIkeaService productIkeaService = new ProductIkeaService();
        WebDriver driver = productIkeaService.getDriver();
        WebDriverWait wait = productIkeaService.getDriverWait(driver);
        
        String name = productIkeaService.scrapeName(driver, wait);
        System.out.println(name);
        assertNotNull(name);
        driver.quit();
        
    }

    @Test
    void scrapeReference() {
        ProductIkeaService productIkeaService = new ProductIkeaService();
        WebDriver driver = productIkeaService.getDriver();
        WebDriverWait wait = productIkeaService.getDriverWait(driver);
        
        String ref = productIkeaService.scrapeReference(driver, wait);
        System.out.println(ref);
        assertNotNull(ref);
        driver.quit();
        
    }


    @Test
    void scrapeMaterialAndCareUnitsTest() {
        ProductIkeaService productIkeaService = new ProductIkeaService();
        productIkeaService.setUrl("https://www.ikea.com/ru/ru/p/nordviken-barnyy-stol-belyy-70369612/");
        WebDriver driver = productIkeaService.getDriver();
        WebDriverWait wait = productIkeaService.getDriverWait(driver);
        productIkeaService.clickOK(driver, wait);
        productIkeaService.clickPodrobneeOTovare(driver, wait);
        
        List<String> p = productIkeaService.scrapeMaterialAndCareUnits(driver, wait);
        System.out.println("1 element: ");
        System.out.println(p.get(0));
        System.out.println("2 element");
        System.out.println(p.get(1));
        System.out.println("3 element");
        System.out.println(p.get(2));
        assertNotNull(p);

        driver.quit();
        
    }

    @Test
    void scrapePacksCountTestProductWithPacks() {
        ProductIkeaService productIkeaService = new ProductIkeaService();
        productIkeaService.setUrl("https://www.ikea.com/ru/ru/p/nordviken-barnyy-stol-belyy-70369612/");
        WebDriver driver = productIkeaService.getDriver();
        WebDriverWait wait = productIkeaService.getDriverWait(driver);
        productIkeaService.clickOK(driver, wait);
        productIkeaService.clickPodrobneeOTovare(driver, wait);
        productIkeaService.clickPackInformation(driver, wait);
        String text = productIkeaService.scrapePacksCount(driver, wait);
        System.out.println(text);
        assertNotNull(text);
        driver.quit();
        
    }

    @Test
    void scrapePacksCountTestProductHasNoPacs() {
        ProductIkeaService productIkeaService = new ProductIkeaService();
        productIkeaService.setUrl("https://www.ikea.com/ru/ru/p/ikea-365-igerdig-banka-dlya-speciy-steklo-chernyy-50376147/");
        WebDriver driver = productIkeaService.getDriver();
        WebDriverWait wait = productIkeaService.getDriverWait(driver);
        productIkeaService.clickOK(driver, wait);
        productIkeaService.clickPodrobneeOTovare(driver, wait);
        productIkeaService.clickPackInformation(driver, wait);
        String text = productIkeaService.scrapePacksCount(driver, wait);
        System.out.println(text);
        assertEquals("Нет количества упаковок",text);
        driver.quit();
        
    }

    @Test
    void scrapePacksDimensionsTest() {
        ProductIkeaService productIkeaService = new ProductIkeaService();
        productIkeaService.setUrl("https://www.ikea.com/ru/ru/p/nordviken-barnyy-stol-belyy-70369612/");
        WebDriver driver = productIkeaService.getDriver();
        WebDriverWait wait = productIkeaService.getDriverWait(driver);

        productIkeaService.clickOK(driver, wait);
        productIkeaService.clickPodrobneeOTovare(driver, wait);
        productIkeaService.clickPackInformation(driver, wait);
        
        List<String> items = productIkeaService.scrapePacksDimensions(driver, wait);
        System.out.println(items);
        assertNotNull(items);
        driver.quit();
        
    }

    @Test
    void scrapeProductIkeaTest() throws IOException {
        ProductIkeaService productIkeaService = new ProductIkeaService();
        productIkeaService.setUrl("https://www.ikea.com/ru/ru/p/nordviken-barnyy-stol-belyy-70369612/");
        ProductIkea productIkea = productIkeaService.scrapeProductIkea();
        System.out.println(productIkea);
        assertNotNull(productIkea);
    }

    @Test
    void scrapeProductImagesTest() throws IOException {
        ProductIkeaService productIkeaService = new ProductIkeaService();
        productIkeaService.setUrl("https://www.ikea.com/ru/ru/p/loberget-blisker-rabochiy-stul-belyy-s39331867/");
        WebDriver driver = productIkeaService.getDriver();
        WebDriverWait wait = productIkeaService.getDriverWait(driver);
        productIkeaService.clickOK(driver, wait);
        productIkeaService.scrapeProductImages(driver, wait);
        driver.quit();
    }

}