package model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class Pretre.
 */
public class Pretre extends Unite {

	/**
	 *  Points de soin d'un pretre.
	 */
	private int pointsSoin;

	/**
	 *  Constructeur d'un pretre.
	 *  @param hex Hexagone
	 *  @param joueur Joueur
	 */
	public Pretre(Hex hex, Joueur joueur) {
		super(hex, joueur);
		hex.setUnit(this);
		this.pointsAttaque = 2;
		this.pointsDefense = 1;
		this.pointsDeplacement = 6;
		this.pointsDeplacementInit = 6;
		this.pointsDeVie = 60;
		this.pointsDeVieMax = 60;
		this.vision = 4;
		this.pointsSoin = 15;
	}

	/**
	 * Heal de l'unité si elle n'a pas bougé.
	 * Soigne toutes les unités alliés autour de lui.
	 * @param map HexMap
	 * @param joueur Joueur
	 */
	public void soigne(HexMap map, Joueur joueur) {
		Hex[] voisins = new Hex[6];
		voisins = this.hex.getNeighbours();
		for (Hex voisin : voisins) {
			if (map.getHex(voisin.getX(), voisin.getY()).getUnit() != null) {
				Unite unite = map.getHex(voisin.getX(), voisin.getY()).getUnit();
				if (joueur.getUnite().contains(unite)) {
					unite.pointsDeVie = (int) (unite.pointsDeVie + this.pointsSoin);
					if (unite.pointsDeVie > unite.pointsDeVieMax) {
						unite.pointsDeVie = unite.pointsDeVieMax;
					}
				}
			}
		}
	}

	/**
	 * IA.
	 * Déplace une unité en fonction des possibilités de déplacement de cette dernière.
	 * @param joueur Joueur actuelle
	 * @param map HexMap
	 */
	@Override
	public void joueTour(Joueur joueur, HexMap map) {
		List<Hex> positionPossible = new ArrayList<Hex>();// NEED FONCTION

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
