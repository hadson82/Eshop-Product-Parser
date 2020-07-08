package com.app.services.old;

import com.app.domain.productDelivery.PrestaShop;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import org.openqa.selenium.WebDriver;

@Data
public class ProductParse {



    private String url = "https://www.ikea.com/ru/ru/p/ikea-365-igerdig-banka-dlya-speciy-steklo-chernyy-50376147/";


    public ProductParse() {
    }


    public PrestaShop productBuild() throws XMLStreamException, IOException {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("product.xml");
        XMLInputFactory inputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(resourceAsStream);

        XmlMapper mapper = new XmlMapper();

        PrestaShop prestashop = mapper.readValue(xmlStreamReader, PrestaShop.class);

        ProductParse productscrape = new ProductParse();

        WebDriver driver = productscrape.getDriver();
        WebDriverWait wait = productscrape.getDriverWait(driver);


        prestashop.getProduct().setId_category_default(2);
        prestashop.getProduct().getName().getLanguage().setText(productscrape.parseName(driver, wait));
        prestashop.getProduct().getDescription().getLanguage().setText(productscrape.parseDescription());
        //prestashop.getProduct().getDescription_short().getLanguage().setText(productscrape.parseDescriptionShort(driver, wait));
        prestashop.getProduct().setReference(productscrape.parseReference(driver, wait));
        prestashop.getProduct().setPrice(productscrape.parsePrice(driver, wait));
        prestashop.getProduct().setActive(1);
        prestashop.getProduct().setAvailable_for_order(1);


        driver.quit();

        return prestashop;
    }

    public String parseReference(WebDriver driver, WebDriverWait wait) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("range-revamp-product-identifier__number")));
        String text = driver.findElement(By.className("range-revamp-product-identifier__number")).getText();

        return text;
    }

    public String parseName(WebDriver driver, WebDriverWait wait) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("range-revamp-header-section__title--big")));
        String text = driver.findElement(By.className("range-revamp-header-section__title--big")).getText();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("range-revamp-header-section__description-text")));
        String text2 = driver.findElement(By.className("range-revamp-header-section__description-text")).getText();

        String text2ToLowerCase = text2.toLowerCase();

        return text+" "+text2ToLowerCase;
    }



    public int parsePrice(WebDriver driver, WebDriverWait wait) {


        wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[contains(@class, 'range-revamp-pip-price-package__main-price')]/span[contains(@class, 'range-revamp-price')]/span[contains(@class, 'range-revamp-price__integer')]"))));
        String text = driver.findElement(By.xpath("//div[contains(@class, 'range-revamp-pip-price-package__main-price')]/span[contains(@class, 'range-revamp-price')]/span[contains(@class, 'range-revamp-price__integer')]")).getText();
        String textWithoutSpasec = text.replaceAll("\\s", "");

        int price = Integer.parseInt(textWithoutSpasec);

        return price;
    }



    public String parseDescriptionShort(WebDriver driver, WebDriverWait wait) {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Ок')]")));
        driver.findElement(By.xpath("//span[contains(text(), 'Ок')]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Подробнее о товаре')]")));
        driver.findElement(By.xpath("//span[contains(text(), 'Подробнее о товаре')]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"range-modal-mount-node\"]/div/div[3]/div/div[2]/div/div")));
        String text = driver.findElement(By.xpath("//*[@id=\"range-modal-mount-node\"]/div/div[3]/div/div[2]/div/div")).getText();

        return text;
    }

    public String parseDescription() {
        return null;
    }















    public WebDriver getDriver(){

        WebDriver driver = new ChromeDriver();
        driver.get(getUrl());
        driver.manage().window().maximize();

        return driver;

    }

    public WebDriverWait getDriverWait(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 30);

        return wait;

    }



}
