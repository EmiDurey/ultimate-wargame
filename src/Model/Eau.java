package model;

/**
 *  Classe Eau.
 *  @see Hex
 */
public class Eau extends Hex {

	/**
	 * Construit un objet de type Eau.
	 * @param newX int
	 * @param newY int
	 */
	Eau(int newX, int newY) {
		super(newX, newY);
		this.cost = -1;
		this.defense = 0;
		rarity = (float) 0.2;
	}

	/**
	 * Construit un objet de type Eau.
	 * @param newX int
	 * @param newY int
	 * @param newZ int
	 */
	Eau(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
		rarity = (float) 0.135;
	}

	/**
	 * Retour la rareté du biom.
	 * @return rarity float
	 */
	public float getRarity() {
		return rarity;
	}

	/**
	 * Affiche le type de biom en console.
	 * (pour les tests console)
	 */
	public void print() {
		System.out.print("W");
	}
}
