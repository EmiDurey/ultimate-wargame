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
			for (int i = 0; i < 6; i++) {
				if (!voisins[i].isEmpty()) {
					if (!joueur.getUnite().contains(voisins[i].unit)) {
						if (rand > 2) {
							voisins[i].unit.pointsDeVie = (int) (voisins[i].unit.pointsDeVie - (3 * (this.pointsAttaque - voisins[i].unit.pointsDefense)));
						} else {
							voisins[i].unit.pointsDeVie = (int) (voisins[i].unit.pointsDeVie - (3 * (this.pointsAttaque - voisins[i].unit.pointsDefense)));
						}
					}
				}
			}
		} else {
			if (unite.hex.getCost() <= this.pointsDeplacement) {
				trajet = map.pathfinding(this.hex, unite.hex);
				this.setHex(trajet.get(trajet.size() - 1));
				for (int i = 0; i < 6; i++) {
					if (!voisins[i].isEmpty()) {
						if (!joueur.getUnite().contains(voisins[i].unit)) {
							if (rand > 2) {
								voisins[i].unit.pointsDeVie = (int) (voisins[i].unit.pointsDeVie - (3 * (this.pointsAttaque - voisins[i].unit.pointsDefense)));
							} else {
								voisins[i].unit.pointsDeVie = (int) (voisins[i].unit.pointsDeVie - (3 * (this.pointsAttaque - voisins[i].unit.pointsDefense)));
							}
						}
					}
				}
			}
		}
		this.pointsDeplacement = 0;
	}

	public int getPV() {
		return vie;
	}
}
