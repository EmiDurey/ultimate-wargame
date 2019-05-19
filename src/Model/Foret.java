package model;

public class Foret extends Terrain {

	/* attributs du terrain */
	private final int cout = 2; // cout du deplacement dans ce terrain
	private final int defense = 40; // pourcentage

	public Foret() {
		this.coutDeplacement = this.cout;
		this.bonusDefense = this.defense;
	}

}
