package com.techelevator;

import com.techelevator.view.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

public class VendingMachineCLI {

	private static final String VENDING_MACHINE_STOCK_FILE = "vendingmachine.csv";

	private static final String LOG_FILE = "Log.txt";
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };

	private final Menu menu;

	private final Map<String, VendingMachineItems> slotMap;

	private final List<String> slotList = new ArrayList<>();

	private final TransactionLog log;

	public VendingMachineCLI(Menu menu) throws Exception {
		this.menu = menu;
		this.slotMap = new HashMap<>();
		this.log = new TransactionLog(LOG_FILE);
	}

	public void loadVendingMachineStock() {
		File file = new File(VENDING_MACHINE_STOCK_FILE);
		try(Scanner loader = new Scanner(file)) {
			while (loader.hasNext()) {
				String line = loader.nextLine();
				String[] itemProperties = line.split("\\|");
				VendingMachineItems item = createItemFromString(itemProperties);
				String itemSlot = itemProperties[0];
				slotList.add(itemSlot);
				slotMap.put(itemSlot, item);
			}
		} catch (Exception e) {

		}
	}

	private VendingMachineItems createItemFromString(String[] itemProperties) {
		VendingMachineItems item;
		String name = itemProperties[1];
		double price = Double.parseDouble(itemProperties[2]);
		String type = itemProperties[3];
		if(itemProperties[3].equals("Candy")) {
			item = new Candy(name, price, type);
		} else if(itemProperties[3].equals("Gum")) {
			item = new Gum(name, price, type);
		} else if(itemProperties[3].equals("Chips")) {
			item = new Chips(name, price, type);
		} else {
			item = new Beverages(name, price, type);
		}
		return item;
	}

	public void run() {
		boolean running = true;

		// Read file to restock vending machine
		loadVendingMachineStock();
		// Run vending machine
		while (running) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			Menu purchaseMenu = new Menu(System.in, System.out); // create a purchase menu
			Purchase purchase = new Purchase(purchaseMenu, log); // Create a brand new transaction
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				for(String slot: slotList) {
					VendingMachineItems item = slotMap.get(slot);
					System.out.println("[" + slot + "] " + item.getName() + "  $" + item.getPrice() + "    " + item.getProductType() + "   |   "
					+ "Current Quantity:" + "  " + item.getQuantity());
				}
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				purchase.run(slotMap);
			} else if(choice.equals(MAIN_MENU_OPTION_EXIT)) {
				//exit
				log.flushAndCloseLog();
				running = false;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
