package model;

public class Eau extends Hex {

	/* attributs du terrain */
	private final int cout = 5; // cout du deplacement dans ce terrain
	private final int defense = 0; // pourcentage


	Eau(int newX, int newY) {
		super(newX, newY);
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
