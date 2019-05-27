package model;

public class InfanterieLourde extends Unite {

	public InfanterieLourde(Hex hex) {
		super(hex);
		this.pointsAttaque = 10;
		this.pointsDefense = 10;
		this.pointsDeplacement = 4;
		this.pointsDeplacementInit = 4;
		this.pointsDeVie = 40;
		this.pointsDeVieMax = 40;
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
