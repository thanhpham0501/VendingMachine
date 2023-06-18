package com.techelevator.view;

public class Gum extends VendingMachineItems{
    public Gum(String itemName, double itemPrice, String productType) {
        super(itemName, itemPrice, productType);
    }

    @Override
    public void use() {
        System.out.println("Chew Chew, Yum!");
    }
}
