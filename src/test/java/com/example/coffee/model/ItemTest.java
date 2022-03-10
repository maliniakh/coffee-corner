package com.example.coffee.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    public void testGetByName() {
        Item largeCoffee = Item.getByName("large coffee");
        assertEquals(largeCoffee, Item.LARGE_COFFEE);

        Item baconRoll = Item.getByName("bacon roll");
        assertEquals(baconRoll, Item.BACON_ROLL);
    }

    @Test
    public void testGetByNameNotExists() {
        Item beer = Item.getByName("beer");
        assertNull(beer);
    }

}