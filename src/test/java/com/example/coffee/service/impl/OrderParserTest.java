package com.example.coffee.service.impl;

import com.example.coffee.ex.NoSuchItemException;
import com.example.coffee.model.Item;
import com.example.coffee.service.OrderParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderParserTest {

    OrderParser orderParser = new OrderParserImpl();

    @Test
    void parseCoffee() throws NoSuchItemException {
        List<Item> items = orderParser.parse("large coffee");
        assertEquals(1, items.size());
        assertEquals(Item.LARGE_COFFEE, items.get(0));
    }

    @Test
    void noSuchItem() {
        assertThrows(NoSuchItemException.class, () -> orderParser.parse("beer"));
    }

    @Test
    void parseCoffeeAndRoll() throws NoSuchItemException {
        List<Item> orders = orderParser.parse("large coffee, bacon roll");
        assertEquals(2, orders.size());
        assertTrue(orders.contains(Item.LARGE_COFFEE));
        assertTrue(orders.contains(Item.BACON_ROLL));
    }

    @Test
    void parseMultipleOrders() throws NoSuchItemException {
        List<Item> items = orderParser.parse("large coffee, large coffee");
        assertEquals(2, items.size());
        assertEquals(Item.LARGE_COFFEE, items.get(0));
        assertEquals(Item.LARGE_COFFEE, items.get(1));
    }

    @Test
    void testAllDelimiters() throws NoSuchItemException {
        List<Item> items = orderParser.parse("large coffee with foamed milk, bacon roll and orange juice");
        assertEquals(4, items.size());
        assertTrue(items.contains(Item.LARGE_COFFEE));
        assertTrue(items.contains(Item.FOAMED_MILK));
        assertTrue(items.contains(Item.BACON_ROLL));
        assertTrue(items.contains(Item.ORANGE_JUICE));
    }
}