package model;

/**
 *  Class Pretre.
 */
public class Pretre extends Unite {

	private int pointsSoin;
	
	/**
	 *  Constructeur d'un pretre.
	 *  @param hex Hexagone
	 */
	public Pretre(Hex hex) {
		super(hex);
		this.pointsAttaque = 2;
		this.pointsDefense = 2;
		this.pointsDeplacement = 6;
		this.pointsDeplacementInit = 6;
		this.pointsDeVie = 60;
		this.pointsDeVieMax = 60;
		this.vision = 4;
		this.pointsSoin = 15;
	}

	/**
	 * Heal de l'unité si elle n'a pas bougé.
	 * Soigne toutes les unités alliés autour de lui.
	 * @param joueur Joueur
	 */
	public void soigne(Joueur joueur) {
		Hex[] voisins = new Hex[6];
		voisins = this.hex.getNeighbours();
		for (Hex voisin : voisins) {
			if (!voisin.isEmpty()) {
				if (joueur.getUnite().contains(voisin.getUnit())) {
					voisin.getUnit().pointsDeVie = (int) (voisin.getUnit().pointsDeVie + this.pointsSoin);
					if (voisin.getUnit().pointsDeVie > voisin.getUnit().pointsDeVieMax) {
						voisin.getUnit().pointsDeVie = voisin.getUnit().pointsDeVieMax;
					}
				}
			}
		}
	}

	/**
	 * Heal de l'unité si elle n'a pas bougé.
	 */
	public void heal() {
		if (this.pointsDeplacement == this.pointsDeplacementInit) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
		}
	}

	/**
	 * Réinitialise les points de déplacement de l'unité.
	 */
	public void initialize() {
		this.pointsDeplacement = this.pointsDeplacementInit;
	}
}
