package com.example.coffee.service.impl;

import com.example.coffee.ex.NoSuchItemException;
import com.example.coffee.model.Item;
import com.example.coffee.model.Receipt;
import com.example.coffee.service.OrderParser;
import com.example.coffee.service.PriceCalculator;
import com.example.coffee.service.ReceiptPrinter;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;


public class ReceiptPrinterImpl implements ReceiptPrinter {

    private final OrderParser orderParser = new OrderParserImpl();

    private final PriceCalculator priceCalculator = new PriceCalculatorImpl();

    private final String receiptTemplate =
            "CHARLENE COFFEE CORNER\n\n" +
            "Receipt: \n\n"+
            "{0}\n\n" +
            "Discounts:\n\n" +
            "{1}\n\n" +
            "----------------------------------\n\n" +
            "                  Total: CHF {2,number,#.##}";

    private final String itemTemplate = "%dx %16s  %6s %6s";

    private final String priceFormat = "%-4.2f";

    @Override
    public String formatReceipt(String input) throws NoSuchItemException {
        List<Item> items = orderParser.parse(input);
        Receipt receipt = priceCalculator.getCalculation(items);

        String itemsSection = formatItems(receipt.getItem2Quantity(), false);

        String discountSection = null;
        if (receipt.getDiscounted().size() > 0) {
            discountSection = formatItems(receipt.getDiscounted(), true);
        }

        return MessageFormat.format(receiptTemplate, itemsSection, discountSection, receipt.getTotal());
    }

    private String formatItems(Map<Item, Long> items, boolean discount) {

        return items.entrySet().stream().map(e -> {
            BigDecimal itemPrice = e.getKey().getPrice().multiply(BigDecimal.valueOf(discount ? -1:1));
            Long quantity = e.getValue();
            return format(itemTemplate, quantity, e.getKey().getName(),
                    format(priceFormat, itemPrice),
                    format(priceFormat, itemPrice.multiply(BigDecimal.valueOf(quantity)) ));
        }).collect(Collectors.joining("\n"));
    }
}
