package model;

public class Eau extends Terrain {

	/* attributs du terrain */
	private final int cout = 5; // cout du deplacement dans ce terrain
	private final int defense = 0; // pourcentage

	public Eau() {
		this.coutDeplacement = this.cout;
		this.bonusDefense = this.defense;
	}

}
