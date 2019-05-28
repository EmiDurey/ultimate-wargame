package model;

public class Forteresse extends Hex {

	/* attributs du terrain */
	private final int cout = 1; // cout du deplacement dans ce terrain
	private final int defense = 60; // pourcentage


	Forteresse(int newX, int newY) {
		super(newX, newY);
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
