package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Unite {

	protected int pointsAttaque;
	protected float pointsDefense;
	protected int pointsDeplacement;
	protected int pointsDeVie;
	protected int vision;
	protected Hex hex;

	public Unite(Hex hex) {
		this.hex = hex;
	}

	public Unite() {
	}

	public void heal() {
	}

	public void initialize() {
	}

	public void combat(HexMap map, Joueur joueur, Unite unite) {
		int rand = (int)(Math.random() * 10);
		List<Hex> trajet = new ArrayList<Hex>();
		if (this.hex.isNeighbour(unite.hex)) {
			if (rand > 2) {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
			} else {
				unite.pointsDeVie = (int) (unite.pointsDeVie - (3 * (this.pointsAttaque - unite.pointsDefense)));
			}
		} else {
			if (unite.hex.getCost() <= this.pointsDeplacement) {
				trajet = map.pathfinding(this.hex, unite.hex);
				this.setHex(trajet.get(trajet.size() - 1));
				if (rand > 2) {
					unite.pointsDeVie = (int) (unite.pointsDeVie - (this.pointsAttaque - unite.pointsDefense));
				} else {
					unite.pointsDeVie = (int) (unite.pointsDeVie - (3 * (this.pointsAttaque - unite.pointsDefense)));
				}
			}
		}
		this.pointsDeplacement = 0;
	}

	public int getPoints() {
		return this.pointsDeVie;
	}

      //IA
		public void joueTour(int tour) {
		}

	public void seDeplace(Hex newHex){
		if(newHex.getCost()<=this.pointsDeplacement) {
			this.setHex(newHex);
		}
	}

	public Hex getHex() {
		return hex;
	}

	public void setHex(Hex hex) {
		this.hex = hex;
	}

	public float getDefense() {
		return pointsDefense;
	}

	public void setDefense(float pointsDefense) {
		this.pointsDefense = pointsDefense;
	}

	public float getPointsDeplacement() {
		return pointsDeplacement;
	}

	public void setPointsDeplacement(int pointsDeplacement) {
		this.pointsDeplacement = pointsDeplacement;
	}

	public int getVie() {
		return 0;
	}

}