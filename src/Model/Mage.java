package model;

public class Mage extends Unite {

	public Mage(Hex hex) {
		super(hex);
		this.pointsAttaque = 8;
		this.pointsDefense = 3;
		this.pointsDeplacement = 5;
		this.pointsDeplacementInit = 5;
		this.pointsDeVie = 25;
		this.pointsDeVieMax = 25;
		this.vision = 5;
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
