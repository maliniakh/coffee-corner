package com.example.coffee.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

/**
 * Represents a receipt with all the prices calculated.
 * Note some business logic implementation here, as I stick in the project rather to rich model instead of anemic one.
 */
public class Receipt {
    /**
     * Items mapped to their quantity.
     */
    private final Map<Item, Long> item2Quantity;

    /**
     * Free items
     */
    private final Map<Item, Long> discounted;

    /**
     * Redundant information about sum of all the prices
     */
    private final BigDecimal allItemsPrice;

    /**
     * Total discount sum
     */
    private final BigDecimal discountSum;

    public Receipt(Map<Item, Long> item2Quantity, Map<Item, Long> discounted) {
        this.item2Quantity = unmodifiableMap(item2Quantity);
        this.discounted = unmodifiableMap(discounted);

        allItemsPrice = sumPrices(item2Quantity);
        discountSum = sumPrices(discounted);
    }

    /**
     * @return Amount to be paid - all items price minus discount
     */
    public BigDecimal getTotal() {
        return allItemsPrice.subtract(discountSum);
    }

    private static BigDecimal sumPrices(Map<Item, Long> item2Quantity) {
        return item2Quantity.entrySet().stream()
                .map(e -> e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.CEILING);
    }

    public Map<Item, Long> getItem2Quantity() {
        return item2Quantity;
    }

    public Map<Item, Long> getDiscounted() {
        return discounted;
    }

    public BigDecimal getAllItemsPrice() {
        return allItemsPrice;
    }

    public BigDecimal getDiscountSum() {
        return discountSum;
    }
}
