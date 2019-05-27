package model;

public class Infanterie extends Unite {

	public Infanterie(Hex hex) {
		super(hex);
		this.pointsAttaque = 5;
		this.pointsDefense = 4;
		this.pointsDeplacement = 6;
		this.pointsDeplacementInit = 6;
		this.pointsDeVie = 30;
		this.pointsDeVieMax = 30;
		this.vision = 4;
	}

	public void heal() {
		if (this.pointsDeplacement == this.pointsDeplacementInit) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
		}
	}

	public void initialize() {
		this.pointsDeplacement = this.pointsDeplacementInit;
	}

}
