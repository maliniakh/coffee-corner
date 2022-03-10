package com.example.coffee.service;


import com.example.coffee.ex.NoSuchItemException;

public interface ReceiptPrinter {

    String formatReceipt(String input) throws NoSuchItemException;
}
