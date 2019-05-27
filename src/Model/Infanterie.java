package model;

/**
 *  Class Infanterie.
 */
public class Infanterie extends Unite {

	/**
	 *  Constructeur de l'infanterie.
	 *  @param hex Hexagone
	 */
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
