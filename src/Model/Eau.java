package model;

public class Eau extends Terrain {

	/* attributs du terrain */
	private final int COUT = 5; // cout du deplacement dans ce terrain
	private final int BONUS_DEFENSE = 0; // pourcentage
	
	public Eau() {
		this.coutDeplacement = this.COUT;
		this.bonusDefense = this.BONUS_DEFENSE;
	}

}
