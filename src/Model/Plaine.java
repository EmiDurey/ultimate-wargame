package model;

public class Plaine extends Hex {

	/* attributs du terrain */
	private final int cout = 15; // cout du deplacement dans ce terrain
	private final float defense = 10; // pourcentage


	Plaine(int newX, int newY) {
		super(newX, newY);
	}

	Plaine(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
	}


	public void print() {
		System.out.print("P");
	}

}
