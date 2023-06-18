package com.techelevator.view;


import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Purchase extends Menu {
    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, MENU_OPTION_FINISH_TRANSACTION};
    private double currentMoney;
    private final Menu purchaseMenu;

    private final TransactionLog log;

    public Purchase(Menu purchaseMenu, TransactionLog log) {
        this.purchaseMenu = purchaseMenu;
        this.log = log;
        this.currentMoney = 0.00;

    }

    public void run(Map<String, VendingMachineItems> slotMap) {
        boolean running = true;
        while (running) {

            System.out.println("Current Money Provided: " + "$" + getCurrentMoney());
            String choice = (String) purchaseMenu.getChoiceFromOptions(MENU_OPTIONS);


            if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                // display vending machine items
                //String money = feedMoney();
                System.out.println("Please deposit in whole dollar amounts");
                String money = (String) purchaseMenu.getUserPrompt();
                try {
                    double moneyInput = Double.parseDouble(money);
                    feedMoney(moneyInput);
                } catch (Exception e) {
                    System.out.println("Please enter a valid dollar amount");
                }
                String logDetail = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString() + " FEED MONEY: $" + money + " $" + getCurrentMoney();
                log.log(logDetail);
            } else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
                // do purchase
                System.out.println("Please enter valid product code");
                String usersChoice = (String) purchaseMenu.getUserPrompt();
                if (slotMap.containsKey(usersChoice)) {
                    VendingMachineItems itemOfChoice = slotMap.get(usersChoice);
                    if (currentMoney < itemOfChoice.getPrice()) {
                        System.out.println("Not enough funds to purchase" + " " + itemOfChoice.getName());
                    } else if (slotMap.get(usersChoice).getQuantity() <= 0) {
                        System.out.println("Item is currently out of stock!");
                    } else {
                        System.out.println("Dispensing:" + itemOfChoice.getName());
                        itemOfChoice.use();

                        //update vending machine slot after dispense
                        itemOfChoice.updatedAmount();
                        slotMap.put(usersChoice, itemOfChoice);
                        String logDetail = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString()+ " "+ itemOfChoice.getName()
                                + "  " + usersChoice + "  $" + itemOfChoice.getPrice() + "   $" + getCurrentMoney();
                        log.log(logDetail);
                        deductPayment(itemOfChoice.getPrice());

                    }
                    } else {
                    System.out.println("Your code provided is not valid, please try again!");
                }

            } else if (choice.equals(MENU_OPTION_FINISH_TRANSACTION)) {
                //exit
                System.out.println("Transaction completed return change $" + getCurrentMoney());
                String logDetail = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString()+ " GIVE CHANGE: $" + getCurrentMoney()  + " $0.0";
                deductPayment(getCurrentMoney());
                log.log(logDetail);
                running = false;
            }
        }
    }


    private void purchaseMenu() {

        List<String> menuOptions = new ArrayList<>();
        menuOptions.add("Feed Money");
        menuOptions.add("Select Product");
        menuOptions.add("Finish Transaction");

    }

    public void resetBalance(double currentMoney) {
        this.currentMoney = 0.0;
    }

    public void deductPayment(double purchasePrice) {
        this.currentMoney -= purchasePrice;
    }


    public void feedMoney(double moneyInput) {
        currentMoney += moneyInput;
    }

    public double getCurrentMoney() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedNumber = decimalFormat.format(this.currentMoney);
        this.currentMoney = Double.parseDouble(formattedNumber);
        return this.currentMoney;
    }

}
