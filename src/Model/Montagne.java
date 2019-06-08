package model;

/**
 *  Classe Montagne.
 */
public class Montagne extends Hex {

	/*
	 * Constructeur.
	 */
	Montagne(int newX, int newY) {
		super(newX, newY);
		this.cost = 33;
		this.defense = 40;
		rarity = (float) 0.24;
	}

	/*
	 * Constructeur.
	 */
	Montagne(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
		rarity = (float) 0.24;
	}

	/*
	 * Retour la rareté du biom.
	 * @return rarity float
	 */
	public float getRarity() {
		return rarity;
	}


	/*
	 * Affiche le type de biom en console
	 * (pour les tests console)
	 */
	public void print() {
		System.out.print("M");
	}
}
