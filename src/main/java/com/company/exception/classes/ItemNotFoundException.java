package com.company.exception.classes;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException() {
        super("Item Not Found!!!");
    }
}
