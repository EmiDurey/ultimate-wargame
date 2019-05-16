package model;

public class Mage extends Unite {

	/* attributs de base */
	private final int POWER = 8; //gros c'est la puissance
	private final int DEFENSE = 3; //gros c'est la defense
	private final int MOVE = 5; //fais le mouv, fais le mouv
	private final int PV = 25; //c'est la vie, lalalalala
	private final int VISION = 5;//perception humaine des rayonnements lumineux
	
	public Mage(Hex hex) {
		super(hex);
		this.pointsAttaque = this.POWER;
		this.pointsDefense = this.DEFENSE;
		this.pointsDeplacement = this.MOVE;
		this.pointsDeVie = this.PV;
		this.vision = this.VISION;
	}

	public int getPV() {
		return PV;
	}
}
