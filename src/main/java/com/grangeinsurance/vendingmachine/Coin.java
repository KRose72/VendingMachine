package com.grangeinsurance.vendingmachine;

public class Coin {
	String name;
	float value;
	int available;
	
	public Coin(String name, float value, int available) {
		this.name = name;
		this.value = value;
		this.available = available;
	}
	
	public String getName() {
		return name;
	}
	
	public float getValue() {
		return value;
	}
	
	public int getAvailable() {
		return available;
	}
}
