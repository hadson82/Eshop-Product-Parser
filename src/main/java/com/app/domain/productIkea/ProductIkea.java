package com.app.domain.productIkea;

import lombok.Data;

import java.util.List;

/**
 * Содержит поля, снятые с карточки ИКЕА
 */

@Data
public class ProductIkea {

    private String name;

    private String name2;

    private int price;

    private String referense;

    private List<String> descriptionShortP;

    /**
     * Поля, для создания Description
     */

    // Размеры товара в собранном виде
    private List<String> productAssembledDimensions;

    // Информация блока Материалы изготовления
    private List<String> materialAndUnitParagraphs;

    /**
     * Информация блока "Информация об упаковке"
     */
    private String packsCount;

    private List<String> packs;

}
