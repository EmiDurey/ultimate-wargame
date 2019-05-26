package model;

public class Mage extends Unite {

	/* attributs de base */
	private final int puissance = 8; //gros c'est la puissance
	private final int defense = 3; //gros c'est la defense
	private final int deplacement = 5; //fais le mouv, fais le mouv
	private final int vie = 25; //c'est la vie, lalalalala
	private final int vue = 5;//perception humaine des rayonnements lumineux

	public Mage(Hex hex) {
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

	public int getVie() {
		return vie;
	}
}
