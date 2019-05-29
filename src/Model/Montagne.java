package model;

public class Montagne extends Hex {

	/* attributs du terrain */
	private final int cout = 3; // cout du deplacement dans ce terrain
	private final int defense = 60; // pourcentage


	Montagne(int newX, int newY) {
		super(newX, newY);
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
