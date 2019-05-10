package model;

public class Montagne extends Terrain {

	/* attributs du terrain */
	private final int COUT = 3; // cout du deplacement dans ce terrain
	private final int BONUS_DEFENSE = 60; // pourcentage
	
	public Montagne() {
		this.coutDeplacement = this.COUT;
		this.bonusDefense = this.BONUS_DEFENSE;
	}

}
