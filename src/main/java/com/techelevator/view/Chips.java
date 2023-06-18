package com.techelevator.view;

public class Chips extends VendingMachineItems {


    public Chips(String itemName, double itemPrice, String productType) {
        super(itemName, itemPrice, productType);
    }

    @Override
    public void use() {
        System.out.println( "Crunch Crunch, Yum!");
    }
}
