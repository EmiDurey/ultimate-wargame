package model;

/**
 *  Class BarreMenu.
 */
public class Archer extends Unite {

	/**
	 * Points de puissance de l'unité.
	 */
	private final int puissance = 6;
	/**
	 * Points de défense de l'unité.
	 */
	private final int defense = 3;
	/**
	 * Points de déplacement de l'unité.
	 */
	private final int deplacement = 5;
	/**
	 * Points de vie de l'unité.
	 */
	private final int vie = 35;
	/**
	 * Points de vision de l'unité.
	 */
	private final int vue = 7;
	/**
	 * Points de portée de l'unité.
	 */
	private int portee;

	/**
	 *  Constructeur d'un archer.
	 *  @param hex Hexagone
	 */
	public Archer(Hex hex) {
		super(hex);
		this.pointsAttaque = this.puissance;
		this.pointsDefense = this.defense;
		this.pointsDeplacement = this.deplacement;
		this.pointsDeVie = this.vie;
		this.vision = this.vue;
	}

	/**
	 * Heal de l'unité si elle n'a pas bougé.
	 */
	public void heal() {
		if (this.pointsDeplacement == deplacement) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
		}
	}

	/**
	 * Réinitialise les points de déplacement de l'unité.
	 */
	public void initialize() {
		this.pointsDeplacement = deplacement;
	}

	/**
	 * Méthode combat propre à l'archer.
	 * @param map Map
	 * @param joueur Joueur
	 * @param unite Unite
	 */
	public void combat(HexMap map, Joueur joueur, Unite unite) {
		final int constante = 7;
		portee = (int) pointsDeVie / constante;
		if (map.pathfinding(this.hex, unite.hex).size() < portee) {
			unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque));
		}
	}

	/**
	 * Renvoi les points de vie de base.
	 * @return vie vie
	 */
	public int getVie() {
		return vie;
	}
}
