package model;

public class Neige extends Hex {

	Neige(int newX, int newY) {
		super(newX, newY);
		this.cost = 25;
		this.defense = 5;
		rarity = (float) 0.24;
	}

	Neige(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
		rarity = (float) 0.24;
	}


	public float getRarity() {
		return rarity;
	}


	public void print() {
		System.out.print("S");
	}
}
