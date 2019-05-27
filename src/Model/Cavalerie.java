package model;

public class Cavalerie extends Unite {

	public Cavalerie(Hex hex) {
		super(hex);
		this.pointsAttaque = 8;
		this.pointsDefense = 7;
		this.pointsDeplacement = 8;
		this.pointsDeVie = 40;
		this.vision = 6;
		this.pointsDeVieMax = 40;
	}

	public void heal() {
		if (this.pointsDeplacement == 8) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
		}
	}

	public void initialize() {
		this.pointsDeplacement = 8;
	}

	public Cavalerie() {
	}
}
