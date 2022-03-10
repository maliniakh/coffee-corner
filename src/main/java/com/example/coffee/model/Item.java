package com.example.coffee.model;

import com.example.coffee.util.PropertiesUtils;

import java.math.BigDecimal;

/**
 * Arguably the price shouldn't be read from properties within enum like this.
 * Another option would be to wrap an Item along with the price in a separate class and resolve instances
 * with a factory, yet I find the used approach to be the most pragmatic here.
 *
 * Ideally items should be configured with properties (just like the prices are),
 * but that would considerable complicate the whole solution.
 */
public enum Item {

    SMALL_COFFEE(ItemType.BEVERAGE),
    MEDIUM_COFFEE(ItemType.BEVERAGE),
    LARGE_COFFEE(ItemType.BEVERAGE),
    ORANGE_JUICE(ItemType.BEVERAGE),
    BACON_ROLL(ItemType.SNACK),
    EXTRA_MILK(ItemType.EXTRA),
    FOAMED_MILK(ItemType.EXTRA),
    ROAST_COFFEE(ItemType.EXTRA);

    private final ItemType type;

    private final BigDecimal price;

    Item(ItemType type) {
        this.type = type;
        price = new BigDecimal(PropertiesUtils.getProperties().getProperty("price." + name().toLowerCase()));
    }

    public ItemType getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name().toLowerCase().replace('_', ' ');
    }

    public static Item getByName(String name) {
        for(Item i : values()) {
            if(i.getName().equals(name)) {
                return i;
            }
        }

        return null;
    }
}
