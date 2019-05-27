package model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class Unite.
 */
public abstract class Unite {

	/**
	 *  Points d'attaque d'une unitée.
	 */
	protected int pointsAttaque;
	/**
	 *  Points de défense d'une unitée.
	 */
	protected int pointsDefense;
	/**
	 *  Points de déplacement d'une unitée.
	 *  Evolue au cours du tour.
	 */
	protected int pointsDeplacement;
	/**
	 *  Points de déplacement initial d'une unitée.
	 *  Ne change pas au cours de la partie.
	 */
	protected int pointsDeplacementInit;
	/**
	 *  Points de vie d'une unitée.
	 *  Evolue au cours de la partie.
	 */
	protected int pointsDeVie;
	/**
	 *  Points de vie maximum d'une unitée.
	 *  Ne change pas au cours de la partie.
	 */
	protected int pointsDeVieMax;
	/**
	 *  Points de vision d'une unitée.
	 */
	protected int vision;
	/**
	 *  Position d'une unitée.
	 */
	protected Hex hex;

	/**
	 *  Constructeur d'une unitée.
	 *  @param hex Hexagone
	 */
	public Unite(Hex hex) {
		this.hex = hex;
	}

	/**
	 *  Constructeur d'une unitée.
	 */
	public Unite() {
	}

	/**
	 * Heal de l'unité si elle n'a pas bougé.
	 */
	public void heal() {
	}

	/**
	 * Réinitialise les points de déplacement de l'unité.
	 */
	public void initialize() {
	}

	/**
	 * Méthode combat pour la plupart des unités.
	 * @param map Map
	 * @param joueur Joueur
	 * @param unite Unite
	 */
	public void combat(HexMap map, Joueur joueur, Unite unite) {
		final int crit = 3;
		final int chanceCrit = 2;
		int rand = (int)(Math.random() * 10);
		List<Hex> trajet = new ArrayList<Hex>();
		if (this.hex.isNeighbour(unite.hex)) {
			if (rand > chanceCrit) {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
			} else {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
			}
		} else {
			if (unite.hex.distance(this.hex) <= this.pointsDeplacement) {
				trajet = map.pathfinding(this.hex, unite.hex);
				this.setHex(trajet.get(trajet.size() - 1));
				if (rand > chanceCrit) {
					unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
				} else {
					unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
				}
			}
		}
		this.pointsDeplacement = 0;
	}

	/**
	 * IA.
	 * @param tour Int
	 */
	public void joueTour(int tour) {
	}

	/**
	 * Déplace une unité si c'est possible.
	 * @param newHex Hex
	 */
	public void seDeplace(Hex newHex){
		if (newHex.distance(this.hex) <= this.pointsDeplacement) {
			this.setHex(newHex);
		}
	}

	/**
	 * Récupère la position d'une unité.
	 * @return hex Hex
	 */
	public Hex getHex() {
		return hex;
	}

	/**
	 * Assigne la position d'une unité.
	 * @param hex Hex
	 */
	public void setHex(Hex hex) {
		this.hex = hex;
	}

	/**
	 * Récupère la défense d'une unité.
	 * @return pointsDefense int
	 */
	public int getDefense() {
		return pointsDefense;
	}

	/**
	 * Assigne la défense d'une unité.
	 * @param pointsDefense int
	 */
	public void setDefense(int pointsDefense) {
		this.pointsDefense = pointsDefense;
	}

	/**
	 * Récupère les points de déplacement d'une unité.
	 * @return pointsDeplacement int
	 */
	public float getPointsDeplacement() {
		return pointsDeplacement;
	}

	/**
	 * Assigne les points de déplacement d'une unité.
	 * @param pointsDeplacement int
	 */
	public void setPointsDeplacement(int pointsDeplacement) {
		this.pointsDeplacement = pointsDeplacement;
	}

	/**
	 * Récupère les points de vie maximum d'une unité.
	 * @return pointsDeVieMax int
	 */
	public int getPointsDeVieMax() {
		return pointsDeVieMax;
	}
}