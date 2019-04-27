package model;

public class Archer extends Unite {
	
	/* attributs de base */
	private final int POWER = 6; //gros c'est la puissance
	private final int DEFENSE = 3; //gros c'est la defense
	private final int MOVE = 5; //fais le mouv, fais le mouv
	private final int PV = 35; //c'est la vie, lalalalala
	private final int VISION = 7;//perception humaine des rayonnements lumineux
	
	public Archer(Hex hex, Position pos) {
		super(hex, pos);
		this.pointsAttaque = this.POWER;
		this.pointsDefense = this.DEFENSE;
		this.pointsDeplacement = this.MOVE;
		this.pointsDeVie = this.PV;
		this.vision = this.VISION;
	}
}
