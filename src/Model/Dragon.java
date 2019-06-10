package model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class Dragon.
 *  @see Hex
 *  @see HexMap
 *  @see Joueur
 *  @see Unite
 */
public class Dragon extends Unite {

	/**
	 *  Constructeur d'un dragon.
	 *  @param hex Hexagone
	 *  @param joueur Joueur
	 *  @see Hex
	 *  @see Joueur
	 */
	public Dragon(Hex hex, Joueur joueur) {
		super(hex, joueur);
		hex.setUnit(this);
		this.pointsAttaque = 20;
		this.pointsDefenseInit = 3;
		this.pointsDefense = this.pointsDefenseInit;
		this.pointsDeplacementInit = 150;
		this.pointsDeplacement = this.pointsDeplacementInit;
		this.pointsDeVieMax = 45;
		this.pointsDeVie = this.pointsDeVieMax;
		this.vision = 8;
	}

	/**
	 *  Constructeur d'un dragon.
	 */
	public Dragon() {
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

	/**
	 * Méthode combat propre au dragon.
	 * S'il attaque une unité,
	 * attaque toutes les unités ennemis adjacentes en même temps.
	 * @param map Map
	 * @param joueurAct Joueur Actuel
	 * @param unite Unite Ã  attaquer
	 * @see Hex
	 * @see HexMap
	 * @see Joueur
	 * @see Unite
	 */
	@Override
	public void combat(HexMap map, Joueur joueurAct, Unite unite) {
		if (hasAttacked) {
			return;
		}

		final int crit = 2;
		final int chanceCrit = 2;
		Hex[] voisins = new Hex[6];
		voisins = unite.hex.getNeighbours();
		int rand = (int) (Math.random() * 10);
		List<Hex> trajet = new ArrayList<Hex>();
		if (this.hex.isNeighbour(unite.hex)) {
			if (rand > chanceCrit) {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
				hasAttacked = true;
			} else {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
				hasAttacked = true;
			}
			for (Hex voisin : voisins) {
				if (map.getHex(voisin.getX(), voisin.getY()).getUnit() != null) {
					Unite uniteCol = map.getHex(voisin.getX(), voisin.getY()).getUnit();
					if (!joueurAct.getUnite().contains(uniteCol)) {
						if (rand > chanceCrit) {
							uniteCol.pointsDeVie = (int) (uniteCol.pointsDeVie - (this.pointsAttaque - uniteCol.pointsDefense));
						} else {
							uniteCol.pointsDeVie = (int) (uniteCol.pointsDeVie - (crit * (this.pointsAttaque - uniteCol.pointsDefense)));
						}
					}
				}
			}
		} else {
			trajet = map.pathfinding(this.hex, unite.hex);
			if ((!trajet.isEmpty()) && (map.moveCost(this.hex, trajet.get(trajet.size() - 2)) <= this.pointsDeplacement) && trajet.get(trajet.size() - 2).getUnit() == null) {
			  	this.getHex().setUnit(null);
			 	trajet.get(trajet.size() - 2).setUnit(this);
				this.setHex(trajet.get(trajet.size() - 2));
				map.reveal(joueurAct, this.hex, this.vision);
				this.setDefense((int) ((float) (this.getHex().getDefense() / 100) * this.pointsDefenseInit + this.pointsDefenseInit));
				if (rand > chanceCrit) {
					unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
				} else {
					unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
				}
				for (Hex voisin : voisins) {
					if (!voisin.isEmpty()) {
						Unite uniteCol = map.getHex(voisin.getX(), voisin.getY()).getUnit();
						if (!joueurAct.getUnite().contains(voisin.getUnit())) {
							if (rand > chanceCrit) {
								uniteCol.pointsDeVie = (int) (uniteCol.pointsDeVie - (this.pointsAttaque - uniteCol.pointsDefense));
							} else {
								uniteCol.pointsDeVie = (int) (uniteCol.pointsDeVie - (crit * (this.pointsAttaque - uniteCol.pointsDefense)));
							}
						}
					}
				}
			}
		}
		if (unite.getPointsDeVie() <= 0) {
			unite.joueur.getUnite().remove(unite);
			unite.getHex().setUnit(null);
		}
		this.pointsDeplacement = 0;
	}
}
