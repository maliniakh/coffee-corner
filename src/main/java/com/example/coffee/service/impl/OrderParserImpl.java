package com.example.coffee.service.impl;

import com.example.coffee.ex.NoSuchItemException;
import com.example.coffee.model.Item;
import com.example.coffee.service.OrderParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

/**
 * Simple implementation, where it's assumed that each item is separated by predefined delimiters.
 */
public class OrderParserImpl implements OrderParser {

    /**
     * strings which split the order into items
     */
    private final static String splitRegex = "with|and|,";

    @Override
    public List<Item> parse(String input) throws NoSuchItemException {
        List<String> itemsStr = Arrays.stream(input.split(splitRegex))
                .map(String::trim)
                .filter(not(String::isBlank)).collect(Collectors.toList());

        // would be nice to continue with streams, but sadly streams can't handle checked exceptions
        List<Item> items = new ArrayList<>();
        for(String itemStr : itemsStr) {
            items.add(findItem(itemStr));
        }

        return items;
    }

    private static Item findItem(String itemStr) throws NoSuchItemException {
        // assuming that only a single item can match
        // which might not be the case with growth of the menu
        return Arrays.stream(Item.values())
                .filter(item -> itemStr.contains(item.getName()))
                .findFirst().orElseThrow(() -> new NoSuchItemException((itemStr)));
    }
}
