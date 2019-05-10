package model;

public class Foret extends Terrain {

	/* attributs du terrain */
	private final int COUT = 2; // cout du deplacement dans ce terrain
	private final int BONUS_DEFENSE = 40; // pourcentage
	
	public Foret() {
		this.coutDeplacement = this.COUT;
		this.bonusDefense = this.BONUS_DEFENSE;
	}

}
