package com.example.coffee.service.impl;

import com.example.coffee.model.Item;
import com.example.coffee.model.ItemType;
import com.example.coffee.model.Receipt;
import com.example.coffee.service.PriceCalculator;
import com.example.coffee.util.PropertiesUtils;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class PriceCalculatorImpl implements PriceCalculator {

    Properties props = PropertiesUtils.getProperties();

    @Override
    public Receipt getCalculation(List<Item> items) {
        // free extra for every coffee and a snack
        int snackCount = (int) items.stream().filter(o -> o.getType() == ItemType.SNACK).count();
        int beverageCount = (int) items.stream().filter(o -> o.getType() == ItemType.BEVERAGE).count();
        int freeExtraCount = Math.min(snackCount, beverageCount);
        var discounted = getDiscount(items, ItemType.EXTRA, freeExtraCount);

        // every 5th beverage free
        // assuming they need to be ordered together
        // another option would be to include information about number of stamps already collected
        // assuming that discount can be combined (which is acceptable here I believe)
        if (beverageCount >= getNthBeverage()) {
            var everyNthDiscounted = getDiscount(items, ItemType.BEVERAGE, beverageCount / getNthBeverage());
            discounted = mergeItems(discounted, everyNthDiscounted);
        }

        var allItems = items.stream().collect(groupingBy(identity(), counting()));
        return new Receipt(allItems, discounted);
    }

    /**
     * Gather discounted items, based on the provided <i>itemType<i/> and <i>limit<i/>. It takes cheaper items first.
     * @return Discounted items mapped to their quantity.
     */
    private Map<Item, Long> getDiscount(List<Item> items, ItemType itemType, int limit) {
        return items.stream()
                .filter(o -> o.getType() == itemType)
                .sorted(comparing(Item::getPrice))
                .limit(limit)
                .collect(groupingBy(identity(), counting()));
    }

    private Map<Item, Long> mergeItems(Map<Item, Long> discounted, Map<Item, Long> everyNthDiscounted) {
        return Stream.concat(discounted.entrySet().stream(), everyNthDiscounted.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.summingLong(Map.Entry::getValue)));
    }

    private int getNthBeverage() {
        return Integer.parseInt(props.getProperty("bonus.nth_beverage"));
    }
}
