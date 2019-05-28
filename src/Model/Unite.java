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
		int rand = (int) (Math.random() * 10);
		List<Hex> trajet = new ArrayList<Hex>();
		System.out.println(rand+"/10, crit > 2");
		System.out.println(!this.hex.isNeighbour(unite.hex));
		if (!this.hex.isNeighbour(unite.hex)) {// VIRER !
			if (rand > chanceCrit) {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
			} else {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
			}
		} else {
			trajet = map.pathfinding(this.hex, unite.hex);
			/*if((!trajet.isEmpty()) && (trajet.get(trajet.size()-2) COUTE < this.pointsDeplacement)) {
			 	this.getHex().setUnit(null);
			 	trajet.get(trajet.size()-2).setUnit(this);
				this.setHex(trajet.get(trajet.size()-2));
				if (rand > chanceCrit) {
					unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
				} else {
					unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
				}
			}*/
		}
		this.pointsDeplacement = 0;
	}

	/**
	 * IA.
	 * Déplace une unité en fonction des posssibilités de déplacement de cette dernière.
	 * @param tour Int
	 * @param joueur Joueur actuelle
	 * @param map HexMap
	 */
	public void joueTour(int tour, Joueur joueur, HexMap map) {
		List<Hex> positionPossible = new ArrayList<Hex>();// NEED FONCTION
		List<Hex> unitInRange = new ArrayList<Hex>();
		List<Hex> trajet = new ArrayList<Hex>();
		for (Hex hex : positionPossible) {
			if (!hex.isEmpty()) {
				unitInRange.add(hex);
			}
			if (unitInRange.isEmpty()) {
				if (this.pointsDeVie >= this.pointsDeVieMax / 2) {
					//ON SE RAPPROCHE
				} else {
					this.setPointsDeplacement(0);
					break;
				}
			} else {
				for (Hex unitPos : unitInRange) {
					trajet = map.pathfinding(this.hex, unitPos);
					/*if((!trajet.isEmpty()) 
							&& (trajet.get(trajet.size()-2) COUTE < this.pointsDeplacement) 
							&& unitPos.getUnit().pointsDeVie<this.pointsAttaque - unitPos.getUnit().getDefense()) {
						this.combat(map, joueur, unitPos.getUnit());
					} else {
						if((!trajet.isEmpty()) 
								&& (trajet.get(trajet.size()-2) COUTE < this.pointsDeplacement) 
								&& this.pointsDeVie > unitPos.getUnit().pointsAttaque - this.getDefense()) {
							this.combat(map, joueur, unitPos.getUnit());
						}
					}*/
				}
			}
		}
		if (this.pointsDeplacement != 0) {
			//ON S'ELOIGNE
		}
		
	}

	/**
	 * Déplace une unité si c'est possible.
	 * @param map HexMap
	 * @param newHex Hex
	 */
	public void seDeplace(HexMap map, Hex newHex){
		List<Hex> trajet = map.pathfinding(this.hex, newHex);
		/*if((!trajet.isEmpty()) && (trajet.get(trajet.size()-1) COUTE < this.pointsDeplacement)) {
		  	this.getHex().setUnit() = null;
			newHex.setUnit(this);
			this.setHex(newHex);
		}*/
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

	public int getPointsDeVie() {
		return pointsDeVie;
	}

	public void setPointsDeVie(int pointsDeVie) {
		this.pointsDeVie = pointsDeVie;
	}
}