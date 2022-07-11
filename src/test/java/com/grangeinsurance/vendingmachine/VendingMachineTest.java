package com.grangeinsurance.vendingmachine;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VendingMachineTest {
	private VendingMachine machine;
	
	@BeforeEach
	public void initialize() {
		machine = new VendingMachine();
	}
	
	
	@Test
	void displayReadsInsertCoinWhenEmpty () {
		assertThat(machine.machineDisplay()).isEqualTo("INSERT COIN");
	}
	
	@Test
	void displayTwentyFiveCentsWhenGivenQuarter () {
		insertQuarter();
		assertThat(machine.machineDisplay()).isEqualTo("$0.25");
	}
	
	@Test
	void displayTenCentsWhenGivenDime () {
		insertDime();
		assertThat(machine.machineDisplay()).isEqualTo("$0.10");
	}
	
	@Test
	void displayFiveCentsWhenGivenNickel () {
		insertNickel();
		assertThat(machine.machineDisplay()).isEqualTo("$0.05");
	}
	
	@Test
	void displayInsertCoinWhenGivenPenny () {
		insertPenny();
		assertThat(machine.machineDisplay()).isEqualTo("INSERT COIN");
	}
	
	@Test
	void displayFortyCentsWhenGivenQuarterNickelDimeAndPenny () {
		insertPenny();
		insertNickel();
		insertDime();
		insertQuarter();
		assertThat(machine.machineDisplay()).isEqualTo("$0.40");
	}
	
	@Test
	void displayOneCentInChangeReturnForRejectedPenny() {
		insertPenny();
		assertThat(machine.machineDisplay()).isEqualTo("INSERT COIN");
		assertThat(machine.coinReturn()).isEqualTo("Penny");
	}
	
	@Test
	void displayOneDollarWhenItemOneIsSelected() {
		machine.makeSelection("Cola");
		assertThat(machine.machineDisplay()).isEqualTo("PRICE $1.00");
	}
	
	@Test
	void displayFiftyCentsWhenItemTwoIsSelected() {
		machine.makeSelection("Chips");
		assertThat(machine.machineDisplay()).isEqualTo("PRICE $0.50");
	}
	
	@Test
	void displaySixtyFiveWhenItemThreeIsSelected() {
		machine.makeSelection("Candy");
		assertThat(machine.machineDisplay()).isEqualTo("PRICE $0.65");
	}
	
	@Test
	void displayThankYouWhenItemOneIsSelectedAndFourQuarters() {
		insertQuarter();
		insertQuarter();
		insertQuarter();
		insertQuarter();
		selectCola();
		assertThat(machine.machineDisplay()).isEqualTo("THANK YOU");
		assertThat(machine.dispenseTray()).isEqualTo("Cola");
	}
	
	@Test
	void displayThankYouWhenItemTwoIsSelectedAndTwoQuarters() {
		insertQuarter();
		insertQuarter();
		selectChips();
		assertThat(machine.machineDisplay()).isEqualTo("THANK YOU");
		assertThat(machine.dispenseTray()).isEqualTo("Chips");
	}
	
	@Test
	void displayThankYouWhenItemThreeIsSelectedAndTwoQuartersOneDimeOneNickel() {
		insertQuarter();
		insertQuarter();
		insertDime();
		insertNickel();
		selectCandy();
		assertThat(machine.machineDisplay()).isEqualTo("THANK YOU");
		assertThat(machine.dispenseTray()).isEqualTo("Candy");
	}

	@Test
	void displaysInsertCoinsWhenGumISSelected() {
		selectGum();
		assertThat(machine.machineDisplay()).isEqualTo("INSERT COIN");
	}
	
	@Test
	void displaysQuarterwhenThreeQuartersAreInsertedAndChipsAreSelected() {
		insertQuarter();
		insertQuarter();
		insertQuarter();
		selectChips();
		assertThat(machine.machineDisplay()).isEqualTo("THANK YOU");
		assertThat(machine.dispenseTray()).isEqualTo("Chips");
		assertThat(machine.coinReturn()).isEqualTo("Quarter");
	}
	
	@Test
	void displays2QuarterDimeWhenFourQuartersOneDimeAreInsertedAndChipsAreSelected() {
		insertQuarter();
		insertQuarter();
		insertQuarter();
		insertQuarter();
		insertDime();
		selectChips();
		assertThat(machine.machineDisplay()).isEqualTo("THANK YOU");
		assertThat(machine.dispenseTray()).isEqualTo("Chips");
		assertThat(machine.coinReturn()).isEqualTo("Quarter, Quarter, Dime");
	}
	
	@Test
	void display3QuartersOneNickelWhenReturnCoinsButtonIsHit () {
		insertQuarter();
		insertQuarter();
		insertDime();
		insertDime();
		insertDime();
		selectReturnCoins();
		assertThat(machine.machineDisplay()).isEqualTo("INSERT COIN");
		assertThat(machine.coinReturn()).isEqualTo("Quarter, Quarter, Quarter, Nickel");
	}
	
	@Test
	void displayOutOfStockWhen2ColaAreBought () {
		insertQuarter();
		insertQuarter();
		insertQuarter();
		insertQuarter();
		selectCola();
		selectCola();
		assertThat(machine.machineDisplay()).isEqualTo("SOLD OUT");
	}
	
	void displayOutOfStockWhen2ColaAreBoughtAndEightQuartersEntered () {
		insertQuarter();
		insertQuarter();
		insertQuarter();
		insertQuarter();
		selectCola();
		insertQuarter();
		insertQuarter();
		insertQuarter();
		insertQuarter();
		selectCola();
		assertThat(machine.machineDisplay()).isEqualTo("SOLD OUT");
		selectReturnCoins();
		assertThat(machine.coinReturn()).isEqualTo("Quarter, Quarter, Quarter, Quarter");
	}
		
	void displayOutOfStockWhen3ChipsAreBoughtAndEightQuartersEntered () {
		insertQuarter();
		insertQuarter();
		selectChips();
		insertQuarter();
		insertQuarter();
		selectChips();
		insertQuarter();
		insertQuarter();
		selectChips();
		insertQuarter();
		insertQuarter();
		selectChips();
		assertThat(machine.machineDisplay()).isEqualTo("SOLD OUT");
		selectReturnCoins();
		assertThat(machine.coinReturn()).isEqualTo("Quarter, Quarter");
	}
	
	void displayOutOfStockWhen2CandyAreBoughtAndSixQuartersEntered () {
		insertQuarter();
		insertQuarter();
		insertQuarter();
		selectCandy();
		insertQuarter();
		insertQuarter();
		insertQuarter();
		selectCandy();
		assertThat(machine.machineDisplay()).isEqualTo("SOLD OUT");
		selectReturnCoins();
		assertThat(machine.coinReturn()).isEqualTo("Quarter, Quarter, Quarter");
	}
	
	private void insertQuarter() {
		machine.insertCoin(0.25f);
	}
	private void insertDime() {
		machine.insertCoin(0.10f);
	}
	private void insertNickel() {
		machine.insertCoin(0.05f);
	}
	private void insertPenny() {
		machine.insertCoin(0.01f);
	}
	
	private void selectCola() {
		machine.makeSelection("Cola");
	}
	
	private void selectChips() {
		machine.makeSelection("Chips");
	}
	
	private void selectCandy() {
		machine.makeSelection("Candy");
	}
	
	private void selectGum() {
		machine.makeSelection("Gum");
	}
	
	private void selectReturnCoins() {
		machine.makeSelection("Return Coins");
	}
}
