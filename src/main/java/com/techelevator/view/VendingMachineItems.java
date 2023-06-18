package com.techelevator.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class VendingMachineItems {


    private final String itemName;
    private final double itemPrice;
    private final String productType;
    private int quantity;



    public VendingMachineItems(String itemName, double itemPrice, String productType){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.productType = productType;
        this.quantity = 5;

    }

    public String getName() {
        return itemName;
    }

    public double getPrice() {
        return itemPrice;
    }

    public String getProductType() {
        return productType;
    }

    public abstract void use();

    public int getQuantity() {
        return this.quantity;
    }
    public void updatedAmount(){
        if(this.quantity > 0) this.quantity--;
    }

}
