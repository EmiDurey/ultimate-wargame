package model;

/**
 *  Class Cavalerie.
 */
public class Cavalerie extends Unite {

	/**
	 *  Constructeur d'un cavalier.
	 *  @param hex Hexagone
	 */
	public Cavalerie(Hex hex) {
		super(hex);
		this.pointsAttaque = 8;
		this.pointsDefense = 7;
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
	public void heal() {
		if (this.pointsDeplacement == 8) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
		}
	}

	/**
	 * Réinitialise les points de déplacement de l'unité.
	 */
	public void initialize() {
		this.pointsDeplacement = 8;
	}

}
