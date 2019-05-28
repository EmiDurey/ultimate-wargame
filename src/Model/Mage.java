package model;

/**
 *  Class Mage.
 */
public class Mage extends Unite {

	/**
	 *  Constructeur d'un mage.
	 *  @param hex Hexagone
	 */
	public Mage(Hex hex) {
		super(hex);
		hex.setUnit(this);
		this.pointsAttaque = 10;
		this.pointsDefense = 1;
		this.pointsDeplacement = 5;
		this.pointsDeplacementInit = 5;
		this.pointsDeVie = 25;
		this.pointsDeVieMax = 25;
		this.vision = 5;
	}

	/**
	 * Heal de l'unité si elle n'a pas bougé.
	 */
	@Override
	public void heal() {
		if (this.pointsDeplacement == this.pointsDeplacementInit) {
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
		this.pointsDeplacement = this.pointsDeplacementInit;
	}
}
