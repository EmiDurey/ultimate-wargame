package model;

public class Infanterie extends Unite {

	/* attributs de base */
	private final int puissance = 5; //gros c'est la puissance
	private final int defense = 4; //gros c'est la defense
	private final int deplacement = 6; //fais le mouv, fais le mouv
	private final int vie = 30; //c'est la vie, lalalalala
	private final int vue = 4; //perception humaine des rayonnements lumineux

	public Infanterie(Hex hex) {
		super(hex);
		this.pointsAttaque = this.puissance;
		this.pointsDefense = this.defense;
		this.pointsDeplacement = this.deplacement;
		this.pointsDeVie = this.vie;
		this.vision = this.vue;
	}

	public void heal() {
		if (this.pointsDeplacement == deplacement) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
		}
	}

	public void initialize() {
		this.pointsDeplacement = deplacement;
	}

	public int getPV() {
		return vie;
	}

}
