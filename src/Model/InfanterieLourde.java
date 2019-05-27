package model;

/**
 *  Class InfantereLourde.
 */
public class InfanterieLourde extends Unite {

	/**
	 *  Constructeur de l'infanterie lourde.
	 *  @param hex Hexagone
	 */
	public InfanterieLourde(Hex hex) {
		super(hex);
		this.pointsAttaque = 10;
		this.pointsDefense = 10;
		this.pointsDeplacement = 4;
		this.pointsDeplacementInit = 4;
		this.pointsDeVie = 40;
		this.pointsDeVieMax = 40;
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
