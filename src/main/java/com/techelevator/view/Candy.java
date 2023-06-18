package com.techelevator.view;

public class Candy extends VendingMachineItems{
    public Candy(String itemName, double itemPrice, String productType) {
        super(itemName, itemPrice, productType);
    }


    @Override
    public void use() {
        System.out.println("Munch Munch, Yum!");
    }
}
