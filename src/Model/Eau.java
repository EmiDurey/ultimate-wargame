package model;

public class Eau extends Hex {

	Eau(int newX, int newY) {
		super(newX, newY);
		this.cost = -1; 
		this.defense = 0;
		rarity = (float) 0.2;
	}

	Eau(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
		rarity = (float) 0.2;
	}


	public float getRarity() {
		return rarity;
	}


	public void print() {
		System.out.print("W");
	}
}
