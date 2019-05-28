package model;

public class Eau extends Hex {

	/* attributs du terrain */
	private final int cout = 5; // cout du deplacement dans ce terrain
	private final int defense = 0; // pourcentage

	private float rarity = 1/6;


	Eau(int newX, int newY) {
		super(newX, newY);
	}

	Eau(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
	}


	public float getRarity() {
		return rarity;
	}


	public void print() {
		System.out.print("W");
	}
}
