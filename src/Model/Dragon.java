package model;

import java.util.ArrayList;
import java.util.List;

public class Dragon extends Unite{

	/* attributs de base */
	private final int puissance = 10; //gros c'est la puissance
	private final int defense = 10; //gros c'est la defense
	private final int deplacement = 7; //fais le mouv, fais le mouv
	private final int vie = 45; //c'est la vie, lalalalala
	private final int vue = 7; //perception humaine des rayonnements lumineux

	public Dragon(Hex hex) {
		super(hex);
		this.pointsAttaque = this.puissance;
		this.pointsDefense = this.defense;
		this.pointsDeplacement = this.deplacement;
		this.pointsDeVie = this.vie;
		this.vision = this.vue;
	}

	public void heal() {
		if (this.pointsDeplacement == deplacement) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
		}
	}

	public void initialize() {
		this.pointsDeplacement = deplacement;
	}

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
			if (unite.hex.getCost() <= this.pointsDeplacement) {
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

	public int getVie() {
		return vie;
	}
}
