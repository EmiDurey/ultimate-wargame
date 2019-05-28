package model;

public class Forteresse extends Hex {

	/* attributs du terrain */
	private final int cout = 1; // cout du deplacement dans ce terrain
	private final int defense = 60; // pourcentage


	private float rarity = 1/8;


	Forteresse(int newX, int newY) {
		super(newX, newY);
	}

	Forteresse(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
	}



	public float getRarity() {
		return rarity;
	}

	public void print() {
		System.out.print("C");
	}
}
