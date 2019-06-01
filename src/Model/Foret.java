package model;

public class Foret extends Hex {

	Foret(int newX, int newY) {
		super(newX, newY);
		this.cost = 33;
		this.defense = 35;
		rarity = (float) 0.24;
	}

	Foret(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
		rarity = (float) 0.24;
	}


	public float getRarity() {
		return rarity;
	}

	public void print() {
		System.out.print("F");
	}
}
