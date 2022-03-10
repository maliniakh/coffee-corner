package com.example.coffee.service.impl;

import com.example.coffee.ex.NoSuchItemException;
import com.example.coffee.service.ReceiptPrinter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptPrinterImplTest {

    ReceiptPrinter receiptPrinter = new ReceiptPrinterImpl();

    @Test
    void test() throws NoSuchItemException {
        // kind of a sanity check, as strings like that are not meant to be tested
        String receiptStr = receiptPrinter.formatReceipt("bacon roll, small coffee, orange juice");
        assertFalse(receiptStr.isBlank());
        assertTrue(receiptStr.contains("bacon roll"));
        assertTrue(receiptStr.contains("10.95"));
    }
}