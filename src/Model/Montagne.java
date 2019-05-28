package model;

public class Montagne extends Hex {

	/* attributs du terrain */
	private final int cout = 3; // cout du deplacement dans ce terrain
	private final int defense = 60; // pourcentage

	private float rarity = 1/6;


	Montagne(int newX, int newY) {
		super(newX, newY);
	}

	Montagne(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
	}



	public float getRarity() {
		return rarity;
	}


	public void print() {
		System.out.print("M");
	}
}
