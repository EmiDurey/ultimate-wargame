package model;

public class Foret extends Hex {

	/* attributs du terrain */
	private final int cout = 2; // cout du deplacement dans ce terrain
	private final int defense = 40; // pourcentage

	private float rarity = 1/5;


	Foret(int newX, int newY) {
		super(newX, newY);
	}

	Foret(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
	}


	public float getRarity() {
		return rarity;
	}

	public void print() {
		System.out.print("F");
	}
}
