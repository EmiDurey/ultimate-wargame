package model;

public class Forteresse extends Terrain {

	/* attributs du terrain */
	private final int COUT = 1; // cout du deplacement dans ce terrain
	private final int BONUS_DEFENSE = 60; // pourcentage
	
	public Forteresse() {
		this.coutDeplacement = this.COUT;
		this.bonusDefense = this.BONUS_DEFENSE;
	}

}
