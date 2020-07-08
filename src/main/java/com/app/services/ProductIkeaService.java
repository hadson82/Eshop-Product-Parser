package com.app.services;

import com.app.domain.productIkea.ProductIkea;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductIkeaService {

    private String url = "https://www.ikea.com/ru/ru/p/ikea-365-igerdig-banka-dlya-speciy-steklo-chernyy-50376147/";

    public ProductIkea scrapeProductIkea() throws IOException {

        ProductIkea productikea = new ProductIkea();

        WebDriver driver = this.getDriver();
        WebDriverWait wait = this.getDriverWait(driver);

        clickOK(driver, wait);

        productikea.setName(scrapeName(driver, wait));
        productikea.setName2(scrapeName2(driver, wait));
        productikea.setPrice(scrapePrice(driver, wait));
        productikea.setReferense(scrapeReference(driver, wait));
        scrapeProductImages(driver, wait);
        productikea.setProductAssembledDimensions(scrapeAssembeledDimensionsItems(driver, wait));
        productikea.setDescriptionShortP(scrapeDescriptionShortListP(driver, wait));

        productikea.setMaterialAndUnitParagraphs(scrapeMaterialAndCareUnits(driver, wait));
        productikea.setPacksCount(scrapePacksCount(driver, wait));
        productikea.setPacks(scrapePacksDimensions(driver, wait));


        driver.quit();

        return productikea;
    }

    public String scrapeName(WebDriver driver, WebDriverWait wait) {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("range-revamp-header-section__title--big")));
        String text = driver.findElement(By.className("range-revamp-header-section__title--big")).getText();

        return text;
    }

    public String scrapeName2(WebDriver driver, WebDriverWait wait) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("range-revamp-header-section__description-text")));
        String text = driver.findElement(By.className("range-revamp-header-section__description-text")).getText().toLowerCase();


        return text;
    }

    public int scrapePrice(WebDriver driver, WebDriverWait wait) {


        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[contains(@class, 'range-revamp-pip-price-package__main-price')]/span[contains(@class, 'range-revamp-price')]/span[contains(@class, 'range-revamp-price__integer')]"))));
        String text = driver.findElement(By.xpath("//div[contains(@class, 'range-revamp-pip-price-package__main-price')]/span[contains(@class, 'range-revamp-price')]/span[contains(@class, 'range-revamp-price__integer')]")).getText();
        String textWithoutSpasec = text.replaceAll("\\s", "");

        int price = Integer.parseInt(textWithoutSpasec);


        return price;
    }

    public String scrapeReference(WebDriver driver, WebDriverWait wait) {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class, 'range-revamp-product-identifier__number')]")));
        String text = driver.findElement(By.xpath("//span[contains(@class, 'range-revamp-product-identifier__number')]")).getText();


        return text;

    }

    public List<String> scrapeAssembeledDimensionsItems(WebDriver driver, WebDriverWait wait) {


        clickProductDimensionsLink(driver, wait);


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='range-modal-mount-node']/div/div[3]/div/div[2]/div/dl/div")));
        List<WebElement> elements = driver.findElements(By.xpath("//div[@id='range-modal-mount-node']/div/div[3]/div/div[2]/div/dl/div"));

        List<String> items = new ArrayList<>();
        for(WebElement element : elements) {
            String item = element.getText();
            items.add(item);
        }

        clickProductDimensionsWindowClose(driver, wait);

        return items;
    }



    public List<String> scrapeDescriptionShortListP(WebDriver driver, WebDriverWait wait) {


        clickPodrobneeOTovare(driver, wait);


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='range-modal-mount-node']/div[contains(@class, 'range-revamp-lightbox')]/div[3]/div[contains(@class, 'range-revamp-modal')]/div[contains(@class, 'range-revamp-modal__content')]/div[contains(@class, 'range-revamp-product-details')]/div[contains(@class, 'range-revamp-product-details__container')]/div[contains(@class, 'range-revamp-expander')]/div[contains(@class, 'range-revamp-expander__container')]/div[contains(@class, 'range-revamp-expander__content')]/span[contains(@class, 'range-revamp-product-details__paragraph')]")));
        List<WebElement> elements = driver.findElements(By.xpath("//div[@id='range-modal-mount-node']/div[contains(@class, 'range-revamp-lightbox')]/div[3]/div[contains(@class, 'range-revamp-modal')]/div[contains(@class, 'range-revamp-modal__content')]/div[contains(@class, 'range-revamp-product-details')]/div[contains(@class, 'range-revamp-product-details__container')]/div[contains(@class, 'range-revamp-expander')]/div[contains(@class, 'range-revamp-expander__container')]/div[contains(@class, 'range-revamp-expander__content')]/span[contains(@class, 'range-revamp-product-details__paragraph')]"));
        List<String> items = new ArrayList<>();
        for (WebElement element : elements) {
            String item = element.getText();
            items.add(item);
        }

        return  items;
    }

    public List<String> scrapeMaterialAndCareUnits(WebDriver driver, WebDriverWait wait) {


        clickMaterialAndUnitsTab(driver, wait);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/main/div/div[2]/div/div[3]/div/div[2]/div/ul/li[1]/div[2]/div/div[1]/div")));
        List<WebElement> elements = driver.findElements(By.xpath("//html/body/main/div/div[2]/div/div[3]/div/div[2]/div/ul/li[1]/div[2]/div/div[1]/div"));

        List<String> headers = new ArrayList<>();
        for(WebElement element : elements) {
            String item = element.getText();
            headers.add(item);
        }


        return headers;
    }




    public String scrapePacksCount(WebDriver driver, WebDriverWait wait) {

        String text = null;

        clickPackInformation(driver, wait);


        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div[2]/div/div[3]/div/div[2]/div/ul/li[2]/div[2]/div/span")));
            text = driver.findElement(By.xpath("//div/div[2]/div/div[3]/div/div[2]/div/ul/li[2]/div[2]/div/span")).getText();
        } catch (Exception e){
            System.out.println("Нет количества упаковок");
            text = "Нет количества упаковок";
        }

        return text;
    }

    public List<String> scrapePacksDimensions(WebDriver driver, WebDriverWait wait) {




        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div[3]/div/div[2]/div/ul/li[2]/div[2]/div/div/div/span")));
        List<WebElement> elements = driver.findElements(By.xpath("//div/div[3]/div/div[2]/div/ul/li[2]/div[2]/div/div/div/span"));

        List<String> items = new ArrayList<>();
        for(WebElement element : elements) {
            String item = element.getText();
            items.add(item);
        }


        return items;
    }



    public void scrapeProductImages(WebDriver driver, WebDriverWait wait) throws IOException {

        //клик по кнопке Больше изображений, если она существует
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/main/div/div[1]/div/div[2]/div[1]/div/button/span/span")));
        driver.findElement(By.xpath("/html/body/main/div/div[1]/div/div[2]/div[1]/div/button/span/span")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//html/body/main/div/div[1]/div/div[2]/div[1]/div/div/div/div/img")));
        List<WebElement> images = driver.findElements(By.xpath("//html/body/main/div/div[1]/div/div[2]/div[1]/div/div/div/div/img"));

        for(int i = 0; i<images.size(); i++) {
            String imageSRC = images.get(i).getAttribute("src");
            URL imageURL = new URL(imageSRC);
            BufferedImage saveImage = ImageIO.read(imageURL);
            ImageIO.write(saveImage, "jpg", new File("/home/hadson/Documents/ikeaApps/IkeaProductParserJacksonGradle/src/main/resources/images/"+i+"-ikea-image.jpg"));

        }

    }




    /**
     * Клик по ссылке Материалы и уход на вкладке Подробнее о товаре
     * @param driver
     * @param wait
     */
    public void clickMaterialAndUnitsTab(WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/div[2]/div/ul/li[1]/div[1]/button/span/span")));
        driver.findElement(By.xpath("//div/div[2]/div/ul/li[1]/div[1]/button/span/span")).click();
    }


    /**
     *   закрыть окно Размеры товара в собранном виде
      */
    public void clickProductDimensionsWindowClose(WebDriver driver, WebDriverWait wait) {

        //div[contains(@class, 'range-revamp-modal__title')]/button[contains(@class, 'range-revamp-btn')]
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'range-revamp-modal__title')]/button[contains(@class, 'range-revamp-btn')]")));
        driver.findElement(By.xpath("//div[contains(@class, 'range-revamp-modal__title')]/button[contains(@class, 'range-revamp-btn')]")).click();
    }



    // клик по ссылке размеры товара
    public void clickProductDimensionsLink(WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Размер товара')]")));
        driver.findElement(By.xpath("//span[contains(text(), 'Размер товара')]")).click();
    }

    public void clickOK(WebDriver driver, WebDriverWait wait) {


        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[8]/div/div[2]/button/span/span")));
            driver.findElement(By.xpath("/html/body/div[8]/div/div[2]/button/span/span")).click();
        } catch (Exception e){
            System.out.println("Не шмогла нажать кнопку Ок");
        }
    }

    public void clickPodrobneeOTovare(WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Подробнее о товаре')]")));
        driver.findElement(By.xpath("//span[contains(text(), 'Подробнее о товаре')]")).click();
    }

    /**
     * Клик по табу Информация об упаковке на вкладке Подробнее о товаре.
     * @param driver
     * @param wait
     */
    public void clickPackInformation(WebDriver driver, WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[2]/div/div[3]/div/div[2]/div/ul/li[2]/div[1]/button/span/span")));
            driver.findElement(By.xpath("//div[2]/div/div[3]/div/div[2]/div/ul/li[2]/div[1]/button/span/span")).click();

        } catch (Exception e){
            System.out.println("Нет ссылки \"Информация об Упаковке\"");
        }
    }

    public WebDriver getDriver(){

        WebDriver driver = new ChromeDriver();
        driver.get(getUrl());
        driver.manage().window().maximize();

        return driver;

    }

    public WebDriverWait getDriverWait(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 5);

        return wait;

    }

}
