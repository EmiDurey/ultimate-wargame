package model;

/**
 *  Class Cavalerie.
 *  @see Hex
 *  @see Joueur
 *  @see Unite
 */
public class Cavalerie extends Unite {

	/**
	 *  Constructeur d'un cavalier.
	 *  @param hex Hexagone
	 *  @param joueur Joueur
	 *  @see Hex
	 *  @see Joueur
	 */
	public Cavalerie(Hex hex, Joueur joueur) {
		super(hex, joueur);
		hex.setUnit(this);
		this.pointsAttaque = 17;
		this.pointsDefenseInit = 4;
		this.pointsDefense = this.pointsDefenseInit;
		this.pointsDeplacementInit = 120;
		this.pointsDeplacement = this.pointsDeplacementInit;
		this.pointsDeVieMax = 40;
		this.pointsDeVie = this.pointsDeVieMax;
		this.vision = 6;
	}

	/**
	 *  Constructeur d'un cavalier.
	 */
	public Cavalerie() {
	}

	/**
	 * Heal de l'unit� si elle n'a pas boug�.
	 */
	@Override
	public void heal() {
		if (this.pointsDeplacement == 8) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.10);
			if (this.pointsDeVie > this.pointsDeVieMax) {
				this.pointsDeVie = this.pointsDeVieMax;
			}
		}
	}

	/**
	 * R�initialise les points de d�placement de l'unit�.
	 */
	@Override
	public void initialize() {
		this.pointsDeplacement = this.pointsDeplacementInit;
	}

}
