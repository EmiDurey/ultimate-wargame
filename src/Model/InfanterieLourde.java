package model;

public class InfanterieLourde extends Unite {

	/* attributs de base */
	private final int POWER = 10; //gros c'est la puissance
	private final int DEFENSE = 10; //gros c'est la defense
	private final int MOVE = 4; //fais le mouv, fais le mouv
	private final int PV = 40; //c'est la vie, lalalalala
	private final int VISION = 4;//perception humaine des rayonnements lumineux
	
	public InfanterieLourde(Hex hex) {
		super(hex);
		this.pointsAttaque = this.POWER;
		this.pointsDefense = this.DEFENSE;
		this.pointsDeplacement = this.MOVE;
		this.pointsDeVie = this.PV;
		this.vision = this.VISION;
	}

	public void heal() {
		if(this.pointsDeplacement==MOVE) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
		}
	}
	
	public void initialize() {
		this.pointsDeplacement = MOVE;
	}
	
	public int getPV() {
		return PV;
	}
}
