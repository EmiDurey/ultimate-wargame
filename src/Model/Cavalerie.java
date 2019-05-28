package model;

/**
 *  Class Cavalerie.
 */
public class Cavalerie extends Unite {

	/**
	 *  Constructeur d'un cavalier.
	 *  @param hex Hexagone
	 *  @param joueur Joueur
	 */
	public Cavalerie(Hex hex, Joueur joueur) {
		super(hex, joueur);
		hex.setUnit(this);
		this.pointsAttaque = 8;
		this.pointsDefense = 3;
		this.pointsDeplacement = 8;
		this.pointsDeVie = 40;
		this.vision = 6;
		this.pointsDeVieMax = 40;
	}

	/**
	 *  Constructeur d'un cavalier.
	 */
	public Cavalerie() {
	}

	/**
	 * Heal de l'unité si elle n'a pas bougé.
	 */
	@Override
	public void heal() {
		if (this.pointsDeplacement == 8) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
			if (this.pointsDeVie > this.pointsDeVieMax) {
				this.pointsDeVie = this.pointsDeVieMax;
			}
		}
	}

	/**
	 * Réinitialise les points de déplacement de l'unité.
	 */
	@Override
	public void initialize() {
		this.pointsDeplacement = 8;
	}

}
