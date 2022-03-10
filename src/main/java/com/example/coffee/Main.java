package com.example.coffee;


import com.example.coffee.ex.NoSuchItemException;
import com.example.coffee.service.impl.ReceiptPrinterImpl;

public class Main {

    public static void main(String[] args) {
        if(args.length != 1) {
            System.err.println("Provide list of items in quotation marks");
            System.exit(1);
        }
        String input = args[0];

        try {
            String formattedReceipt = new ReceiptPrinterImpl().formatReceipt(input);
            System.out.println(formattedReceipt);
        } catch (NoSuchItemException e) {
            System.err.println("Unknown item: '" + e.getMessage() + "'");
            System.exit(1);
        }
    }
}
