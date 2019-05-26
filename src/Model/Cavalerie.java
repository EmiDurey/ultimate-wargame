package model;

public class Cavalerie extends Unite {

	/* attributs de base */
	private final int puissance = 8; //gros c'est la puissance
	private final int defense = 7; //gros c'est la defense
	private final int deplacement = 8; //fais le mouv, fais le mouv
	private final int vie = 40; //c'est la vie, lalalalala
	private final int vue = 6; //perception humaine des rayonnements lumineux

	public Cavalerie(Hex hex) {
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

	public Cavalerie() {
	}

	public int getVie() {
		return vie;
	}
}
