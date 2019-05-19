package model;

public class Pretre extends Unite {

	/*attributs de base */
	private final int puissance = 2; //gros c'est la puissance
	private final int defense = 2; //gros c'est la defense
	private final int deplacement = 6; //fais le mouv, fais le mouv
	private final int vie = 60; //c'est la vie, lalalalala
	private final int vue = 4; //perception humaine des rayonnements lumineux
	private int pouvoirSoin = 15; //Vivre c'est bien

	public Pretre(Hex hex) {
		super(hex);
		this.pointsAttaque = this.puissance;
		this.pointsDefense = this.defense;
		this.pointsDeplacement = this.deplacement;
		this.pointsDeVie = this.vie;
		this.vision = this.vue;
	}

	public void soigne(Joueur joueur) {
		Hex[] voisins = new Hex[6];
		voisins = this.hex.getNeighbours();
		for (int i = 0; i < 6; i++) {
			if (!voisins[i].isEmpty()) {
				if (joueur.getUnite().contains(voisins[i].unit)) {
					voisins[i].unit.pointsDeVie = (int) (voisins[i].unit.pointsDeVie + this.pouvoirSoin * Math.random());
					if (voisins[i].unit.pointsDeVie > voisins[i].unit.getPV) {
						voisins[i].unit.pointsDeVie = voisins[i].unit.getPV;
					}
				}
			}
		}
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
