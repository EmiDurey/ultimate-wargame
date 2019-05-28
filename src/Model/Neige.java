package model;

public class Neige extends Hex {

	/* attributs du terrain */
	private final int cout = 2; // cout du deplacement dans ce terrain
	private final int defense = 30; // pourcentage


	Neige(int newX, int newY) {
		super(newX, newY);
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
