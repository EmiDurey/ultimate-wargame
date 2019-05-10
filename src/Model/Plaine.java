package model;

public class Plaine extends Terrain {

	/* attributs du terrain */
	private final int COUT = 1; // cout du deplacement dans ce terrain
	private final int BONUS_DEFENSE = 20; // pourcentage
	
	public Plaine() {
		this.coutDeplacement = this.COUT;
		this.bonusDefense = this.BONUS_DEFENSE;
	}

}