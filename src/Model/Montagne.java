package model;

/**
 *  Classe Montagne.
 *  @see Hex
 */
public class Montagne extends Hex {

	/**
	 * Construit un objet de type Montagne.
	 * @param newX int
	 * @param newY int
	 */
	Montagne(int newX, int newY) {
		super(newX, newY);
		this.cost = 33;
		this.defense = 40;
		rarity = (float) 0.24;
	}

	/**
	 * Construit un objet de type Montagne.
	 * @param newX int
	 * @param newY int
	 * @param newZ int
	 */
	Montagne(int newX, int newY, int newZ) {
		super(newX, newY, newZ);
		rarity = (float) 0.24;
	}

	/**
	 * Retour la rareté du biom.
	 * @return rarity float
	 */
	public float getRarity() {
		return rarity;
	}
}
