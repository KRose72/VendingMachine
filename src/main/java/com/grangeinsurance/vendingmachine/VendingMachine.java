package com.grangeinsurance.vendingmachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class VendingMachine {

	List<String> returnedCoins = new ArrayList<>();
	List<String> itemsInTray = new ArrayList<>();
	float currentTotal = 0;
	float priceOfItem = 0;
	String itemSelected = "";
	String priceFormat = "$%.2f";
	
	static Coin quarter = new Coin("Quarter", 0.25f, 50);
	static Coin dime = new Coin("Dime", 0.10f, 50);
	static Coin nickel = new Coin("Nickel", 0.05f, 50);
	
	static Product cola = new Product("Cola", 1.00f, 5);
	static Product chips = new Product("Chips", 0.50f, 5);
	static Product candy = new Product("Candy", 0.65f, 5);
	
	private static HashMap<Coin, Float> acceptedCoins = new HashMap<>();
	static {
	acceptedCoins.put(nickel, nickel.getValue());
	acceptedCoins.put(dime, dime.getValue());
	acceptedCoins.put(quarter, quarter.getValue());
	}
	
	private static HashMap<String, Float> availableItems = new HashMap<>();
	static {
	availableItems.put(cola.getName(), cola.getPrice());
	availableItems.put(chips.getName(), chips.getPrice());
	availableItems.put(candy.getName(), candy.getPrice());
	}
	
	private static HashMap<String, Float> rejectedCoins = new HashMap<>();
	static {
	rejectedCoins.put("Penny", 0.01f);
	rejectedCoins.put("Half Dollar", 0.50f);
	rejectedCoins.put("Silver Dollar", 1.00f);
	}
	
	public Object machineDisplay() {
		
		if (currentTotal > 0.0 && !availableItems.containsKey(itemSelected)) {
			return String.format(priceFormat, currentTotal);
		}
		else if (priceOfItem > currentTotal && availableItems.containsKey(itemSelected)) 
		{
			return "PRICE " + String.format(priceFormat, priceOfItem);
		} 
		else if (priceOfItem == currentTotal && availableItems.containsKey(itemSelected)) 
		{
			itemsInTray.add(itemSelected);
			return "THANK YOU";
		} 
		else if (priceOfItem < currentTotal && availableItems.containsKey(itemSelected)) 
		{
			currentTotal -= priceOfItem;
			itemsInTray.add(itemSelected);
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
		} else {
			for (Entry<String, Float> entry: rejectedCoins.entrySet()) {
				if (entry.getValue() == total) {
					returnedCoins.add(entry.getKey());
				}
			}
		}
	}

	public void makeSelection(String item) {
		if (availableItems.containsKey(item)) {
			priceOfItem = availableItems.get(item);
			itemSelected = item;
		} else if ("Return Coins".equals(item)) {
			makeChange();
		}
		
	}

	public void makeChange() {
		while (currentTotal >= quarter.getValue()) {
			currentTotal -= quarter.getValue();
			returnedCoins.add(quarter.getName());
		}
		while (currentTotal >= dime.getValue()) {
			currentTotal -= dime.getValue();
			returnedCoins.add(dime.getName());
		}
		while (currentTotal >= nickel.getValue()) {
			currentTotal -= nickel.getValue();
			returnedCoins.add(nickel.getName());
		}
		currentTotal = 0;
	}


}
