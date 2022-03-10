package com.example.coffee.service;

import com.example.coffee.model.Item;
import com.example.coffee.model.Receipt;

import java.util.List;

public interface PriceCalculator {

    Receipt getCalculation(List<Item> orders);

}
