package com.techelevator.view;

public class Beverages extends VendingMachineItems{
    public Beverages(String itemName, double itemPrice, String productType) {
        super(itemName, itemPrice, productType);
    }

    @Override
    public void use() {
        System.out.println("Glug Glug, Yum!");
    }
}
