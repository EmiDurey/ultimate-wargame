package model;

/**
 *  Class Infanterie.
 *  @see Hex
 *  @see Joueur
 *  @see Unite
 */
public class Infanterie extends Unite {

	/**
	 *  Constructeur de l'infanterie.
	 *  @param hex Hexagone
	 *  @param joueur Joueur
	 *  @see Hex
	 *  @see Joueur
	 */
	public Infanterie(Hex hex, Joueur joueur) {
		super(hex, joueur);
		hex.setUnit(this);
		this.pointsAttaque = 16;
		this.pointsDefenseInit = 4;
		this.pointsDefense = this.pointsDefenseInit;
		this.pointsDeplacementInit = 100;
		this.pointsDeplacement = this.pointsDeplacementInit;
		this.pointsDeVieMax = 35;
		this.pointsDeVie = this.pointsDeVieMax;
		this.vision = 8;
	}

	/**
	 * Heal de l'unité si elle n'a pas bougé.
	 */
	@Override
	public void heal() {
		if (this.pointsDeplacement == this.pointsDeplacementInit) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.10);
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
		this.pointsDeplacement = this.pointsDeplacementInit;
	}

}
