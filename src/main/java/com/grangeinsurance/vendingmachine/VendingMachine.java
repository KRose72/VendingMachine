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
	
	private static HashMap<String, Float> rejectedCoins = new HashMap<>();
	static {
	rejectedCoins.put("Penny", 0.01f);
	rejectedCoins.put("Half Dollar", 0.50f);
	rejectedCoins.put("Silver Dollar", 1.00f);
	}
	
	private static HashMap<String, Float> acceptedCoins = new HashMap<>();
	static {
	acceptedCoins.put("Nickel", 0.05f);
	acceptedCoins.put("Dime", 0.10f);
	acceptedCoins.put("Quarter", 0.25f);
	}
	
	private static HashMap<String, Float> availableItems = new HashMap<>();
	static {
	availableItems.put("Cola", 1.00f);
	availableItems.put("Chips", 0.50f);
	availableItems.put("Candy", 0.65f);
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
		while (currentTotal >= 0.25) {
			currentTotal -= 0.25;
			returnedCoins.add("Quarter");
		}
		while (currentTotal >= 0.10) {
			currentTotal -= 0.10;
			returnedCoins.add("Dime");
		}
		while (currentTotal >= 0.05) {
			currentTotal -= 0.05;
			returnedCoins.add("Nickel");
		}
		currentTotal = 0;
	}


}
