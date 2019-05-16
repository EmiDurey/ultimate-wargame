package model;

import java.util.ArrayList;
import java.util.List;

public class Dragon extends Unite{

	/* attributs de base */
	private final int POWER = 10; //gros c'est la puissance
	private final int DEFENSE = 10; //gros c'est la defense
	private final int MOVE = 7; //fais le mouv, fais le mouv
	private final int PV = 45; //c'est la vie, lalalalala
	private final int VISION = 7;//perception humaine des rayonnements lumineux
	
	public Dragon(Hex hex) {
		super(hex);
		this.pointsAttaque = this.POWER;
		this.pointsDefense = this.DEFENSE;
		this.pointsDeplacement = this.MOVE;
		this.pointsDeVie = this.PV;
		this.vision = this.VISION;
	}
	
	public void heal() {
		if(this.pointsDeplacement==MOVE) {
			this.pointsDeVie = (int) ((float) this.pointsDeVie * 1.15);
		}
	}
	
	public void initialize() {
		this.pointsDeplacement = MOVE;
	}
	
	public void combat(HexMap map, Joueur joueur, Unite unite){
		Hex[] voisins = new Hex[6];
		voisins = unite.hex.getNeighbours();
		int rand = (int)(Math.random() * 10);
		List<Hex> trajet = new ArrayList<Hex>();
		if(this.hex.isNeighbour(unite.hex)) { 
			for(int i = 0;i<6;i++) {
				if(!voisins[i].isEmpty()) {
					if(!joueur.getUnite().contains(voisins[i].unit)) {
						if(rand>2) {
							voisins[i].unit.pointsDeVie = (int) (voisins[i].unit.pointsDeVie - (3*(this.pointsAttaque - voisins[i].unit.pointsDefense)));
						}
						else {
							voisins[i].unit.pointsDeVie = (int) (voisins[i].unit.pointsDeVie - (3*(this.pointsAttaque - voisins[i].unit.pointsDefense)));
						}
					}
				}
			}
		}
		else {
			if(unite.hex.getCost()<=this.pointsDeplacement) {
				trajet = map.pathfinding(this.hex,unite.hex);
				this.setHex(trajet.get(trajet.size()-1));
				for(int i = 0;i<6;i++) {
					if(!voisins[i].isEmpty()) {
						if(!joueur.getUnite().contains(voisins[i].unit)) {
							if(rand>2) {
								voisins[i].unit.pointsDeVie = (int) (voisins[i].unit.pointsDeVie - (3*(this.pointsAttaque - voisins[i].unit.pointsDefense)));
							}
							else {
								voisins[i].unit.pointsDeVie = (int) (voisins[i].unit.pointsDeVie - (3*(this.pointsAttaque - voisins[i].unit.pointsDefense)));
							}
						}
					}
				}
			}
		}
		this.pointsDeplacement = 0;
	}
	
	public int getPV() {
		return PV;
	}
}
