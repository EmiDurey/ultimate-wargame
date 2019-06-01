package model;

public class Plaine extends Hex {
	
	Plaine(int newX, int newY) {
		super(newX, newY);
		this.cost = 15;
		this.defense = 10;
	}

	Plaine(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
	}


	public void print() {
		System.out.print("P");
	}

}
