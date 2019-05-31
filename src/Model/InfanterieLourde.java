package model;

/**
 *  Class InfantereLourde.
 */
public class InfanterieLourde extends Unite {

	/**
	 *  Constructeur de l'infanterie lourde.
	 *  @param hex Hexagone
	 *  @param joueur Joueur
	 */
	public InfanterieLourde(Hex hex, Joueur joueur) {
		super(hex, joueur);
		hex.setUnit(this);
		this.pointsAttaque = 8;
		this.pointsDefenseInit = 5;
		this.pointsDefense = this.pointsDefenseInit;
		this.pointsDeplacementInit = 80;
		this.pointsDeplacement = this.pointsDeplacementInit;
		this.pointsDeVieMax = 45;
		this.pointsDeVie = this.pointsDeVieMax;
		this.vision = 5;
		joueur.addUnit(this);
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
