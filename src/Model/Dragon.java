package model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class Dragon.
 */
public class Dragon extends Unite{

	/**
	 *  Constructeur d'un dragon.
	 *  @param hex Hexagone
	 */
	public Dragon(Hex hex) {
		super(hex);
		hex.setUnit(this);
		this.pointsAttaque = 10;
		this.pointsDefense = 5;
		this.pointsDeplacement = 7;
		this.pointsDeplacementInit = 7;
		this.pointsDeVie = 45;
		this.vision = 7;
		this.pointsDeVieMax = 45;
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

	/**
	 * Méthode combat propre au dragon.
	 * S'il attaque une unité,
	 * attaque toutes les unités ennemis adjacentes en même temps.
	 * @param map Map
	 * @param joueurAct Joueur Actuel
	 * @paral joueurUnit Joueur possédant l'unité
	 * @param unite Unite à attaquer
	 */
	@Override
	public void combat(HexMap map, Joueur joueurAct, Joueur joueurUnit, Unite unite) {
		final int crit = 3;
		final int chanceCrit = 2;
		Hex[] voisins = new Hex[6];
		voisins = unite.hex.getNeighbours();
		int rand = (int)(Math.random() * 10);
		List<Hex> trajet = new ArrayList<Hex>();
		if (this.hex.isNeighbour(unite.hex)) {
			System.out.println("JE SUIS TON VOISIN");
			if (rand > chanceCrit) {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
			} else {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
			}
			for (Hex voisin : voisins) {
				if (map.getHex(voisin.getX(), voisin.getY()).getUnit() != null) {
					System.out.println("JE SUIS LE VOISIN DE TON VOISIN : "+map.getHex(voisin.getX(), voisin.getY()).getUnit());
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
			/*if((!trajet.isEmpty()) && (trajet.get(trajet.size()-2) COUTE < this.pointsDeplacement)) {
			  	this.getHex().setUnit(null);
			 	trajet.get(trajet.size()-2).setUnit(this);
				this.setHex(trajet.get(trajet.size()-2));
				if (rand > chanceCrit) {
					unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
				} else {
					unite.pointsDeVie = (int) (unite.pointsDeVie - (crit * (this.pointsAttaque - unite.pointsDefense)));
				}
				for (Hex voisin : voisins) {
					if (!voisin.isEmpty()) {
						Unite uniteCol = map.getHex(voisin.getX(), voisin.getY()).getUnit();
						if (!joueur.getUnite().contains(voisin.getUnit())) {
							if (rand > chanceCrit) {
								uniteCol.pointsDeVie = (int) (uniteCol.pointsDeVie - (this.pointsAttaque - uniteCol.pointsDefense));
							} else {
								uniteCol.pointsDeVie = (int) (uniteCol.pointsDeVie - (crit * (this.pointsAttaque - uniteCol.pointsDefense)));
							}
						}
					}
				}
			}*/
		}
		if (unite.getPointsDeVie() < 0) {
			joueurUnit.getUnite().remove(unite);
			unite.getHex().setUnit(null);
		}
		this.pointsDeplacement = 0;
	}
}
	
