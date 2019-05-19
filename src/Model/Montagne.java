package model;

public class Montagne extends Terrain {

	/* attributs du terrain */
	private final int cout = 3; // cout du deplacement dans ce terrain
	private final int defense = 60; // pourcentage

	public Montagne() {
		this.coutDeplacement = this.cout;
		this.bonusDefense = this.defense;
	}

}
