package com.grangeinsurance.vendingmachine;

public class Product {
	String name;
	float price;
	int stock;
	
	public Product(String name, float price, int stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}
	
	public String getName() {
		return name;
	}
	
	public float getPrice() {
		return price;
	}
	
	public int getStock() {
		return stock;
	}
}
