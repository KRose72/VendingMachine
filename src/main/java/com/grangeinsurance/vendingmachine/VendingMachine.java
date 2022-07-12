package com.grangeinsurance.vendingmachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

public class VendingMachine {

	List<String> returnedCoins = new ArrayList<>();
	List<String> itemsInTray = new ArrayList<>();
	float currentTotal = 0;
	float priceOfItem = 0;
	int currentStock = 0;
	String itemSelected = "";
	String priceFormat = "$%.2f";
	
	static Coin quarter = new Coin("Quarter", 0.25f, 50);
	static Coin dime = new Coin("Dime", 0.10f, 50);
	static Coin nickel = new Coin("Nickel", 0.05f, 50);
	
	Product cola = new Product("Cola", 1.00f, 1);
	Product chips = new Product("Chips", 0.50f, 2);
	Product candy = new Product("Candy", 0.65f, 1);
	
	private static HashMap<Coin, Float> acceptedCoins = new HashMap<>();
	static {
	acceptedCoins.put(nickel, nickel.getValue());
	acceptedCoins.put(dime, dime.getValue());
	acceptedCoins.put(quarter, quarter.getValue());
	}
	
	private static HashMap<String, Float> availableItems = new HashMap<>();
	static {
	availableItems.put("Cola", 1.00f);
	availableItems.put("Chips", 0.50f);
	availableItems.put("Candy", 0.65f);
	}
	
	private static HashMap<String, Float> rejectedCoins = new HashMap<>();
	static {
	rejectedCoins.put("Penny", 0.01f);
	rejectedCoins.put("Half Dollar", 0.50f);
	rejectedCoins.put("Silver Dollar", 1.00f);
	}
	
	public Object machineDisplay() {
		System.out.println(chips.getStock());
		if (currentTotal > 0.0 && !availableItems.containsKey(itemSelected)) {
			return String.format(priceFormat, currentTotal);
		}
		else if (currentStock == 0 && availableItems.containsKey(itemSelected)) 
		{
			return "SOLD OUT";
		}
		else if (priceOfItem > currentTotal && availableItems.containsKey(itemSelected)) 
		{
			return "PRICE " + String.format(priceFormat, priceOfItem);
		}
		else if (priceOfItem == currentTotal && availableItems.containsKey(itemSelected)) 
		{
			itemsInTray.add(itemSelected);
			updateStock(itemSelected);
			currentTotal = 0;
			return "THANK YOU";
		} 
		else if (priceOfItem < currentTotal && availableItems.containsKey(itemSelected)) 
		{
			currentTotal -= priceOfItem;
			itemsInTray.add(itemSelected);
			updateStock(itemSelected);
			if (currentTotal > 0.0) {
				makeChange();
			}
			return "THANK YOU";
		}
		else 
		{
			return "INSERT COIN";
		}
	}
	
	public Object coinReturn() {
		return returnedCoins.toString().replace("[", "").replace("]", "");
	}
	
	public Object dispenseTray() {
		return itemsInTray.toString().replace("[", "").replace("]", "");
	}
	
	public void insertCoin (float total) {
		if (acceptedCoins.containsValue(total)) {
			currentTotal += total;
			updateCoinsAvailable(total);
		} else {
			for (Entry<String, Float> entry: rejectedCoins.entrySet()) {
				if (entry.getValue() == total) {
					returnedCoins.add(entry.getKey());
				}
			}
		}
	}

	private void updateCoinsAvailable(float total) {
		if (total == quarter.getValue()) {
			quarter.setAvailable(quarter.available + 1);
		} else if (total == dime.getValue()) {
			dime.setAvailable(dime.available + 1);
		} else if (total == nickel.getValue()) {
			nickel.setAvailable(nickel.available + 1);
		}
		
	}

	public void makeSelection(String item) {
		if (availableItems.containsKey(item)) {
			getProductInfo(item);
		} else if ("Return Coins".equals(item)) {
			makeChange();
		}
		
	}

	private void getProductInfo(String item) {
		if (Objects.equals(cola.getName(), item)) {
			priceOfItem = cola.getPrice();
			currentStock = cola.getStock();
			itemSelected = cola.getName();
		} else if (Objects.equals(chips.getName(), item)) {
			priceOfItem = chips.getPrice();
			currentStock = chips.getStock();
			itemSelected = chips.getName();
		} else if (Objects.equals(candy.getName(), item)) {
			priceOfItem = candy.getPrice();
			currentStock = candy.getStock();
			itemSelected = candy.getName();
		}
		
	}
	 
	private void updateStock (String item) {
		if (Objects.equals(cola.getName(), item)) {
			cola.setStock(currentStock - 1);
		} else if (Objects.equals(chips.getName(), item)) {
			chips.setStock(currentStock - 1);
		} else if (Objects.equals(candy.getName(), item)) {
			candy.setStock(currentStock - 1);
		}
	}

	public void makeChange() {
		while (currentTotal >= quarter.getValue()) {
			quarter.setAvailable(quarter.available - 1);
			currentTotal -= quarter.getValue();
			returnedCoins.add(quarter.getName());
		}
		while (currentTotal >= dime.getValue()) {
			dime.setAvailable(dime.available - 1);
			currentTotal -= dime.getValue();
			returnedCoins.add(dime.getName());
		}
		while (currentTotal >= nickel.getValue()) {
			nickel.setAvailable(nickel.available - 1);
			currentTotal -= nickel.getValue();
			returnedCoins.add(nickel.getName());
		}
		currentTotal = 0;
	}


}
