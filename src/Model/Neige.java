package model;

public class Neige extends Terrain {

	/* attributs du terrain */
	private final int cout = 2; // cout du deplacement dans ce terrain
	private final int defense = 30; // pourcentage

	public Neige() {
		this.coutDeplacement = this.cout;
		this.bonusDefense = this.defense;
	}

}
