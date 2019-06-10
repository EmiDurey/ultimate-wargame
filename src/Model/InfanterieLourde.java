package model;

/**
 *  Class InfantereLourde.
 *  @see Hex
 *  @see Joueur
 *  @see Unite
 */
public class InfanterieLourde extends Unite {

	/**
	 *  Constructeur de l'infanterie lourde.
	 *  @param hex Hexagone
	 *  @param joueur Joueur
	 *  @see Hex
	 *  @see Joueur
	 */
	public InfanterieLourde(Hex hex, Joueur joueur) {
		super(hex, joueur);
		hex.setUnit(this);
		this.pointsAttaque = 16;
		this.pointsDefenseInit = 5;
		this.pointsDefense = this.pointsDefenseInit;
		this.pointsDeplacementInit = 80;
		this.pointsDeplacement = this.pointsDeplacementInit;
		this.pointsDeVieMax = 45;
		this.pointsDeVie = this.pointsDeVieMax;
		this.vision = 5;
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
