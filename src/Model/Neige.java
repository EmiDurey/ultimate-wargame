package model;

public class Neige extends Terrain {

	/* attributs du terrain */
	private final int COUT = 2; // cout du deplacement dans ce terrain
	private final int BONUS_DEFENSE = 30; // pourcentage
	
	public Neige() {
		this.coutDeplacement = this.COUT;
		this.bonusDefense = this.BONUS_DEFENSE;
	}

}
