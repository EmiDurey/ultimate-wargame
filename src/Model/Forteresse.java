package model;

/**
 *  Classe Forteresse.
 */
public class Forteresse extends Hex {

	/*
	 * Constructeur.
	 */
	Forteresse(int newX, int newY) {
		super(newX, newY);
		this.cost = 15;
		this.defense = 60;
		rarity = (float) 0.1;
	}

	/*
	 * Constructeur.
	 */
	Forteresse(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
		rarity = (float) 0.1;
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
		System.out.print("C");
	}
}
