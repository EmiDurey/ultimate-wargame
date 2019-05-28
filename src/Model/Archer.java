package model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class Archer.
 */
public class Archer extends Unite {

	
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
		hex.setUnit(this);
		this.pointsAttaque = 6;
		this.pointsDefense = 2;
		this.pointsDeplacement = 5;
		this.pointsDeplacementInit = 5;
		this.pointsDeVie = 35;
		this.vision = 7;
		this.pointsDeVieMax = 35;
		this.portee = 7;
		
	}

	/**
	 *  Constructeur d'un archer.
	 */
	public Archer() {
	}
	
	/**
	 * Heal de l'unité si elle n'a pas bougé.
	 */
	@Override
	public void heal() {
		if (this.pointsDeplacement == this.pointsDeplacementInit) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
		}
	}

	/**
	 * Réinitialise les points de déplacement de l'unité.
	 */
	@Override
	public void initialize() {
		this.pointsDeplacement = this.pointsDeplacementInit;
	}

	/**
	 * Méthode combat propre à l'archer.
	 * Peut attaquer à distance.
	 * @param map Map
	 * @param joueur Joueur
	 * @param unite Unite
	 */
	@Override
	public void combat(HexMap map, Joueur joueur, Unite unite) {
		final int crit = 3;
		final int chanceCrit = 2;
		int rand = (int) (Math.random() * 10);
		List<Hex> trajet = new ArrayList<Hex>();
		portee = (int) pointsDeVie / this.portee;
		if (this.hex.distance(unite.hex) < portee) {
			if (rand > chanceCrit) {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
			} else {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
			}
		} else {
			trajet = map.pathfinding(this.hex, unite.hex);
			for(Hex hex : trajet) {
				if ((hex.distance(unite.hex) <= portee) /*&& ( Cout déplacement )*/ ){
					this.getHex().setUnit(null);
				 	hex.setUnit(this);
					this.setHex(hex);
					if (rand > chanceCrit) {
						unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
					} else {
						unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
					}
					break;
				}
			}
		}
		this.pointsDeplacement = 0;
	}
}



