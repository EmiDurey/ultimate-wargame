package model;

public class Archer extends Unite {
	
	/* attributs de base */
	private final int POWER = 6; //gros c'est la puissance
	private final int DEFENSE = 3; //gros c'est la defense
	private final int MOVE = 5; //fais le mouv, fais le mouv
	private final int PV = 35; //c'est la vie, lalalalala
	private final int VISION = 7;//perception humaine des rayonnements lumineux
	private final int PORTEE = 5;//portée des tirs de l'archer
	private int portee;
	
	public Archer(Hex hex) {
		super(hex);
		this.pointsAttaque = this.POWER;
		this.pointsDefense = this.DEFENSE;
		this.pointsDeplacement = this.MOVE;
		this.pointsDeVie = this.PV;
		this.vision = this.VISION;
		this.portee = this.PORTEE;
	}

	public Archer() {
	}
	
	public void combat(Unite unite){
		portee = (int) pointsDeVie/7;
		if(hex.isInRange(unite.hex, portee))
			unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque * Math.random()));
	}
}
