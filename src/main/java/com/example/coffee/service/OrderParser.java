package com.example.coffee.service;


import com.example.coffee.ex.NoSuchItemException;
import com.example.coffee.model.Item;

import java.util.List;

public interface OrderParser {
    List<Item> parse(String input) throws NoSuchItemException;
}
