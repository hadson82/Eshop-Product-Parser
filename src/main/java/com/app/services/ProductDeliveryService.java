package com.app.services;

import com.app.domain.productDelivery.PrestaShop;
import com.app.domain.productIkea.ProductIkea;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ibm.icu.text.Transliterator;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Data
public class ProductDeliveryService {

    public PrestaShop createProductDelivery(String url) throws XMLStreamException, IOException {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("product.xml");
        XMLInputFactory inputFactory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(resourceAsStream);

        XmlMapper mapper = new XmlMapper();

        PrestaShop prestashop = mapper.readValue(xmlStreamReader, PrestaShop.class);

        ProductIkeaService productIkeaService = new ProductIkeaService();
        productIkeaService.setUrl(url);
        ProductIkea productIkea = productIkeaService.scrapeProductIkea();

        prestashop.getProduct().setId_category_default(2);
        prestashop.getProduct().getName().getLanguage().setText(createName(productIkea));
        prestashop.getProduct().getDescription_short().getLanguage().setText(createDescriptionShort(productIkea));
        prestashop.getProduct().setReference(createReference(productIkea));
        prestashop.getProduct().setPrice(createPrice(productIkea));
        prestashop.getProduct().setActive(1);
        prestashop.getProduct().setAvailable_for_order(1);
        prestashop.getProduct().getMeta_title().getLanguage().setText(createMetaTitle(productIkea));
        prestashop.getProduct().getLink_rewrite().getLanguage().setText(createLinkRewrite(productIkea));
        prestashop.getProduct().getMeta_description().getLanguage().setText(createMetaDescription(productIkea));

        prestashop.getProduct().getDescription().getLanguage().setText(createDescription(productIkea));
        renameImages(productIkea);

        return prestashop;

    }

    public void renameImages(ProductIkea productIkea) {

        String folder_path =
                "/home/hadson/Documents/ikeaApps/IkeaProductParserJacksonGradle/src/main/resources/images";

        File dir = new File(folder_path);

        File[] file_array = dir.listFiles();

        for (int i = 0; i < file_array.length; i++) {

            if (file_array[i].isFile()) {

                File myfile = new File(dir +
                        "/" + file_array[i].getName());
                myfile.renameTo(new File(folder_path +
                        "/" + i + "-" + createImageName(productIkea) + ".jpg"));
            }
        }
    }

    public String createImageName(ProductIkea productIkea) {
        final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";

        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);

        String name = productIkea.getName().toLowerCase();
        String name2 = productIkea.getName2().toLowerCase().replace(",", "").replace(" ", "-");

        String nameTranslit = toLatinTrans.transliterate(name).toLowerCase().replace(" ", "-").replace("+", "").replace("·", "");
        String name2Translit = toLatinTrans.transliterate(name2);


        String result = nameTranslit + "-" + name2Translit + "-ikea";

        return result;
    }


    public String createName(ProductIkea productIkea) {
        String result = productIkea.getName() + " " + productIkea.getName2();
        return result;
    }

    public String createDescriptionShort(ProductIkea productIkea) {
        String d = "<p>" + productIkea.getDescriptionShortP().get(0) + "</p>" + "\n"
                + "<p style=\"color: green;\" itemprop=\"availability\" href=\"http://schema.org/InStock\"><b>В наличии</b></p>";
        return d;
    }

    public String createMetaDescription(ProductIkea productIkea) {

        String text = productIkea.getName() + " " + productIkea.getName2() + " ИКЕА. Доставка по Москве и России";

        return text;
    }

    public String createLinkRewrite(ProductIkea productIkea) {

        final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";

        String referense = productIkea.getReferense();
        String referenseWithoutDots = referense.replace(".", "");

        String name = productIkea.getName().replace(" /", "");

        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        String nameTranslit = toLatinTrans.transliterate(name).toLowerCase().replace(" ", "-").replace("+", "").replace("·", "");

        String linkRewrite = referenseWithoutDots + "-" + nameTranslit;

        return linkRewrite;
    }

    public String createMetaTitle(ProductIkea productIkea) {

        String text = productIkea.getName() + " ИКЕА в наличии - купить с доставкой " + productIkea.getName() + " " + productIkea.getName2() + " в интернет-магазине del-i-very.ru";

        return text;
    }

    public int createPrice(ProductIkea productIkea) {
        return productIkea.getPrice();
    }

    public String createReference(ProductIkea productIkea) {
        return productIkea.getReferense();
    }


    public String createDescription(ProductIkea productIkea) {

        StringBuilder html = new StringBuilder();

        html.append("<h2>").append(createName(productIkea)).append("</h2>")
                .append("<p>").append(productIkea.getDescriptionShortP().get(0)).append("</p>\n")
                .append("<h2>Размеры товара в собранном виде</h2>\n").append("<p>");

        for (String dimension : productIkea.getProductAssembledDimensions()) {
            html.append(dimension).append("<br/>");
        }
        html.append("</p>").append("<h2>Материалы изготовления и уход за товаром</h2>\n");
        for (String materialUnit : productIkea.getMaterialAndUnitParagraphs()) {
            html.append("<p>").append(materialUnit).append("</p>");
        }
        html.append("<h2>Информация о количестве и весе упаковок</h2>\n")
                .append("<p>").append(productIkea.getPacksCount()).append("</p>\n");
        html.append("<p>");
        for (String packs : productIkea.getPacks()) {
            html.append(packs).append("<br/>");
            if (packs.contains("Упаковки")) {
                html.append("<br/>");
            }
        }
        html.append("</p>");

        return html.toString();
    }


    public String productToXml(PrestaShop prestashop) throws IOException {

        XmlMapper mapper = new XmlMapper();

        String xml = mapper.writeValueAsString(prestashop);

        return xml;
    }

    public void sentProductByPost(String xml) throws IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://del-i-very.ru/api/products/");

        httpPost.setHeader("Content-Type", "application/xml");

        StringEntity xmlEntity = new StringEntity(xml, StandardCharsets.UTF_8);


        httpPost.setEntity(xmlEntity);


        CloseableHttpResponse response = client.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode());

    }


    public void sentImagesByPost(int id_product) throws IOException {

        String url = "http://del-i-very.ru/api/images/products/" + id_product;


        String folder_path =
        "/src/main/resources/images";

        File dir = new File(folder_path);
        File[] file_array = dir.listFiles();

        for (int i = 0; i < file_array.length; i++) {

            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            FileBody fileBody = new FileBody(file_array[i], ContentType.DEFAULT_BINARY);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("image", fileBody);
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);

            HttpResponse response = client.execute(httpPost);
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }
}
