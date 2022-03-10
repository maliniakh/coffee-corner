package com.example.coffee.service.impl;

import com.example.coffee.model.Item;
import com.example.coffee.model.Receipt;
import com.example.coffee.service.PriceCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PriceCalculatorTest {

    PriceCalculator priceCalculator = new PriceCalculatorImpl();

    @Test
    void noDiscountPrice() {
        List<Item> items = List.of(Item.LARGE_COFFEE, Item.FOAMED_MILK, Item.FOAMED_MILK);
        Receipt receipt = priceCalculator.getCalculation(items);
        assertEquals(new BigDecimal("4.50"), receipt.getTotal());
        assertEquals(new BigDecimal("0.00"), receipt.getDiscountSum());
    }

    @Test
    void freeExtraWithCoffeeAndSnack() {
        List<Item> items = List.of(
                Item.SMALL_COFFEE,
                Item.BACON_ROLL,
                Item.ROAST_COFFEE);
        Receipt receipt = priceCalculator.getCalculation(items);
        assertEquals(new BigDecimal("7.00"), receipt.getTotal());
        assertEquals(new BigDecimal("0.90"), receipt.getDiscountSum());
    }

    @Test
    void discountCheaperExtrasFirst() {
        List<Item> items = List.of(
                Item.SMALL_COFFEE,
                Item.BACON_ROLL,
                Item.ROAST_COFFEE,
                Item.FOAMED_MILK,
                Item.EXTRA_MILK);
        Receipt receipt = priceCalculator.getCalculation(items);
        assertEquals(new BigDecimal("8.40"), receipt.getTotal());
    }

    @Test
    void every5thCoffeeFree() {
        List<Item> items = List.of(
                Item.SMALL_COFFEE,
                Item.MEDIUM_COFFEE,
                Item.LARGE_COFFEE,
                Item.ORANGE_JUICE,
                Item.LARGE_COFFEE);
        Receipt receipt = priceCalculator.getCalculation(items);
        assertEquals(new BigDecimal("13.95"), receipt.getTotal());
        assertEquals(new BigDecimal("2.50"), receipt.getDiscountSum());
    }

    @Test
    void twoDiscountsAtOnce() {
        List<Item> items = List.of(
                Item.SMALL_COFFEE, // 2.50
                Item.MEDIUM_COFFEE, // 3
                Item.LARGE_COFFEE,  // 3.50
                Item.ORANGE_JUICE,  // 3.95
                Item.ORANGE_JUICE,  // 3.95
                Item.SMALL_COFFEE,  // 2.50
                Item.BACON_ROLL,   // 4.50
                Item.ROAST_COFFEE, // 0.90
                Item.FOAMED_MILK); // 0.50

        Receipt receipt = priceCalculator.getCalculation(items);
        assertEquals(new BigDecimal("22.30"), receipt.getTotal());
        assertEquals(new BigDecimal("3.00"), receipt.getDiscountSum());
    }
}