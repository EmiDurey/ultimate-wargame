package model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class Dragon.
 */
public class Dragon extends Unite{

	/**
	 *  Constructeur d'un archer.
	 *  @param hex Hexagone
	 */
	public Dragon(Hex hex) {
		super(hex);
		this.pointsAttaque = 10;
		this.pointsDefense = 10;
		this.pointsDeplacement = 7;
		this.pointsDeplacementInit = 7;
		this.pointsDeVie = 45;
		this.vision = 7;
		this.pointsDeVieMax = 45;
	}

	/**
	 *  Constructeur d'un archer.
	 */
	public Dragon() {
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

	/**
	 * Méthode combat propre au dragon.
	 * @param map Map
	 * @param joueur Joueur
	 * @param unite Unite
	 */
	public void combat(HexMap map, Joueur joueur, Unite unite) {
		Hex[] voisins = new Hex[6];
		voisins = unite.hex.getNeighbours();
		int rand = (int)(Math.random() * 10);
		List<Hex> trajet = new ArrayList<Hex>();
		if (this.hex.isNeighbour(unite.hex)) {
			for (Hex voisin : voisins) {
				if (!voisin.isEmpty()) {
					if (!joueur.getUnite().contains(voisin.getUnit())) {
						if (rand > 2) {
							voisin.getUnit().pointsDeVie = (int) (voisin.getUnit().pointsDeVie - (3 * (this.pointsAttaque - voisin.getUnit().pointsDefense)));
						} else {
							voisin.getUnit().pointsDeVie = (int) (voisin.getUnit().pointsDeVie - (3 * (this.pointsAttaque - voisin.getUnit().pointsDefense)));
						}
					}
				}
			}
		} else {
			if (unite.hex.distance(this.hex) <= this.pointsDeplacement) {
				trajet = map.pathfinding(this.hex, unite.hex);
				this.setHex(trajet.get(trajet.size() - 1));
				for (Hex voisin : voisins) {
					if (!voisin.isEmpty()) {
						if (!joueur.getUnite().contains(voisin.getUnit())) {
							if (rand > 2) {
								voisin.getUnit().pointsDeVie = (int) (voisin.getUnit().pointsDeVie - (3 * (this.pointsAttaque - voisin.getUnit().pointsDefense)));
							} else {
								voisin.getUnit().pointsDeVie = (int) (voisin.getUnit().pointsDeVie - (3 * (this.pointsAttaque - voisin.getUnit().pointsDefense)));
							}
						}
					}
				}
			}
		}
		this.pointsDeplacement = 0;
	}
}
