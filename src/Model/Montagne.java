package model;

public class Montagne extends Hex {

	Montagne(int newX, int newY) {
		super(newX, newY);
		this.cost = 33;
		this.defense = 40;
		rarity = (float) 0.24;
	}

	Montagne(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
		rarity = (float) 0.24;
	}



	public float getRarity() {
		return rarity;
	}


	public void print() {
		System.out.print("M");
	}
}
