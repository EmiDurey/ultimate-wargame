package model;

public class Neige extends Hex {

	/* attributs du terrain */
	private final int cout = 2; // cout du deplacement dans ce terrain
	private final int defense = 30; // pourcentage

	private float rarity = 1/5;


	Neige(int newX, int newY) {
		super(newX, newY);
	}

	Neige(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
	}


	public float getRarity() {
		return rarity;
	}


	public void print() {
		System.out.print("S");
	}
}
