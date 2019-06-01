package model;

public class Forteresse extends Hex {

	Forteresse(int newX, int newY) {
		super(newX, newY);
		this.cost = 15;
		this.defense = 60;
		rarity = (float) 0.1;
	}

	Forteresse(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
		rarity = (float) 0.1;
	}



	public float getRarity() {
		return rarity;
	}

	public void print() {
		System.out.print("C");
	}
}
