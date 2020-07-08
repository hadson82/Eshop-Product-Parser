package com.app.services.old;

import com.app.domain.productDelivery.PrestaShop;
import com.ibm.icu.text.Transliterator;
import lombok.Data;

@Data
public class ProductCreateMeta {



    public PrestaShop createMetaTitle(PrestaShop prestaShop) {

        String name = prestaShop.getProduct().getName().getLanguage().getText();

        String metaTitle = name + " - купить с доставкой в интернет-магазине ИКЕА del-i-very.ru";

        prestaShop.getProduct().getMeta_title().getLanguage().setText(metaTitle);

        return prestaShop;
    }

    public PrestaShop createLinkRewrite(PrestaShop prestaShop) {

        final String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";

        String referense = prestaShop.getProduct().getReference();
        String referenseWithoutDots = referense.replace(".", "");
        String name = prestaShop.getProduct().getName().getLanguage().getText();

        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        String nameTranslit = toLatinTrans.transliterate(name);

        String linkRewrite = referenseWithoutDots + " " + nameTranslit;

        String linkRewrite2 = linkRewrite.replace(",","").toLowerCase().replace(" ", "-").replace("+", "")
                .replace("·", "");

        prestaShop.getProduct().getLink_rewrite().getLanguage().setText(linkRewrite2);

        return prestaShop;
    }

    public PrestaShop createMetaDescription(PrestaShop prestaShop) {

        String name = prestaShop.getProduct().getName().getLanguage().getText();

        String metaDescription = name + ". Доставка по Москве и России.";

        prestaShop.getProduct().getMeta_description().getLanguage().setText(metaDescription);

        return prestaShop;
    }


}
