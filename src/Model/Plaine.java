package model;

public class Plaine extends Terrain {

	/* attributs du terrain */
	private final int cout = 1; // cout du deplacement dans ce terrain
	private final int defense = 20; // pourcentage
	
	public Plaine() {
		this.coutDeplacement = this.cout;
		this.bonusDefense = this.defense;
	}

}