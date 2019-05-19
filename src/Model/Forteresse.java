package model;

public class Forteresse extends Terrain {

	/* attributs du terrain */
	private final int cout = 1; // cout du deplacement dans ce terrain
	private final int defense = 60; // pourcentage
	
	public Forteresse() {
		this.coutDeplacement = this.cout;
		this.bonusDefense = this.defense;
	}

}
